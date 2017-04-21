package com.lw.rpc.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.lw.rpc.util.JsonDateTimeSerializer;

/**
 * 外部数据 - model
 * 
 * 备注：由于时间紧，本来model，dao，service层分别都是一个单独的工程。
 * 
 * @author shangdc
 *
 */
public class SpiderOrder implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 数据库名称
	 */
	public static final String TABLE_NAME = "lh_spider_order";

	/**
	 * id
	 */
	private Long id;

	/**
	 * 目标Id
	 */
	private Long targetId;

	/**
	 * 爬取目标名字
	 */
	private String targetName;

	/**
	 * 目标url
	 */
	private String targetUrl;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 此类型一共有多少条总数
	 */
	private String targetTotalNum;

	/**
	 * 类型,静态OR动态
	 */
	private String type;

	/**
	 * 爬取页数
	 */
	private Integer pageNum;

	/**
	 * 0完成，1 已完成
	 */
	private Integer status;

	/**
	 * 有效无效
	 */
	private Integer valid;

	/**
	 * 模板名字，service名字
	 */
	private String templateServiceName;

	/**
	 * 模板方法，对应service方法
	 */
	private String templateServiceMethod;

	/**
	 * 请求参数
	 */
	private String requestParam;

	/**
	 * 优先级
	 */
	private String priority;

	/**
	 * 订单完成时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonSerialize(using = JsonDateTimeSerializer.class)
	private Date finishTime;

	/**
	 * 定时任务启动时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonSerialize(using = JsonDateTimeSerializer.class)
	private Date quartzJobStartTime;

	/**
	 * 当前页一共有多少条信息
	 */
	private int currentPageTotalItemNum;

	// /**
	// * 当前页数
	// */
	// private int currentPageNum;

	/**
	 * 当前批次，也就是版本号
	 */
	private int spiderOrderBatch;

	/**
	 * 创建时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonSerialize(using = JsonDateTimeSerializer.class)
	private Date createTime;

	/**
	 * 更新时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonSerialize(using = JsonDateTimeSerializer.class)
	private Date updateTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	// public int getId() {
	// return id;
	// }
	//
	// public void setId(int id) {
	// this.id = id;
	// }

	public Long getTargetId() {
		return targetId;
	}

	public void setTargetId(Long targetId) {
		this.targetId = targetId;
	}

	public String getTargetName() {
		return targetName;
	}

	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}

	public String getTargetUrl() {
		return targetUrl;
	}

	public void setTargetUrl(String targetUrl) {
		this.targetUrl = targetUrl;
	}

	public String getTargetTotalNum() {
		return targetTotalNum;
	}

	public void setTargetTotalNum(String targetTotalNum) {
		this.targetTotalNum = targetTotalNum;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getValid() {
		return valid;
	}

	public void setValid(Integer valid) {
		this.valid = valid;
	}

	public String getTemplateServiceName() {
		return templateServiceName;
	}

	public void setTemplateServiceName(String templateServiceName) {
		this.templateServiceName = templateServiceName;
	}

	public String getTemplateServiceMethod() {
		return templateServiceMethod;
	}

	public void setTemplateServiceMethod(String templateServiceMethod) {
		this.templateServiceMethod = templateServiceMethod;
	}

	public Date getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

	public Date getQuartzJobStartTime() {
		return quartzJobStartTime;
	}

	public void setQuartzJobStartTime(Date quartzJobStartTime) {
		this.quartzJobStartTime = quartzJobStartTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getRequestParam() {
		return requestParam;
	}

	public void setRequestParam(String requestParam) {
		this.requestParam = requestParam;
	}

	public int getCurrentPageTotalItemNum() {
		return currentPageTotalItemNum;
	}

	public void setCurrentPageTotalItemNum(int currentPageTotalItemNum) {
		this.currentPageTotalItemNum = currentPageTotalItemNum;
	}

	// public int getCurrentPageNum() {
	// return currentPageNum;
	// }
	//
	// public void setCurrentPageNum(int currentPageNum) {
	// this.currentPageNum = currentPageNum;
	// }

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSpiderOrderBatch() {
		return spiderOrderBatch;
	}

	public void setSpiderOrderBatch(int spiderOrderBatch) {
		this.spiderOrderBatch = spiderOrderBatch;
	}

}
