package com.toperc.lifestyle.https.exception;

import java.io.IOException;

/**
 * @author Toper-C
 * @date 2018/5/25
 * @description
 */
public class TokenExpiredException extends IOException {
  private int code;
  private String message;

  public TokenExpiredException(int code, String message) {
    this.code = code;
    this.message = message;
  }

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  @Override
  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
