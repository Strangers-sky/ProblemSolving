package com.uuwatch.spider.manager.util;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import com.uuwatch.core.ice.CrawlParserModelPrx;
import com.uuwatch.core.model.automatic.CrawlTemplet;
import com.uuwatch.core.model.automatic.MediaEntry;
import com.uuwatch.core.parser.model.ArticleContentModel;
import com.uuwatch.core.parser.model.TemplateDataFieldListModel;
import com.uuwatch.core.parser.model.TemplateDataFieldModel;
import com.uuwatch.core.parser.model.TemplateDataModel;
import com.uuwatch.core.parser.model.UrlLinkModel;
import com.uuwatch.spider.manager.ice.IceFactory;

public class ParserUtil {
	
	private static CrawlParserModelPrx parserRemoteServicePrx = IceFactory.getParserRemoteServicePrx();
	
	/**
	 * 网络列表通用解析
	 * @param parserRemoteServicePrx
	 * @param html
	 * @param mediaEntry
	 * @return UrlLinkModel
	 */
	public static UrlLinkModel gParseWebList(String html,MediaEntry mediaEntry){
		
		String resultJsonStr = parserRemoteServicePrx.generalLinkListPageParserNext(html, mediaEntry.getsUrl(),mediaEntry.getFilterDomain(),mediaEntry.getFilterHost());
		
		JSONObject json = JSONObject.fromObject(resultJsonStr);
		
		Map<String, Class<ArticleContentModel>> classMap = new HashMap<String, Class<ArticleContentModel>>();
		classMap.put("lacm", ArticleContentModel.class);
		UrlLinkModel ulm = (UrlLinkModel)JSONObject.toBean(json, UrlLinkModel.class,classMap);
		return ulm;
	}
	
	/**
	 * 网络列表模版解析
	 * @param parserRemoteServicePrx
	 * @param html
	 * @param mediaEntry
	 * @param crawlTemplet
	 * @return TemplateDataModel
	 */
	public static TemplateDataModel tParseWebList(String html,MediaEntry mediaEntry,CrawlTemplet crawlTemplet){
		
		String result = parserRemoteServicePrx.templatePageParser(html, mediaEntry.getsUrl(), crawlTemplet.getTplContent());//列表模版解析
		
		Map<String, Class<?>> classMap = new HashMap<String, Class<?>>();  
		classMap.put("ltdflm", TemplateDataFieldListModel.class); 
        classMap.put("ltdfm", TemplateDataFieldModel.class);
		//将返回的解析结果转化成javabean
		TemplateDataModel td = (TemplateDataModel) JSONObject.toBean(JSONObject.fromObject(result), TemplateDataModel.class,classMap);
		return td;
	}
	
	/**
	 * 模版解析
	 * @param parserRemoteServicePrx
	 * @param html
	 * @param mediaEntry
	 * @param crawlTemplet
	 * @return TemplateDataModel
	 */
	public static TemplateDataModel tParseWebList(String html,String url,CrawlTemplet crawlTemplet){
		
		String result = parserRemoteServicePrx.templatePageParser(html, url, crawlTemplet.getTplContent());//列表模版解析
		
		Map<String, Class<?>> classMap = new HashMap<String, Class<?>>();  
		classMap.put("ltdflm", TemplateDataFieldListModel.class); 
        classMap.put("ltdfm", TemplateDataFieldModel.class);
		//将返回的解析结果转化成javabean
		TemplateDataModel td = (TemplateDataModel) JSONObject.toBean(JSONObject.fromObject(result), TemplateDataModel.class,classMap);
		return td;
	}
	
	/**
	 * 报刊频道连接通用解析
	 * @param parserRemoteServicePrx
	 * @param html
	 * @param mediaEntry
	 * @return UrlLinkModel
	 */
	public static UrlLinkModel gParsePaperList(String html,MediaEntry mediaEntry){
		
		String resultJsonStr = parserRemoteServicePrx.generalPaperBoardLinkListParserNext(html, mediaEntry.getsUrl(),mediaEntry.getFilterDomain(),mediaEntry.getFilterHost());
		
		JSONObject json = JSONObject.fromObject(resultJsonStr);
		
		Map<String, Class<ArticleContentModel>> classMap = new HashMap<String, Class<ArticleContentModel>>();
		classMap.put("lacm", ArticleContentModel.class);
		UrlLinkModel ulm = (UrlLinkModel)JSONObject.toBean(json, UrlLinkModel.class,classMap);
		return ulm;
	}
	
	/**
	 * 报刊内容利列表通用解析
	 * @param parserRemoteServicePrx
	 * @param html
	 * @param url
	 * @param mediaEntry
	 * @return UrlLinkModels
	 */
	public static UrlLinkModel gPaperBoardList(String html,String url,MediaEntry mediaEntry){
		
		String resultJsonStr = parserRemoteServicePrx.generalPaperArticleLinkListParserNext(html,url,mediaEntry.getFilterDomain(),mediaEntry.getFilterHost());
		JSONObject json = JSONObject.fromObject(resultJsonStr);
		Map<String, Class<ArticleContentModel>> classMap = new HashMap<String, Class<ArticleContentModel>>();
		classMap.put("lacm", ArticleContentModel.class);
		UrlLinkModel ulm = (UrlLinkModel)JSONObject.toBean(json, UrlLinkModel.class,classMap);
		return ulm;
	}
	
	/**
	 * 内容页通用解析
	 * @param parserRemoteServicePrx
	 * @param html
	 * @param url
	 * @param mediaEntry
	 * @return
	 */
	public static ArticleContentModel gParseContent(String html,String url,MediaEntry mediaEntry){
		String parseResultJsonStr = parserRemoteServicePrx.generalArticleDetailPageParserNext(html,url,mediaEntry.getnCrawlType());
		JSONObject json = JSONObject.fromObject(parseResultJsonStr);
		ArticleContentModel acm = (ArticleContentModel)JSONObject.toBean(json, ArticleContentModel.class);
		return acm;
	}
	
	
	/**
	 * 内容页通用解析
	 * @param parserRemoteServicePrx
	 * @param html
	 * @param url
	 * @param mediaEntry
	 * @return
	 */
	public static ArticleContentModel gParseContent(String html,String url,int nMeida){
		String parseResultJsonStr = parserRemoteServicePrx.generalArticleDetailPageParserNext(html,url,nMeida);
		JSONObject json = JSONObject.fromObject(parseResultJsonStr);
		ArticleContentModel acm = (ArticleContentModel)JSONObject.toBean(json, ArticleContentModel.class);
		return acm;
	}
	
	
}
