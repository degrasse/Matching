package matching;

import java.util.Scanner;

public class finiteAutoma {

	static int d = 256;
	public static int nextState(String p, int m, int state,int c){
		//int c = character to compare with current character in patten p
		//int m = length of p
		int nextstate;
		int s;

		if( state < m && c == p.charAt(state)  ){
			return state+1;
		}
		
		for(nextstate = state; nextstate > 0; nextstate--){
			if(p.charAt(nextstate-1) == c){
				for(s = 0; s < nextstate-1; s++)
					if(p.charAt(s) != p.charAt(state-nextstate+1+s))
						break;		
					
					if(s == nextstate-1)
						return nextstate;
			}
		}
		return 0;
	}
	
	static void transition(String p, int m, int tfunc[][]){ // m = p.length //d = size of alphabet
		int state;
		int s;
		
		for(state = 0; state <= m; state++){
			for(s = 0; s < d; s++){
				tfunc[state][s] = nextState(p, m, state, s);
			}	
		}
	}
	
	static  void faMatch(String T, String P){
		int n = T.length();
		int m = P.length();
		int state = 0;
		int s;
		
		int[][] transitionFunc = new int[m+1][d];
		
		transition(P, m, transitionFunc);
		
		for(s = 0; s < n; s++){
			state = transitionFunc[state][T.charAt(s)];
			if(state == m){
				System.out.println("Substring found beginning at index " + (s-m+1) + " in string S");
			}
		}
	}
	
	
	
//////////////////////////////////////////////////////////////////////////////////////
	public static void main(String[] args){
		

		
		String T = "sjkfhrygywgogroybabbabekjjhdfbabe";
		String P = "babe";
		System.out.println("enter d");
		Scanner sc = new Scanner(System.in);
		
		//int d = sc.nextInt();
		
		faMatch(T, P);
		
		
		
	}
	
}

	