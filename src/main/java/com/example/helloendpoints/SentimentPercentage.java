package com.example.helloendpoints;

public class SentimentPercentage {

	private Integer SentiNum;
	private String SentiName;
	public SentimentPercentage(Integer sentiNum, String sentiName) {
		SentiNum = sentiNum;
		SentiName = sentiName;
	}
	public Integer getSentiNum() {
		return SentiNum;
	}
	public void setSentiNum(Integer sentiNum) {
		SentiNum = sentiNum;
	}
	public String getSentiName() {
		return SentiName;
	}
	public void setSentiName(String sentiName) {
		SentiName = sentiName;
	}
	
	
}
