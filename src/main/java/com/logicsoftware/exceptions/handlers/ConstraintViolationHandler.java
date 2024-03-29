package com.logicsoftware.exceptions.handlers;

import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.logicsoftware.utils.enums.AppStatus;
import com.logicsoftware.utils.request.ErrorResponse;


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
