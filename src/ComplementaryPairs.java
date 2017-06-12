import java.util.*;

import static java.util.Arrays.asList;

public class ComplementaryPairs {

	public int[] twoSum(int[] nums, int target) {
		int firstComplementaryPair[] = new int[2];
		Integer firstComplementaryPairIdx = 0;
		Integer leftIdx = 0;
		Integer rightIdx = 0;
		List<List<Integer>> complementaryPairs = new ArrayList<>();
		int nums_sorted[] = nums.clone();
		HashMap<Integer, Set<Integer>> positions_by_value = new HashMap<>();
		Set<Integer> positions = null;
		Arrays.sort(nums_sorted);

		for (Integer i = 0; i < nums.length; i++) {
			Integer num_cur = nums[i];
			if ((positions = (Set<Integer>) positions_by_value.get(num_cur)) != null) {
				positions.add(i);
			} else {
				positions_by_value
						.put(num_cur, new HashSet<Integer>(asList(i)));
			}
		}

		rightIdx = nums.length - 1;

		while (rightIdx > leftIdx) {
			Integer leftNum = nums_sorted[leftIdx];
			Integer rightNum = nums_sorted[rightIdx];
			System.out.println("me lleva la mierda " + leftNum + " + "
					+ rightNum + " = " + (leftNum + rightNum));

			if (leftNum + rightNum > target) {
				rightIdx--;
			} else {
				if (leftNum + rightNum < target) {
					leftIdx++;
				} else {
					Integer firstNum = 0;
					Integer secondNum = 0;
					firstNum = (Integer) positions_by_value.get(leftNum)
							.toArray()[0];
					secondNum = (Integer) positions_by_value.get(rightNum)
							.toArray()[positions_by_value.get(rightNum)
							.toArray().length - 1];
					complementaryPairs.add(asList(firstNum, secondNum));
					leftIdx++;
				}
			}
		}

		for (Integer num_cur : complementaryPairs.get(0)) {
			firstComplementaryPair[firstComplementaryPairIdx] = num_cur;
			firstComplementaryPairIdx++;
		}

		return firstComplementaryPair;
	}
}
