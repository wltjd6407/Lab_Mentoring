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
}
