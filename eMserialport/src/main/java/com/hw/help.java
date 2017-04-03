package com.hw;

import android.util.Log;

public class help {
	private final static char[] HEX_CODE = {'0', '1', '2', '3', '4', '5', '6', '7',
			'8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

	public static String toString(byte[] data) {
		if (data == null) {
			return null;
		}
		return toString(data, 0, data.length);
	}

	public static String toString(byte[] data, int start, int end) {
		if (data == null) {
			return null;
		}
		int length = data.length;
		if (start < 0 || end < 0 || end < start ||
				start > length || end > length) {
			return null;
		}
		String mystring = "";
		for (int i = start; i < end; i ++) {
			byte b = data[i];
			mystring += HEX_CODE[(b >> 4) & 0x0F];
			mystring += HEX_CODE[b & 0x0F];
			mystring += " ";
		}
		return mystring;
	}

	public static byte[] toByteArray(String string) {
			if (string == null || string.length() == 0) {
				return null;
			}

			int length = string.length();
			byte[] result = new byte[(length + 1) >> 1];
			boolean nibble = false;
			int j = 0;
			for (int i = 0; i < length; i ++) {
				byte temp = (byte) 0xff;
				char c = string.charAt(i);
				if (c >= '0' && c <= '9') {
					temp = (byte) (c & 0x0F);
				} else if (c >= 'a' && c <= 'f') {
					temp = (byte) (c - 'a' + 0x0a);
				} else if (c >= 'A' && c <= 'F') {
					temp = (byte) (c - 'A' + 0x0a);
				} else if (c == ' '){
					if (nibble) {
						return null;
					}
					continue;
				} else {
					return null;
				}
				if (temp < 0x10) {
					if (nibble) {
						result[j] += temp;
						j ++;
					} else {
						result[j] = (byte) (temp << 4);
					}
					nibble = !nibble;
				}
			}
			if (nibble) {
				//b[j] <<= 4;
				j ++;
			}
			if (j < result.length) {
				byte[] newResult = new byte[j];
				for (int i = 0; i < j; i ++) {
					newResult[i] = result[i];
				}
				return newResult;
			}
			return result;
	}

	public static byte[] subByteArray(byte[] data, int start, int end) {
		if (data == null) {
			return null;
		}
		int length = data.length;
		if (start < 0 || end < 0 || end < start ||
				start > length || end > length) {
			return null;
		}
		byte[] newByteArray = new byte[end - start];
		for (int i = start, j = 0; i < end; i ++, j ++) {
			newByteArray[j] = data[i];
		}
		return newByteArray;
	}

	public static byte[] set(byte[] dst, byte[] src, int start) {
		for (int i = 0, j = start; i < src.length; i ++, j ++) {
			dst[j] = src[i];
		}
		return dst;
	}

	public static boolean compare(byte[] data1, byte[] data2) {
		if (data1 == null || data2 == null
				|| data1.length != data2.length) {
			return false;
		}
		for (int i = 0; i < data1.length; i ++) {
			if (data1[i] != data2[i]) {
				return false;
			}
		}
		return true;
	}

	public static int toInteger(String string) {
		if (string == null || string.length() == 0) {
			return 0;
		}
		int result = 0;
		for (int i = 0; i < string.length(); i ++) {
			char c = string.charAt(i);
			if (c >= '0' && c <= '9') {
				result *= 10;
				result += c - '0';
			} else {
				return 0;
			}
		}
		return result;
	}

	public static int toInteger(byte[] data, boolean endian) {
		if (data == null) {
			return 0;
		}
		// 大端模式,高位被忽略
		int result = 0;
		if (!endian) {
			int start = 0;
			int end = data.length;
			if (end > 8) {
				start = end - 8;
			}
			for (int i = start; i < end; i ++) {
				result <<= 8;
				result += data[i] & 0xff;
			}
		} else {
			int start = data.length - 1;
			int end = 0;
			if (start > 7) {
				start = 7;
			}
			for (int i = start; i >= end; i --) {
				result <<= 8;
				result += data[i] & 0xff;
			}
		}
		return result;
	}
}