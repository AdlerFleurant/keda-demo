package org.acme.quote.processor;

import io.smallrye.reactive.messaging.annotations.Blocking;
import org.acme.quote.model.Quote;
import org.acme.quote.RequestProcessorService;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

/**
 * A bean consuming data from the "request" AMQP queue and giving out a random quote.
 * The result is pushed to the "quotes" AMQP queue.
 */
@ApplicationScoped
public class QuoteProcessor {
    @Inject
    RequestProcessorService processorService;

    @Incoming("requests")
    @Outgoing("responses")
    @Blocking
    public Quote process(String quoteRequest) {
        return processorService.apply(quoteRequest);
    }
}