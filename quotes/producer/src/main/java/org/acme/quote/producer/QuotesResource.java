package org.acme.quote.producer;

import io.quarkus.logging.Log;
import io.smallrye.mutiny.Multi;
import org.acme.quote.model.Quote;
import org.acme.quote.RequestSupplierService;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/quotes")
public class QuotesResource {

    @Channel("requests")
    Emitter<String> quoteRequestEmitter;
    @Channel("responses")
    Multi<Quote> quotes;

    @Inject
    RequestSupplierService requestCreationService;

    /**
     * Endpoint retrieving the "quotes" queue and sending the items to a server sent event.
     */
    @GET
    @Produces(MediaType.SERVER_SENT_EVENTS)
    public Multi<Quote> stream() {
        Log.info("Returning the Stream. I have no idea if it's empty but it was requested");
        return quotes;
    }

    /**
     * Endpoint to generate a new quote request id and send it to "quote-requests" AMQP queue using the emitter.
     */
    @POST
    @Path("/request")
    @Produces(MediaType.TEXT_PLAIN)
    public String createRequest() {
        String request = requestCreationService.get();
        quoteRequestEmitter.send(request);
        return request;
    }
}