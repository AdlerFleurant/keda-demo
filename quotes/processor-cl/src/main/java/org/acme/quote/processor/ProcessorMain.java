package org.acme.quote.processor;

import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;

import javax.inject.Inject;

@QuarkusMain
public class ProcessorMain implements QuarkusApplication {

    @Inject
    ProcessorService service;

    @Override
    public int run(String... args) {
        service.process();
        return 0;
    }
}
