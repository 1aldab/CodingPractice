/*
Write code to decode strings. For example, String str = "3[a2[bd]g4[ef]h]", 
the output should be "abdbdgefefefefhabdbdgefefefefhabdbdgefefefefh". 
*/

package decoder;

public class Decoder {

static int maxLevel(String code) {
	int level = 0;
	int max = 0;
	for (int i = 0; i < code.length(); i++) {
		if (code.charAt(i) == '[') {
			level++;
			if (level > max) max = level;
		}
		if (code.charAt(i) == ']') level--;
	}
	return max;
}

static int findClosingBracket(String code) {
	int count = 0;
	for (int i = 0; i < code.length(); i++) {
		if (code.charAt(i) == '[') count++;
		if (code.charAt(i) == ']') {
			count--;
			if (count == 0) return i;
		}
	}
	return -1;
}

static String easyDecode(String code) {
	int lb = 0;
	for (int i = 0; i < code.length(); i++) {
		if (code.charAt(i) == '[') {
			lb = i;
			break;
		}
	}
	int count = Integer.parseInt(code.substring(0, lb));
	String text = code.substring(lb + 1, code.length() - 1);
	String result = "";
	for (int i = 0; i < count; i++) result += text;
	return result;
}

static boolean isDigit(char c) {
	return c >= '0' && c <= '9';
}

static boolean isChar(char c) {
	return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
}

static String decode(String code) {
	if (maxLevel(code) == 0) return code;
	String result = "";
	int beg = 0, end = 0;
	while (end < code.length()) {
		if (isChar(code.charAt(beg))) {
			while (end < code.length() && !isDigit(code.charAt(end))) end++;
			result += code.substring(beg, end);
		}
		if (isDigit(code.charAt(beg))) {
			int lb = beg + 1;
			while (code.charAt(lb) != '[') lb++;
			int rb = lb + findClosingBracket(code.substring(lb));
			String subprob = decode(code.substring(lb + 1, rb));
			result += easyDecode(code.substring(beg, lb) + "[" + subprob + "]");
			end = rb + 1;
		}
		beg = end;
	}
	return result;
}

public static void test(String t) {
	System.out.println("Input:  " + t);
	System.out.println("Output: " + decode(t));
	System.out.println("_____________________________________________________");
}

public static void main(String[] args) {
	String[] tests = {"a2[b]3[c]", "2[a2[bd]g4[ef]h]"};
	for (String t : tests) test(t);
}
}