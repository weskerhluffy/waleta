package task

import spock.lang.Specification
import java.sql.*;
import groovy.sql.Sql

class SplitColumnTest extends Specification{
	def 'must split a column by a delimiter'() {
		given:
		List<String> rows=new ArrayList<String>();
		def sql = Sql.newInstance('jdbc:mysql://localhost:3306/caca', 'caca',
				'caca', 'com.mysql.jdbc.Driver')

		when:
		println("pero ka cra");
                sql.eachRow("call split_column ('|')") { tp ->
                        println([
                                tp.result_str
                        ])
                        rows.add(tp.result_str)
                }

		then:
		true

	}
}
