package ru.sadv1r.cloud.correlation.generator;

import java.util.UUID;

public class DefaultRequestIdGenerator implements RequestIdGenerator {

    @Override
    public String generateCorrelationId() {
        return UUID.randomUUID().toString();
    }

}
