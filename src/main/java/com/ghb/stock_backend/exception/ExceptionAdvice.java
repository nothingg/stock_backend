package com.ghb.stock_backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import com.ghb.stock_backend.service.StorageException;

@RestControllerAdvice
public class ExceptionAdvice {
	@ExceptionHandler
	@ResponseStatus(HttpStatus.NOT_FOUND)
	String handlerProductNotFound(ProductNotFoundException ex) {
		return ex.getMessage(); 
	}
	
	@ExceptionHandler
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	String handlerStorageException(StorageException ex) {
		return ex.getMessage(); 
	}
	
	@ExceptionHandler
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	String handlerMaxUploadSize(MaxUploadSizeExceededException ex) {
		return "Maximum Upload Size!!"; 
	}
	
	@ExceptionHandler
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	String handlerValidation(ValidationException ex) {
		return ex.getMessage(); 
	}
}













