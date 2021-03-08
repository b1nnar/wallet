package ro.alexandru.wallet.s1.http;

import org.apache.commons.lang3.exception.ExceptionUtils;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ExceptionHandler implements ExceptionMapper<Exception> {

    private static final int INTERNAL_SERVER_ERROR = 500;

    @Override
    public Response toResponse(Exception e) {
        return Response.status(INTERNAL_SERVER_ERROR)
                .entity(ExceptionUtils.getStackTrace(e))
                .type(MediaType.TEXT_PLAIN)
                .build();
    }
}
