package com.uuwatch.spider.manager.util;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.uuwatch.core.download.model.FetchRequest;
import com.uuwatch.core.download.model.FetchResponse;
import com.uuwatch.core.ice.DownloadRemoteServicePrx;
import com.uuwatch.core.model.automatic.MediaEntry;
import com.uuwatch.core.proxy.model.ProxyModel;
import com.uuwatch.spider.manager.ice.IceFactory;

/**
 * 下载模块调用共用方法类
 * @author 66-sunzhiguo
 *
 */
public class DownloadUtil {
	
	private static DownloadRemoteServicePrx downloadRemoteServicePrx = IceFactory.getDownloadRemoteServicePrx();
	
	/**
	 * 根据url下载
	 * @param downloadRemoteServicePrx
	 * @param url
	 * @return 下载成功的 json串 ,用 fastjosn工具转换 
	 */
	public static FetchResponse download(String url){
		
		return download(url, "");
	}
	
	/**
	 * 根据url和字符集进行下载
	 * @param downloadRemoteServicePrx
	 * @param url
	 * @param charset
	 * @return 下载成功的 json串 ,用 fastjosn工具转换 
	 */
	public static FetchResponse download(String url,String charset){
		return download(url, charset, "");
	}
	
	/**
	 * 
	 * @param downloadRemoteServicePrx
	 * @param url
	 * @param charset
	 * @param cookie
	 * @return 下载成功的 json串 ,用 fastjosn工具转换 
	 */
	public static FetchResponse download(String url,String charset,String cookie){
		
		FetchRequest fetchRequest = new FetchRequest();
		
		if(StringUtils.isEmpty(url)){
			return null;
		}
		if(!StringUtils.isEmpty(charset)){
			fetchRequest.setCharset(charset);
		}
		if(!StringUtils.isEmpty(cookie)){
			fetchRequest.setCookie(cookie);
		}
		fetchRequest.setUrl(url);
		return download(fetchRequest);
	}
	
	/**
	 * 代理下载
	 * @param downloadRemoteServicePrx
	 * @param url
	 * @param charset
	 * @param cookie
	 * @param ip
	 * @param port
	 * @return
	 */
	public static FetchResponse download(String url,String charset,String cookie,ProxyModel proxy){
		
		FetchRequest fetchRequest = new FetchRequest();
		
		if(StringUtils.isEmpty(url)){
			return null;
		}
		if(!StringUtils.isEmpty(charset)){
			fetchRequest.setCharset(charset);
		}
		if(!StringUtils.isEmpty(cookie)){
			fetchRequest.setCookie(cookie);
		}
		if(proxy!=null){
			fetchRequest.setProxy(proxy);
		}
		fetchRequest.setUrl(url);
		return download(fetchRequest);
	}
	
	/**
	 * 
	 * @param downloadRemoteServicePrx
	 * @param fetcher
	 * @return 下载成功的 json串 ,用 fastjosn工具转换 
	 */
	public static FetchResponse download(FetchRequest fetcher){
		
		String param = JSONObject.toJSONString(fetcher);
		String result = downloadRemoteServicePrx.download(param);
		FetchResponse json = JSONObject.parseObject(result,FetchResponse.class);
		return json;
	}
	
	/**
	 * 根据入口配置信息下载该入口url
	 * @param mediaEntry
	 * @param downloadRemoteServicePrx
	 * @return
	 */
	public static FetchResponse download(MediaEntry mediaEntry){
		
		return download(mediaEntry.getsUrl(), mediaEntry.getsCharset());
	}
	
	/**
	 * ajax下载
	 * @param fetcher
	 * @return
	 */
	public static FetchResponse downloadAjax(FetchRequest fetcher){
		
		String param = JSONObject.toJSONString(fetcher);
		String result = downloadRemoteServicePrx.downloadAjax(param);
		FetchResponse json = JSONObject.parseObject(result,FetchResponse.class);
		return json;
	}
}
