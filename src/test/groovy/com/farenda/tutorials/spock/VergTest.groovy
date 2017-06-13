package com.farenda.tutorials.spock
 
import spock.lang.Specification
import java.sql.*;
import groovy.sql.Sql

 
class VergTest extends Specification {
 
    def 'la mierda'() {
    given:
    String ass="";
    // [] is Groovy literal for List and is infered
    def sql = Sql.newInstance('jdbc:mysql://red_queen:3306/caca', 'caca',
         'caca', 'com.mysql.jdbc.Driver')

        // | emp_no | birth_date | first_name | last_name | gender | hire_date  |


    when:
      sql.eachRow('select * from bugs limit 1') {
         tp ->
         println([tp.id,tp.open_date,tp.close_date,tp.severity])
	 ass="${tp.id}~${tp.open_date}"
      }

    then:
    // Asserts are implicit and not need to be stated.
    // Change "==" to "!=" and see what's happening!
    ass=="1~2012-01-01"

    }
}
