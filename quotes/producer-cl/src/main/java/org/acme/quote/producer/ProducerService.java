package org.acme.quote.producer;

import io.quarkus.logging.Log;
import org.acme.quote.RequestSupplierService;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.concurrent.CompletionStage;
import java.util.stream.IntStream;

@Singleton
public class ProducerService implements Runnable {

    @Inject
    RequestSupplierService service;

    @Channel("requests")
    Emitter<String> quoteRequestEmitter;

    @ConfigProperty(name = "requests.quantity", defaultValue = "1000")
    int quantity;

    @Override
    public void run() {
        IntStream.range(0, quantity)
                .parallel()
                .mapToObj(index -> {
                    String request = service.get();
                    CompletionStage<Void> stage = quoteRequestEmitter.send(request);
                    Log.info("Sent request " + index + " " + request);
                    return stage;
                }).forEach(stage -> stage.toCompletableFuture().join());

        quoteRequestEmitter.complete();
    }
}
