package com.mypcr;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.mypcr.emulator.MyPCR;

public class Main 
{
	public static void main(String[] args) 
	{
		Lotto lt = new Lotto();
		int lt2[];
		lt2 = lt.createLotto();
		for(int i : lt2)
		{
			 System.out.print(i + " ");
		}
		
	}
}
