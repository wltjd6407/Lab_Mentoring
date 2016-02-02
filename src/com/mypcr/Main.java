
/**
	String pcr1 = "1	25	40\n" +
  	 		      "2	kk	50\n" +
  			 	  "3	60	100\n";
  				
	String pcr2 = "1	25	40\n" +
  				  "2	40	50\n" +
  				  "3	60\n";
  				  
	1. MyPCR�� ������ ���� �Լ��� �����
	
		public ArrayList<Protocol> makeProtocolList(String pcr)
		�� �Լ��� ������ ������ pcr line ���� �о pcr list �� ����� ������ �̿��ؾ� �˴ϴ�..
		�Ű����� pcr�� ���� pcr1 �� pcr2�� ����ϸ� �ȴ�.
		
	2. (1)�� ������ ���� ��, ������ �߻��Ѵ�.
	 ���� �߿� NumberFormatException �Ǵ� Array ������ �߻��� ���,
	 "�߸��� PCR �����Դϴ�" ��� ��� �Ŀ� return null; �� ȣ���Ͻÿ�.
	 
	3. public void showProtocolList(ArrayList<Protocol> list);
	 ���� �Լ��� ������ ������ �������� ��� �κ��� �̿��ϸ� �ȴ�.
	�� �Ű������� ���� list�� null ���� üũ�ϰ�, null �� ���, "�߸��� PCR�����Դϴ�" ��� ��� �Ŀ� return; ȣ��
  */
package com.mypcr;
 
import java.util.ArrayList;

import com.mypcr.emulator.MyPCR;
import com.mypcr.emulator.Protocol;

public class Main 
{
	public static void main(String[] args) 
	{
		String pcr = "1	25	40\n" + 
			     "2	40	50\n" +
			     "3	60	100\n";
	
		String pcr1 = "1 25 40\n" +
		            "2 kk 50\n" +
		         "3 60 100\n";
	
		String pcr2 = "1 25 40\n" +
		        "2 40 50\n" +
		        "3 60\n";

		ArrayList<Protocol> list = new ArrayList<Protocol>();
		MyPCR mypcr = new MyPCR();
		
		
		
		list = mypcr.makeProtocolList(pcr1);
		mypcr.showProtocolList(list);
	}
}
