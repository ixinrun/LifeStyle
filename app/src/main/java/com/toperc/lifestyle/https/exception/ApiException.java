package com.toperc.lifestyle.https.exception;

import java.io.IOException;


/**
 * @author Toper-C
 * @date 2018/5/25
 * @description
 */
public class ApiException extends IOException {

  private int code;

  public ApiException(int code, String message) {
    super(message);
    this.code = code;
  }

  public int getCode() {
    return code;
  }
}
