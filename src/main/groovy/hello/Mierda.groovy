package hello
import java.sql.*; 
import groovy.sql.Sql
 
class Mierda{
   static void main(String[] args) {
      // Creating a connection to the database
      def sql = Sql.newInstance('jdbc:mysql://localhost:3306/caca', 'caca', 
         'caca', 'com.mysql.jdbc.Driver')  
			
      sql.eachRow('select * from employees') {
         tp -> 
         println([tp.emp_no,tp.birth_date,tp.first_name,tp.last_name,tp.gender, tp.hire_date])
      }  
	// | emp_no | birth_date | first_name | last_name | gender | hire_date  |	
      sql.close()
   } 
}
