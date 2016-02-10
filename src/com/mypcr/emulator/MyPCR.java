package com.mypcr.emulator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class MyPCR extends Thread
{
	private double mTemp;
	private double mPrevTemp, mTargetTemp;
	private int state;
	
	private static final int STATE_READY = 0x00;
	private static final int STATE_RUN = 0x01;
	
	private static final double DEFAULT_TEMP = 25.1;
	private boolean isMonitor = false;
	
	private int mElapsedTime = 0;
	private double tempRise = 0.01;
	
	public MyPCR(){
		mTemp = DEFAULT_TEMP;
		mPrevTemp = DEFAULT_TEMP;
		mTargetTemp = DEFAULT_TEMP;
		state = STATE_READY;
	}
	
	public void run(){
		int sec = 0;
		boolean tempflag = false;
		while(true){
			try {
				Thread.sleep(100);	
			} catch (InterruptedException e){
				e.printStackTrace();
			}
			
			if(state == STATE_RUN){
				if(sec > 9){
					sec = 0;
					mElapsedTime++;
				}
					
				mTemp += tempRise;
				sec++;
				
				if(mTemp > mPrevTemp){
					tempRise*=-1;
					tempflag = true;
				}
				if((mTemp < mTargetTemp) && tempflag){
					tempRise*=-1;
					tempflag = false;
					stopPCR();
				}
			}
			else if(state == STATE_READY){
				mTemp = DEFAULT_TEMP;
				mPrevTemp = DEFAULT_TEMP;
				mTargetTemp = DEFAULT_TEMP;
				sec = 0;
			}
			
			
		}
	}
	
	
	public ArrayList<Protocol> makeProtocolList(String pcr)
	{
		if(pcr == null)
			return null;
		
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
	
	public String loadProtocolFromFile(String path)
	{
		String result = "";
		BufferedReader in = null;
		try {
			in = new BufferedReader(new FileReader(new File(path)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		
		try {
			String line = null;
			while((line = in.readLine()) != null)
			{
				result += line + "\n";
			}
			in.close();
		} catch (IOException e1) {
			// TODO: handle exception
			e1.printStackTrace();
			return null;
		}
		
		return result;
	}
	
	private String getStateString()
	{
		String curState = null;
		if(state == STATE_READY)
			curState = "준비중";
		else if(state == STATE_RUN)
			curState = "실행중";
		
		return curState;
	}
	
	public void printStatus(){
		System.out.println(String.format("상태 : %s , 온도 : %3.1f , elpsedTime : %s", getStateString(), mTemp, getElapsedTime()));
	}
	
	public void startPCR(){
		if(state == STATE_RUN)
			return;
		mTargetTemp = 50.0;
		mPrevTemp = 95.0;
		state = STATE_RUN;
		System.out.println("PCR 시작!");
	}
	
	public void stopPCR(){
		if(state == STATE_READY)
			return;
		state = STATE_READY;
		System.out.println("PCR 중지!");
		setMonitoring(false);
	}
	
	public void setMonitoring(boolean monitor){
		isMonitor = monitor;
	}
	
	public boolean isMonitoring(){
		return isMonitor;
	}
	
	private String getElapsedTime(){
		return String.format("%02d:%02d:%02d", (mElapsedTime/3600)%60, (mElapsedTime/60)%60, mElapsedTime%60);
	}
}
