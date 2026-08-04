package com.cvn.vaccination.client;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.cvn.vaccination.exception.ForbiddenException;
import com.cvn.vaccination.exception.InvalidRequestException;
import com.cvn.vaccination.exception.ResourceNotFoundException;
import com.cvn.vaccination.exception.ServiceCommunicationException;
import com.cvn.vaccination.exception.UnauthorizedException;

import feign.Response;
import feign.codec.ErrorDecoder;

public class FeignErrorDecoder implements ErrorDecoder {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Exception decode(String methodKey, Response response) {
        String message = "Unknown error.";
        try {
            if (response.body() != null) {
                FeignErrorResponse error = objectMapper.readValue(
                                response.body().asInputStream(), FeignErrorResponse.class);
                message = error.getMessage();
            }
        }
        catch (IOException e) {
            message = "Unable to parse error response.";
        }
        return switch (response.status()) {
            case 400 -> new InvalidRequestException(message);

            case 401 -> new UnauthorizedException(message);

            case 403 -> new ForbiddenException(message);

            case 404 -> new ResourceNotFoundException(message);

            default -> new ServiceCommunicationException(message);
        };
    }

}