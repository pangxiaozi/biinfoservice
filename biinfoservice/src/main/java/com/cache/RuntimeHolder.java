package com.cache;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.mapper.TradeMapper;

@Component
public class RuntimeHolder implements  CommandLineRunner{
	  @Autowired
	  private TradeMapper tradeMapper;
	  
	  @Autowired
	  private RedisTemplate<String, Serializable> redisCacheTemplate;
	  @Override
	  public void run(String... args) throws Exception {
		  this.load();
	  }	  
	  public void load() {
		 List<HashMap<String, Object>>result= tradeMapper.getApikeyAllInfo();
		 for (int i = 0; i < result.size(); i++) {
			 HashMap< String, Object>temp=result.get(i);
			 redisCacheTemplate.opsForSet().add("apiKey", (String)temp.get("oap_user_key"));
		 }
	  }	  
}
