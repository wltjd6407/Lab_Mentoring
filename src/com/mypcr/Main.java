/**
 1. startPCR(), stopPCR() �� , PCR ����!, PCR ����!
 2. start��  mPrevTemp = 95.0, mTargetTemp = 50.0 ���� ���� 
    run() �Լ� ������ mPrevTemp ���� �µ� ���޽�Ű��, �� �Ŀ� mTargetTemp���� �µ��� �����ϵ��� ����,
    mTargetTemp ���� �µ��� ������ ���, �ڵ����� stop �ǵ��� ����
 3. startPCR(), stopPCR() ��, ���� ���°� ���� ���� ���� �׳� �����ϵ��� ����
 	ex) start �ߴµ� , state�� run�̸� �׳� return �ϵ��� ���� stop�õ�
 4. private int mElpsedTime ���� ��, 0���� �ʱ�ȭ
 	run()�Լ����� state�� run�� ��� 1sec ���� ������Ŵ
 	printStateus() �Լ����� String.format���� elapsedTime : %s �߰�
 	
 	mTemp += 0.01 �� ����
 	getElapsedTime()�� ������ ���� �����Ͻÿ�.
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
