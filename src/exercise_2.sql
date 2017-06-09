drop function if exists initcap;
delimiter $$
create function initcap(input_str text)
  returns text
begin
  declare input_str_int text;
  DECLARE input_str_len INT;
  declare spaces_present int;
  declare char_to_replace varchar(1);
  
  set input_str_len=length(input_str_int);
  
  if(input_str_len = 0 or (input_str REGEXP '[[:alpha:]]') = 0) then
  	return input_str;
  end if;
  
  set input_str_int=lower(input_str);
  
  set spaces_present=instr(input_str_int, ' ');
  while (spaces_present <> 0) do
    if(spaces_present+1 <= input_str_len) then
      set char_to_replace = substring(input_str_int, spaces_present+1,1);
      if((char_to_replace REGEXP '[[:alpha:]]') <> 0) then
	      set char_to_replace = upper(char_to_replace);
	      set input_str_int=insert(input_str_int,spaces_present+1,1,char_to_replace);
      end if;
    end if;
    set spaces_present=locate(' ',input_str_int,spaces_present+1);
  end while;
  
  return input_str_int;
end;
$$
delimiter ;

select initcap('earthfeef');
select initcap('earth feef');
