package tasks;

public class Palindrome {

	/**
	 * Remove all characters that are not A-Z, a-z and 0-9. It is equivalent to
	 * <code>s.toLowerCase().replaceAll("[^a-zA-Z0-9]", "")</code>, but much
	 * faster because this function is O(n), while regex operations are not
	 * always linear.
	 * 
	 * @param s
	 * @return {@code s} string with only alphanumeric characters.
	 */
	public static String normalize(String s) {
		String result = "";
		Integer sLen = s.length();

		for (int i = 0; i < sLen; i++) {
			Character currentChar = s.charAt(i);

			if ((currentChar >= 'A' && currentChar <= 'Z')
					|| (currentChar >= 'a' && currentChar <= 'z')
					|| (currentChar >= '0' && currentChar <= '9')) {
				result += currentChar;
			}
		}

		return result;
	}

	public static boolean isPalindrome(String s) {
		Boolean isPalindrome = true;
		if (s == null || s.length() == 0) {
			return true;
		}
		// String inputStrInt = s.toLowerCase().replaceAll("[^a-zA-Z0-9]", "");
		String inputStrInt = normalize(s.toLowerCase());
		System.out.println("pero q mierda " + inputStrInt);

		Integer inputStrLen = inputStrInt.length();
		if (inputStrLen == 0) {
			return true;
		}

		for (Integer i = 0; i < inputStrLen / 2; i++) {
			Integer counterpartPos = inputStrLen - 1 - i;
			System.out.println("i is " + i + " caca " + counterpartPos);
			if (inputStrInt.charAt(i) != inputStrInt.charAt(counterpartPos)) {
				isPalindrome = false;
				break;
			}
		}

		return isPalindrome;
	}
}
