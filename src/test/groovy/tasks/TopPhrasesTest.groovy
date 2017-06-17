package tasks

import spock.lang.Specification
import java.sql.*;
import groovy.sql.Sql
import tasks.TopPhrases


class TopPhrasesTest extends Specification{
	def 'find top N phrases with more cardinality'() {
		given:
		List<String> res=null;

		when:
		res=TopPhrases.findTopPhrases(fileName, topNumber)

		println("oie zhii "+res)
		
		then:
		res==topPhrases
		where:
		fileName |topNumber | topPhrases
		"top_phrases_test_small_1.txt" |5|["","l","o","e","m"]
		"top_phrases_test_small_2.txt" |50|["", "z", "k", "c", "n", "d", "v", "m", "e", "b", "p", "q", "g", "o", "l", "y", "i", "u", "j", "r", "f", "s", "x", "a", "h", "t", "w", "tk", "ns", "wr", "ma", "em", "bh", "ao", "us", "rw", "ek", "cy", "sq", "kf", "jv", "hg", "xi", "vp", "ty", "rb", "qx", "ui", "ub", "ra"]
	}
}
