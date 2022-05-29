package com.web.offood.exception;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Slf4j
public class ResponseBodyException {
  private String type = "";
  private String message;
  private Integer status;
  private Integer code = 0;
  private String internal;
  private List<MsgInfo> msgList = new ArrayList<>();
  private Map validMsgList = new HashMap<String, List<String>>();

  public static ResponseBodyException of(HttpServletRequest req, Exception e, HttpStatus status) {
    ResponseBodyException body = new ResponseBodyException();
    log.error(e.getMessage());
    // body.setTitle(e.getMessage());
    body.setStatus(status.value());
    body.setInternal(req.getRequestURI());

    return body;
  }

  public static ResponseBodyException of(
      HttpServletRequest req, String message, HttpStatus status) {
    ResponseBodyException body = new ResponseBodyException();
    log.error(message);
    body.setMessage("Exception");
    body.setStatus(status.value());
    body.setInternal(req.getRequestURI());

    return body;
  }

  public void addMsg(MsgInfo msg) {
    msgList.add(msg);
  }
}
