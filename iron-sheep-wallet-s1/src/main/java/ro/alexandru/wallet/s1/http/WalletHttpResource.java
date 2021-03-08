package ro.alexandru.wallet.s1.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ro.alexandru.wallet.domain.model.Wallet;
import ro.alexandru.wallet.domain.model.event.WalletOperation;
import ro.alexandru.wallet.domain.model.event.WalletOperationError;
import ro.alexandru.wallet.domain.model.event.WalletOperationResult;
import ro.alexandru.wallet.domain.model.event.WalletOperationSuccess;
import ro.alexandru.wallet.messaging.producer.MessageProducerException;
import ro.alexandru.wallet.s1.http.exception.HttpOperationException;
import ro.alexandru.wallet.s1.http.model.UpdateWalletBalanceRequest;
import ro.alexandru.wallet.s1.messaging.WalletOperationMessageBroker;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static ro.alexandru.wallet.domain.model.event.WalletOperationErrorType.WALLET_NOT_FOUND;
import static ro.alexandru.wallet.domain.model.event.WalletOperationType.CREDIT;
import static ro.alexandru.wallet.domain.model.event.WalletOperationType.DEBIT;
import static ro.alexandru.wallet.domain.model.event.WalletOperationType.GET;

@Path("wallet")
public class WalletHttpResource {

    private static final Logger LOG = LoggerFactory.getLogger(WalletHttpResource.class);

    private static final int OK = 200;
    private static final int CREATED = 201;
    private static final int BAD_REQUEST = 400;
    private static final int NOT_FOUND = 404;

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getWalletBalance(@PathParam("id") String walletId) {
        WalletOperation walletOperation = new WalletOperation(GET, walletId, null);
        WalletOperationResult result = sendAndReceive(walletOperation);
        if (result.isError()) {
            int status = result.getError().getType() == WALLET_NOT_FOUND ? NOT_FOUND : BAD_REQUEST;
            return errorResponse(status, result.getError());
        }
        return successResponse(OK, result.getSuccess());
    }

    @POST
    @Path("credit")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response creditWallet(UpdateWalletBalanceRequest creditRequest) {
        WalletOperation walletOperation = new WalletOperation(CREDIT, creditRequest.getId(), creditRequest.getAmount());
        WalletOperationResult result = sendAndReceive(walletOperation);
        if (result.isError()) {
            errorResponse(BAD_REQUEST, result.getError());
        }
        return successResponse(CREATED, result.getSuccess());
    }

    @POST
    @Path("debit")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response debitWallet(UpdateWalletBalanceRequest debitRequest) {
        WalletOperation walletOperation = new WalletOperation(DEBIT, debitRequest.getId(), debitRequest.getAmount());
        WalletOperationResult result = sendAndReceive(walletOperation);
        if (result.isError()) {
            int status = result.getError().getType() == WALLET_NOT_FOUND ? NOT_FOUND : BAD_REQUEST;
            return errorResponse(status, result.getError());
        }
        return successResponse(CREATED, result.getSuccess());
    }

    private WalletOperationResult sendAndReceive(WalletOperation walletOperation) {
        LOG.info("Sending wallet operation request {}", walletOperation);

        try {
            WalletOperationResult result = WalletOperationMessageBroker.getInstance().sendAndReceive(walletOperation);
            LOG.info("Received wallet operation result {}", result);
            return result;
        } catch (MessageProducerException e) {
            throw new HttpOperationException("Error performing wallet operation of type " + walletOperation.getType(), e);
        }
    }

    private Response successResponse(int status, WalletOperationSuccess success) {
        return Response.status(status).entity(new Wallet(success.getWalletId(), success.getBalance())).build();
    }

    private Response errorResponse(int status, WalletOperationError error) {
        return Response.status(status).entity(error).build();
    }
}