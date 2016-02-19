package com.gocpf.repository;

public class Point {
	
	public static void main(String[] args) {
		Point p = new Point(1, 2);
		Point p1 = new Point(7, 2);
		System.out.println(p.plusGrandeAbs(p1));
	}
	
	private double x;
	private double y;
	
	public Point plusGrandeAbs  (Point p){
		if (p.x >x) {
			return p;
		}else
			return this;
	}
	
	
	
	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}



	@Override
	public String toString() {
		return "Point [x=" + x + ", y=" + y + "]";
	}
	
	

}
