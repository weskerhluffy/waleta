package task

import spock.lang.Specification
import java.sql.*;
import groovy.sql.Sql


class RankVotesTest extends Specification {

	def 'ranks should be increasing and votes decreasing'() {
		given:
		Integer resultIdx=0;
		List<Integer> ranks=new ArrayList<Integer>();
		List<Integer> votes=new ArrayList<Integer>();
		def sql = Sql.newInstance('jdbc:mysql://localhost:3306/caca', 'caca',
				'caca', 'com.mysql.jdbc.Driver')

		when:
		sql.execute("set @rowno=0")
		sql.eachRow('select v.*,@rowno:=@rowno+1 as rank from votes v order by v.votes desc') { tp ->
			println([
				tp.rank,
				tp.votes
			])
			ranks.add(tp.rank)
			votes.add(tp.votes)
			resultIdx++
		}

		then:
		checkOrder(ranks, votes)
	}

	public Boolean checkOrder(List<Integer> ranks, List<Integer> votes){
		if(ranks.size()!=votes.size()){
			return false;
		}
		if(ranks.size()<2){
			return false;
		}

		for(Integer i=1; i< ranks.size();i++){
			if(ranks[i]<=ranks[i-1]){
				return false;
			}
			if(votes[i]>votes[i-1]){
				return false;
			}
		}

		return true;
	}
}

//set @rowno = 0;select v.*,@rowno:=@rowno+1 as rank from votes v order by v.votes;
