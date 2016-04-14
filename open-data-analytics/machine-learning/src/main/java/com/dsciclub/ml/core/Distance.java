package com.dsciclub.ml.core;

public class Distance implements Comparable<Distance> {
	double score;
    int index;

    public Distance(double score, int index) {
        this.score = score;
        this.index = index;
    }

    public int compareTo(Distance o) {
        return score < o.score ? -1 : score > o.score ? 1 : 0;
    }

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
    
}
