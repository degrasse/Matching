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
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String T = "askjhfdvgevababeljdfl";
		String P = "babe";
		System.out.println(T);
		System.out.println(P);
		rabinKarpMatcher(T, P, 26, 7);
		
	}

}
