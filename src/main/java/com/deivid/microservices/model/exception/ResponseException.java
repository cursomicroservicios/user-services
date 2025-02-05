package com.deivid.microservices.model.exception;

public record ResponseException(int code, String message, String status) {
}
