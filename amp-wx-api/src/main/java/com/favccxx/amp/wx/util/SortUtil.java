package com.favccxx.amp.wx.util;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

public class SortUtil {

	/**
	 * 根据字段获取排序方式
	 * @param field
	 * @return
	 */
	public static Sort getSort(String field) {
		Direction direction = Direction.ASC;
		if (field.startsWith("-")) {
			direction = Direction.DESC;
		}

		if (field.startsWith("+") || field.startsWith("-")) {
			return new Sort(direction, field.substring(1));
		}

		return new Sort(direction, field);

	}
}
