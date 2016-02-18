package com.mypcr.emulator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 160211
  숙제 1
 mProtocolList = makeProtocolList("PCR.txt");
  출력 형태 변경(현재 동작하고 있는 프로토콜을 출력)
 Label : %s, TargetTemp : %3.1f , Remain : %ds 
  위의 출력 추가
  만약 PCR이 동작중이지 않은 경우, 이전 출력 형태를 유지
  숙제2
 microPCR_firmware에서 branch를 pic18f4550_emul로 변경하여 
 pwmvalue 계산하는 부분 참조하여 온도 증감 값 정하기.
 **/
public class MyPCR extends Thread
{
	private double mTemp;
	private double mPrevTemp, mTargetTemp;
	private int state;
	
	private static final int STATE_READY = 0x00;
	private static final int STATE_RUN = 0x01;
	
	private static final double DEFAULT_TEMP = 25.0;
	private boolean isMonitor = false;
	private int mElapsedTime = 0;
	private int idx = 0;
	private boolean flag = false;
	private float kp = 0, ki = 0, kd = 0;
	private float integralMax = (float) 2000.0;
	private double lastIntegral = 0;
	private double lastError = 0;
	private ArrayList<Protocol> mProtocolList = null;	
	private int mRemainTime = 0;
	/**
	 1. temps 인덱스 접근 변수 int idx 생성 후 0으로 초기화 , pcrstart할 때 targettemp = temps[idx]; 
	 2. prevtemp가 targettemp보다 낮을 때 temprise 값이 양의 값  그리고 온도가 증가 중이었다 라는 것을 알리기 위한 flag 값 false
	    prevtemp가 targettemp 보다 큰경우 temprise 값이 음의 값 그리고 온도가 증가 중이었다 라는 것을 알리기 위한 flag 값 true
	    
	 2. mtemp가 targettemp에 도달 했을 때 
	  3-1 prevtemp 에 targettemp를 대입해주고 인덱스 접근 변수 idx를 1 증가
	  3-2 targettemp 에 temp[idx] 대입
	  3-2 idx 가 temps의 사이즈만큼 증가했을 경우 pcrstop
	 
	 **/
	
	public MyPCR(){
		mTemp = DEFAULT_TEMP;
		mPrevTemp = DEFAULT_TEMP;
		mTargetTemp = DEFAULT_TEMP;
		state = STATE_READY;
		mProtocolList = makeProtocolList(loadProtocolFromFile("PCR.txt"));
	}
	
	public void run(){
		int sec = 0;
		
		while(true){
			try {
				Thread.sleep(100);	
			} catch (InterruptedException e){
				e.printStackTrace();
			}
			
			
			
			
			if(state == STATE_RUN){
				PID_Control();
				sec++;
				if( mPrevTemp > mTargetTemp){
					flag = true;
				}
				if( mPrevTemp < mTargetTemp){
					flag = false;
				}
				
				if(sec > 9){
					sec = 0;
					mElapsedTime++;
				}
					
			
				
				if( (mTemp > mTargetTemp) && !flag){
					
					idx++;
					System.out.println(idx);
					if(idx >= mProtocolList.size()){
						stopPCR();
						continue;
					}
					mPrevTemp = mTargetTemp;
					mTargetTemp = mProtocolList.get(idx).getTemp();
					mRemainTime -= mProtocolList.get(idx-1).getTime();
				}
				if( (mTemp < mTargetTemp) && flag){
					
					idx++;
					if(idx >= mProtocolList.size()){
						stopPCR();
						continue;
					}
					mPrevTemp = mTargetTemp;
					mTargetTemp = mProtocolList.get(idx).getTemp();
					mRemainTime -= mProtocolList.get(idx-1).getTime();
				}
			}
			if(state == STATE_READY){
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
			System.out.println("잘못된 PCR파일입니다.(numberformat)");
			return null;
		} catch (ArrayIndexOutOfBoundsException e1)
		{
			System.out.println("잘못된 PCR파일입니다.(arrayindexout)");
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
		if(state == STATE_READY)
			System.out.println(String.format("상태 : %s , 온도 : %3.1f , elpsedTime : %s", getStateString(), mTemp, getElapsedTime()));
		if(state == STATE_RUN)
			System.out.println(String.format("Label : %s, TargetTemp : %3.1f, Remain : %d, 상태 : %s , 온도 : %3.1f , elpsedTime : %s", mProtocolList.get(idx).getLabel(), mTargetTemp, mRemainTime, getStateString(), mTemp, getElapsedTime()));
	}
	
	public void startPCR(){
		if(state == STATE_RUN)
			return;
		idx = 0;
		mTargetTemp = mProtocolList.get(0).getTemp();
		mPrevTemp = DEFAULT_TEMP;
		state = STATE_RUN;
		mElapsedTime = 0;
		for(int i=0; i<mProtocolList.size(); i++){
			mRemainTime += mProtocolList.get(i).getTime();
		}
		
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
		return String.format("%02d:%02d:%02d", (mElapsedTime/3600)%60, mElapsedTime/60, mElapsedTime%60);
	}
	
	private void PID_Control(){
		double currentErr = 0, proportional = 0, integral = 0;
		double derivative = 0;
		int pwmValue = 0xffff;
		double emul_value = 0.0;
		if(state==STATE_RUN){
			if(mPrevTemp == 25.0 && mTargetTemp == 95.0){
				kp = 460;	ki = (float) 0.2;	kd = 3000;	
			}
			if(mPrevTemp == 95.0 && mTargetTemp == 60.0){
				kp = 250;	ki = (float) 0.3;	kd = 1000;
			}
			if(mPrevTemp == 60.0 && mTargetTemp == 72.0){
				kp = 350;	ki = (float) 0.11;	kd = 3000;
			}
			if(mPrevTemp == 72.0 && mTargetTemp == 95.0){
				kp = 460;	ki = (float) 0.18;	kd = 3000;
			}
			if(mPrevTemp == 95.0 && mTargetTemp == 50.0){
				kp = 500;	ki = (float) 0.3;	kd = 1000;
			}
		}
		
		currentErr = mTargetTemp - mTemp;
		proportional = currentErr;
		integral = currentErr + lastIntegral;
		
		if( integral > integralMax)
			integral = integralMax;
		else if( integral < -integralMax)
			integral = -integralMax;
		
		derivative = currentErr - lastError;
		pwmValue = (int) (kp*proportional +
					ki * integral +
					kd * derivative);
		
		if(pwmValue > 1023)
			pwmValue = 1023;
		else if(pwmValue < 0)
			pwmValue = 0;
		
		lastError = currentErr;
		lastIntegral = integral;
		
		if( pwmValue == 0 )
			emul_value = -0.1;
		else
			emul_value = (pwmValue/1023.);
		
		mTemp += emul_value;
	}
}
