package matching;
import java.util.Random;
import java.util.Scanner;
import edu.princeton.cs.algs4.Alphabet;


////////////////////////////Naive Matching Algorithm////////////////////////////////////

public class naiveMatcher {
	static int d_finite = 65536;
	
	public static void stringMatch(String S, String T){
		int n = S.length();
		int m= T.length();
		int matchCount = 0;
	
		for(int s =0; s <= n-m; s++){	//for the length m in length n -> search each block of size m in n 
			int i;
			for( i =0; i < m; i++){ //for the length of each potential match check each i for match
				if( S.charAt(i+s)!= T.charAt(i)){ // check match P[1...m] != S[s+1...s+m]
					break; // stop looking if true
				}
			}
			if(i == m){ // if they P[1...m] == S[s+1...s+m] check that the current index is same length as substring
				//System.out.println("Substring found beginning at index " + s + " in string S"); //return index if true
				matchCount++; // add one to match count
			}
		}
		if(matchCount == 0){
			System.out.println("No matches found");
		}
		System.out.println(matchCount + " matches found");
		
	}
//////////////////////////////////////////////////////////////////////////////////////	
///////////////////////////Rabin Karp Algorithm//////////////////////////////////////
	
	public static void rabinKarpMatcher(String T, String P, int d, int q){ // d - radix (# char in alphabet) q - prime #
		int n = T.length();
		int m = P.length();
		int h = 1; 	//value of the digit "1" in the high-order position of an m-digit text window
		int p = 0; 	//decimal value of P
		int t =0; 	//decimal value of T
		int matchCount = 0;
		int j;
		
		for(int x = 0; x < m-1; x++){
			h = (h*d) %q;
		}
		//PRE-PROCESSING
		for(int i = 0; i < m; i++){
			p = (d*p + P.charAt(i))%q;
			t = (d*t + T.charAt(i))%q;
		}
		//MATCHING
		for(int s = 0; s <= n-m; s++){
			
			if( p == t){ 
				
				for( j = 0; j < m; j++){
					
					if(T.charAt(j+s)!= P.charAt(j)){
						break;
						}
					
				}
					if( j == m){
						//System.out.println("Substring found beginning at index " + s + " in string S"); //return index if true
						matchCount++; // add one to match count
					}
			}
			
			if( s < n-m){
				t = (d*(t-T.charAt(s)*h)+T.charAt(s+m))%q;
				 if (t < 0) 
		                t = (t + q); 
			}
		}
		System.out.println(matchCount + " matches found");
	}

//////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////Finite Automa//////////////////////////////////////
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
			for(s = 0; s < d_finite; s++){
				tfunc[state][s] = nextState(p, m, state, s);
			}	
		}
	}
	
	static  void faMatch(String T, String P){
		int n = T.length();
		int m = P.length();
		int state = 0;
		int matchCount = 0;
		int s;
		
		int[][] transitionFunc = new int[m+1][d_finite];
		
		transition(P, m, transitionFunc);
		
		for(s = 0; s < n; s++){
			state = transitionFunc[state][T.charAt(s)];
			if(state == m){
				//System.out.println("Substring found beginning at index " + (s-m+1) + " in string S");
				matchCount++;
			}
		}
		System.out.println(matchCount + " matches found");
	}
	
	
	
	
	
///////////////////////////////Main Test//////////////////////////////////////////////	
	public static void main(String[] args){
	/*
	* To complicate matters further, we will not presume that our strings are from any single alphabet, 
	* but from an alphabet identified at run time. We know that the important distinction among
	*  alphabets is not the set of characters, but the number of characters: the length of the alphabet, r.
    */
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter string length n");
		int n = sc.nextInt();
		System.out.println("Enter substring length m");
		int m;
		//if(sc.nextInt() > n){
			//System.out.println("m must be less than or equal to n");
		//}
		//else{
			 m = sc.nextInt();
		//}
		System.out.println("Enter alphabet size r");
		int r = sc.nextInt();
		
		System.out.println("How many times would you like to run the algoiorthms?");
		int x = sc.nextInt();
		
		sc.close();
		
		Random rand = new Random();
		String S = null;
		String T = null;
		
		int[] str = new int[n];
		int[] sub = new int[m];
		
		/*
		for(int i = 0; i < n; i++){
			str[i] = rand.nextInt(r); // for string, array size n filled with random numbers in range r
		}
		for(int i = 0; i < m; i++){
			sub[i] = rand.nextInt(r); // for substring, array size m filled with random numbers in range r 
		}
		
		
		// generate random numbers between 1 & size of alphabet in int array
		// ex: String s = Alphabet.DNA.toChars(int[])
		//will take each index of int array translate to corresponding character in alphabet
		//and will combine all character to make string s
		
		// BINARY ALPHABET
				if ( r == 2){
					System.out.println("You have chosen the binary alphabet");
					S = Alphabet.BINARY.toChars(str);
					T = Alphabet.BINARY.toChars(sub);
				}
		// DNA ALPHABET
				else if(r == 4){
					System.out.println("You have chosen the DNA alphabet");
					S = Alphabet.DNA.toChars(str);
					T = Alphabet.DNA.toChars(sub);
				}
		// ASCII ALPHABET
				else if(r == 128){
					System.out.println("You have chosen the ASCII alphabet");
					S = Alphabet.ASCII.toChars(str);
					T = Alphabet.ASCII.toChars(sub);
				}
		// ENGLISH ALPHABET 
				else if(r == 26){
					System.out.println("You have chosen the English alphabet");
					S = Alphabet.LOWERCASE.toChars(str);
					T = Alphabet.LOWERCASE.toChars(sub);
				}
		// HEXADECIMAL
				else if(r == 16){
					System.out.println("You have chosen the hexadecimal alphabet");
					S = Alphabet.HEXADECIMAL.toChars(str);
					T = Alphabet.HEXADECIMAL.toChars(sub);
				}
		//DECIMAL 
				else if( r == 10){
					System.out.println("You have chosen the decimal alphabet");
					S = Alphabet.DECIMAL.toChars(str);
					T = Alphabet.DECIMAL.toChars(sub);
				}
		//BASE 64
				else if( r == 64){
					System.out.println("You have chosen the BASE64 alphabet");
					S = Alphabet.BASE64.toChars(str);
					T = Alphabet.BASE64.toChars(sub);
				}
		//UNICODE16
				else if( r == 65536){
					System.out.println("You have chosen the UNICODE16 alphabet");
					S = Alphabet.UNICODE16.toChars(str);
					T = Alphabet.UNICODE16.toChars(sub);
				}
				else{
					System.out.println("I'm sorry I don't know what alphabet is this length");
				}
	*/
		
	//System.out.println("Randomly generated string S of length n: " + S);
	//System.out.println("Randomly generated substring T of length m: " + T);
	
	long time_naive = 0, time_finite = 0, time_rabin = 0;

	Long start_time, diff_time, start_time2, diff_time2, start_time3, diff_time3;
	int i;
	
	for( i = 0; i < x; i++){
		
		for(int j = 0; j < n; j++){
			str[j] = rand.nextInt(r); // for string, array size n filled with random numbers in range r
		}
		for(int j = 0; j < m; j++){
			sub[j] = rand.nextInt(r); // for substring, array size m filled with random numbers in range r 
		}
		if ( r == 2){
			
			S = Alphabet.BINARY.toChars(str);
			T = Alphabet.BINARY.toChars(sub);
		}
// DNA ALPHABET
		else if(r == 4){
		
			S = Alphabet.DNA.toChars(str);
			T = Alphabet.DNA.toChars(sub);
		}
// ASCII ALPHABET
		else if(r == 128){
			System.out.println("You have chosen the ASCII alphabet");
			S = Alphabet.ASCII.toChars(str);
			T = Alphabet.ASCII.toChars(sub);
		}
// ENGLISH ALPHABET 
		else if(r == 26){
			System.out.println("You have chosen the English alphabet");
			S = Alphabet.LOWERCASE.toChars(str);
			T = Alphabet.LOWERCASE.toChars(sub);
		}
// HEXADECIMAL
		else if(r == 16){
			
			S = Alphabet.HEXADECIMAL.toChars(str);
			T = Alphabet.HEXADECIMAL.toChars(sub);
		}
//DECIMAL 
		else if( r == 10){
			
			S = Alphabet.DECIMAL.toChars(str);
			T = Alphabet.DECIMAL.toChars(sub);
		}
//BASE 64
		else if( r == 64){
			
			S = Alphabet.BASE64.toChars(str);
			T = Alphabet.BASE64.toChars(sub);
		}
//UNICODE16
		else if( r == 65536){
			
			S = Alphabet.UNICODE16.toChars(str);
			T = Alphabet.UNICODE16.toChars(sub);
		}
		else{
			System.out.println("I'm sorry I don't know what alphabet is this length");
		}

		
		
		
		start_time = System.nanoTime();
		stringMatch(S, T);
		diff_time = System.nanoTime() - start_time;
		time_naive = time_naive + diff_time;
		
		start_time2 = System.nanoTime();
		rabinKarpMatcher(S, T, r, 101);
		diff_time2 = System.nanoTime() - start_time2;
		time_rabin = time_rabin + diff_time2;
		
		start_time3 = System.nanoTime();
		faMatch(S,T);
		diff_time3 = System.nanoTime() - start_time3;
		time_finite = time_finite + diff_time3;
		
		
		
		
	}
				
				
	//Long start_time;
	//start_time = System.nanoTime();
	//stringMatch(S, T);
	//diff_time = System.nanoTime() - start_time;
	//System.out.println("Naive algortihm took " + diff_time + " nano seconds");
	//time_naive = time_naive + diff_time;
	
	long naive_avg = time_naive / i;
	long rabin_avg = time_rabin / i;
	long finite_avg = time_finite / i;
	
	
	System.out.println("The average time for naive was " + naive_avg);
	System.out.println("The average time for rabin was " + rabin_avg);
	System.out.println("The average time for finite was " + finite_avg);
	
	
	//Long start_time2;
	//start_time2 = System.nanoTime();
	//rabinKarpMatcher(S, T, r, 101);
	//Long diff_time2 = System.nanoTime() - start_time2;
	//System.out.println("Rabin Karp algorithm took " + diff_time2 + " nano seconds");
	
	//Long start_time3;
	//start_time3 = System.nanoTime();
	//faMatch(S,T);
	//Long diff_time3 = System.nanoTime() - start_time3;
	//System.out.println("Finite Automa algorithm took " + diff_time3 + " nano seconds");
		
		

	}

}
