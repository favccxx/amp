package com.favccxx.amp.admin.dto.resp;

import java.util.List;

import com.favccxx.amp.db.model.AmpCategory;

public class CategoryDto {

	private long id;

	private String categoryCode;

	/**
	 * 类别名称
	 */
	private String categoryName;

	private List<CategoryDto> children;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public List<CategoryDto> getChildren() {
		return children;
	}

	public void setChildren(List<CategoryDto> children) {
		this.children = children;
	}

}
