package com.uuwatch.spider.manager.util;

import com.uuwatch.core.model.automatic.MediaEntry;
import com.uuwatch.spider.manager.vo.EntryListVO;

public class Convert {

	public static MediaEntry EntryListVOTOMediaEntry(EntryListVO elv){
		MediaEntry me=new MediaEntry();
		me.seteContentParseType(elv.geteContentParseType());
		me.seteListParseType(elv.geteListParseType());
		me.setFilterDomain(elv.getFilterDomain());
		me.setFilterHost(elv.getFilterHost());
		me.setnBoard(elv.getnBoard());
		me.setnCode(elv.getnCode());
		me.setnCrawlType(elv.getnCrawlType());
		me.setnCreator(elv.getnCreator());
		me.setnDistinct(elv.getnDistinct());
		me.setnEntryStatus(elv.getnEntryStatus());
		me.setnEntryType(elv.getnEntryType());
		me.setnFrequency(elv.getnFrequency());
		me.setnFrom(elv.getnFrom());
		me.setnGrade(elv.getnGrade());
		me.setnIgnoreCheckUrl(elv.getnIgnoreCheckUrl());
		me.setnIndex(elv.getnIndex());
		me.setnPageType(elv.getnPageType());
		me.setnParseTimeWay(elv.getnParseTimeWay());
		me.setnOperator(elv.getnOperator());
		me.setnParseWay(elv.getnParseWay());
		me.setnPriority(elv.getnPriority());
		me.setnRoll(elv.getnRoll());
		me.setnScheduleType(elv.getnScheduleType());
		me.setnSite(elv.getnSite());
		me.setnStatus(elv.getnStatus());
		me.setnTitleWay(elv.getnTitleWay());
		me.setsCharset(elv.getsCharset());
		me.setsDesc(elv.getsDesc());
		me.setsRedirectUrl(elv.getsRedirectUrl());
		me.setsUrl(elv.getsUrl());
		me.settCreate(elv.gettCreate());
		me.settFetch(elv.gettFetch());
		me.settNewest(elv.gettNewest());
		me.settOp(elv.gettOp());
		me.settParse(elv.gettParse());
		return me;
	
	
	}
	public static MediaEntry EntryListVOTOMediaEntryWithoutUrl(EntryListVO elv){
		MediaEntry me=new MediaEntry();
		me.seteContentParseType(elv.geteContentParseType());
		me.seteListParseType(elv.geteListParseType());
		me.setFilterDomain(elv.getFilterDomain());
		me.setFilterHost(elv.getFilterHost());
		me.setnBoard(elv.getnBoard());
		me.setnCode(elv.getnCode());
		me.setnCrawlType(elv.getnCrawlType());
		me.setnCreator(elv.getnCreator());
		me.setnDistinct(elv.getnDistinct());
		me.setnEntryStatus(elv.getnEntryStatus());
		me.setnEntryType(elv.getnEntryType());
		me.setnFrequency(elv.getnFrequency());
		me.setnFrom(elv.getnFrom());
		me.setnGrade(elv.getnGrade());
		me.setnIgnoreCheckUrl(elv.getnIgnoreCheckUrl());
		me.setnIndex(elv.getnIndex());
		me.setnPageType(elv.getnPageType());
		me.setnParseTimeWay(elv.getnParseTimeWay());
		me.setnOperator(elv.getnOperator());
		me.setnParseWay(elv.getnParseWay());
		me.setnPriority(elv.getnPriority());
		me.setnRoll(elv.getnRoll());
		me.setnScheduleType(elv.getnScheduleType());
		me.setnSite(elv.getnSite());
		me.setnStatus(elv.getnStatus());
		me.setnTitleWay(elv.getnTitleWay());
		me.setsCharset(elv.getsCharset());
		me.setsDesc(elv.getsDesc());
		me.setsRedirectUrl(elv.getsRedirectUrl());
		me.settCreate(elv.gettCreate());
		me.settFetch(elv.gettFetch());
		me.settNewest(elv.gettNewest());
		me.settOp(elv.gettOp());
		me.settParse(elv.gettParse());
		return me;
		
		
	}

}
