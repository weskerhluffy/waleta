package tasks;

import java.util.*;

import static java.util.Arrays.asList;

public class ComplementaryPairs {

	public static List<List<Integer>> twoSum(int[] nums, int target) {
		HashMap<Integer, Set<Integer>> positionsByValue = new HashMap<>();
		Integer numsLen = nums.length;
		List<List<Integer>> complementaryPairs = new ArrayList<>();
		Set<Integer> positions = null;
		Set<Integer> alreadyProcessed = new HashSet<Integer>();
		Set<Integer> numbersUniq = null;

		System.out.println("los nums " + Arrays.asList(nums) + " target "
				+ target);
		for (Integer i = 0; i < numsLen; i++) {
			Integer num_cur = nums[i];
			if ((positions = (Set<Integer>) positionsByValue.get(num_cur)) != null) {
				positions.add(i);
			} else {
				positionsByValue.put(num_cur, new HashSet<Integer>(asList(i)));
			}

		}

		numbersUniq = new TreeSet<>(positionsByValue.keySet());

		for (Integer numCur : numbersUniq) {
			System.out.println("procesando " + numCur + " su cardinalidad "
					+ positionsByValue.get(numCur) + " los procesados "
					+ alreadyProcessed);
			if (alreadyProcessed.contains(numCur)) {
				continue;
			}
			Integer complement = target - numCur;
			Integer firstNum;
			Integer secondNum;
			if (complement < numCur) {
				firstNum = complement;
				secondNum = numCur;
			} else {
				firstNum = numCur;
				secondNum = complement;
			}
			if ((positions = (Set<Integer>) positionsByValue.get(complement)) != null) {

				Integer ocurrences_complement = positions.size();
				Integer ocurrences_num_cur = positionsByValue.get(numCur)
						.size();

				if (numCur != complement) {
					for (int i = 0; i < ocurrences_complement; i++) {
						for (int j = 0; j < ocurrences_num_cur; j++) {
							complementaryPairs.add(new ArrayList<>(asList(
									firstNum, secondNum)));
						}
					}
				} else {
					for (int i = 0; i < ocurrences_complement - 1; i++) {
						for (int j = i + 1; j < ocurrences_num_cur; j++) {
							complementaryPairs.add(new ArrayList<>(asList(
									numCur, complement)));
						}
					}
				}
			}
			alreadyProcessed.add(numCur);
			alreadyProcessed.add(complement);
		}

		return complementaryPairs;
	}
}
