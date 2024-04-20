package com.logicsoftware.exceptions.handlers;

import com.logicsoftware.utils.enums.AppStatus;
import com.logicsoftware.utils.request.ErrorResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.Set;
import java.util.stream.Collectors;


@Provider
public class ConstraintViolationHandler implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(ConstraintViolationException exception) {
        Set<ConstraintViolation<?>> constraintViolations = exception.getConstraintViolations();

        ErrorResponse.ErrorResponseBuilder response = ErrorResponse.builder();
        response.status(AppStatus.ERROR);
        response.errors(constraintViolations.stream().collect(Collectors.toMap(ConstraintViolation::getPropertyPath, constraint -> constraint.getMessageTemplate())));
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(response.build()).build();
    }
    
}
