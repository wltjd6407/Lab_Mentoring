
package com.mypcr;
 
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.mypcr.emulator.MyPCR;
import com.mypcr.emulator.Protocol;

public class Main 
{
	public static void main(String[] args) 
	{
		BufferedReader in = null;
		String pt = null;
		MyPCR mypcr = new MyPCR();
		ArrayList<String> strlist = new ArrayList<String>();
		try {
			in = new BufferedReader(new FileReader(new File("Protocol.txt")));
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			String line = null;
			while((line = in.readLine()) != null)
			{
				strlist.add(line);
			}
			if(strlist.get(0).equals("%PCR%") && strlist.get(strlist.size()-1).equals("%END%")){
			
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ArrayList<Protocol> protocol = mypcr.makeProtocolList(pt);
		mypcr.showProtocolList(protocol);
		
	}
}
