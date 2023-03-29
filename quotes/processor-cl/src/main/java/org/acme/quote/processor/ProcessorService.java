package org.acme.quote.processor;

import jakarta.jms.JMSContext;
import jakarta.jms.Queue;
import jakarta.jms.QueueConnectionFactory;
import org.acme.quote.RequestProcessorService;
import org.acme.quote.model.Quote;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ProcessorService {

    @Inject
    QueueConnectionFactory connectionFactory;

    @ConfigProperty(name = "request.address")
    String requestAddress;

    @ConfigProperty(name = "responseAddress")
    String responseAddress;

    @Inject
    RequestProcessorService processorService;

    public void process(){
        try (JMSContext context = connectionFactory.createContext(JMSContext.AUTO_ACKNOWLEDGE)) {

            Queue requestQueue = context.createQueue(requestAddress);
            Queue responseQueue = context.createQueue(responseAddress);

            String request = context.createConsumer(requestQueue).receiveBody(String.class);

            Quote quote = processorService.apply(request);

            context.createProducer().send(responseQueue, quote);
        }
    }
}
