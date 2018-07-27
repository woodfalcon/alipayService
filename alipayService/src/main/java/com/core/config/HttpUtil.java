package com.core.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;


import net.sf.json.JSONObject;


public class HttpUtil {
	 
/**
 * post请求
 */
	  @Test
	    public static String doPost(String uri,Map<String,Object> map) {
		  
	        try {
	            CloseableHttpClient client = null;
	            CloseableHttpResponse response = null;
	            try {
	                // 创建一个提交数据的容器
	                List<BasicNameValuePair> parames = new ArrayList<>();
	                for(String key : map.keySet()){
	     			   String value = map.get(key).toString();
	     			  parames.add(new BasicNameValuePair(key,value));
	     			  }
	               
	                HttpPost httpPost = new HttpPost(uri);
	                httpPost.setEntity(new UrlEncodedFormEntity(parames, "UTF-8"));

	                client = HttpClients.createDefault();
	                response = client.execute(httpPost);
	                HttpEntity entity = response.getEntity();
	                String result = EntityUtils.toString(entity);
	                System.out.println(">>>>>result》》》"+result+"!!!!!");
	                return  result;
	            } finally {
	                if (response != null) {
	                    response.close();
	                }
	                if (client != null) {
	                    client.close();
	                }
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	            return e.toString();
	        }
			
	    }
	public static void main(String[] args) {
		String uri = "http://172.18.119.157:8082/alipay/signVerified";
		String str="{charset='UTF-8', out_trade_no='TvmQrPurchase20180611192837100101pq5vsx', method='alipay.trade.wap.pay.return', total_amount='0.01', sign='DqluqWcqb0pYECtkS6isLGLtImD1hlYsi3oPOZL9+laK1IIQx13NJICHGc56pqRlRg7WjGvA8yC2jE+mg2RH3NLVE/k8k2IIv0ymbfekF/Fc2xG1WQ7zlNMaHP5+ARWKA+RFpzW6htpY37Td7kIzeOPDNX3rrPG15Phqp7I3DKevKHtbknEe3YRAvStZN9FDnSaCziYhjV45gWIpzfRJu9RoUErgIXjqBwNccY3Xq6cJHH+lPKFdb11QFjmLShCfEPkjnoBiko8nMpa9fiB5FSSHWbVj1t4PYmxN8rGLOYvmbB1FAxmVeqwVIvUGJXj04ZSEcOFj9QObj4WUL78Utw==', trade_no='2018061121001004530554284701', auth_app_id='2017062407557967', sign_type='RSA2', app_id='2017062407557967', version='1.0', seller_id='2088721085094677', timestamp='2018-06-11 19:29:04'}";
		JSONObject rtInfo = JSONObject.fromObject(str);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("verifyInfo", rtInfo);
		
		doPost(uri, map);
		
		
		
		
	}
}