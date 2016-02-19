package com.gocpf.repository;

public class QuiSuiJE {
	public static void main(String[] args) {
//		A pA= new B();
//		pA.qui();
		
		int i= 1;
		A a = new A("p");
		String s = "l'encapsulation";
		
		m(i, a, s);
		
	}
	
	
	private static void m(int i,A a,String s) {
		i+=1;
		a.inc();
		s=s.toUpperCase();
		System.out.println(String.valueOf(i)+"."+a+":"+s);
		System.out.println();
	}
}
	
	 class A {
		private int att=1;
		
		public void inc(){
			att++;
		}
		
		@Override
		public String toString() {
			return String.valueOf(att);
		}
		public void qui(){
			System.out.println("A");
		}
		
		public A(String p) {
		}
		
		
	}
	
	class B extends A{
		
		public B(String pa) {
			super(pa);
		}
		
		
		public void qui(){
			System.out.println("B");
		}
	}

