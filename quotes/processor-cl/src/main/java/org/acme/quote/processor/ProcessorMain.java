package org.acme.quote.processor;

import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;
import io.smallrye.mutiny.Multi;
import org.acme.amqp.model.Quote;
import org.acme.quote.RequestProcessorService;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import javax.inject.Inject;

@QuarkusMain
public class ProcessorMain implements QuarkusApplication {

    @Channel("requests")
    Multi<String> requestStream;

    @Channel("responses")
    Emitter<Quote> quoteEmitter;

    @Inject
    RequestProcessorService processorService;

    @Override
    public int run(String... args) {
        String quoteRequest = requestStream.toUni().await().indefinitely();

        Quote quote = processorService.apply(quoteRequest);

        quoteEmitter.send(quote);
        return 0;
    }
}
