package com.mypcr.emulator;

import java.util.ArrayList;

public class MyPCR 
{
	public MyPCR()
	{
		
	}
	
	public ArrayList<Protocol> makeProtocolList(String pcr)
	{
		ArrayList<Protocol> list = new ArrayList<Protocol>();
		
		try
		{
			String pcrs[] = pcr.split("\n");
			for(int i=0; i<pcrs.length; i++)
			{
				String comp[] = pcrs[i].split("\t");
				Protocol p = new Protocol(comp[0], Integer.parseInt(comp[1]), Integer.parseInt(comp[2]));
				list.add(p);
			}
		} catch (NumberFormatException e) 
		{
			System.out.println("잘못된 PCR파일입니다.");
			return null;
		} catch (ArrayIndexOutOfBoundsException e1)
		{
			System.out.println("잘못된 PCR파일입니다.");
			return null;
		}
			
		return list;
	}
	
	public void showProtocolList(ArrayList<Protocol> list)
	{
		if(list != null)
		{
			System.out.println("======Protocol======");
			System.out.println("Label   Temp    Time");
			for(int i=0; i<list.size(); i++)
			{
				System.out.println(list.get(i).getLabel() + "\t" +
								   list.get(i).getTemp()  + "\t" +
								   list.get(i).getTime()  + "\t");
			}
		}
		else
		{
			System.out.println("잘못된 PCR파일 입니다.");
		}
	}
}
