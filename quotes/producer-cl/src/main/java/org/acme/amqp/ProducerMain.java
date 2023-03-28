package org.acme.amqp;

import io.quarkus.logging.Log;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;
import org.acme.quote.RequestSupplierService;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import javax.inject.Inject;
import java.util.stream.IntStream;

@QuarkusMain
public class ProducerMain implements QuarkusApplication {

    @Inject
    RequestSupplierService service;

    @Channel("requests")
    Emitter<String> quoteRequestEmitter;

    @ConfigProperty(name = "requests.quantity", defaultValue = "1000")
    int quantity;

    @Override
    public int run(String... args) {
        IntStream.range(0, quantity).parallel()
                .forEach(ignored -> {
                    String request = service.get();
                    quoteRequestEmitter.send(request);
                    Log.info("Sent request " + request);
                });
        return 0;
    }
}
