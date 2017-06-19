DROP TABLE IF EXISTS `sometbl`;
-- Test strings, the delimiters in the tests are | and ^.
CREATE TABLE sometbl ( ID INT, NAME VARCHAR(50) );INSERT INTO sometbl VALUES (1, 'Smith'), (2, 'Julio|Jones|Falcons'), (3, 'White|Snow'), (4, 'Paint|It|Red'), (5, 'Green|Lantern'), (6, 'Brown|bag');
INSERT INTO sometbl VALUES (7,'|');
INSERT INTO sometbl VALUES (8,'one^two|three^four|five|six^seven');
INSERT INTO sometbl VALUES (9,'aaa|bbb||ccc');
INSERT INTO sometbl VALUES (10,'||||hello');
INSERT INTO sometbl VALUES (11,'||||hello|||');

-- Split a column into rows by splitting the string of a given column using a
-- given character as delimiter. This implementation is very specific to the
-- test table, because making it generic for every given table and column of
-- such table would imply to generate sql code on the fly (because cursor
-- declarations in MySQL can't take variables in them, such as one for the
-- name of the table). So this is left as future work but this implementation
-- shows the main idea of splitting a string and producing one row for each
-- resulting string.
-- Parameters:
-- delimiter IN The character to be used to split the values of the column.
-- Returns: There isn't a returned value per se, but it outputs the resulting
-- rows so the caller would see this result as a result of a query.
drop procedure if exists split_column;
DELIMITER //
CREATE PROCEDURE split_column 
(IN delimiter CHAR(20))
BEGIN
  DECLARE v_finished INTEGER DEFAULT 0;
  DECLARE v_id int DEFAULT 0;
  DECLARE v_name varchar(50) DEFAULT 0;
  DECLARE previous_pos int DEFAULT 0;
  DECLARE current_pos int DEFAULT 0;
  DECLARE finished_cur_split boolean;
  DECLARE v_name_len int DEFAULT 0;
  DECLARE cur_split_len int DEFAULT 0;
  DECLARE cur_split varchar(50);
  DECLARE result_str_pre text;
  DECLARE result_str text;
  DECLARE sc_cursor CURSOR FOR
    SELECT s.id, s.name from sometbl s;
  DECLARE CONTINUE HANDLER FOR NOT FOUND SET v_finished = 1;  
  create temporary table result_strs
  (
    result_str text
  );
 
  OPEN sc_cursor;

  get_column: LOOP
 
    FETCH sc_cursor INTO v_id, v_name;
 
    IF v_finished = 1 THEN
      LEAVE get_column;
    END IF;
    
    set finished_cur_split = 0;
    set previous_pos = 0;
    set current_pos = 0;
    set v_name_len=length(v_name);
    
    -- The string that is to be prefixed to each resulting string after
    -- splitting the volues of the column. Ideally, there would also have to
    -- be a string to be prepended, if there were columns after the one we are
    -- splitting.
    set result_str_pre = concat(v_id);
    -- We have 2 pointers, one at the last position of the delimiter and the
    -- other at the current position. This marks the current portion of the
    -- string splitted by the delimiter.
    -- So we traverse the string by moving the current position to the the
    -- next ocurrence of the delimiter, and after every iteration we set the
    -- last position to the current one.
    -- The time complexity of this is O(n) where n is the size of the string,
    -- because we visit each element once.
    repeat
      set current_pos = locate(delimiter,v_name,current_pos+1);
      if(current_pos = 0) then
        set finished_cur_split = 1;
        set current_pos=v_name_len;
        set cur_split_len=current_pos-previous_pos;
      else
        set cur_split_len=current_pos-previous_pos-1;
      end if;
      set cur_split=substring(v_name,previous_pos+1,cur_split_len);
      set result_str = concat(result_str_pre,', ',cur_split);
      -- Store the resulting string into a temporary table.
      insert into result_strs values(result_str);
      set previous_pos=current_pos;
    until finished_cur_split
    end repeat;
    
  END LOOP get_column;
 
  CLOSE sc_cursor;

  -- Output all elements of the temporary table.
  select * from result_strs;
  -- The space complexity of this is O(n*m) where n is the size of the bigger
  -- resulting string, and m is the number of rows inserted into the temporary
  -- table.
  drop temporary table result_strs;
END //
DELIMITER ;

call split_column ('|');
