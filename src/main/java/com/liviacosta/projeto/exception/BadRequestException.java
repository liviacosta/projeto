package com.liviacosta.projeto.exception;
public class BadRequestException extends RuntimeException {

  public BadRequestException(String message) {
    super(message);
  }

}