package com.test.bank.exceptions;

import com.test.bank.domain.dto.ErrorDTO;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/*******************************************************************************
 *
 * @author : <a href="mailto:borislepeshenkov@gmail.com">Boris Lepeshenkov</a>
 * @since : 14.03.2021
 */

@ControllerAdvice
@RequiredArgsConstructor
public class WebErrorHandler {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorDTO(500, ex.getMessage()));
    }

}

