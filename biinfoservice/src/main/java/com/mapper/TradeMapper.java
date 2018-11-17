package com.mapper;

import java.util.HashMap;
import java.util.List;

public interface TradeMapper {

	public List getAll();
	
	public List<HashMap<String, Object>> getApikeyAllInfo();
	
	public int insertApiTrade(HashMap<String, Object> map);
}
