perl -p -e "s/\|/\n/g" $1|tr -d '\r' |awk '{$1=$1}1'|sort|uniq -c|sort -n -r |head -5
