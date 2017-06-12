import java.util.*;

import static java.util.Arrays.asList;

public class ComplementaryPairs {

	public int[] twoSum(int[] nums, int target) {
		HashMap<Integer, Set<Integer>> positions_by_value = new HashMap<>();
		Integer nums_len = nums.length;
		List<List<Integer>> complementaryPairs = new ArrayList<>();
		Set<Integer> positions = null;
		int firstComplementaryPair[] = new int[2];
		Integer firstComplementaryPairIdx = 0;

		for (Integer i = 0; i < nums_len; i++) {
			Integer num_cur = nums[i];
			if ((positions = (Set<Integer>) positions_by_value.get(num_cur)) != null) {
				positions.add(i);
			} else {
				positions_by_value
						.put(num_cur, new HashSet<Integer>(asList(i)));
			}

		}

		for (Integer num_cur : positions_by_value.keySet()) {
			Integer complement = target - num_cur;
			Integer firstNum;
			Integer secondNum;
			if (complement < num_cur) {
				firstNum = complement;
				secondNum = num_cur;
			} else {
				firstNum = num_cur;
				secondNum = complement;
			}
			if ((positions = (Set<Integer>) positions_by_value.get(complement)) != null) {

				Set<Integer> positions_num_cur = positions_by_value
						.get(num_cur);

				for (Integer position_complement : positions) {
					for (Integer position_num_cur : positions_num_cur) {
						if (position_complement != position_num_cur) {
							if (position_complement < position_num_cur) {
								firstNum = position_complement;
								secondNum = position_num_cur;
							} else {
								firstNum = position_num_cur;
								secondNum = position_complement;
							}
							complementaryPairs.add(new ArrayList<>(asList(
									firstNum, secondNum)));
						}
					}
				}

				/*
				 * Integer ocurrences_complement = positions.size(); Integer
				 * ocurrences_num_cur = positions_by_value.get(num_cur) .size();
				 * 
				 * 
				 * 
				 * for (int i = 0; i < ocurrences_complement; i++) { for (int j
				 * = 0; j < ocurrences_num_cur - 1; j++) {
				 * complementaryPairs.add(new ArrayList<>(asList(firstNum,
				 * secondNum))); } } if (num_cur != complement) { for (int j =
				 * 0; j < ocurrences_complement; j++) {
				 * complementaryPairs.add(new ArrayList<>(asList(num_cur,
				 * complement))); } }
				 */
			}
		}

		for (Integer num_cur : complementaryPairs.get(0)) {
			firstComplementaryPair[firstComplementaryPairIdx] = num_cur;
			firstComplementaryPairIdx++;
		}
		return firstComplementaryPair;
	}
}
