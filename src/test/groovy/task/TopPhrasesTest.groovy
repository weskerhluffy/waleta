package task

import spock.lang.Specification
import java.sql.*;
import groovy.sql.Sql
import tasks.TopPhrases


class TopPhrasesTest extends Specification{
	def 'find top 5 phrases with more cardinality'() {
		given:
		List<String> res=null;

		when:
		res=TopPhrases.findTopPhrases("top_phrases_test_small_1.txt", 5)

		println("oie zhii "+res)
		
		then:
		res[rank]==phrase
		where:
		rank | phrase
		0 | ""
		1 | "l"
		2 | "o"
		3 | "e"
		4 | "m"
	}
}
