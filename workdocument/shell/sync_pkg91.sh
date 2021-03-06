#!/bin/bash

WARPATH="/home"
BASE_HOME="/home/zygong/wordspace/git/51szzc"


echo ":::::::sync select...::::::::"
echo "Usage: { jar | war }"
echo -n "Your input is: "
read var
case $var in
jar)
        echo 正在同步所有jar包...
	if [ $? -ne 0 ];then
		echo jar包同步失败！
	else
       		echo jar包同步完成!!!
	fi
;;
war)
        echo 正在同步所有war包...
        sshpass -p "asd123" scp $BASE_HOME/51szzc-new/out/artifacts/vcoin/vcoin.war root@130.252.100.91:${WARPATH}
	if [ $? -ne 0 ];then
		echo war包同步失败！
	else
       		echo war包同步完成!!!
	fi
;;
*)
        echo Input error.
        exit 1
esac
