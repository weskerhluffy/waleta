package hello
import java.sql.*; 
import groovy.sql.Sql
 
class Mierda{
   static void main(String[] args) {
      // Creating a connection to the database
      def sql = Sql.newInstance('jdbc:mysql://red_queen:3306/caca', 'caca', 
         'caca', 'com.mysql.jdbc.Driver')  
			
      sql.eachRow('select * from bugs') {
         tp -> 
         println([tp.id,tp.open_date])
      }  
	// | emp_no | birth_date | first_name | last_name | gender | hire_date  |	
      sql.close()
   } 
}
