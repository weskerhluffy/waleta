-- Change the first letter of every word to uppercase. In this case a word is 
-- defined as a set of characters which start with an alphabetic character and
-- is delimited by empty space.
-- Parameters:
-- input_str IN The string whose words are to be capitalized on its first
--              letter.
drop function if exists initcap;
delimiter $$
create function initcap(input_str text)
  returns text
begin
  declare input_str_int text;
  DECLARE input_str_len INT;
  declare spaces_present int;
  declare char_to_replace varchar(1);
  
  -- For simplicity, remove leading and ending spaces.
  set input_str_int=trim(lower(input_str));
  set input_str_len=length(input_str_int);
  
  -- If the string does not contain any alphanumeric character, there is
  -- nothing to do to it.
  if(input_str_len = 0 or (input_str REGEXP '[[:alpha:]]*') = 0) then
    return input_str;
  end if;
  
  -- Traverse the string by jumping to every white space occurrence, on each
  -- space check if the following character is alphabetic, if so, capitalize
  -- it and jump to the next space, else, only look for the next white space.
  -- Repeat this until there are no more spaces.
  set spaces_present=0;
  repeat
    -- Also make sure we don't exceed the length of the string.
    if(spaces_present+1 <= input_str_len) then
      set char_to_replace = substring(input_str_int, spaces_present+1,1);
      if((char_to_replace REGEXP '[[:alpha:]]') <> 0) then
        set char_to_replace = upper(char_to_replace);
        set input_str_int=insert(input_str_int,spaces_present+1,1,char_to_replace);
      end if;
      set spaces_present=locate(' ',input_str_int,spaces_present+1);
    else
      set spaces_present=0;
    end if;
  until spaces_present = 0
  end repeat;
  
  return input_str_int;
end;
$$
delimiter ;