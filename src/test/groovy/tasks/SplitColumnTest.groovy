package tasks

import spock.lang.Specification

import java.sql.*;
import groovy.sql.Sql
import com.sjurgemeyer.spock.SingleExecution
import spock.lang.Shared


class SplitColumnTest extends Specification{

	@Shared
	Sql sql=null;

	def setupSpec() {
		sql = Sql.newInstance('jdbc:mysql://localhost:3306/test', 'test',
				'test', 'com.mysql.jdbc.Driver')
	}

	/**
	 * Check that the columns are split in the "|" character. The
	 * <code>SingleExecution</code> annotation is to execute
	 * <code>callSplitColumn</code> once and compare the multiple rows of the
	 * result against the corresponding entry in the <code>where</code> table
	 * according to the order in which the results where delivered.
	 * @return
	 */
	@SingleExecution ("callSplitColumn")
	def "must split a column by a delimiter |"() {
		given:
		String row=""

		when:
		row=result[idx];

		then:
		row==resultStr
		where:
		idx | resultStr
		1 | "1, Smith"
		2 | "2, Julio"
		3 | "2, Jones"
		4 | "2, Falcons"
		5 | "3, White"
		6 | "3, Snow"
		7 | "4, Paint"
		8 | "4, It"
		9 | "4, Red"
		10 | "5, Green"
		11 | "5, Lantern"
		12 | "6, Brown"
		13 | "6, bag"
		14 | "7, "
		15 | "7, "
		16 | "8, one^two"
		17 | "8, three^four"
		18 | "8, five"
		19 | "8, six^seven"
		20 | "9, aaa"
		21 | "9, bbb"
		22 | "9, "
		23 | "9, ccc"
		24 | "10, "
		25 | "10, "
		26 | "10, "
		27 | "10, "
		28 | "10, hello"
		29 | "11, "
		30 | "11, "
		31 | "11, "
		32 | "11, "
		33 | "11, hello"
		34 | "11, "
		35 | "11, "
		36 | "11, "
	}

	/**
	 * Check that the columns are split in the "^" character.
	 * @return
	 */
	@SingleExecution ("callSplitColumn1")
	def "must split a column by a delimiter ^"() {
		given:
		String row=""

		when:
		row=result[idx];

		then:
		row==resultStr
		where:
		idx | resultStr
		0 | "1, Smith"
		1 | "2, Julio|Jones|Falcons"
		2 | "3, White|Snow"
		3 | "4, Paint|It|Red"
		4 | "5, Green|Lantern"
		5 | "6, Brown|bag"
		6 | "7, |"
		7 | "8, one"
		8 | "8, two|three"
		9 | "8, four|five|six"
		10 | "8, seven"
		11 | "9, aaa|bbb||ccc"
		12 | "10, ||||hello"
		13 | "11, ||||hello|||"
	}

	def callSplitColumn= {
		List<String> rows=new ArrayList<String>();
		rows.add(null)

		sql.eachRow("call split_column ('|')") { tp ->
			rows.add(tp.result_str)
		}
		return rows;
	}

	def callSplitColumn1= {
		List<String> rows=new ArrayList<String>();

		sql.eachRow("call split_column ('^')") { tp ->
			rows.add(tp.result_str)
		}
		return rows;
	}
}
