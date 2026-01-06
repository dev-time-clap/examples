package de.devtime.app.example.api.contract.dto;

import lombok.Data;

@Data
public class ErrorDto {

  private String errorCode;
  private String errorMessage;
  private Throwable cause;

}
