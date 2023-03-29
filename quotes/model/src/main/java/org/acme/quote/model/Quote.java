package org.acme.quote.model;

import io.quarkus.runtime.annotations.RegisterForReflection;

import java.io.Serializable;

@RegisterForReflection
public class Quote implements Serializable {

    public String id;
    public int price;

    /**
     * Default constructor required for Jackson serializer
     */
    public Quote() {
    }

    public Quote(String id, int price) {
        this.id = id;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Quote{" +
                "id='" + id + '\'' +
                ", price=" + price +
                '}';
    }
}