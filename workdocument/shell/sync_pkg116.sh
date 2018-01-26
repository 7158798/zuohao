#!/bin/bash

JARPATH="/home/otcuser/update-res/jar"
WARPATH="/home/otcuser/update-res/api"
BASE_HOME="/home/zygong/wordspace/git/51szzc/otc-zh"


echo ":::::::sync select...::::::::"
echo "Usage: { jar | war }"
echo -n "Your input is: "
read var
case $var in
jar)
        echo 正在同步所有jar包...
	sshpass -p "otc\$2017" scp $BASE_HOME/otc-service/otc-service-all/target/otc-service.jar otcuser@130.252.100.116:${JARPATH}
	if [ $? -ne 0 ];then
		echo jar包同步失败！
	else
       		echo jar包同步完成!!!
	fi
;;
war)
        echo 正在同步所有war包...
        sshpass -p "otc\$2017" scp $BASE_HOME/otc-api/otc-api-web/target/otc-api-web.war otcuser@130.252.100.116:${WARPATH}
        sshpass -p "otc\$2017" scp $BASE_HOME/otc-api/otc-api-console/target/otc-api-console.war otcuser@130.252.100.116:${WARPATH}
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
