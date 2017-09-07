package com.zhw.web.util;

import java.io.Serializable;
/**
 * 分页对象
 * @author todd
 *
 */
import java.util.Map;

import com.zhw.core.util.Map2Obj;

@SuppressWarnings("serial")
public class PageInfo implements Serializable {

	private Integer pageNum = new Integer(1);/* 当前页面号 */
	private Integer numPerPage = new Integer(20);/* 每页显示属相 */
	private Integer totalCount = new Integer(0);/* 总条数 */
	private Integer pageNumShown = new Integer(1);/* 导航页显示个数 */
	private Integer startNum = new Integer(0);
	private Integer endNum = new Integer(0);

	public Integer getStartNum() {
		startNum = (pageNum - 1) > 0 ? ((pageNum - 1) * numPerPage) : 0;
		return startNum;
	}

	public void setStartNum(Integer startNum) {
		this.startNum = startNum;
	}

	public Integer getEndNum() {
		endNum = pageNum * numPerPage;
		return endNum;
	}

	public void setEndNum(Integer endNum) {
		this.endNum = endNum;
	}

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public Integer getNumPerPage() {
		return numPerPage;
	}

	public void setNumPerPage(Integer numPerPage) {
		this.numPerPage = numPerPage;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public Integer getPageNumShown() {
		int allpages = (totalCount%numPerPage)>0?(totalCount/numPerPage+1):(totalCount/numPerPage);
		pageNumShown = allpages>5?5:allpages;
		return pageNumShown;
	}

	public void setPageNumShown(Integer pageNumShown) {
		this.pageNumShown = pageNumShown;
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> toMap() {
		Map<String, Object> map = (Map<String, Object>) Map2Obj.obj2map(this);
		return map;
	}

}
