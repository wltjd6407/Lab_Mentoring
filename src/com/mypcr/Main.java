/**
 1. startPCR(), stopPCR() 시 , PCR 시작!, PCR 종료!
 2. start시  mPrevTemp = 95.0, mTargetTemp = 50.0 으로 변경 
    run() 함수 내에서 mPrevTemp 까지 온도 도달시키고, 그 후에 mTargetTemp까지 온도가 도달하도록 구현,
    mTargetTemp 까지 온도가 도달할 경우, 자동으로 stop 되도록 구현
 3. startPCR(), stopPCR() 시, 현재 상태가 맞지 않은 경으 그냥 무시하도록 구현
 	ex) start 했는데 , state가 run이면 그냥 return 하도록 구현 stop시도
 4. private int mElpsedTime 선언 후, 0으로 초기화
 	run()함수에서 state가 run일 경우 1sec 마다 증가시킴
 	printStateus() 함수에서 String.format에다 elapsedTime : %s 추가
 	
 	mTemp += 0.01 로 변경
 	getElapsedTime()를 다음과 같이 구현하시오.
 	private String getElapsedTime();
 **/
package com.mypcr;
 
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.mypcr.emulator.MyPCR;

public class Main 
{
	public static void main(String[] args) 
	{
		final MyPCR mypcr = new MyPCR();
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
		mypcr.start();
		while(true)
		{
			try 
			{
				String input = in.readLine();
				if(input.equals("start")){
					mypcr.startPCR();
				}
				else if(input.equals("stop")){
					mypcr.stopPCR();
				}
				else if(input.equals("print")){
					mypcr.printStatus();
				}
				else if(input.equals("monitor"))
				{
					mypcr.setMonitoring(true);
					Thread t = new Thread(){
						public void run(){
							while(mypcr.isMonitoring()){
								try{
									Thread.sleep(10);
								}catch(InterruptedException e){
									e.printStackTrace();
								}
								mypcr.printStatus();
							}
						}
					};
					
					t.start();
					in.readLine();
					mypcr.setMonitoring(false);
				}
			}catch (IOException e) {
				e.printStackTrace();	
			}
				
		}
		
	}
}
