package org.acme.quote;

import io.quarkus.logging.Log;
import org.acme.quote.model.Quote;

import javax.enterprise.context.Dependent;
import java.util.Random;
import java.util.function.Function;

@Dependent
public class RequestProcessorService implements Function<String, Quote> {

    private final Random random = new Random();

    @Override
    public Quote apply(String request) {
        Log.info("Processing request " + request);
        try {
            Thread.sleep(200);
        } catch (InterruptedException ignored) {
        }

        return new Quote(request, random.nextInt(100));
    }
}
