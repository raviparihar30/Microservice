package com.rv.accounts.exception;

public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(String resourceName, String fieldName, String feildValue)
    {
        super(String.format("%s not found with the given input data %s : '%s'",resourceName, fieldName,feildValue));
    }
}
