package org.acme.quote;

import javax.enterprise.context.RequestScoped;
import java.util.UUID;
import java.util.function.Supplier;

@RequestScoped
public class RequestSupplierService implements Supplier<String> {

    @Override
    public String get() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
}
