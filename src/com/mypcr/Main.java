package com.mypcr;

import java.util.ArrayList;

import com.mypcr.emulator.Protocol;

public class Main 
{
	public static void main(String[] args) 
	{
		ArrayList<Protocol> list = new ArrayList<Protocol>();
		Protocol p1 = new Protocol("1", 20, 20);
		list.add(p1);
		System.out.println("=======Protocol=======");
		System.out.println("Label   temp    time");
		for(int i=0; i<list.size(); i++)
		{
			Protocol p = list.get(i);
			System.out.println(p.getLabel() + "\t" +
							   p.getTemp() + "\t" + 
					           p.getTime());
		}
		
	}
}
