package com.web;



import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.service.UnderTakerService;

@RestController
@RequestMapping("/biinfoservice")
public class DataController {
	@Autowired
	private UnderTakerService underTakerService;
	
	@Autowired
	private RedisTemplate<String, Serializable> redisCacheTemplate;
	
    @RequestMapping("/undertaker")
	public String undertaker(HttpServletRequest request) {
    	
    	String apiKey=request.getParameter("apiKey"); 	
    	boolean isApiKey=redisCacheTemplate.opsForSet().isMember("apiKey", apiKey);
    	if(isApiKey==false) {
    		JSONObject jsonObject=new JSONObject();
    		jsonObject.put("resultCode", 100012);
    		jsonObject.put("message", "apiKey不正确，请重新确认查询！");
    		return jsonObject.toJSONString();
    	}
    	String queryNo=request.getParameter("queryNo");
    	String queryName=request.getParameter("queryName");
    	if(Strings.isNullOrEmpty(queryNo)) {
    		JSONObject jsonObject=new JSONObject();
    		jsonObject.put("200001", "对不起，您使用的证件号不是正确的证件号。");
    		return jsonObject.toJSONString();
    	}
    	JSONArray result=underTakerService.query(queryName,queryNo);
    	if(result.size()>=0) {
    		JSONObject jsonObject=new JSONObject();
    		jsonObject.put("result", result);
    		jsonObject.put("message", "查询成功！");
    		jsonObject.put("resultCode", 200);
    		return jsonObject.toJSONString();
    	}
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("resultCode", 404);
		jsonObject.put("message", "查询成功,未查询到数据！");
		return jsonObject.toJSONString();
	}
}
