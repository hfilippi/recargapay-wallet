package com.hfilippi.wallet.error;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class CustomExceptionHandler {

	@ExceptionHandler(WalletNotFoundException.class)
	public ResponseEntity<ErrorMessage> walletNotFoundExceptionHandler(Exception ex, WebRequest request) {
		return this.buildErrorResponse(ex, request, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(DuplicateWalletException.class)
	public ResponseEntity<ErrorMessage> duplicateWalletExceptionHandler(Exception ex, WebRequest request) {
		return this.buildErrorResponse(ex, request, HttpStatus.CONFLICT);
	}

	@ExceptionHandler(InsufficientFundsException.class)
	public ResponseEntity<ErrorMessage> insufficientFundsExceptionHandler(Exception ex, WebRequest request) {
		return this.buildErrorResponse(ex, request, HttpStatus.CONFLICT);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorMessage> globalExceptionHandler(Exception ex, WebRequest request) {
		return buildErrorResponse(ex, request, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	private ResponseEntity<ErrorMessage> buildErrorResponse(Exception ex, WebRequest request, HttpStatus httpStatus) {
		// @formatter:off
		ErrorMessage errorMessage = ErrorMessage.builder()
				.statusCode(httpStatus.value())
				.message(ex.getMessage())
				.description(request.getDescription(false))
				.timestamp(new Date())
				.build();
		// @formatter:on

		return new ResponseEntity<>(errorMessage, httpStatus);
	}

}
