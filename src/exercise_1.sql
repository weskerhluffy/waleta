CREATE TABLE votes ( name CHAR(10), votes INT );

INSERT INTO votes VALUES ('Smith',10), ('Jones',15), ('White',20), ('Black',40), ('Green',50), ('Brown',20);


-- They key is to set a mysql variable to 0, then increment it by 1 for each found row.
set @rowno = 0;
select v.*,@rowno:=@rowno+1 as rank from votes v order by v.votes desc;