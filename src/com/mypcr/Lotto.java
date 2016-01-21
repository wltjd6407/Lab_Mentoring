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
		case 1: System.out.println(rst + "개 일치합니다. 6등 당첨입니다." );break;
		case 2: System.out.println(rst + "개 일치합니다. 5등 당첨입니다." );break;
		case 3: System.out.println(rst + "개 일치합니다. 4등 당첨입니다." );break;
		case 4: System.out.println(rst + "개 일치합니다. 3등 당첨입니다." );break;
		case 5: System.out.println(rst + "개 일치합니다. 2등 당첨입니다." );break;
		case 6: System.out.println(rst + "개 일치합니다. 1등 당첨입니다." );break;
		default : System.out.println("아무것도 안맞았다. 꽝임");
		}
		
	}
}
