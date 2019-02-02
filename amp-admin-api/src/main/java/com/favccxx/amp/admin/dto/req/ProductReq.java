package com.favccxx.amp.admin.dto.req;

import java.math.BigDecimal;
import java.util.Date;

public class ProductReq {

	
	private long id;

	/**
	 * 产品类别Id
	 */
	private long categoryId;

	/**
	 * 俱乐部Id
	 */
	private long clubId;
	
	/**
	 * 店铺代码
	 */
	private String shopCode;
	
	/**
	 * 产品名称
	 */
	private String productName;

	/**
	 * 产品类型
	 */
	private String productType;
	
	/**
	 * 产品主图
	 */
	private String productImg;

	/**
	 * 产品等级
	 */
	private int star;

	/**
	 * 产品状态
	 */
	private String status;	

	/**
	 * 产品原始价格/厂家建议价格
	 */
	private BigDecimal originalPrice;

	/**
	 * 产品销售单价
	 */
	private BigDecimal salePrice;

	/**
	 * 产品服务时间（分钟）
	 */
	private int workTime;

	/**
	 * 产品详情
	 */
	private String detail;

	/**
	 * 产品上线日期
	 */
	private String onlineDate;

	/**
	 * 产品下线日期
	 */
	private String offlineDate;
	
	private String[] sliders;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}

	public long getClubId() {
		return clubId;
	}

	public void setClubId(long clubId) {
		this.clubId = clubId;
	}

	
	public String getProductImg() {
		return productImg;
	}

	public void setProductImg(String productImg) {
		this.productImg = productImg;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public int getStar() {
		return star;
	}

	public void setStar(int star) {
		this.star = star;
	}

	

	public String getShopCode() {
		return shopCode;
	}

	public void setShopCode(String shopCode) {
		this.shopCode = shopCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public BigDecimal getOriginalPrice() {
		return originalPrice;
	}

	public void setOriginalPrice(BigDecimal originalPrice) {
		this.originalPrice = originalPrice;
	}

	public BigDecimal getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(BigDecimal salePrice) {
		this.salePrice = salePrice;
	}

	public int getWorkTime() {
		return workTime;
	}

	public void setWorkTime(int workTime) {
		this.workTime = workTime;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getOnlineDate() {
		return onlineDate;
	}

	public void setOnlineDate(String onlineDate) {
		this.onlineDate = onlineDate;
	}

	public String getOfflineDate() {
		return offlineDate;
	}

	public void setOfflineDate(String offlineDate) {
		this.offlineDate = offlineDate;
	}

	public String[] getSliders() {
		return sliders;
	}

	public void setSliders(String[] sliders) {
		this.sliders = sliders;
	}

	
	

}
