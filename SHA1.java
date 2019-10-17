import java.util.*;

public class SHA1 {
	private static final Scanner sc = new Scanner(System.in);
	private static final int INT_A = 0x67452301;
	private static final int INT_B = 0xEFCDAB89;
	private static final int INT_C = 0x98BADCFE;
	private static final int INT_D = 0x10325476;
	private static final int INT_E = 0xC3D2E1F0;

	private static byte[] computeSHA1(byte[] message) {
		int messageLenBytes = message.length;
		int numBlocks = ((messageLenBytes + 8) >> 6) + 1;
		int totalLen = numBlocks << 6;
		byte[] paddingBytes = new byte[totalLen - messageLenBytes];
		paddingBytes[0] = (byte) 0x80;
		long messageLenBits = (long) (messageLenBytes << 3);
		for(int i = 0; i < 8; ++i) {
			paddingBytes[paddingBytes.length - i - 1] = (byte) messageLenBits;
			messageLenBits >>>= 8;
		}
		
		int[] blocks = new int[numBlocks << 4];
		for(int i = 0; i < messageLenBytes; ++i) {
			int by = 24 - ((i % 4) << 3);
			blocks[(i >> 2)] |= (message[i]&0xFF) << by;
		}
		for(int i = 0; i < paddingBytes.length; ++i) {
			int index = i + messageLenBytes;
			int by = 24 - ((index % 4) << 3);
			blocks[(index >> 2)] |= (paddingBytes[i]&0xFF) << by;
		}

		int a = INT_A;
		int b = INT_B;
		int c = INT_C;
		int d = INT_D;
		int e = INT_E;
		int[] w = new int[80];
		for(int i = 0; i < numBlocks; ++i) {
			int index = i << 4;
			for(int j = 0; j < 80; ++j) {
				if(j < 16) w[j] = blocks[index + j];
				else {
					w[j] = Integer.rotateLeft(w[j - 3] ^ w[j - 8] ^ w[j - 14] ^ w[j - 16], 1);
				}
			}
			int originalA = a;
			int originalB = b;
			int originalC = c;
			int originalD = d;
			int originalE = e;
			for(int j = 0; j < 80; ++j) {
				int div20 = j / 20;
				int f = 0;
				int k = 0;
				switch(div20) {
					case 0: f = (b & c) | (~b & d);
									k = 0x5A827999;
									break;
					case 1: f = (b ^ c ^ d);
									k = 0x6ED9EBA1;
									break;
					case 2: f = (b & c) | (b & d) | (c & d);
									k = 0x8F1BBCDC;
									break;
					case 3: f = (b ^ c ^ d);
									k = 0xCA62C1D6;
									break;
				}
				int temp = Integer.rotateLeft(a, 5) + e + f + k + w[j];
				e = d;
				d = c;
				c = Integer.rotateLeft(b, 30);
				b = a;
				a = temp;
			}
      a += originalA;
   	  b += originalB;
		  c += originalC;
			d += originalD;
 			e += originalE;
		}
		int arr[] = {a, b, c, d, e};
		byte[] sha1 = new byte[20];
		for(int i = 0; i < 5; ++i) {
			int n = arr[i];
			int count = i * 4;
			for(int j = 0; j < 4; ++j) {
				sha1[count + 4 - j - 1] = (byte) n;
				n >>>= 8;
			}
		}
		return sha1;
  }

	private static String toHexString(byte[] b) {
		StringBuilder builder = new StringBuilder();
		for(int i = 0; i < b.length; ++i) {
			builder.append(String.format("%02X", b[i]&0xFF));
		}
		return builder.toString();
	}

	public static void main(String[] args) {
		System.out.print("Enter message: ");
		String message = sc.nextLine();
		String hash = toHexString(computeSHA1(message.getBytes()));
		System.out.println("SHA1 hash: " + hash);
	}
}
