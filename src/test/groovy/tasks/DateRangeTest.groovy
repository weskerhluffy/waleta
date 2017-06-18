package tasks

import spock.lang.Specification
import java.sql.*;
import groovy.sql.Sql
import java.time.LocalDate

class DateRangeTest extends Specification{
	def 'search for open bugs'() {
		given:
		List<List<LocalDate>,List<Integer>> bugCount;
		PreparedStatement pstmt;
		Connection con;
		ResultSet res;
		def sql = Sql.newInstance('jdbc:mysql://red_queen:3306/caca', 'caca',
				'caca', 'com.mysql.jdbc.Driver')
		con=sql.getConnection();

		pstmt=con.prepareStatement(""" 
select a.Date as date, (select count(*) from bugs b where b.open_date<=a.Date and (b.close_date> a.Date or b.close_date is null)  ) as open_bugs
from (
    select date(@range_begin) + INTERVAL (a.a + (10 * b.a) + (100 * c.a)) DAY as Date
    from (select 0 as a union all select 1 union all select 2 union all select 3 union all select 4 union all select 5 union all select 6 union all select 7 union all select 8 union all select 9) as a
    cross join (select 0 as a union all select 1 union all select 2 union all select 3 union all select 4 union all select 5 union all select 6 union all select 7 union all select 8 union all select 9) as b
    cross join (select 0 as a union all select 1 union all select 2 union all select 3 union all select 4 union all select 5 union all select 6 union all select 7 union all select 8 union all select 9) as c
) a
where a.Date between ? and ? """)
		when:
		LocalDate beginRangeLocal= LocalDate.of(yearBegin,monthBegin,dayBegin)
		LocalDate endRangeLocal= LocalDate.of(yearEnd,monthEnd,dayEnd )
		Date beginRange= Date.valueOf(beginRangeLocal)
		Date endRange= Date.valueOf(endRangeLocal)

		pstmt.setDate(1,beginRange)
		pstmt.setDate(2,endRange)

		res=pstmt.executeQuery()
		println(res.class.name)

		// TODO: try/catch block
		while (res.next()) {
			Date date= rs.getDate("date").toLocalDate();
			Integer openBugs=rs.getInt("open_bugs")
			println("la fecha "+date+" los bugs abiertos "+openBugs)
		}

		then:
		true

		where:
		yearBegin|monthBegin|dayBegin| yearEnd|monthEnd|dayEnd|bugCountRef
		2011|11|12|2012|04|26|[]
	}
}
