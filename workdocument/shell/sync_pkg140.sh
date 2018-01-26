#!/bin/bash

JARPATH="/home/szzc/update-res/jar"
WARPATH="/home/szzc/update-res/api"
BASE_HOME="/home/zygong/wordspace/git/51szzc"


echo ":::::::sync select...::::::::"
echo "Usage: { jar | war }"
echo -n "Your input is: "
read var
case $var in
jar)
        echo 正在同步所有jar包...
        sshpass -p "szzc\$2017" scp $BASE_HOME/51szzc-rebuild/szzc-service-parent/szzc-service-wallet/target/szzc-service-wallet.jar szzc@130.252.100.140:${JARPATH}
        sshpass -p "szzc\$2017" scp $BASE_HOME/51szzc-rebuild/szzc-service-parent/szzc-service-three/target/szzc-service-three.jar szzc@130.252.100.140:${JARPATH}
	if [ $? -ne 0 ];then
		echo jar包同步失败！
	else
       		echo jar包同步完成!!!
	fi
;;
war)
        echo 正在同步所有war包...
	sshpass -p "szzc\$2017" scp $BASE_HOME/51szzc-rebuild/szzc-api/szzc-api-app/target/szzc-api-app.war szzc@130.252.100.140:${WARPATH}
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
