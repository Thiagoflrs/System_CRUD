package com.devsuperior.desafio.controllers.handllers;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.devsuperior.desafio.dto.CustomError;
import com.devsuperior.desafio.dto.ValidationError;
import com.devsuperior.desafio.services.exceptions.ResourceNotFoundException;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ControllerExceptionHandller {
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<CustomError> resourceNotFound(ResourceNotFoundException e, HttpServletRequest request ) {
		HttpStatus status = HttpStatus.NOT_FOUND; /*Erro 404*/
		CustomError err = new CustomError(Instant.now(), status.value(), e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<CustomError> methodArgumentNotValidation(MethodArgumentNotValidException e, HttpServletRequest request ) {
		HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY; /*Erro 422*/
		ValidationError err = new ValidationError(Instant.now(), status.value(),"Dados inválidos", request.getRequestURI());
		for (FieldError f : e.getBindingResult().getFieldErrors()) { /*Retorna a lista de erros passadas nas anotações*/
			err.addError(f.getField(), f.getDefaultMessage());
		}
		return ResponseEntity.status(status).body(err);
	}

}
