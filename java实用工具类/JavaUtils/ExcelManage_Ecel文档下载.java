package com.uuwatch.spider.manager.util;

import com.uuwatch.core.model.automatic.CrawlNews;
import com.uuwatch.core.model.automatic.CrawlTempNews;
import com.uuwatch.core.model.automatic.MediaMonitorNow;
import com.uuwatch.core.model.automatic.MediaSiteAnalyseEntry;
import com.uuwatch.spider.manager.vo.WatchTaskExtend;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 从excel读取数据/往excel中写入 excel有表头，表头每列的内容对应实体类的属性
 */
public class ExcelManage {

	private static Logger logger = LoggerFactory.getLogger(ExcelManage.class);

	public static HSSFWorkbook writer(List<CrawlNews> list) throws IOException {
		String titleRow[] = { "媒体", "发布时间", "新闻code", "新闻标题", "新闻url" };

		// 创建工作文档对象
		HSSFWorkbook wb = new HSSFWorkbook();
		// 创建sheet对象
		Sheet sheet = (Sheet) wb.createSheet("sheet1");

		// 创建sheet对象
		if (sheet == null) {
			sheet = (Sheet) wb.createSheet("sheet1");
		}

		// 添加表头
		Row row = sheet.createRow(0);
		Cell cell = row.createCell(0);
		row.setHeight((short) 540);
		cell.setCellValue("大搜索"); // 创建第一行

		CellStyle style = wb.createCellStyle(); // 样式对象
		// 设置单元格的背景颜色为淡蓝色
		style.setFillForegroundColor(HSSFColor.PALE_BLUE.index);

		// style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 垂直
		// style.setAlignment(CellStyle.ALIGN_CENTER);// 水平
		style.setWrapText(true);// 指定当单元格内容显示不下时自动换行

		cell.setCellStyle(style); // 样式，居中

		Font font = wb.createFont();
		// font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		font.setFontName("宋体");
		font.setFontHeight((short) 280);
		style.setFont(font);
		// 单元格合并
		// 四个参数分别是：起始行，起始列，结束行，结束列
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 7));
		sheet.autoSizeColumn(5200);

		row = sheet.createRow(1); // 创建第二行
		for (int i = 0; i < titleRow.length; i++) {
			cell = row.createCell(i);
			cell.setCellValue(titleRow[i]);
			cell.setCellStyle(style); // 样式，居中
			if (i >= 3) {
				sheet.setColumnWidth(i, 45 * 256);
			} else {
				sheet.setColumnWidth(i, 25 * 256);
			}
		}
		row.setHeight((short) 540);

		// 循环写入行数据
		for (int i = 0; i < list.size(); i++) {
			row = (Row) sheet.createRow(i + 2);
			row.setHeight((short) 500);
			row.createCell(0).setCellValue((list.get(i)).getAuthor());
			row.createCell(1).setCellValue((list.get(i)).gettPub());
			row.createCell(2).setCellValue((list.get(i)).getSuid());
			row.createCell(3).setCellValue((list.get(i)).getName());
			row.createCell(4).setCellValue((list.get(i)).getsUrl());
		}
		return wb;
	}

	public static Workbook writerAnalyseEntry(List<MediaSiteAnalyseEntry> list, Integer nSite) {
		// 创建工作文档对象
		Workbook wb = new XSSFWorkbook();
		// 创建sheet对象
		Sheet sheet = (Sheet) wb.createSheet("sheet1");
		// 创建sheet对象
		if (sheet == null) {
			sheet = (Sheet) wb.createSheet("sheet1");
		}
		Row row = null;
		for (int i = 0; i < list.size(); i++) {
			// 创建行，不添加样式
			row = sheet.createRow(i);
			// 行写入数据
			row.createCell(0).setCellValue(list.get(i).getsUrl());
		}
		sheet.autoSizeColumn(0); // 调整第一列宽度
		return wb;
	}

	/**
	 * 监控站点列表
	 * 
	 * @param list
	 * @return
	 */
	public static Workbook writerMonitorSite(List<MediaMonitorNow> list, String fileName) {
		String fileType = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
		String titleRow[] = { "所属来源", "站点名称", "站点ID", "站点链接", "媒体类型", "昨日抓取量", "同比值" };
		// 创建工作文档对象
		Workbook wb = null;
		if (fileType.equals("xls")) {
			wb = new HSSFWorkbook();
		} else if (fileType.equals("xlsx")) {
			wb = new XSSFWorkbook();
		}
		// 创建sheet对象
		Sheet sheet = (Sheet) wb.createSheet("sheet1");
		// 创建sheet对象
		if (sheet == null) {
			sheet = (Sheet) wb.createSheet("sheet1");
		}
		// 添加表头
		Row row = sheet.createRow(0);
		for (int i = 0; i < titleRow.length; i++) {
			// 行写入数据
			row.createCell(i).setCellValue(titleRow[i]);
		}
		// 循环写入行数据
		for (int i = 0; i < list.size(); i++) {
			row = sheet.createRow(i + 1);
			row.createCell(0).setCellValue((list.get(i)).getsFromName());
			row.createCell(1).setCellValue((list.get(i)).getsName());
			row.createCell(2).setCellValue((list.get(i)).getnCode());
			row.createCell(3).setCellValue((list.get(i)).getsUrl());
			String media = null;
			switch ((list.get(i)).getnMedia()) {
			case 1:
				media = "电视";
				break;
			case 2:
				media = "报刊";
				break;
			case 4:
				media = "网络";
				break;
			case 8:
				media = "微博";
				break;
			case 16:
				media = "论坛";
				break;
			case 32:
				media = "博客";
				break;
			case 64:
				media = "博客";
				break;
			case 128:
				media = "客户端";
				break;
			case 256:
				media = "广播";
				break;
			case 512:
				media = "视频";
				break;
			case 1024:
				media = "外媒";
				break;
			default:
				media = "";
				break;
			}
			row.createCell(4).setCellValue(media);
			row.createCell(5).setCellValue((list.get(i)).getnTotalCount());
			row.createCell(6).setCellValue((list.get(i)).getnYearValue());
		}
		return wb;
	}

	/**
	 * 临时抓取新闻
	 * @param list
	 * @return
	 */
	public static Workbook writerTempNews(List<CrawlTempNews> list, String fileName) throws Exception {
		String fileType = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
		String titleRow[] = { "N_CODE", "新闻url", "列表url hash", "新闻标题", "发布时间", "正文", "抓取时间", "转发数量", "评论数量",
				"新闻作者或转发来源" };
		// 创建工作文档对象
		Workbook wb = null;
		if (fileType.equals("xls")) {
			wb = new HSSFWorkbook();
		} else if (fileType.equals("xlsx")) {
			wb = new XSSFWorkbook();
		}
		// 创建sheet对象
		Sheet sheet = (Sheet) wb.createSheet("sheet1");
		// 创建sheet对象
		if (sheet == null) {
			sheet = (Sheet) wb.createSheet("sheet1");
		}
		// 添加表头
		Row row = sheet.createRow(0);
		for (int i = 0; i < titleRow.length; i++) {
			// 行写入数据
			row.createCell(i).setCellValue(titleRow[i]);
		}
		// 循环写入行数据
		for (int i = 0; i < list.size(); i++) {
			row = sheet.createRow(i + 1);
			row.createCell(0).setCellValue((list.get(i)).getnCode());
			row.createCell(1).setCellValue((list.get(i)).getsUrl());
			row.createCell(2).setCellValue((list.get(i)).getsUrlHash());
			row.createCell(3).setCellValue((list.get(i)).getsTitle());
			row.createCell(4).setCellValue(DateUtil.getDataStringFromDate((list.get(i)).gettPub()));
			//转换html格式字符
			String content = StringEscapeUtils.unescapeHtml((list.get(i)).getsContent());
			if(content.length() > 32760) {
				logger.error("ExcelManage export filed,content length above 32760！nCode is:"+list.get(i).getnCode()+",content length is:"+content.length());
				continue;
			}
			row.createCell(5).setCellValue(content);
			row.createCell(6).setCellValue(DateUtil.getDataStringFromDate((list.get(i)).gettOp()));
			row.createCell(7).setCellValue((list.get(i)).getnForward() != null ? (list.get(i)).getnForward() : 0);
			row.createCell(8).setCellValue((list.get(i)).getnComment() != null ? (list.get(i)).getnComment() : 0);
			row.createCell(9).setCellValue((list.get(i)).getsAuthor() != null ? (list.get(i)).getsAuthor() : "");
		}
		return wb;
	}

	public static Workbook writerExample() {
		String titleRow[] = { "新闻url", "标题", "发布时间", "正文", "评论数", "转发数", "转发来源" };
		// 创建工作文档对象
		Workbook wb = new XSSFWorkbook();
		// 创建sheet对象
		Sheet sheet = (Sheet) wb.createSheet("sheet1");
		// 创建sheet对象
		if (sheet == null) {
			sheet = (Sheet) wb.createSheet("sheet1");
		}
		// 添加表头
		Row row = sheet.createRow(0);
		for (int i = 0; i < titleRow.length; i++) {
			// 行写入数据
			row.createCell(i).setCellValue(titleRow[i]);
		}
		// 写入行数据
		row = sheet.createRow(1);
		row.createCell(0).setCellValue("http://finance.jxcn.cn/system/2017/07/12/016270469.shtml");
		row.createCell(1).setCellValue("P2P理财靠谱吗?ppmoney、钱来也、极光金融");
		row.createCell(2).setCellValue("2017-11-11 11:11:11");
		row.createCell(3).setCellValue("P2P理财以周期短、收益可观跻身理财排行榜前列，受到众多投资人的追捧");
		row.createCell(4).setCellValue(10);
		row.createCell(5).setCellValue(10);
		return wb;
	}

	/**
	 * 监控站点列表
	 *
	 * @param list
	 * @return
	 */
	public static Workbook writerKeyWords(List<WatchTaskExtend> list, String fileName) {
		String fileType = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
		String[] titleRow = {"N_TASK", "检索关键字", "创建人", "修改人", "创建时间", "修改时间", "微信支持", "微博支持", "论坛支持", "大搜索支持", "任务状态", "备注"};
		// 创建工作文档对象
		Workbook wb = null;
		if (fileType.equals("xls")) {
			wb = new HSSFWorkbook();
		} else if (fileType.equals("xlsx")) {
			wb = new XSSFWorkbook();
		}
		// 创建sheet对象
		Sheet sheet = wb.createSheet("sheet1");
		// 创建sheet对象
		if (sheet == null) {
			sheet = wb.createSheet("sheet1");
		}
		// 添加表头
		Row row = sheet.createRow(0);
		for (int i = 0; i < titleRow.length; i++) {
			// 行写入数据
			row.createCell(i).setCellValue(titleRow[i]);
		}
		// 循环写入行数据
		for (int i = 0; i < list.size(); i++) {
			row = sheet.createRow(i + 1);
			WatchTaskExtend taskExtend = list.get(i);
			row.createCell(0).setCellValue(taskExtend.getnTask());
			row.createCell(1).setCellValue(taskExtend.getsKeywords());
			row.createCell(2).setCellValue(taskExtend.getUserName());
			row.createCell(3).setCellValue(taskExtend.getEditName());
			row.createCell(4).setCellValue(DateUtil.dateToString(taskExtend.gettVerificate(),null));
			row.createCell(5).setCellValue(DateUtil.dateToString(taskExtend.gettModify(),null));
			row.createCell(6).setCellValue(taskExtend.getnWeixin() == 0 ? "不支持" : "支持");
			row.createCell(7).setCellValue(taskExtend.getnMbolg() == 0 ? "不支持" : "支持");
			row.createCell(8).setCellValue(taskExtend.getnForum() == 0 ? "不支持" : "支持");
			row.createCell(9).setCellValue(taskExtend.getnSearch() == 0 ? "不支持" : "支持");
			row.createCell(10).setCellValue(taskExtend.getnState() == 0 ? "停用" : (taskExtend.getnState() == 2 ? "无效" : "启用"));
			row.createCell(11).setCellValue(taskExtend.getnDesc());
		}
		return wb;
	}

	/**
	 * @param list
	 * @param fileName
	 * @Return org.apache.poi.ss.usermodel.Workbook
	 * @Date 2018/12/11 11:18
	 * @Description 获取相似新闻入口号xml导出对象
	 */
	public static Workbook writerSimilarEntryList(List<Map<String, String>> list, String fileName) {
		String fileType = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
		String[] titleRow = {"入口号", "重复数量"};
		// 创建工作文档对象
		Workbook wb = null;
		if (fileType.equals("xls")) {
			wb = new HSSFWorkbook();
		} else if (fileType.equals("xlsx")) {
			wb = new XSSFWorkbook();
		}
		// 创建sheet对象
		Sheet sheet = wb.createSheet("sheet1");
		// 创建sheet对象
		if (sheet == null) {
			sheet = wb.createSheet("sheet1");
		}
		// 添加表头
		Row row = sheet.createRow(0);
		for (int i = 0; i < titleRow.length; i++) {
			// 行写入数据
			row.createCell(i).setCellValue(titleRow[i]);
		}
		// 循环写入行数据
		for (int i = 0; i < list.size(); i++) {
			row = sheet.createRow(i + 1);
			Map<String, String> map = list.get(i);
			row.createCell(0).setCellValue(map.get("entryKey"));
			row.createCell(1).setCellValue(map.get("similarNum"));
		}
		return wb;
	}

}