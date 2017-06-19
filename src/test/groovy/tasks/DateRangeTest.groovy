package tasks

import spock.lang.Specification
import java.sql.*;
import groovy.sql.Sql
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class DateRangeTest extends Specification{
	def 'search for open bugs for each date in a range'() {
		given:
		List<List<String>,List<Integer>> bugCount=new ArrayList<>();
		PreparedStatement pstmt;
		Connection con;
		ResultSet res;

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		def sql = Sql.newInstance('jdbc:mysql://red_queen:3306/caca', 'caca',
				'caca', 'com.mysql.jdbc.Driver')
		con=sql.getConnection();

		pstmt=con.prepareStatement(""" 
select a.Date as date, (select count(*) from bugs b where b.open_date<=a.Date and (b.close_date> a.Date or b.close_date is null)  ) as open_bugs
from (
    select date(?) + INTERVAL (a.a + (10 * b.a) + (100 * c.a)) DAY as Date
    from (select 0 as a union all select 1 union all select 2 union all select 3 union all select 4 union all select 5 union all select 6 union all select 7 union all select 8 union all select 9) as a
    cross join (select 0 as a union all select 1 union all select 2 union all select 3 union all select 4 union all select 5 union all select 6 union all select 7 union all select 8 union all select 9) as b
    cross join (select 0 as a union all select 1 union all select 2 union all select 3 union all select 4 union all select 5 union all select 6 union all select 7 union all select 8 union all select 9) as c
) a
where a.Date between ? and ? """)
		when:
		LocalDate beginRangeLocal= LocalDate.parse(beginDateStr, formatter);
		LocalDate endRangeLocal= LocalDate.parse(endDateStr, formatter);
		Date beginRange= Date.valueOf(beginRangeLocal)
		Date endRange= Date.valueOf(endRangeLocal)

		pstmt.setString(1,beginDateStr)
		pstmt.setDate(2,beginRange)
		pstmt.setDate(3,endRange)

		res=pstmt.executeQuery()
		println(res.class.name)

		// TODO: try/catch block
		while (res.next()) {
			String date= res.getString("date");
			Integer openBugs=res.getInt("open_bugs")
			println("la fecha "+date+" los bugs abiertos "+openBugs)
			bugCount.add([date, openBugs])
		}

		then:
		bugCount==bugCountRef

		where:
		beginDateStr|endDateStr|bugCountRef
		/** Just one range is used because the test values where adjusted to check several cases, basically:
		 * <ul> 
		 * <li> When the bug has open_date before the range. 
		 * 		<ul>
		 * 			<li> When the bug has close_date before the range.
		 * 			<li> When the bug has close_date during the range.
		 * 			<li> When the bug has close_date after the range.
		 * 		</ul>
		 * <li> When the bug has open_date during the range.
		 * 		<ul>
		 * 			<li> When the bug has close_date during the range.
		 * 			<li> When the bug has close_date after the range.
		 * 		</ul>
		 * <li> When the bug has open_date after range.
		 * 		<ul>
		 * 			<li> When the bug has close_date after the range.
		 * 		</ul>
		 * </ul>
		 */
		
		
		"2011-11-12"|  "2012-04-26"|[
			["2011-11-12", 5],
			["2011-11-13", 4],
			["2011-11-14", 4],
			["2011-11-15", 4],
			["2011-11-16", 4],
			["2011-11-17", 4],
			["2011-11-18", 4],
			["2011-11-19", 4],
			["2011-11-20", 4],
			["2011-11-21", 4],
			["2011-11-22", 5],
			["2011-11-23", 5],
			["2011-11-24", 5],
			["2011-11-25", 5],
			["2011-11-26", 5],
			["2011-11-27", 5],
			["2011-11-28", 5],
			["2011-11-29", 5],
			["2011-11-30", 5],
			["2011-12-01", 5],
			["2011-12-02", 5],
			["2011-12-03", 5],
			["2011-12-04", 5],
			["2011-12-05", 5],
			["2011-12-06", 5],
			["2011-12-07", 5],
			["2011-12-08", 5],
			["2011-12-09", 5],
			["2011-12-10", 6],
			["2011-12-11", 6],
			["2011-12-12", 6],
			["2011-12-13", 6],
			["2011-12-14", 6],
			["2011-12-15", 6],
			["2011-12-16", 6],
			["2011-12-17", 6],
			["2011-12-18", 6],
			["2011-12-19", 6],
			["2011-12-20", 6],
			["2011-12-21", 6],
			["2011-12-22", 6],
			["2011-12-23", 6],
			["2011-12-24", 6],
			["2011-12-25", 6],
			["2011-12-26", 6],
			["2011-12-27", 6],
			["2011-12-28", 6],
			["2011-12-29", 6],
			["2011-12-30", 6],
			["2011-12-31", 6],
			["2012-01-01", 6],
			["2012-01-02", 6],
			["2012-01-03", 6],
			["2012-01-04", 6],
			["2012-01-05", 6],
			["2012-01-06", 6],
			["2012-01-07", 6],
			["2012-01-08", 6],
			["2012-01-09", 6],
			["2012-01-10", 6],
			["2012-01-11", 6],
			["2012-01-12", 6],
			["2012-01-13", 5],
			["2012-01-14", 5],
			["2012-01-15", 5],
			["2012-01-16", 5],
			["2012-01-17", 5],
			["2012-01-18", 5],
			["2012-01-19", 5],
			["2012-01-20", 5],
			["2012-01-21", 5],
			["2012-01-22", 5],
			["2012-01-23", 5],
			["2012-01-24", 5],
			["2012-01-25", 5],
			["2012-01-26", 5],
			["2012-01-27", 5],
			["2012-01-28", 5],
			["2012-01-29", 5],
			["2012-01-30", 5],
			["2012-01-31", 5],
			["2012-02-01", 5],
			["2012-02-02", 5],
			["2012-02-03", 5],
			["2012-02-04", 5],
			["2012-02-05", 5],
			["2012-02-06", 5],
			["2012-02-07", 5],
			["2012-02-08", 5],
			["2012-02-09", 5],
			["2012-02-10", 5],
			["2012-02-11", 5],
			["2012-02-12", 5],
			["2012-02-13", 5],
			["2012-02-14", 5],
			["2012-02-15", 5],
			["2012-02-16", 5],
			["2012-02-17", 5],
			["2012-02-18", 7],
			["2012-02-19", 7],
			["2012-02-20", 7],
			["2012-02-21", 7],
			["2012-02-22", 7],
			["2012-02-23", 7],
			["2012-02-24", 6],
			["2012-02-25", 6],
			["2012-02-26", 6],
			["2012-02-27", 6],
			["2012-02-28", 6],
			["2012-02-29", 6],
			["2012-03-01", 6],
			["2012-03-02", 6],
			["2012-03-03", 6],
			["2012-03-04", 6],
			["2012-03-05", 6],
			["2012-03-06", 6],
			["2012-03-07", 6],
			["2012-03-08", 6],
			["2012-03-09", 6],
			["2012-03-10", 6],
			["2012-03-11", 6],
			["2012-03-12", 6],
			["2012-03-13", 6],
			["2012-03-14", 6],
			["2012-03-15", 6],
			["2012-03-16", 6],
			["2012-03-17", 6],
			["2012-03-18", 6],
			["2012-03-19", 6],
			["2012-03-20", 6],
			["2012-03-21", 6],
			["2012-03-22", 6],
			["2012-03-23", 6],
			["2012-03-24", 6],
			["2012-03-25", 6],
			["2012-03-26", 6],
			["2012-03-27", 7],
			["2012-03-28", 7],
			["2012-03-29", 7],
			["2012-03-30", 7],
			["2012-03-31", 7],
			["2012-04-01", 7],
			["2012-04-02", 7],
			["2012-04-03", 7],
			["2012-04-04", 7],
			["2012-04-05", 7],
			["2012-04-06", 7],
			["2012-04-07", 7],
			["2012-04-08", 7],
			["2012-04-09", 7],
			["2012-04-10", 7],
			["2012-04-11", 7],
			["2012-04-12", 7],
			["2012-04-13", 7],
			["2012-04-14", 7],
			["2012-04-15", 7],
			["2012-04-16", 7],
			["2012-04-17", 7],
			["2012-04-18", 7],
			["2012-04-19", 7],
			["2012-04-20", 7],
			["2012-04-21", 7],
			["2012-04-22", 7],
			["2012-04-23", 6],
			["2012-04-24", 6],
			["2012-04-25", 6],
			["2012-04-26", 7]
		]


	}
}
