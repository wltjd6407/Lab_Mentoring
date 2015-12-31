package com.mypcr.emulator;

public class MyPCR {
	public MyPCR()
	{
		
	}
	
	public int add(int a, int b)
	{
		return a+b;
	}
	
	public int mul(int a, int b)
	{
		return a*b;
	}
	
	public int sub(int a, int b)
	{
		return a-b;
	}
	
	public int div(int a, int b)
	{
		if( b == 0 )
			return 0;
		else
			return a/b;
	}
}
