package ru.duckcoder.bankservice.exception;

import ru.duckcoder.bankservice.model.Mappable;

import java.util.Map;
import java.util.stream.Collectors;

import static java.lang.String.format;

public class ResourceAlreadyExistsException extends RuntimeException {
    public ResourceAlreadyExistsException(Class<? extends Mappable> resourceClass, Map<String, String> fields) {
        super(format("%s with [%s] already exists.", resourceClass, fieldsToString(fields)));
    }
    private static String fieldsToString(Map<String, String> fields) {
        return fields.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .map(entry -> format("%s:%s", entry.getValue(), entry.getKey()))
                .collect(Collectors.joining(", "));
    }
}
