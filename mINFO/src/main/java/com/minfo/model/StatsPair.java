package com.minfo.model;

public class StatsPair {
	private String tag;
	private String answer;
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public String getAnswer() {
		return answer;
	}
	public StatsPair(String tag, String answer) {
		super();
		this.tag = tag;
		this.answer = answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
	public boolean equals(Object o) {
		if(o instanceof StatsPair) {
			StatsPair sp = (StatsPair)o;
			if(this.tag!=null && this.tag.equals(sp.getTag()) &&
			   this.answer!=null && this.answer.equals(sp.getAnswer())) {
				return true;
			}
		}
		return false;
	}
	
	public int hashCode() {
		int hash = 7;
		hash = 19 * hash + (null == this.tag ? 0 : this.tag.hashCode());
		hash = 31 * hash + (null == this.answer ? 0 : this.answer.hashCode());
		return hash;
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
	    
	    retValue = "StatsPair ( "
	        + "tag = " + this.tag + TAB
	        + "answer = " + this.answer + TAB
	        + " )";
	
	    return retValue;
	}
	
	
}	
