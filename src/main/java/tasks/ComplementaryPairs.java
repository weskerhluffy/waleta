package tasks;

import java.util.*;

import static java.util.Arrays.asList;

public class ComplementaryPairs {

	/**
	 * Find all pair of numbers in {@code nums} which sum {@code target}, even
	 * if there are repeated pairs, for example: if {@code nums} is
	 * <code>[3,4,4,4,3]</code> and the target is 7, the result would be [[3,4],
	 * [3,4],[3,4],[3,4],[3,4],[3,4],[3,4],[3,4],[3,4],[3,4],[3,4],[3,4]] (the 3
	 * in position 0 against all 3 "4" occurrences and the 3 in the position 4
	 * against those same "4" occurrences).
	 * 
	 * In the case the number that is half of the target is in {@code num}, a
	 * pair of that number is returned for every combination of positions of
	 * such number. For example: if {@code nums} is [[5,5,5]] the result would
	 * be [[5,5],[5,5],[5,5]] (the "5" at position pairing with the ones at
	 * other positions after it, then the "5" at position one with the others
	 * after it).
	 * 
	 * @param nums
	 * @param target
	 * @return The list of pairs of numbers in {@code nums} that sum
	 *         {@code target} ordered by the first element in the pair and then
	 *         the second.
	 */
	public static List<List<Integer>> twoSum(int[] nums, int target) {
		/**
		 * Space complexity is O(n^2) (taking into counting the result
		 * <code>complementaryPairs</code> and n being the size of
		 * <code>nums</code>), since in the worst case we will have 2
		 * (complementary) numbers in the array, each happening half of the
		 * total size of the array, and each occurrence of the numbers will be
		 * paired with all occurrences in the other.
		 * 
		 * Let a,b be the only unique numbers in <code>nums</code>, N is the
		 * size of the whole array and N/2 is the number of occurrences of a and
		 * N/2 is the number of occurrences of b and a+b=t<code>target</code>.
		 * Then (N^2)/4 is the size of the resulting array (comprised of pairs
		 * [a,b]), bounded by (N^2) so the space complexity in this worst
		 * scenario is O(N^2).
		 * 
		 */
		Integer numsLen = nums.length;
		Set<Integer> positions = null;
		Set<Integer> numbersUniq = null;
		/**
		 * If we don't take into account the resulting list, the space
		 * complexity is O(n) because we use at most n slots of memory for
		 * storing the occurrences of each unique number or the number itself.
		 */
		Set<Integer> alreadyProcessed = new HashSet<Integer>();
		HashMap<Integer, Set<Integer>> positionsByValue = new HashMap<>();
		List<List<Integer>> complementaryPairs = new ArrayList<>();

		for (Integer i = 0; i < numsLen; i++) {
			Integer numCur = nums[i];
			/**
			 * Store all positions of the number numCur in a hash table, if the
			 * insertion in the table is O(1), then this whole loop is O(n),
			 * where n is the size of nums array.
			 */
			if ((positions = (Set<Integer>) positionsByValue.get(numCur)) != null) {
				positions.add(i);
			} else {
				positionsByValue.put(numCur, new HashSet<Integer>(asList(i)));
			}

		}

		/**
		 * Here we sort the numbers (one occurrence of each) so as to return the
		 * pairs ordered, as the HashMap.keySet method is not guaranteed to sort
		 * the keys. This is the most expensive operation (in time) in the
		 * function, as the insertion of a number in a tree is O(log(n)), so to
		 * store all unique numbers at most this operation will take
		 * O(n*log(n)).
		 */
		numbersUniq = new TreeSet<>(positionsByValue.keySet());

		/** Generate all complementary pairs. */
		for (Integer numCur : numbersUniq) {
			/**
			 * If the number hasn't been processed (maybe as a complement of
			 * another number) check the set of already processed numbers.
			 */
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

				Integer ocurrencesComplement = positions.size();
				Integer ocurrencesNumCur = positionsByValue.get(numCur).size();

				/**
				 * When the complement is not the number itself, the number of
				 * pairs is the same as the cartesian product of the positions
				 * of each.
				 */
				if (numCur != complement) {
					for (int i = 0; i < ocurrencesComplement; i++) {
						for (int j = 0; j < ocurrencesNumCur; j++) {
							complementaryPairs.add(new ArrayList<>(asList(
									firstNum, secondNum)));
						}
					}
				}
				/**
				 * If the complement of the current number is itself, then the
				 * number of occurrences of pairs of that number is the same as
				 * the number of combinations of all the positions of such
				 * number.
				 */
				else {
					for (int i = 0; i < ocurrencesComplement - 1; i++) {
						for (int j = i + 1; j < ocurrencesNumCur; j++) {
							complementaryPairs.add(new ArrayList<>(asList(
									numCur, complement)));
						}
					}
				}
			}
			/**
			 * To avoid reporting the pairs twice (one time for each element in
			 * the pair of complementary numbers) we store the numbers we
			 * already processed and the complement.
			 */
			alreadyProcessed.add(numCur);
			alreadyProcessed.add(complement);
		}

		return complementaryPairs;
	}
}
