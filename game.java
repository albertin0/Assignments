/* package whatever; // don't place package name! */

import java.util.*;
import java.lang.*;
import java.io.*;

/* Name of the class has to be "Main" only if the class is public. */
class Game
{
	public static void main (String[] args) throws java.lang.Exception
	{
		// your code goes here
		Scanner sc = new Scanner(System.in);
		String input = sc.nextLine();
		System.out.println(input);
		ArrayList<Card> deck = new ArrayList<Card>();
		int i;
		for(i=1;i<=13;i++)	{
			deck.add(new Card(i,"SPADES"));
			deck.add(new Card(i,"HEART"));
			deck.add(new Card(i,"CLUB"));
			deck.add(new Card(i,"DIAMONDS"));
		}
		for(Card c:deck)
			System.out.println(c.print());
	}
}

class Card	
{
	public int val=0;
	public String suit="";
	public Card(int i,String s)	{
		val = i;
		suit = s;
	}
	public String print()	{
		return String.valueOf(val)+" "+suit;
	}
}
