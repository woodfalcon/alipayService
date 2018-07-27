package com.core.dao.alipay;



import java.util.HashMap;
import java.util.List;

import com.core.common.model.AlipayInfo;
import com.core.model.demo.UserDomain;


public interface AlipayDao {


    List<AlipayInfo> selectAlipayInfo(HashMap<String, String> map);
    List<AlipayInfo> selectZfbUserInfo(HashMap<String, String> map);

	void addAlipayInfo(HashMap<String, String> map);
	void addZfbUsrInfo(HashMap<String, String> map);
	
	void updateUserId(HashMap<String, String> map);
	//更新支付状态
	void updateZfbState(HashMap<String, String> map);
}