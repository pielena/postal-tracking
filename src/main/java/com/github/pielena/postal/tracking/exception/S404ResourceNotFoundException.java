package com.github.pielena.postal.tracking.exception;

import lombok.NonNull;
import org.springframework.http.HttpStatus;

import java.util.UUID;

public class S404ResourceNotFoundException extends RestException {


    public S404ResourceNotFoundException(@NonNull Class<?> resourceClass, @NonNull UUID id) {
        super(resourceClass.getSimpleName() + " with Id: " + id + " not found", HttpStatus.NOT_FOUND);
    }

    public S404ResourceNotFoundException(@NonNull Class<?> resourceClass, @NonNull Integer id) {
        super(resourceClass.getSimpleName() + " with Id: " + id + " not found", HttpStatus.NOT_FOUND);
    }

    public S404ResourceNotFoundException(@NonNull Class<?> itemClass, @NonNull String name) {
        super(itemClass.getSimpleName() + " with name: " + name + " not found", HttpStatus.NOT_FOUND);
    }
}
