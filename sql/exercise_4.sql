drop table if exists bugs;
CREATE TABLE bugs
( 
	ID INT auto_increment, 
	open_date date,
	close_date date,
	severity int,
	primary key (id)
);

insert into bugs values (NULL, '2011-01-01','2011-10-02',10); -- 0
insert into bugs values (NULL, '2011-02-04','2011-11-12',10); -- 0
insert into bugs values (NULL, '2011-07-04','2011-11-13',10); -- 1
insert into bugs values (NULL, '2011-03-14','2012-02-24',10); -- 1
insert into bugs values (NULL, '2011-01-17','2012-07-22',10); -- 1
insert into bugs values (NULL, '2010-11-09',NULL,10);         -- 1

insert into bugs values (NULL, '2011-11-12','2012-01-13',10); -- 1
insert into bugs values (NULL, '2011-12-10','2012-04-23',10); -- 1
insert into bugs values (NULL, '2012-03-27','2012-04-26',10); -- 1
insert into bugs values (NULL, '2012-02-18','2012-06-19',10); -- 1
insert into bugs values (NULL, '2011-11-22','2012-04-27',10); -- 1
insert into bugs values (NULL, '2012-04-26','2012-11-04',10); -- 1
insert into bugs values (NULL, '2012-02-18',NULL,10);         -- 1

insert into bugs values (NULL, '2012-04-26','2012-08-13',10); -- 1
insert into bugs values (NULL, '2012-05-21','2012-09-16',10); -- 0
insert into bugs values (NULL, '2012-10-05','2012-12-17',10); -- 0


-- The main problem with this task is that there is no out-of-the-box way to
-- generate a series of anything (like generate_series in postgreSQL), so that
-- I could generate all dates of a given date range. So, to circunvent this, I
-- generated all combinations of 0-9 from units to hundreds (000-999), one in
-- each row, and in the select part of the query I added them (as days) to the
-- begin date of the range wanted (this of course is assuming the range wanted
-- is composed of less than a thousand days, but if that is the case we can
-- generate the combinations up to 9999 or more as needed). 
--
-- After that I filtered only the window of interest (from the begin of the
-- range to the end). This way I generated all the dates in the date range.
--
-- In the outer select I did a subselect (which is executed for each date in
-- the range) to count all bugs that were opened in the current date or before
-- and that ended the day after the current date or have not close date.
--
-- In this case I used mysql variables to centralize the parameters of the
-- query (begin date, end date), but in a programming language this is easily
-- traduced to a prepared statement and the dates can be the paremeters of
-- such statement.
set @range_begin = '2011-11-12';
set @range_end= '2012-04-26';
select a.Date as date, (select count(*) from bugs b where b.open_date<=a.Date and (b.close_date> a.Date or b.close_date is null)  ) as open_bugs
from (
    select date(@range_begin) + INTERVAL (a.a + (10 * b.a) + (100 * c.a)) DAY as Date
    from (select 0 as a union all select 1 union all select 2 union all select 3 union all select 4 union all select 5 union all select 6 union all select 7 union all select 8 union all select 9) as a
    cross join (select 0 as a union all select 1 union all select 2 union all select 3 union all select 4 union all select 5 union all select 6 union all select 7 union all select 8 union all select 9) as b
    cross join (select 0 as a union all select 1 union all select 2 union all select 3 union all select 4 union all select 5 union all select 6 union all select 7 union all select 8 union all select 9) as c
) a
where a.Date between @range_begin and @range_end;