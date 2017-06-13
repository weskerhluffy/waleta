package com.farenda.tutorials.spock
 
import spock.lang.Specification
import java.sql.*;
import groovy.sql.Sql

 
class VergTest extends Specification {
 
    def 'la mierda'() {
    given:
    String ass="";
    // [] is Groovy literal for List and is infered
    def sql = Sql.newInstance('jdbc:mysql://localhost:3306/caca', 'caca',
         'caca', 'com.mysql.jdbc.Driver')

        // | emp_no | birth_date | first_name | last_name | gender | hire_date  |


    when:
      sql.eachRow('select * from employees') {
         tp ->
         println([tp.emp_no,tp.birth_date,tp.first_name,tp.last_name,tp.gender, tp.hire_date])
	 ass="${tp.emp_no}~${tp.birth_date}"
      }

    then:
    // Asserts are implicit and not need to be stated.
    // Change "==" to "!=" and see what's happening!
    ass=="1~2017-06-12"

    }
}
