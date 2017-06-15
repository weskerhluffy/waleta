perl -p -e "s/\|/\n/g" $1 |sed "s/^ *//"|sed "s/ *$//"|sort|uniq -c|sort -n -r |head -5
