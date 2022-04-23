package com.bits.springjwt.payload.response;

public class BitsMessageResponse {
  private String message;

  public BitsMessageResponse(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
