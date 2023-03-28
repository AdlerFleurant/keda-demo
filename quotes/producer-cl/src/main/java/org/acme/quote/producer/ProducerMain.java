package org.acme.quote.producer;

import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;

import javax.inject.Inject;

@QuarkusMain
public class ProducerMain implements QuarkusApplication {

    @Inject
    ProducerService service;

    @Override
    public int run(String... args) {
        service.run();
        return 0;
    }
}
