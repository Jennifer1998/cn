import java.util.*;
import java.io.*;
import java.math.*;
import java.lang.Math;

class my_des{						
	private static String keys[] = new String[16];
	private static int[] ip = { 58, 50, 42, 34, 26, 18, 10, 2, 60, 52, 44, 36,
            28, 20, 12, 4, 62, 54, 46, 38, 30, 22, 14, 6, 64, 56, 48, 40, 32,
            24, 16, 8, 57, 49, 41, 33, 25, 17, 9, 1, 59, 51, 43, 35, 27, 19,
            11, 3, 61, 53, 45, 37, 29, 21, 13, 5, 63, 55, 47, 39, 31, 23, 15, 7 };
    private static int[] ip_inverse = { 40, 8, 48, 16, 56, 24, 64, 32, 39, 7, 47,
            15, 55, 23, 63, 31, 38, 6, 46, 14, 54, 22, 62, 30, 37, 5, 45, 13,
            53, 21, 61, 29, 36, 4, 44, 12, 52, 20, 60, 28, 35, 3, 43, 11, 51,
            19, 59, 27, 34, 2, 42, 10, 50, 18, 58, 26, 33, 1, 41, 9, 49, 17,
            57, 25 };
    private static int[][][] s_table = {
            { 		{ 14, 4, 13, 1, 2, 15, 11, 8, 3, 10, 6, 12, 5, 9, 0, 7 },
                    { 0, 15, 7, 4, 14, 2, 13, 1, 10, 6, 12, 11, 9, 5, 3, 8 },
                    { 4, 1, 14, 8, 13, 6, 2, 11, 15, 12, 9, 7, 3, 10, 5, 0 },
                    { 15, 12, 8, 2, 4, 9, 1, 7, 5, 11, 3, 14, 10, 0, 6, 13 }
            },
            { 		{ 15, 1, 8, 14, 6, 11, 3, 2, 9, 7, 2, 13, 12, 0, 5, 10 },
                    { 3, 13, 4, 7, 15, 2, 8, 14, 12, 0, 1, 10, 6, 9, 11, 5 },
                    { 0, 14, 7, 11, 10, 4, 13, 1, 5, 8, 12, 6, 9, 3, 2, 15 },
                    { 13, 8, 10, 1, 3, 15, 4, 2, 11, 6, 7, 12, 0, 5, 14, 9 }
            },
            { 		{ 10, 0, 9, 14, 6, 3, 15, 5, 1, 13, 12, 7, 11, 4, 2, 8 },
                    { 13, 7, 0, 9, 3, 4, 6, 10, 2, 8, 5, 14, 12, 11, 15, 1 },
                    { 13, 6, 4, 9, 8, 15, 3, 0, 11, 1, 2, 12, 5, 10, 14, 7 },
                    { 1, 10, 13, 0, 6, 9, 8, 7, 4, 15, 14, 3, 11, 5, 2, 12 }
            },
            { 		{ 7, 13, 14, 3, 0, 6, 9, 10, 1, 2, 8, 5, 11, 12, 4, 15 },
                    { 13, 8, 11, 5, 6, 15, 0, 3, 4, 7, 2, 12, 1, 10, 14, 9 },
                    { 10, 6, 9, 0, 12, 11, 7, 13, 15, 1, 3, 14, 5, 2, 8, 4 },
                    { 3, 15, 0, 6, 10, 1, 13, 8, 9, 4, 5, 11, 12, 7, 2, 14 }
            },
            { 		{ 2, 12, 4, 1, 7, 10, 11, 6, 8, 5, 3, 15, 13, 0, 14, 9 },
                    { 14, 11, 2, 12, 4, 7, 13, 1, 5, 0, 15, 10, 3, 9, 8, 6 },
                    { 4, 2, 1, 11, 10, 13, 7, 8, 15, 9, 12, 5, 6, 3, 0, 14 },
                    { 11, 8, 12, 7, 1, 14, 2, 13, 6, 15, 0, 9, 10, 4, 5, 3 }
            },
            { 		{ 12, 1, 10, 15, 9, 2, 6, 8, 0, 13, 3, 4, 14, 7, 5, 11 },
                    { 10, 15, 4, 2, 7, 12, 9, 5, 6, 1, 13, 14, 0, 11, 3, 8 },
                    { 9, 14, 15, 5, 2, 8, 12, 3, 7, 0, 4, 10, 1, 13, 11, 6 },
                    { 4, 3, 2, 12, 9, 5, 15, 10, 11, 14, 1, 7, 6, 0, 8, 13 }

            },
            { 		{ 4, 11, 2, 14, 15, 0, 8, 13, 3, 12, 9, 7, 5, 10, 6, 1 },
                    { 13, 0, 11, 7, 4, 9, 1, 10, 14, 3, 5, 12, 2, 15, 8, 6 },
                    { 1, 4, 11, 13, 12, 3, 7, 14, 10, 15, 6, 8, 0, 5, 9, 2 },
                    { 6, 11, 13, 8, 1, 4, 10, 7, 9, 5, 0, 15, 14, 2, 3, 12 }
            },
            { 		{ 13, 2, 8, 4, 6, 15, 11, 1, 10, 9, 3, 14, 5, 0, 12, 7 },
                    { 1, 15, 13, 8, 10, 3, 7, 4, 12, 5, 6, 11, 0, 14, 9, 2 },
                    { 7, 11, 4, 1, 9, 12, 14, 2, 0, 6, 10, 13, 15, 3, 5, 8 },
                    { 2, 1, 14, 7, 4, 10, 8, 13, 15, 12, 9, 0, 3, 5, 6, 11 }

            } };
    private static int[] pc1 = { 57, 49, 41, 33, 25, 17, 9, 1, 58, 50, 42, 34,
            26, 18, 10, 2, 59, 51, 43, 35, 27, 19, 11, 3, 60, 52, 44, 36, 63,
            55, 47, 39, 31, 23, 15, 7, 62, 54, 46, 38, 30, 22, 14, 6, 61, 53,
            45, 37, 29, 21, 13, 5, 28, 20, 12, 4 };
    private static int[] pc2 = { 14, 17, 11, 24, 1, 5, 3, 28, 15, 6, 21, 10,
            23, 19, 12, 4, 26, 8, 16, 7, 27, 20, 13, 2, 41, 52, 31, 37, 47, 55,
            30, 40, 51, 45, 33, 48, 44, 49, 39, 56, 34, 53, 46, 42, 50, 36, 29,
            32 };
    private static int[] expansion_permutation = { 32, 1, 2, 3, 4, 5, 4, 5, 6, 7, 8, 9, 8,
            9, 10, 11, 12, 13, 12, 13, 14, 15, 16, 17, 16, 17, 18, 19, 20, 21,
            20, 21, 22, 23, 24, 25, 24, 25, 26, 27, 28, 29, 28, 29, 30, 31, 32,
            1 };
    private static int[] P_func = { 16, 7, 20, 21, 29, 12, 28, 17, 1, 15, 23, 26, 5,
            18, 31, 10, 2, 8, 24, 14, 32, 27, 3, 9, 19, 13, 30, 6, 22, 11, 4,
            25};
    private static int[] keyShift = { 1, 1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 1 };

    private static String decimalToBinary(int n){
    	String binary = Integer.toBinaryString(n); //Use this function to convert int to binary string
    	String newBinary = new String();
    	int remainder = binary.length() % 8; //Check if it has 8 bits. Else find remainder and prepend 0's.
    	if(remainder != 0){
    		for(int i=1; i<=(8 - remainder); ++i){
    			newBinary += 0;
    		}
    	}
    	newBinary += binary;
    	return newBinary;
    }

    private static String stringToBinary(String plaintext){
    	String plaintextInBits = new String();
    	for(int i=0; i<plaintext.length(); ++i){
    		plaintextInBits += decimalToBinary((int)plaintext.charAt(i));
    		//plaintextInBits += " ";
    	}
    	return plaintextInBits;
    }

    private static String findPermutation(String inputString, int[] permutationTable){
    	String outputString = new String();
    	for(int i=0; i<permutationTable.length; ++i){
    		outputString += inputString.charAt(permutationTable[i] - 1); //Find the character in input string at the position specified by the values of the respective permutation table
    	}
    	return outputString;
    }

    private static String hexToBinary(String key){
    	String cur = new String();
    	String hexKey = new String();
    	for(int i=0; i<key.length(); ++i){
    		switch(key.charAt(i)){
	    		case 'A':{
	    			cur = decimalToBinary(10);
	    		};break;
	    		case 'B':{
	    			cur = decimalToBinary(11);
	    		};break;
	    		case 'C':{
	    			cur = decimalToBinary(12);
	    		};break;
	    		case 'D':{
	    			cur = decimalToBinary(13);
	    		};break;
	    		case 'E':{
	    			cur = decimalToBinary(14);
	    		};break;
	    		case 'F':{
	    			cur = decimalToBinary(15);
	    		};break;
	    		default:{
	    			cur = decimalToBinary(key.charAt(i));
	    		}
	    	}
	    	hexKey += cur.substring(cur.length()-4); //Getting the last four characters
    	}
    	return hexKey;
    }

    private static String leftCircularShift(String str, int bits){
    	//return (str.substring(str.length()-bits) + str.substring(0,bits));
    	return (str.substring(bits) + str.substring(0,bits));
    }

    private static void generateSubKeys(String pc1_keyInBits){
    	for(int i=1; i<=16; ++i){
    		String keyLeft = pc1_keyInBits.substring(0,28);
    		String keyRight = pc1_keyInBits.substring(28,56);
    		System.out.println("Left: "+ keyLeft + " Right: " + keyRight);
    		keyLeft = leftCircularShift(keyLeft,keyShift[i-1]);
    		keyRight = leftCircularShift(keyRight,keyShift[i-1]);
    		String keyForNextRound = keyLeft + keyRight;
    		String pc2_keyInBits = findPermutation(keyForNextRound, pc2);
    		keys[i-1] = pc2_keyInBits;
    		pc1_keyInBits = keyForNextRound;
    	}
    }

    private static String findXor(String a, String b){
    	String output = new String();
    	for(int i=0; i<a.length(); ++i){
    		if(a.charAt(i) == b.charAt(i)) output += "0";
    		else output += "1";
    	}
    	return output;
    }

    private static int binaryToDecimal(String x){
    	int sum=0;
    	for(int i=x.length()-1; i>=0;--i){
    		sum += (Character.getNumericValue(x.charAt(i)) * Math.pow(2,x.length()-i-1));
    	}
    	return sum;
    }

    private static String sbox(String input){ // input = 100011000101011100110110110000100110001000010011
    	String output = new String();
    	for(int i=0; i<input.length(); i=i+6){
    		String rowInBinary = Character.toString(input.charAt(i)) + Character.toString(input.charAt(i+5));
    		String colInBinary = input.substring(i+1, i+5);
    		// System.out.print("rowInBinary: "+ rowInBinary);
    		// System.out.print(" colInBinary: "+ colInBinary);
    		int row = binaryToDecimal(rowInBinary);
    		int col = binaryToDecimal(colInBinary);
    		// System.out.print("row: "+ row);
    		// System.out.print("col: "+ col);
    		// System.out.println();
    		int sboxVal = s_table[i/6][row][col];
    		String sboxValInBinary = decimalToBinary(sboxVal);
    		sboxValInBinary = sboxValInBinary.substring(sboxValInBinary.length() - 4);
    		output += sboxValInBinary;
    	}
    	return output;
    }

    private static String round(String input, String key){
    	String leftInput = input.substring(0,32);
    	String rightInput = input.substring(32,64);
    	String expanded_rightInput = findPermutation(rightInput, expansion_permutation);
    	String rightInput_xor_key = findXor(expanded_rightInput, key);
    	// System.out.println("expanded_rightInput: "+ expanded_rightInput);
    	// System.out.println("Key: "+ key);
    	// System.out.println("rightInput_xor_key: "+ rightInput_xor_key);
    	String sboxOutput = sbox(rightInput_xor_key);
    	String permutedSboxOutput = findPermutation(sboxOutput,P_func);
    	String xor_permutedSboxOutput = findXor(permutedSboxOutput, leftInput);
    	 return (rightInput + xor_permutedSboxOutput);
    }

    private static String binaryToHex(String binary){
    	String hex = new String();
    	System.out.println(binary);
    	for(int i=0; i<binary.length(); i = i+4){
    		int decimal = binaryToDecimal(binary.substring(i, i+4));
    		if(decimal == 10) hex += "A";
    		else if(decimal == 11) hex += "B";
    		else if(decimal == 12) hex += "C";
    		else if(decimal == 13) hex += "D";
    		else if(decimal == 14) hex += "E";
    		else if(decimal == 15) hex += "F";
    		else hex += Integer.toString(decimal);
    	}
    	return hex;
    }

    private static String encryption(String plaintextInBits){
    	String output = plaintextInBits;
    	for(int i=1; i<=16; ++i){
    		String input = output;
    		output = round(input,keys[i-1]);
    	}

    	//32 bit swap
    	output = output.substring(32,64) + output.substring(0,32);

    	String cipherTextInBinary = findPermutation(output,ip_inverse);
    	
    	String cipherTextInHex = binaryToHex(cipherTextInBinary);

    	return cipherTextInHex;
    }

    private static String decryption(String cipherText){
    	String output = cipherText;
    	for(int i=16; i>0; --i){
    		String input = output;
    		output = round(input,keys[i-1]);
    	}

    	//32 bit swap
    	output = output.substring(32,64) + output.substring(0,32);

    	String originalTextInBinary = findPermutation(output,ip_inverse);
    	
    	//String originalTextInHex = binaryToHex(cipherTextInBinary);

    	return originalTextInBinary;
    }

    public static void main(String [] args){
    	Scanner sc = new Scanner(System.in);
    	String plaintext = sc.nextLine();
    	String plaintextInBits = stringToBinary(plaintext);
    	System.out.println("plaintextInBits: " + plaintextInBits);

    	//Pass it through Initial Permutation

    	String ip_plaintextInBits = findPermutation(plaintextInBits, ip);
    	System.out.println("ip_plaintextInBits: " + ip_plaintextInBits);

    	//16 Sub-Keys Generation

    	String key = sc.nextLine();
    	String keyInBits = hexToBinary(key);
    	System.out.println("KeyinBits: " + keyInBits);
    	String pc1_keyInBits = findPermutation(keyInBits, pc1);
    	System.out.println("pc1_keyInBits: " + pc1_keyInBits);
    	generateSubKeys(pc1_keyInBits);
    	String cipherText = encryption(ip_plaintextInBits);

    	System.out.println("Cipher Text: "+ cipherText);

			String cipherTextInBits = hexToBinary(cipherText);
    	String ip_cipherTextInBits = findPermutation(cipherTextInBits, ip);
    	System.out.println("ip_cipherTextInBits: " + ip_cipherTextInBits);

    	String output = decryption(ip_cipherTextInBits);
    	System.out.println("Original Text: "+ output);

    }

    //Tester for functions
    // public static void main(String [] args){
    // 	Scanner sc = new Scanner(System.in);
    // 	System.out.println("stringToBinary: "+stringToBinary("security"));
    // 	// String test = findPermutation(sc.next(),ip);
    // 	// for(int i=0;i<test.length(); i = i+7){
    // 	// 	for(int j=i; i<test.length()-1 && j<i+8; ++j){
    // 	// 		System.out.print(test.charAt(j));
    // 	// 	}
    // 	// 	System.out.print(" ");
    // 	// }

    // 	String key = sc.next();
    // 	String keyInBits = hexToBinary(key);
    // 	System.out.println(keyInBits);
    	// String test = sbox("100011000101011100110110110000100110001000010011");
    	// System.out.println(test);
    	// System.out.println(test.length());
    // }






































}