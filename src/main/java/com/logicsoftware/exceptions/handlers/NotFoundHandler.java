package com.logicsoftware.exceptions.handlers;

import com.logicsoftware.utils.enums.AppStatus;
import com.logicsoftware.utils.request.ErrorResponse;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.Map;

@Provider
public class NotFoundHandler implements ExceptionMapper<NotFoundException> {

    @Override
    public Response toResponse(NotFoundException exception) {
        ErrorResponse.ErrorResponseBuilder response = ErrorResponse.builder();
        response.status(AppStatus.ERROR);
        response.exception(exception.getClass());
        response.errors(Map.of("message", exception.getMessage()));
        return Response.status(Response.Status.NOT_FOUND).entity(response.build()).build();
    }
}
