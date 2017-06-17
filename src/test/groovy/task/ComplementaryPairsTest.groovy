package task

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
	def 's'() {
		given:
		List<List<Integer>> complementaryPairs;

		when:
		int []numbersArray

		println("q mierdas "+numbersArray)
		numbersArray=numbers.toArray()
		println("q mierdas aroa "+numbersArray.getClass().getName())
		complementaryPairs=ComplementaryPairs.twoSum(numbersArray, target)
		
		then:
		complementaryPairs == complementaryPairsRef

		where:
		
		numbers|target|complementaryPairsRef
		[] | 10|[]
		[9] | 10|[]
		[10] | 10|[]
		[5,5] | 10|[[5, 5]]
	}
}
