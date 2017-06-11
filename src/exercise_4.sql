--CREATE TABLE bugs
--( 
--	ID INT auto_increment, 
--	open_date date,
--	close_date date,
--	severity int,
--	primary key (id)
--);

--insert into bugs values (NULL, '2012-01-01','2012-01-02',10);


set @range_begin = '2012-11-01';
set @range_end= '2012-12-02';
set @range_size= datediff(date(@range_end),date(@range_begin));
select @range_size;

select a.Date 
from (
    select date(@range_begin) + INTERVAL (a.a + (10 * b.a) + (100 * c.a)) DAY as Date
    from (select 0 as a union all select 1 union all select 2 union all select 3 union all select 4 union all select 5 union all select 6 union all select 7 union all select 8 union all select 9) as a
    cross join (select 0 as a union all select 1 union all select 2 union all select 3 union all select 4 union all select 5 union all select 6 union all select 7 union all select 8 union all select 9) as b
    cross join (select 0 as a union all select 1 union all select 2 union all select 3 union all select 4 union all select 5 union all select 6 union all select 7 union all select 8 union all select 9) as c
) a
where a.Date between @range_begin and @range_end;

select b.* 
from bugs b
where (((b.close_date - interval 1 day) between '2012-01-01' and '2012-02-01') 
       or (b.open_date between '2012-01-01' and '2012-02-01' and (b.close_date is null or b.close_date>'2012-02-01'))
      )
and b.close_date>b.open_date;


select a.Date, (select count(b.*) from bugs b where (b.close_date - interval 1 day) =a.Date)
from (
    select date(@range_begin) + INTERVAL (a.a + (10 * b.a) + (100 * c.a)) DAY as Date
    from (select 0 as a union all select 1 union all select 2 union all select 3 union all select 4 union all select 5 union all select 6 union all select 7 union all select 8 union all select 9) as a
    cross join (select 0 as a union all select 1 union all select 2 union all select 3 union all select 4 union all select 5 union all select 6 union all select 7 union all select 8 union all select 9) as b
    cross join (select 0 as a union all select 1 union all select 2 union all select 3 union all select 4 union all select 5 union all select 6 union all select 7 union all select 8 union all select 9) as c
) a
where a.Date between @range_begin and @range_end;