package ro.alexandru.wallet.rest;

import org.apache.commons.lang3.exception.ExceptionUtils;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ExceptionHandler implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception e) {
        return Response.status(500)
                .entity(ExceptionUtils.getStackTrace(e))
                .type(MediaType.TEXT_PLAIN)
                .build();
    }
}
