package com.minfo.model;

public class UserStats {
	private String answer;
	private String tag;
	private double percent;
	private int count;
	
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public double getPercent() {
		return percent;
	}
	public void setPercent(double percent) {
		this.percent = percent;
	}
	/**
	 * Constructs a <code>String</code> with all attributes
	 * in name = value format.
	 *
	 * @return a <code>String</code> representation 
	 * of this object.
	 */
	public String toString()
	{
	    final String TAB = "    ";
	    
	    String retValue = "";
	    
	    retValue = "UserStats ( "
	        + "answer = " + this.answer + TAB
	        + "tag = " + this.tag + TAB
	        + "percent = " + this.percent + TAB
	        + "count = " + this.count + TAB
	        + " )";
	
	    return retValue;
	}
}
