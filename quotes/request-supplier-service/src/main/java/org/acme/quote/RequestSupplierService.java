package org.acme.quote;

import javax.enterprise.context.Dependent;
import java.util.UUID;
import java.util.function.Supplier;

@Dependent
public class RequestSupplierService implements Supplier<String> {

    @Override
    public String get() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
}
