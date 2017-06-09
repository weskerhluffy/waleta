CREATE TABLE votes ( name CHAR(10), votes INT );

INSERT INTO votes VALUES ('Smith',10), ('Jones',15), ('White',20), ('Black',40), ('Green',50), ('Brown',20);


set @rowno = 0;
select v.*,@rowno:=@rowno+1 as rank from votes v order by v.votes;