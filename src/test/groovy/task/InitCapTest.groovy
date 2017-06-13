package task

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
		"hello world" | "Hello World"
		"helloworld" | "Helloworld"
		" hello world" | "Hello World" 
		"%#hello world" | "%#hello World" 
		"%#hello 321world" | "%#hello 321world" 
		"%#hello a 321world" | "%#hello A 321world" 
		"%#hello a	321world" | "%#hello A	321world" 
		"%#hello a	321world      " | "%#hello A	321world" 
		"" | "" 
		"v" | "V" 
		"9" | "9" 
		"Something Else" | "Something Else" 
		"%*124323" | "%*124323"
		"		  		" | "		  		" 
		"	while do something " | "	while Do Something" 
		"        while do something " | "While Do Something" 
		"        while //do something " | "While //do Something" 
	}
}
