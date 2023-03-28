package org.acme.quote.processor;

import io.smallrye.mutiny.Multi;
import org.acme.quote.model.Quote;
import org.acme.quote.RequestProcessorService;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ProcessorService {

    @Channel("requests")
    Multi<String> requestStream;

    @Channel("responses")
    Emitter<Quote> quoteEmitter;

    @Inject
    RequestProcessorService processorService;

    public void process(){
        String quoteRequest = requestStream.toUni().await().indefinitely();

        Quote quote = processorService.apply(quoteRequest);

        quoteEmitter.send(quote);
    }
}
