package com.mypcr;

import com.mypcr.emulator.MyPCR;

public class Main 
{
	public static void main(String[] args) 
	{
		int test = 0;
		
		MyPCR mypcr = new MyPCR();
		test = mypcr.add(1, 2);
		
		System.out.println(test);
		
		test = mypcr.sub(2, 1);
		
		System.out.println(test);
		
		test = mypcr.mul(1, 4);
		
		System.out.println(test);
		
		test = mypcr.div(2, 0);
	}
}
