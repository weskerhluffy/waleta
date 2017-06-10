-- CREATE TABLE sometbl ( ID INT, NAME VARCHAR(50) );
-- INSERT INTO sometbl VALUES (1, 'Smith'), (2, 'Julio|Jones|Falcons'), (3, 'White|Snow'), (4, 'Paint|It|Red'), (5, 'Green|Lantern'), (6, 'Brown|bag');

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
    
    set result_str_pre = concat(v_id);
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
      select result_str;
      set previous_pos=current_pos;
    until finished_cur_split
    end repeat;
    
  END LOOP get_column;
 
  CLOSE sc_cursor;
END //
DELIMITER ;

call split_column ("oi");