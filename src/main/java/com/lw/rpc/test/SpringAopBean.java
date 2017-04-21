package com.lw.rpc.test;

public class SpringAopBean {

	public void aopMethod() throws NullPointerException {
		System.out.println("空指针异常");
	}
	
	public int aopMethod1(String numStr) throws NumberFormatException {
		int num = Integer.parseInt(numStr);
		return num;
	}
	
}
