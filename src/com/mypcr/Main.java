
/**
	String pcr1 = "1	25	40\n" +
  	 		      "2	kk	50\n" +
  			 	  "3	60	100\n";
  				
	String pcr2 = "1	25	40\n" +
  				  "2	40	50\n" +
  				  "3	60\n";
  				  
	1. MyPCR에 다음과 같은 함수를 만들기
	
		public ArrayList<Protocol> makeProtocolList(String pcr)
		위 함수는 이전에 구현한 pcr line 값을 읽어서 pcr list 를 만드는 예제를 이용해야 됩니다..
		매개변수 pcr은 위의 pcr1 과 pcr2를 사용하면 된다.
		
	2. (1)번 예제를 돌릴 시, 에러가 발생한다.
	 에러 중에 NumberFormatException 또는 Array 에러가 발생할 경우,
	 "잘못된 PCR 파일입니다" 라고 출력 후에 return null; 을 호출하시오.
	 
	3. public void showProtocolList(ArrayList<Protocol> list);
	 위의 함수는 이전에 구현한 프로토콜 출력 부분을 이용하면 된다.
	단 매개변수로 받은 list가 null 인지 체크하고, null 일 경우, "잘못된 PCR파일입니다" 라고 출력 후에 return; 호출
  */package com.mypcr;
 
import java.util.ArrayList;

import com.mypcr.emulator.Protocol;

public class Main 
{
	public static void main(String[] args) 
	{
		ArrayList<Protocol> list = new ArrayList<Protocol>();
		
		String pcr = "1	25	40\n" + 
				     "2	40	50\n" +
				     "3	60	100\n";
		
		String pcrs[] = pcr.split("\n");
		for(int i=0; i<pcrs.length; i++)
		{
			String comp[] = pcrs[i].split("\t");
			Protocol p = new Protocol(comp[0], Integer.parseInt(comp[1]), Integer.parseInt(comp[2]));
			list.add(p);
		}
		
		System.out.println("======Protocol======");
		System.out.println("Label   Temp    Time");
		for(int i=0; i<list.size(); i++)
		{
			System.out.println(list.get(i).getLabel() + "\t" +
							   list.get(i).getTemp()  + "\t" +
							   list.get(i).getTime()  + "\t");
		}
	}
}
