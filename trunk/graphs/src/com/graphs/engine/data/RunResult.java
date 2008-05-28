package com.graphs.engine.data;

import java.awt.image.BufferedImage;

public class RunResult {
	private long time;
	BufferedImage image;
	
	
	public BufferedImage getImage() {
		return image;
	}
	public void setImage(BufferedImage buff) {
		this.image = buff;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	
	
}
