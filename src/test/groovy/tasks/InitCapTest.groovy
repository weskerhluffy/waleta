package tasks

import spock.lang.Specification
import java.sql.*;
import groovy.sql.Sql

class InitCapTest extends Specification{
	def 'must return words capitalized in the first letter'() {
		given:
		String inputStr=a

		String initCapped=""
		def sql = Sql.newInstance('jdbc:mysql://red_queen:3306/caca', 'caca',
				'caca', 'com.mysql.jdbc.Driver')

		when:
		def res=sql.firstRow("select initcap('"+inputStr+"') as cap");
		initCapped=res.cap

		then:
		initCapped== expectedResult

		where:
		a | expectedResult
		// Very small/null inputs.
		"" | ""
		"v" | "V"
		"9" | "9"
		// Not white-space spaces.
		"		  		" | "		  		"
		// Trivial cases.
		"hello world" | "Hello World"
		"helloworld" | "Helloworld"
		// Test trimming.
		" hello world" | "Hello World"
		// Testing that a word must start with an alphabetic char.
		"%#hello world" | "%#hello World"
		// More general cases compounding previous cases.
		"%#hello 321world" | "%#hello 321world"
		"%#hello a 321world" | "%#hello A 321world"
		"%#hello a	321world" | "%#hello A	321world"
		"%#hello a	321world      " | "%#hello A	321world"
		"Something Else" | "Something Else"
		"%*124323" | "%*124323"
		"	while do something " | "	while Do Something"
		"        while do something " | "While Do Something"
		"        while //do something " | "While //do Something"
	}
}
