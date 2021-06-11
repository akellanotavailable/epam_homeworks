package com.epam.expositions.controller;

import com.epam.expositions.exception.InvalidDataException;
import com.epam.expositions.exception.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    public static final String DEFAULT_ERROR_VIEW = "error/internalerror";
    public static final String BAD_REQUEST_ERROR_VIEW = "error/baddata";
    public static final String NO_ACCESS_ERROR_VIEW = "error/noaccess";

    @ExceptionHandler(InvalidDataException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Unexpected data received.")
    public ModelAndView invalidDataErrorHandler(HttpServletRequest req, InvalidDataException e) {
        log.warn(e.getMessage(), e);
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", e);
        mav.addObject("url", req.getRequestURL());
        mav.setViewName(BAD_REQUEST_ERROR_VIEW);
        return mav;
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "You have no access to this page.")
    public ModelAndView accessDeniedErrorHandler(HttpServletRequest req, AccessDeniedException e) {
        log.warn(e.getMessage(), e);
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", e);
        mav.addObject("url", req.getRequestURL());
        mav.setViewName(NO_ACCESS_ERROR_VIEW);
        return mav;
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "User was not found.")
    public ModelAndView userNotFoundErrorHandler(HttpServletRequest req, UserNotFoundException e) {
        log.warn(e.getMessage(), e);
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", e);
        mav.addObject("url", req.getRequestURL());
        mav.setViewName(BAD_REQUEST_ERROR_VIEW);
        return mav;
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception e) {
        log.error(e.getMessage(), e);
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", e);
        mav.addObject("url", req.getRequestURL());
        mav.setViewName(DEFAULT_ERROR_VIEW);
        return mav;
    }

}