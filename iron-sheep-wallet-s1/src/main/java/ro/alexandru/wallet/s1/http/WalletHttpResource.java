package ro.alexandru.wallet.s1.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ro.alexandru.wallet.domain.model.Wallet;
import ro.alexandru.wallet.domain.model.event.WalletOperation;
import ro.alexandru.wallet.domain.model.event.WalletOperationType;
import ro.alexandru.wallet.messaging.producer.KafkaMessageProducer;
import ro.alexandru.wallet.messaging.producer.KafkaMessageProducerConfig;
import ro.alexandru.wallet.messaging.producer.MessageProducer;
import ro.alexandru.wallet.messaging.producer.MessageProducerException;
import ro.alexandru.wallet.messaging.serializer.JSONSerializer;
import ro.alexandru.wallet.s1.http.exception.HttpOperationException;
import ro.alexandru.wallet.s1.http.model.UpdateWalletBalanceRequest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;

@Path("wallet")
public class WalletHttpResource {

    private static final Logger LOG = LoggerFactory.getLogger(WalletHttpResource.class);

    private static final MessageProducer<WalletOperation> MESSAGE_PRODUCER = new KafkaMessageProducer<>(
            new KafkaMessageProducerConfig(
                    "localhost:9092", "s1-producer", "TOPIC.S1.01"
            ),
            new JSONSerializer<>()
    );

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getWalletBalance(@PathParam("id") String walletId) {
        WalletOperation walletOperation = new WalletOperation(WalletOperationType.GET, walletId, null);
        sendWalletOperationEvent(walletOperation);
        return Response.status(200).entity(new Wallet("todo", BigDecimal.TEN)).build();
    }

    @POST
    @Path("credit")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response creditWallet(UpdateWalletBalanceRequest creditRequest) {
        WalletOperation walletOperation = new WalletOperation(WalletOperationType.CREDIT, creditRequest.getId(), creditRequest.getAmount());
        sendWalletOperationEvent(walletOperation);
        return Response.status(201).build();
    }

    @POST
    @Path("debit")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response debitWallet(UpdateWalletBalanceRequest debitRequest) {
        WalletOperation walletOperation = new WalletOperation(WalletOperationType.DEBIT, debitRequest.getId(), debitRequest.getAmount());
        sendWalletOperationEvent(walletOperation);
        return Response.status(201).build();
    }

    private void sendWalletOperationEvent(WalletOperation walletOperation) {
        try {
            MESSAGE_PRODUCER.send(walletOperation);
        } catch (MessageProducerException e) {
            throw new HttpOperationException("Error performing wallet operation of type " + walletOperation.getType(), e);
        }
    }
}