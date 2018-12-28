package com.favccxx.amp.db.base;

import com.favccxx.amp.db.dto.RestResult;

@FunctionalInterface
public interface RestProcessor {

	RestResult process();
	
}
