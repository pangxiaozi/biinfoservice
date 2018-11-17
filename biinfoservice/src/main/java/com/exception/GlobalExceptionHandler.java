package com.exception;


import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSONObject;

@ControllerAdvice
public class GlobalExceptionHandler{

	@ResponseBody
	@ExceptionHandler(ProphetException.class)
    public String jsonErrorHandler(HttpServletRequest req,ProphetException ex) throws Exception {
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("resultCode", ex.getErrorCode());
		jsonObject.put("message", ex.getMessage());
		return jsonObject.toJSONString();
    }
}
