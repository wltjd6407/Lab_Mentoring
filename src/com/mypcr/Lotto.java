package com.mypcr;

public class Lotto 
{
	public Lotto()
	{
		
	}
	
	int[] createLotto()
	{
		int []at = new int[7];
		
		for(int i=0; i<at.length; i++)
		{
			at[i] = (int)(Math.random()*45)+1;
			for(int j=0; j<i; j++)
			{
				if(at[i] == at[j])
				{
					i--;
					break;
				}
			}
		}

		return at;
	}
	
	void pirntLotto(int[] ltnum)
	{
		int cnt = 0;
		
		for(int i : ltnum)
		{
			if (cnt++ < ltnum.length -1)
				System.out.print(i + " , ");
			else
				System.out.println(i); 
		}
	}
	
	void checkLotto(int[] ck, int []cknum)
	{
		int rst = 0;
		for(int i=0; i<ck.length; i++)
		{
			for(int j=0; j<cknum.length; j++)
			{
				if(ck[i] == cknum[j])
					rst++;
			}
		}
		switch(rst)
		{
		case 1: System.out.println(rst + "�� ��ġ�մϴ�. 6�� ��÷�Դϴ�." );break;
		case 2: System.out.println(rst + "�� ��ġ�մϴ�. 5�� ��÷�Դϴ�." );break;
		case 3: System.out.println(rst + "�� ��ġ�մϴ�. 4�� ��÷�Դϴ�." );break;
		case 4: System.out.println(rst + "�� ��ġ�մϴ�. 3�� ��÷�Դϴ�." );break;
		case 5: System.out.println(rst + "�� ��ġ�մϴ�. 2�� ��÷�Դϴ�." );break;
		case 6: System.out.println(rst + "�� ��ġ�մϴ�. 1�� ��÷�Դϴ�." );break;
		default : System.out.println("�ƹ��͵� �ȸ¾Ҵ�. ����");
		}
		
	}
}
