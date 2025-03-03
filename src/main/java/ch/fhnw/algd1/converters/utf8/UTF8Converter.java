package ch.fhnw.algd1.converters.utf8;

import java.util.ArrayList;

/*
 * Created on 05.09.2014
 */
/**
 * @author
 */
public class UTF8Converter {
	public static byte[] codePointToUTF(int x) {
		byte[] b = null;
		int firstStep = 0x80;
		int secondStep = 2047;
		int thirdStep = 0x10000;
		int lastStep = 0x200000;
		// TODO allocate b in the right size (depending on x) and fill it with the
		if (x < firstStep) {
			b = new byte[1];
			b[0] = (byte) x;
		} else if (x < secondStep) {
			b = new byte[2];
			b[0] = (byte) ((x >>> 6 | 0xC0));
			b[1] = (byte) ((x & 0x3F) | 0x80);
		} else if (x < thirdStep) {
			b = new byte[3];
			b[0] = (byte) (0xE0 | x >>> 12);
			b[1] = (byte) (0x80 | x >>> 6);
			b[2] = (byte) (0x80 | (x & 0x3F));
		} else if (x < lastStep) {
			b = new byte[4];
			b[0] = (byte) (0xF0 | x >>> 18);
			b[1] = (byte) (0x80 | x >>> 12);
			b[2] = (byte) (0x80 | x >>> 6);
			b[3] = (byte) (0x80 | (x & 0x3F));
		}

		// UTF-8 encoding of code point x. b[0] shall contain the first byte.
		return b;
	}

	public static int UTFtoCodePoint(byte[] bytes) {
		if (isValidUTF8(bytes)) {
			// TODO replace return statement below by code to return the code point
			// UTF-8 encoded in array bytes. bytes[0] contains the first byte
			return 0;
		} else
			return 0;
	}

	private static boolean isValidUTF8(byte[] bytes) {
		if (bytes.length == 1)
			return (bytes[0] & 0b1000_0000) == 0;
		else if (bytes.length == 2)
			return ((bytes[0] & 0b1110_0000) == 0b1100_0000)
					&& isFollowup(bytes[1]);
		else if (bytes.length == 3)
			return ((bytes[0] & 0b1111_0000) == 0b1110_0000)
					&& isFollowup(bytes[1]) && isFollowup(bytes[2]);
		else if (bytes.length == 4)
			return ((bytes[0] & 0b1111_1000) == 0b1111_0000)
					&& isFollowup(bytes[1]) && isFollowup(bytes[2]) && isFollowup(bytes[3]);
		else
			return false;
	}

	private static boolean isFollowup(byte b) {
		return (b & 0b1100_0000) == 0b1000_0000;
	}
}
