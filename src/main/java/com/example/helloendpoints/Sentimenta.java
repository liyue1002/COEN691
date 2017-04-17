package com.example.helloendpoints;

public class Sentimenta {
	
	public Sentimenta(double score, double magnitude) {
		super();
		this.score = score;
		this.magnitude = magnitude;
	}
	private double score;
	private double magnitude;
	

	public Sentimenta() {
		super();
	}
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
	public double getMagnitude() {
		return magnitude;
	}
	public void setMagnitude(double magnitude) {
		this.magnitude = magnitude;
	}
	
}
