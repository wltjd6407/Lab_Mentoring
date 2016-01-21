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
		int lt2[], lt3[], lt4[];
		lt2 = lt.createLotto();
		lt3 = lt.createLotto();
		lt4 = lt.createLotto();
		int cnt = 0;
		
		lt.pirntLotto(lt2);
		lt.pirntLotto(lt3);
		lt.pirntLotto(lt4);
		
		lt.checkLotto(lt2, lt3);
		
		lt.checkLotto(lt2, lt4);
	}
}
