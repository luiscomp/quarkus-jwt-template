package com.logicsoftware.exceptions.handlers;

import com.logicsoftware.utils.enums.AppStatus;
import com.logicsoftware.utils.request.ErrorResponse;
import io.quarkus.arc.log.LoggerName;
import io.quarkus.hibernate.validator.runtime.jaxrs.ResteasyViolationExceptionImpl;
import io.quarkus.security.ForbiddenException;
import io.quarkus.security.UnauthorizedException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Path.Node;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.jboss.logging.Logger;
import org.jboss.resteasy.spi.ApplicationException;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Provider
public class ApplicationHandler implements ExceptionMapper<ApplicationException> {

    @LoggerName("application-handler")
    Logger logger;

    @Override
    public Response toResponse(ApplicationException exception) {
        ErrorResponse.ErrorResponseBuilder response = ErrorResponse.builder();
        Response.Status status = Response.Status.INTERNAL_SERVER_ERROR;

        Throwable rootCause = getRootException(exception);
        response.status(AppStatus.ERROR);
        response.exception(rootCause.getClass());
        response.errors(Map.of("message", Objects.isNull(rootCause.getMessage()) ? "Erro interno" : rootCause.getMessage()));

        if(rootCause.getClass().equals(ResteasyViolationExceptionImpl.class)) {
            ResteasyViolationExceptionImpl error = (ResteasyViolationExceptionImpl) rootCause;
            response.errors(error.getConstraintViolations().stream().collect(Collectors.toMap(this::getFieldPath, ConstraintViolation::getMessageTemplate)));
        }

        if(rootCause.getClass().equals(NotFoundException.class)) {
            status = Response.Status.NOT_FOUND;
        }

        if(rootCause.getClass().equals(UnauthorizedException.class)) {
            status = Response.Status.UNAUTHORIZED;
        }

        if(rootCause.getClass().equals(ForbiddenException.class)) {
            status = Response.Status.FORBIDDEN;
            response.errors(Map.of("message", "Você não tem permissão para acessar este recurso"));
        }

        logger.error(exception.getMessage(), exception);

        return Response.status(status).entity(response.build()).build();
    }

    private Throwable getRootException(Throwable exception) {
        Objects.requireNonNull(exception);
        Throwable rootCause = exception;
        while (rootCause.getCause() != null && rootCause.getCause() != rootCause) {
            rootCause = rootCause.getCause();
        }
        return rootCause;
    }

    private String getFieldPath(ConstraintViolation<?> constraint) {
        StringBuilder path = new StringBuilder();
        Iterator<Node> iterator = constraint.getPropertyPath().iterator();
        iterator.next();
        while (iterator.hasNext()) {
            Node node = iterator.next();
            if (node.getName() != null) {
                path.append(node.getName());
                if(iterator.hasNext()) {
                    path.append(".");
                }
            }
        }
        return path.toString();
    }


}


