/*
 * Copyright (c) 2021, Hol.
 * All rights reserved.
 */
package com.web.offood.exception;

import com.web.offood.dto.base.BaseResponse;
import com.web.offood.dto.base.ResponseBuilder;
import com.web.offood.dto.constant.ValidateType;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.BindException;

@Slf4j
@ControllerAdvice
public class RestErrorAdvice {

//  @InitBinder
//  public void initBinder(WebDataBinder binder) {
//    StringTrimmerEditor stringtrimmer = new StringTrimmerEditor(true);
//    binder.registerCustomEditor(String.class, stringtrimmer);
//    Object obj = binder.getTarget();
//    if (obj == null) {
//      return;
//    }
//    List<Field> fields = new ArrayList<>(Arrays.asList(obj.getClass().getDeclaredFields()));
//    if (obj.getClass().getSuperclass() != null) {
//      fields.addAll(Arrays.asList(obj.getClass().getSuperclass().getDeclaredFields()));
//    }
//    for (Field field : fields) {
//      field.setAccessible(true);
//      try {
//        Object value = field.get(obj);
//        if (value instanceof String) {
//          field.set(obj, ((String) value).replaceAll("(^\\p{Z}+|\\p{Z}+$)", ""));
//        } else if (value instanceof Collection<?>) {
//          ArrayList list = (ArrayList) value;
//          for (Object el : list) {
//            List<Field> fieldChilds =
//                new ArrayList<>(Arrays.asList(el.getClass().getDeclaredFields()));
//            if (obj.getClass().getSuperclass() != null) {
//              fieldChilds.addAll(Arrays.asList(el.getClass().getSuperclass().getDeclaredFields()));
//            }
//            loop3:
//            for (Field fieldChild : fieldChilds) {
//              fieldChild.setAccessible(true);
//              Object valueChild = fieldChild.get(el);
//              if (valueChild instanceof String) {
//                fieldChild.set(el, ((String) valueChild).replaceAll("(^\\p{Z}+|\\p{Z}+$)", ""));
//              }
//            }
//          }
//        }
//      } catch (IllegalAccessException e) {
//        e.printStackTrace();
//      }
//    }
//  }


  @ExceptionHandler(ServletRequestBindingException.class)
  public ResponseEntity<ResponseBodyException> handleServletRequestBindingException(
      HttpServletRequest req, ServletRequestBindingException e) {
    log.warn(e.getMessage(), e);
    HttpStatus status = HttpStatus.BAD_REQUEST;
    ResponseBodyException body = ResponseBodyException.of(req, e, status);
    return new ResponseEntity<>(body, status);
  }

  @ExceptionHandler(HttpMediaTypeException.class)
  public ResponseEntity<ResponseBodyException> handleHttpMediaTypeException(
      HttpServletRequest req, HttpMediaTypeException e) {
    log.warn(e.getMessage(), e);
    HttpStatus status = HttpStatus.BAD_REQUEST;
    ResponseBodyException body = ResponseBodyException.of(req, e, status);
    return new ResponseEntity<>(body, status);
  }

  @ExceptionHandler(OptimisticLockingFailureException.class)
  public ResponseEntity<ResponseBodyException> handleOptimisticLockingFailureException(
      HttpServletRequest req, OptimisticLockingFailureException e) {
    log.warn(e.getMessage(), e);
    HttpStatus status = HttpStatus.BAD_REQUEST;
    ResponseBodyException body = ResponseBodyException.of(req, e, status);
    body.addMsg(MsgInfo.by(MessageId.OPTIMISTIC_LOCK));
    return new ResponseEntity<>(body, status);
  }

  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<ResponseBodyException> handleAccessDeniedException(
      HttpServletRequest req, AccessDeniedException e) {
    log.warn(e.getMessage());
    HttpStatus status = HttpStatus.UNAUTHORIZED;
    ResponseBodyException body = ResponseBodyException.of(req, e, status);
    return new ResponseEntity<>(body, status);
  }

  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<ResponseBodyException> handleEntityNotFoundException(
      HttpServletRequest req, EntityNotFoundException e) {
    log.warn(e.getMessage(), e);
    HttpStatus status = HttpStatus.BAD_REQUEST;
    ResponseBodyException body = ResponseBodyException.of(req, e, status);
    return new ResponseEntity<>(body, status);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<ResponseBodyException> handleConstraintViolationException(
      HttpServletRequest req, ConstraintViolationException e) {
    log.warn(e.getMessage());
    HttpStatus status = HttpStatus.BAD_REQUEST;
    ResponseBodyException body = ResponseBodyException.of(req, e, status);
    return new ResponseEntity<>(body, status);
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<ResponseBodyException> handleHttpMessageNotReadableException(
      HttpServletRequest req, HttpMessageNotReadableException e) {
    log.warn(e.getMessage());
    HttpStatus status = HttpStatus.BAD_REQUEST;
    ResponseBodyException body = ResponseBodyException.of(req, e, status);
    return new ResponseEntity<>(body, status);
  }


  private ValidateType checkNoIndexFieldError(Object[] arguments) {
    for (Object arg : arguments) {
      if (arg instanceof MessageSourceResolvable) {
        MessageSourceResolvable argMsg = (MessageSourceResolvable) arg;
        if (argMsg.getDefaultMessage().equalsIgnoreCase(ValidateType.GLOBAL_OBJECT_IN_OBJECT)) {
          return ValidateType.GLOBALOBJINOBJ;
        } else if (argMsg
            .getDefaultMessage()
            .equalsIgnoreCase(ValidateType.PRIVATE_OBJECT_IN_OBJECT)) {
          return ValidateType.PRIVATEOBJINOBJ;
        } else if (argMsg.getDefaultMessage().equalsIgnoreCase(ValidateType.SHOW_IN_TITLE)) {
          return ValidateType.SHOWINTITLE;
        }
      }
    }
    return ValidateType.NORMAL;
  }


  @ExceptionHandler(BindException.class)
  public ResponseEntity<ResponseBodyException> handleBindException(
      HttpServletRequest req, BindException e) {

    log.warn(e.getMessage());
    HttpStatus status = HttpStatus.BAD_REQUEST;
    ResponseBodyException body = ResponseBodyException.of(req, e, status);
    return new ResponseEntity<>(body, status);
  }


  @ExceptionHandler(IOException.class)
  public ResponseEntity<ResponseBodyException> handleIoException(
      HttpServletRequest req, IOException e) {
    if (e.getMessage().contains("Broken pipe")) {
      log.info("client connection was unexpected closing. (broken pipe)");
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    } else {
      return handleException(req, e);
    }
  }

  @ExceptionHandler(CannotCreateTransactionException.class)
  public ResponseEntity<ResponseBodyException> handleCannotCreateTransactionException(
      HttpServletRequest req, CannotCreateTransactionException e) {
    log.warn(e.getMessage());
    HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
    ResponseBodyException body = ResponseBodyException.of(req, "db connect failed", status);
    return new ResponseEntity<>(body, status);
  }


  @ExceptionHandler(InvalidDataAccessResourceUsageException.class)
  public ResponseEntity<ResponseBodyException> handleInvalidDataAccessResourceUsageException(
      HttpServletRequest req, InvalidDataAccessResourceUsageException e) {
    log.warn(e.getMessage());
    HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
    ResponseBodyException body = ResponseBodyException.of(req, "db sql error", status);
    return new ResponseEntity<>(body, status);
  }


  @ExceptionHandler(ResourceAccessException.class)
  public ResponseEntity<ResponseBodyException> handleResourceAccessException(
      HttpServletRequest req, ResourceAccessException e) {
    log.warn(e.getMessage());
    HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
    ResponseBodyException body = ResponseBodyException.of(req, "rest connect failed", status);
    return new ResponseEntity<>(body, status);
  }

  @ExceptionHandler(ApiException.class)
  public ResponseEntity<BaseResponse<Void>> handlerApiException(ApiException ex) {

    log.error(" detail : {}", ex.getApiErrorCode().getMessage());

    if (ex.getIsFormat()) {
      String message = String.format(ex.getApiErrorCode().getMessage(), ex.getObjectName());
      return build(ex.getApiErrorCode().getErrorCode(), message, ex.getData());
    }

    if (null != ex.getData()) {
      String message = ex.getApiErrorCode().getMessage();
      return build(ex.getApiErrorCode().getErrorCode(), message, ex.getData());
    }

    if (null != ex.getObjectName()) {
      String message = "";
      if (ex.getIsPrevious() != null && ex.getIsPrevious() == 0) {
        message = ex.getApiErrorCode().getMessage() + " " + ex.getObjectName();
      } else {
        message = ex.getObjectName() + " " + ex.getApiErrorCode().getMessage();
      }

      return build(ex.getApiErrorCode().getErrorCode(), message, null);
    }

    if (null != ex.getObjectValue()) {
      String message = "";
      if (ex.getIsPrevious() != null && ex.getIsPrevious() == 0) {
        message = ex.getApiErrorCode().getMessage() + " " + ex.getObjectValue();
      } else {
        message = ex.getObjectValue() + " " + ex.getApiErrorCode().getMessage();
      }
      return build(ex.getApiErrorCode().getErrorCode(), message, null);
    }

    return build(ex.getApiErrorCode().getErrorCode(), ex.getApiErrorCode().getMessage(), null);
  }

  public ResponseEntity<BaseResponse<Void>> build(Integer errorCode, String message, Object data) {

    return data == null
        ? ResponseEntity.ok().body(ResponseBuilder.ok().with(errorCode, message).build())
        : ResponseEntity.ok()
            .body(ResponseBuilder.ok().with(errorCode, message).with(data).build());
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ResponseBodyException> handleException(
      HttpServletRequest req, Exception e) {

    log.error(e.getMessage(), e);
    HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
    ResponseBodyException body = ResponseBodyException.of(req, e, status);
    return new ResponseEntity<>(body, status);
  }


  @ExceptionHandler(NoHandlerFoundException.class)
  public ResponseEntity<ResponseBodyException> handleNoHandlerFoundException(
      HttpServletRequest req, Exception e) {
    log.error(e.getMessage(), e);
    HttpStatus status = HttpStatus.NOT_FOUND;
    ResponseBodyException body = ResponseBodyException.of(req, e, status);
    return new ResponseEntity<>(body, status);
  }
}
