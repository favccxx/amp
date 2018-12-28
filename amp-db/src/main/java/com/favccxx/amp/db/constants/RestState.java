package com.favccxx.amp.db.constants;

/**
 * REST 响应状态吗
 * @author favccxx
 *
 */
public enum RestState {
	
	SUCCESS(200), WARN(300), ERROR(400);

	private int state;

	RestState(int state) {
        this.state = state;
    }

	public int getState() {
		return state;
	}
}
