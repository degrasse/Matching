package matching;

public class rabinKarp {
	
	public static void rabinKarpMatcher(String T, String P, int d, int q){ // d - radix (# char in alphabet) q - prime #
		int n = T.length();
		int m = P.length();
		int h = 1; 	//value of the digit "1" in the high-order position of an m-digit text window
		int p = 0; 	//decimal value of P
		int t =0; 	//decimal value of T
		int j;
		
		for(int x = 0; x < m-1; x++){
			h = (h*10) %q;
		}
		//PRE-PROCESSING
		for(int i = 0; i < m; i++){
			p = (10*p + P.charAt(i))%q;
			t = (10*t + T.charAt(i))%q;
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
						System.out.println("Substring found beginning at index " + s + " in string S"); //return index if true
						//matchCount++; // add one to match count
					}
			}
			
			if( s < n-m){
				t = (10*(t-T.charAt(s)*h)+T.charAt(s+m))%q;
				 if (t < 0) 
		                t = (t + q); 
			}
		}
	}
	
	/*
	 * int search (string S, length N, substring T, length m, prime: p) 
	 * TModp = 0
	SubModp = 0 
	powerOf10Modp = 1
for j from m-1 downto 1
SubModp = (SubModp + S[i] * powerOfTenModp) mod p 
TModp = (TModp + T[i] * powerOfTenModp) mod p 
powerOfTenModp = (powerOfTenModp * 10) mod p
found = false
for i from 0 to N - m
if (SubModp = = TModp) for j from 0 to m-1
if T[j] != S[i+j] break else
if j == m-1 return i
SubModp = (((SubModp*10- S[i]*10m + S[i + m])modp) + p)modp
	 */
	/*
	void searchg(String S, String T, int p){
		int TmodP = 0;
		int Submodp = 0;
		int powerOf10Modp = 1;
		int m = T.length();
		int n = S.length();
		
		
		for( int j = m-1; j > 1; j--){
			Submodp = ( Submodp + S.charAt(j) * powerOf10Modp) % p;
			TmodP = (TmodP + T.charAt(j) * powerOf10Modp) % p;
			powerOf10Modp = (powerOf10Modp * 10) % p;
		}
		found ==
		
		
		
	}
	*/
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String T = "askjhfdvgevababeljdfl";
		String P = "babe";
		System.out.println(T);
		System.out.println(P);
		rabinKarpMatcher(T, P, 26, 7);
		
	}

}
