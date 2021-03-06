package ro.alexandru.wallet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("balance")
public class WalletBalanceResource {

    private static final Logger LOG = LoggerFactory.getLogger(WalletBalanceResource.class);

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response getBalance() {
        LOG.info("Getting balance");
        Sql2o sql2o = new Sql2o("jdbc:postgresql://localhost:5434/iron_sheep_wallet", "iron_sheep", "iron_sheep");
        List<Wallet> wallets;
        try (Connection con = sql2o.open()) {
            wallets = con.createQuery("SELECT * FROM iron_sheep_wallet.wallet").executeAndFetch(Wallet.class);
        }
        if (wallets.isEmpty()) {
            return Response.status(303).entity("").build();
        }
        return Response.status(200).entity(wallets.get(0).getBalance().toString()).build();
    }
}