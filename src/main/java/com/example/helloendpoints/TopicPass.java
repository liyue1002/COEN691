package com.example.helloendpoints;

public class TopicPass {
	private String topicPass ;
	private String userEmail;
	
	public TopicPass(String topicPass, String userEmail) {
		this.topicPass = topicPass;
		this.userEmail = userEmail;
	}
	
	public String getTopicPass() {
		return topicPass;
	}
	public void setTopicPass(String topicPass) {
		this.topicPass = topicPass;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	
	
}
