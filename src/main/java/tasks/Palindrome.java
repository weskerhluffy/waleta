package tasks;

public class Palindrome {

	/**
	 * Remove all characters that are not A-Z, a-z and 0-9. It is equivalent to
	 * <code>s.toLowerCase().replaceAll("[^a-zA-Z0-9]", "")</code>, but much
	 * faster because this function is O(n), while regex operations are not
	 * always linear in time.
	 * 
	 * @param s
	 * @return {@code s} string with only alphanumeric characters.
	 */
	public static String normalize(String s) {
		String result = "";
		Integer sLen = s.length();

		/**
		 * Traverse the chars in <code>s</code> once, so time complexity is O(n)
		 * where n is the length of <code>s</code>.
		 */
		for (int i = 0; i < sLen; i++) {
			Character currentChar = s.charAt(i);

			if ((currentChar >= 'A' && currentChar <= 'Z')
					|| (currentChar >= 'a' && currentChar <= 'z')
					|| (currentChar >= '0' && currentChar <= '9')) {
				result += currentChar;
			}
		}

		/**
		 * Space complexity is O(n) because we return a string at most as big as
		 * <code>s</code>.
		 */
		return result;
	}

	/**
	 * Check if string {@code s} is palidrome. A palindrome in this case is
	 * defined as a string where all alphanumeric characters are in the same
	 * order if read forward or backwards.
	 * 
	 * @param s
	 * @return If {@code s} is palindrome.
	 */
	public static boolean isPalindrome(String s) {
		/**
		 * If it weren't for the invocation of normalize, the space complexity
		 * would be constant O(1).
		 */
		Boolean isPalindrome = true;
		/** An empty string is a palindrome by definition. */
		if (s == null || s.length() == 0) {
			return true;
		}
		/**
		 * Using a regex would have been easier (and more maintainable) but is
		 * very expensive in running time.
		 */
		// String inputStrInt = s.toLowerCase().replaceAll("[^a-zA-Z0-9]", "");
		String inputStrInt = normalize(s.toLowerCase());

		/**
		 * If there are no alphanumeric characters, there is no point in going
		 * on.
		 */
		Integer inputStrLen = inputStrInt.length();
		if (inputStrLen == 0) {
			return true;
		}

		/**
		 * Space complexity could be reduced to constant O(1), by not making a
		 * filtered copy of <code>s</code> in normalize. In this loop we could
		 * just have 2 pointers starting on each end of the string and move them
		 * to the center skipping the non-alphanumeric characters and comparing
		 * the alphanumeric ones. This could be a future improvement.
		 */
		for (Integer i = 0; i < inputStrLen / 2; i++) {
			Integer counterpartPos = inputStrLen - 1 - i;
			if (inputStrInt.charAt(i) != inputStrInt.charAt(counterpartPos)) {
				isPalindrome = false;
				break;
			}
		}

		return isPalindrome;
	}
}
