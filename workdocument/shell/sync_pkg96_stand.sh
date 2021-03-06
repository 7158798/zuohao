#!/bin/bash

WARPATH="/home/szzc/update-res"
BASE_HOME="/home/zygong/wordspace/git"


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
        sshpass -p "szzc\$2017" scp $BASE_HOME/51szzc-hongkong/out/artifacts/stand/stand.war szzc@130.252.100.96:${WARPATH}
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
