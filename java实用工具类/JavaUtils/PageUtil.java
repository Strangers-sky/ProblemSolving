package com.uuwatch.spider.manager.util;

import com.uuwatch.core.media.model.Node;
import com.uuwatch.core.media.util.NodeUtil;
import com.uuwatch.core.model.automatic.CrawlMonitor;
import com.uuwatch.core.model.automatic.MediaMonitorNow;
import com.uuwatch.core.model.automatic.SyUser;
import com.uuwatch.spider.manager.servlet.MediaEntryController;
import com.uuwatch.spider.manager.vo.DataGridVO;
import com.uuwatch.spider.manager.vo.MediaDataResponseVO;
import com.uuwatch.spider.manager.vo.SiteListVO;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * easy ui page 对象工具类
 * 
 * @author 66-sunzhiguo
 * 
 */
public class PageUtil {

	private static Logger logger = LoggerFactory
			.getLogger(MediaEntryController.class);


	/**
	 * 将从节点缓存获取的json字符串转换为easy ui page 对象
	 * 
	 * @param json
	 * @param cls
	 * @return
	 */
	public static DataGridVO jsonToDataGridVO(String json, Class<?> cls) {
		DataGridVO dataGridVO = new DataGridVO();
		JSONObject jsonObject = JSONObject.fromObject(json);
		String data = jsonObject.getString("data");
		String total = jsonObject.getString("totalCount");
		JSONArray jsonData = JSONArray.fromObject(data);
		@SuppressWarnings({ "deprecation", "unchecked" })
		List<Node> nList = JSONArray.toList(jsonData, Node.class);
		List<?> ifList = NodeUtil.nListToOList(nList, cls);
		dataGridVO.setTotal(total);
		dataGridVO.setRows(ifList);

		return dataGridVO;
	}

	/**
	 * 数据库列表转换成dategrid对象
	 * @param list
	 * @param total
	 * @return
	 */
	public static DataGridVO POJOListToDataGridVO(
			List<?> list, String total) {
		DataGridVO dataGridVO = new DataGridVO();
		dataGridVO.setTotal(total);
		dataGridVO.setRows(list);
		return dataGridVO;
	}
	
	/**
	 * 对象列表转换成 （easy ui datagrid） 对象
	 * @param list
	 * @return
	 */
	public static DataGridVO ObjectListTODataGridVO(List<?> list, long total){
		
		DataGridVO dataGridVO = new DataGridVO();
		dataGridVO.setTotal(String.valueOf(total));
		dataGridVO.setRows(list);
		return dataGridVO;
	}
	
	
	/**
	 * 将数据库获取的List转换为easy ui page 对象
	 * 
	 * @param json
	 * @param cls
	 * @return
	 */
	public static DataGridVO ListToDataGridVO(List<SyUser> list,String total) {
		DataGridVO dataGridVO = new DataGridVO();
		dataGridVO.setTotal(total);
		dataGridVO.setRows(list);
		return dataGridVO;
	}

	public static DataGridVO MonitorListToDataGridVO(List<CrawlMonitor> list,String total) {
		DataGridVO dataGridVO = new DataGridVO();
		dataGridVO.setTotal(total);
		dataGridVO.setRows(list);
		return dataGridVO;
	}

	public static DataGridVO MediaDataResponseVOListToDataGridVO(
			List<MediaDataResponseVO> list, String total) {
		DataGridVO dataGridVO = new DataGridVO();
		dataGridVO.setTotal(total);
		dataGridVO.setRows(list);
		return dataGridVO;
	}

	public static DataGridVO SiteListVOListToDataGridVO(List<SiteListVO> list,
			String total) {
		DataGridVO dataGridVO = new DataGridVO();
		dataGridVO.setTotal(total);
		dataGridVO.setRows(list);
		return dataGridVO;
	}

	public static DataGridVO MediaMonitorNowListToDataGridVO(
			List<MediaMonitorNow> list, String total) {
		DataGridVO dataGridVO = new DataGridVO();
		dataGridVO.setTotal(total);
		dataGridVO.setRows(list);
		return dataGridVO;
	}

	public static DataGridVO MapToDataGridVO(
			List<Map<String,String>> list, String total) {
		DataGridVO dataGridVO = new DataGridVO();
		dataGridVO.setTotal(total);
		dataGridVO.setRows(list);
		return dataGridVO;
	}

}
