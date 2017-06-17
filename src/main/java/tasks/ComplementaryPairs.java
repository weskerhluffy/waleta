package tasks;
import java.util.*;

import static java.util.Arrays.asList;

public class ComplementaryPairs {

	public static List<List<Integer>> twoSum(int[] nums, int target) {
		HashMap<Integer, Set<Integer>> positions_by_value = new HashMap<>();
		Integer nums_len = nums.length;
		List<List<Integer>> complementaryPairs = new ArrayList<>();
		Set<Integer> positions = null;

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

				Integer ocurrences_complement = positions.size();
				Integer ocurrences_num_cur = positions_by_value.get(num_cur)
						.size();

				for (int i = 0; i < ocurrences_complement; i++) {
					for (int j = 0; j < ocurrences_num_cur - 1; j++) {
						complementaryPairs.add(new ArrayList<>(asList(firstNum,
								secondNum)));
					}
				}
				if (num_cur != complement) {
					for (int j = 0; j < ocurrences_complement; j++) {
						complementaryPairs.add(new ArrayList<>(asList(num_cur,
								complement)));
					}
				}
			}
		}

		return complementaryPairs;
	}
}
