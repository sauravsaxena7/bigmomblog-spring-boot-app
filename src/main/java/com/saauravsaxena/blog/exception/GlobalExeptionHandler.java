package com.saauravsaxena.blog.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.saauravsaxena.blog.apiresponse.ApiResponse;

@ControllerAdvice
@RestControllerAdvice
public class GlobalExeptionHandler {
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException e){
		String message = e.getMessage();
		ApiResponse apiResponse = new ApiResponse(404,true,message);
		
		return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String,String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
		Map<String,String> res = new HashMap<>();
		e.getBindingResult().getAllErrors().forEach((error)->{
			String fieldName =((FieldError)(error)).getField();
			String message = error.getDefaultMessage();
			
			res.put(fieldName, message);
		});
		
		return new ResponseEntity<Map<String,String>> (res,HttpStatus.BAD_REQUEST);
		
	}
	
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<ApiResponse> handleDataIntegrityViolationException(DataIntegrityViolationException e){
		String message = e.getMessage();
		ApiResponse apiResponse = new ApiResponse(409,true,message);
		
		return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.NOT_FOUND);	
	}
	
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<ApiResponse> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e){
		String message = "This HttpRequest "+e.getMessage() + " On This Url Path";
		ApiResponse apiResponse = new ApiResponse(405,true,message);
		
		return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.NOT_FOUND);
			
	}
	
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ApiResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException e){
		String message = e.getMessage();
		ApiResponse apiResponse = new ApiResponse(400,true,message);
		
		return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.NOT_FOUND);
			
	}

	@ExceptionHandler(ApiException.class)
	public ResponseEntity<ApiResponse> handleApiException(ApiException e){
		String message = e.getMessage();
		ApiResponse apiResponse = new ApiResponse(400,true,message);
		
		return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.BAD_REQUEST);
			
	}

}
