package tasks

import spock.lang.Specification
import java.sql.*;
import groovy.sql.Sql
import tasks.ComplementaryPairs
import java.nio.file.Files
import java.nio.file.Paths
import java.util.stream.Collectors
import java.nio.charset.Charset
import java.util.regex.Pattern


class ComplementaryPairsTest extends Specification{
	def 'k-complementary pairs'() {
		given:
		List<List<Integer>> complementaryPairs;

		when:
		int []numbersArray

		numbersArray=numbers.toArray()
		complementaryPairs=ComplementaryPairs.twoSum(numbersArray, target)

		then:
		complementaryPairs == complementaryPairsRef

		where:

		// All tests expect the pairs to be ordered in their 1st element and
		// in contentions in the 2nd element.
		numbers|target|complementaryPairsRef
		// These are null/very small input tests.
		[]| 10|[][9]| 10|[]
		[10]| 10|[]
		// Testing the case where the complementary pair elements are the same.
		[5, 5]| 10|[[5, 5]]
		[5, 5, 3]| 10|[[5, 5]]
		[3, 5, 5, 7]| 10|[[3, 7], [5, 5]]
		[5, 3, 5, 7]| 10|[[3, 7], [5, 5]]
		// More general tests. Check that the number of complementary pairs is
		// effectively n*m where n is the number of occurrences of one element
		// of the pair and m the number of occurrences of the other.
		[5, 4, 3, 2, 1, 2, 6, 3, 4, 5]| 10|[[4, 6], [4, 6], [5, 5]]
		[1, 2, 3, 4, 5, 6, 7, 8, 9]| 10|[
			[1, 9],
			[2, 8],
			[3, 7],
			[4, 6]
		]
		[
			7,
			1,
			5,
			2,
			3,
			5,
			4,
			7,
			3,
			5,
			6,
			7,
			5,
			8,
			7,
			9,
			3
		]| 10|[
			[1, 9],
			[2, 8],
			[3, 7],
			[3, 7],
			[3, 7],
			[3, 7],
			[3, 7],
			[3, 7],
			[3, 7],
			[3, 7],
			[3, 7],
			[3, 7],
			[3, 7],
			[3, 7],
			[4, 6],
			[5, 5],
			[5, 5],
			[5, 5],
			[5, 5],
			[5, 5],
			[5, 5]
		]
		[
			7,
			1,
			5,
			2,
			3,
			5,
			4,
			7,
			3,
			5,
			6,
			7,
			5,
			8,
			7,
			9,
			3
		]| 100|[]
		[
			856,
			284,
			899,
			745,
			143,
			599,
			726,
			825,
			338,
			348,
			179,
			378,
			62,
			636,
			406,
			813,
			381,
			154,
			921,
			682
		]| 956|[[143, 813]]
		[
			856,
			284,
			899,
			745,
			143,
			599,
			726,
			825,
			338,
			348,
			179,
			378,
			62,
			636,
			406,
			813,
			381,
			154,
			921,
			682
		]| 957|[]
		//TODO: Test very big cases, like the orders of millions.
	}
}
