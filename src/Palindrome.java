public class Palindrome {

	public static String normalize(String s) {
		String result = "";
		Integer s_len = s.length();

		for (int i = 0; i < s_len; i++) {
			Character current_char = s.charAt(i);

			if ((current_char >= 'A' && current_char <= 'Z')
					|| (current_char >= 'a' && current_char <= 'z')
					|| (current_char >= '0' && current_char <= '9')) {
				result += current_char;
			}
		}

		return result;
	}

	public boolean isPalindrome(String s) {
		Boolean isPalindrome = true;
		// String inputStrInt = s.toLowerCase().replaceAll("[^a-zA-Z0-9]", "");
		String inputStrInt = normalize(s.toLowerCase());
		System.out.println("pero q mierda " + inputStrInt);

		Integer inputStrLen = inputStrInt.length();

		for (Integer i = 0; i < inputStrLen / 2; i++) {
			Integer counterpart_pos = inputStrLen - 1 - i;
			System.out.println("i is " + i + " caca " + counterpart_pos);
			if (inputStrInt.charAt(i) != inputStrInt.charAt(counterpart_pos)) {
				isPalindrome = false;
				break;
			}
		}

		return isPalindrome;
	}
}
