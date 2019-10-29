import java.util.*;
import java.io.*;
import java.math.*;

class my_sha{
	private static int h0 = 0x67452301;
	private static int h1 = 0xEFCDAB89;
	private static int h2 = 0x98BADCFE;
	private static int h3 = 0x10325476;
	private static int h4 = 0xC3D2E1F0;

	public static String stringToBinary(String input){
		String output = new String();
		String temp = new String();
		for(int i=0; i<input.length(); ++i){
			temp = Integer.toBinaryString((int)input.charAt(i));
			if(temp.length()%8 != 0){
				for(int j=1; j<=(8 - temp.length()); ++j){
					output += "0";
				}
				output += temp;
			}
		}
		return output;
	}

    private static String appendLength(int plaintextLength){
    	String length = Integer.toBinaryString(plaintextLength);
    	String output = new String();
    	if(length.length() != 64){
    		for(int i=0; i<(64-length.length()); ++i){
    			output += "0";
    		}
    	}
    	output += length;
    	return output;
    }

    private static int binaryToDecimal(String x){
    	int sum=0;
    	for(int i=x.length()-1; i>=0;--i){
    		sum += (Character.getNumericValue(x.charAt(i)) * Math.pow(2,x.length()-i-1));
    	}
    	return sum;
    }

	public static void main(String [] args){
		Scanner sc = new Scanner(System.in);
		int[] w = new int[80];
		String plaintext = sc.next();
		int plaintextLength = plaintext.length() * 8;
		String plaintextInBits = stringToBinary(plaintext);
		plaintextInBits += "1";
		while(plaintextInBits.length()%512 != 448){
			plaintextInBits += "0";
		}

		System.out.println("plaintextInBits: " + plaintextInBits + " "+ "length: "+plaintextInBits.length());
		plaintextInBits += appendLength(plaintextLength);

		System.out.println("plaintextInBits: " + plaintextInBits + " "+ "length: "+plaintextInBits.length());

		int j=0;
		for(int i=0; i<16; ++i){
			w[i] = binaryToDecimal(plaintextInBits.substring(j,j+32));
			j += 32;
		}

		for(int i=16; i<80; ++i){
			w[i] = Integer.rotateLeft((w[i-3] ^ w[i-8] ^ w[i-14] ^ w[i-16]), 1);
		}

		// for(int i=0; i<80; ++i){
		// 	System.out.println(w[i]);
		// }

		int a = h0;
		int b = h1;
		int c = h2;
		int d = h3;
		int e = h4;

		for(int i=0; i<80; ++i){
			int f=0;
			int k=0;
			int temp = 0;

			if(i>=0 && i<=19){
				f = ((b & c) | ((~b) & d));
				k = 0x5A827999;
			}else if(i>=20 && i<=39){
				f = (b ^ c ^ d);
				k = 0x6ED9EBA1;
			}else if(i>=40 && i<=59){
				f = (b & c)|(b & d)|(c & d);
				k = 0x8F1BBCDC;
			}else if(i>=60 && i<=79){
				f = (b ^ c ^ d);
				k = 0xCA62C1D6;
			} 

			temp = Integer.rotateLeft(a,5) + f + e + k + w[i];
			e = d;
			d = c;
			c = Integer.rotateLeft(b,30);
			b = a;
			a = temp;

		}
		
		h0 = h0 + a;
		h1 = h1 + b;
		h2 = h2 + c;
		h3 = h3 + d;
		h4 = h4 + e;

		System.out.println(Integer.toHexString(h0)+Integer.toHexString(h1)+Integer.toHexString(h2)+Integer.toHexString(h3)+Integer.toHexString(h4));
	}

}