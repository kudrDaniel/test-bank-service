package ru.duckcoder.bankservice.exception;

import ru.duckcoder.bankservice.model.Mappable;

import static java.lang.String.format;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(
            Class<? extends Mappable> resourceClass,
            String resourceName,
            Object resourceId
    ) {
        super(format("%s with %s:%s not found.", resourceClass.getSimpleName(), resourceName, resourceId));
    }
}
