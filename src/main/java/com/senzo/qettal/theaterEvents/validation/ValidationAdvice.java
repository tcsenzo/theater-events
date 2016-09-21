package com.senzo.qettal.theaterEvents.validation;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice(annotations = RestController.class)
public class ValidationAdvice {
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.initDirectFieldAccess();
    }
}
