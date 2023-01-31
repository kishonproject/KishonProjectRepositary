package com.ust.kishon.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice

public class GlobalExceptionHandler {

    @ExceptionHandler(FarmerNotFoundException.class)
    public ResponseEntity<ApiResponse> farmerNotFoundException(FarmerNotFoundException ex){

        String message=ex.getMessage();
        ApiResponse apiResponse = new ApiResponse(message,false);
        return  new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ApiResponse> productNotFoundException(ProductNotFoundException ex){
        String message =ex.getMessage();
        ApiResponse apiResponse = new ApiResponse(message, false);
        return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> handleMethodArgsNotvalodException(MethodArgumentNotValidException ex){
        Map<String,String> resp= new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) ->{
            String fieldName=((FieldError)error).getField();
            String message=error.getDefaultMessage();
            resp.put(fieldName,message);
        });
        return new ResponseEntity<Map<String,String>>(resp,HttpStatus.BAD_REQUEST);
    }


}