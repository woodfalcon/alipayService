package com.core.config;

import javax.validation.constraints.NotEmpty;

/**
 * 支付宝支付回调(二维码、H5、网站)
 * @author jian.wang
 *
 */
public final class AliPayConfig {
	
	 /**
     * 私有的默认构造子，保证外界无法直接实例化
     */
    private AliPayConfig(){};
    // 商户appid
    public static String APPID = "2017062407557967";
 	// 私钥 pkcs8格式的
    public static String RSA_PRIVATE_KEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQC1pY8buYWo/qNT1jMkfaE/ORBmv+n8T8RpQsGBCgMJHfO9XemV08BPhSm9/3t1tacWOK0Wc0qLYgWhmNDYmh4nF39eGs1sPCTeOYFlDibWyOJqJrJzXRIwvLipPI29O/D3Gjq22EvzSGZvbwWFVlFfJ8VocvA4J3TBsnu9A8cdrgwYypfRfT6unlpNoCZY/s2rUtoPgQTpZT/G2O303I0nzrEKxkp97AcZlPhhPitQXS5Ha4u598thKgqH4GLy49sytZdIKhr3LdeZM2EaQm4zm5cjPJQK1yNPAWWWZcqhFU8nVcWJ+qPHw3kCmwGXlbap7VfeSlrEqzQUA3TrZK1FAgMBAAECggEAGRvIe1maqzB/nSAXs6yV9GpL5RgUHOddy/sJSm/62XDFd2Ca4DYD+Dn8+iFtjGjOrtiovFi1bizfgGXiQH6VOmwaFAT6bkKmCf3C8BgTsE7WVTwCIMY1tHzxvZr8MLMvbMi4YvvlC7zvQ+oBxluXJeMWe4ifwfNaRUgJqKf0BxsDLV1hvsfoEO8iVJvdmNDcGsZVjn5OVCebWMlo8UNGpYuCfzZsfbpgkzUjdDgfrbT0WOFAA+fqqUKhvBksSMsqjoASNEU6E6RmUtGUIzHRZL9qkDRIfHfLirwuLmLmEvDwBTQDMqjrkP2+HukLpBTX1e/GtNH12Dfmli2yjzMIoQKBgQDhuqqngeeDe4KKIkjVmpIWP5PRXuvp1LKh+ElMmd1JirS7gwPjAnBSis+7q/sdNJgq8l5kN01wXa2DLXjxadVXUWA9I3gtHJjbjd87IeeOEX8mtL0DJTtNXpRI5T+bx6sWOsLVwUkhchpzFKLydO6+GOyAUS3E/w1NBGwy/bAxCQKBgQDOAYgnzq0iWaYFeTCJuS8Oo5ossmUZM+XO01DC4U6P3K4e8Bg1HsS/Kqf/mgD1soO0Wjv0W1vdHSpvmEQb04HGRT17QOKx1cOyrV6iO3GNEEG0SuyggMWheaHnEXuDQ5WZ7xaOfzAamP4/7V7vbMppC1HtfeOuNgn/3sd5oVw1XQKBgQC9s6eZsLWFLTZOqnN1eDqPkgzDJzeyQruBHW1Uf/aVyIkNERUiIbmN1PSxrt4R9FJxvVCQfl2JMmEQ3hVHJX9Sq6PqnbVwjBj2YuuZAWxdW9z1BW3Xh+GnqvWJsYfcflgNDYMSKycvgXoy/sPWaWj+v2l6iT6i0Qo3kKDx1XvOKQKBgBvUzSZDn9RiNDHNG6efoyHhLHUZbNsozcVRezm44rpA4C1B1qNmksIY6zV3AuYuxehZwHvU0+ZdiFwnQBxaSNAWjqXWMEpCTPwYvA5C5aZfKeFHtgxBGXIlUqA7Fmq3eMChMnTx7ffJ75+HXCoQko/6fYkOVvI0TsmIuX5PNHvpAoGAI27/F865g4A8yoT5kmvZi3XV2kx6CDFusXiYKcSBqhTT4+D1/riIS/qWy2JvfyCaazwWcU+40ruolSOlQbH/kx9KxYTW7o8O9+5bY/dAS/yrshZ1ebjjacp7FLy48XwmNfJRkMQt5hr5Uz3sFgxWTYSsyMyyDOtdFWcIvC6Baqc=";
 	// 服务器异步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
 	public static String notify_url = "http://www.cloudticket-founder.com/alipay/alipayNotifyUrl";
 	// 页面跳转同步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问 商户可以自定义同步跳转地址
 	public static String return_url = "http://www.cloudticket-founder.com/alipay/alipayReturnUrl";
 	//tvm支付成功回调地址
 	public static String tvmQrNotifyUrl = "http://www.cloudticket-founder.com/TvmQrPurchase/tvmQrNotifyUrl";
 	// 请求网关地址
 	public static String URL = "https://openapi.alipay.com/gateway.do";
 	// 编码
 	public static String CHARSET = "UTF-8";
 	// 返回格式
 	public static String FORMAT = "json";
 	// 支付宝公钥
 	public static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAgvG20LBCGrNEssjFIhku79QociXK3C9uLeva4m7UgCXX6ppopO7GvyVpWOJBTuecSpH3nLArS/CtxPemPUMDCZH+X/f+lfrDMPpWb28uu7kTbqb/mnWSBzFs0gXKCXXtVmTKogfy2u32RWQ5iT5WizKdo7BiPtZ0X+azNq2uttOmSQRwUSyunmDxadYokzCP5zH4J0oAIoIMTqW5V68tL4ifjBZY5A/mGwJZUC5eRwnDRAMm5mfKYaxPtN5r95yGBF26IXrlE5m9mkj6uEV2B61RKpVqI8BS1nVk+ATm0zdsZb2t8Cjrfsp9MjXvjvHgAQ4dP7UojHkcPLttpSdfvwIDAQAB";
 	// 日志记录目录
 	public static String log_path = "/alipaylogs";
 	// RSA2
 	public static String SIGNTYPE = "RSA2";
 	//授权回调地址
	public static String redirectUri = "http://www.cloudticket-founder.com/alipay/pay";
	//接口权限值scope
	public static String scope = "auth_base";
	//商品名称
	public static String subject = "购票测试";
	//商品描述
	public static String body = "互联网购票测试";
	//订单关闭时间
	public static String timeoutExpress = "30m";
	//产品码
	public static String productCode = "QUICK_WAP_WAY";
}
