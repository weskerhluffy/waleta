package task

import spock.lang.Specification
import java.sql.*;
import groovy.sql.Sql
import tasks.TopPhrases

class TopPhrasesTest extends Specification{
	def 'ola'() {
		given:
		List<String> res=null;

		when:
		res=TopPhrases.findTopPhrases("/tmp/test.txt",5)
		println("oie zhiii "+res)

		then:
		true
	}
}
