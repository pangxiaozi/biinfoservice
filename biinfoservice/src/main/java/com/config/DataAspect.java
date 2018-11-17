package com.config;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.exception.ProphetException;
import com.google.common.base.Strings;
import com.mapper.TradeMapper;
import com.utils.IPUtils;
import com.utils.LocalDataUtils;

@Aspect
@Component
public class DataAspect {
    private Logger logger = LoggerFactory.getLogger(DataAspect.class);
    ThreadLocal<Long> startTime = new ThreadLocal<>();
    @Autowired
    private TradeMapper tradeMapper;
    
    
    @Pointcut("execution(public * com.web..*.*(..))")
    public void webLog(){}
    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        // 接收到请求
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        startTime.set(System.currentTimeMillis());
        // 记录下请求内容
        logger.info("URL : " + request.getRequestURL().toString());
        logger.info("HTTP_METHOD : " + request.getMethod());
        logger.info("IP : " + request.getRemoteAddr());
        logger.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        logger.info("ARGS : " + Arrays.toString(joinPoint.getArgs()));

    	String apiKey=request.getParameter("apiKey"); 	
    	if(Strings.isNullOrEmpty(apiKey)) {
    		tradeMapper.insertApiTrade(getParams("100012",request));
    		throw new ProphetException("100012", "apiKey不能为空！");
    	}
    }

    @AfterReturning(returning = "ret", pointcut = "webLog()")
    public void doAfterReturning(Object ret) throws Throwable {
        // 处理完请求，返回内容
        logger.info("RESPONSE : " + ret);
        logger.info("SPEND TIME : " + (System.currentTimeMillis() - startTime.get()));
    }
    
    
    public static HashMap<String, Object> getParams(String code,HttpServletRequest request){
		HashMap<String, Object>map=new HashMap<String, Object>(30);
		map.put("TABLE_DATE", LocalDataUtils.dateToString("yyyyMM"));
		map.put("USER_ID", "");
		map.put("API_ID", "");
		map.put("START_TIME",new Date());
		map.put("IS_SUCCESS", code);
		map.put("REQUEST_PARAMS", "");
		map.put("REQUEST_TYPE", "json");
		map.put("RESPONSE_DATA_TYPE", "json");
		map.put("API_KEY", "");
		map.put("REQUEST_IP",IPUtils.getIpAddress(request));
		map.put("ID", UUID.randomUUID().toString());
		map.put("END_TIME",new Date());
		map.put("QUERY_NO","");
		map.put("QUERY_NAME","");
		map.put("RESPONSE_NAME","");
		map.put("RESPONSE_NO","");
		map.put("FROMQUERY","");

    	return map;
    }
}
