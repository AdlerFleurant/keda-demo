package org.acme.quote.processor;


import org.acme.quote.RequestProcessorService;
import org.acme.quote.model.Quote;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.Queue;

@Singleton
public class ProcessorService {

    @Inject
    ConnectionFactory connectionFactory;

    @ConfigProperty(name = "request.address")
    String requestAddress;

    @ConfigProperty(name = "response.address")
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
