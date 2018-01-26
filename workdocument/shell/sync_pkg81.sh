#!/bin/bash

JARPATH="/home/tzgl/update-res/jar"
WARPATH="/home/tzgl/update-res/api"
BASE_HOME="/home/zygong/wordspace/git"


echo ":::::::sync select...::::::::"
echo "Usage: { jar | war }"
echo -n "Your input is: "
read var
case $var in
jar)
        echo 正在同步所有jar包...
        sshpass -p "tzgl\$2016" scp $BASE_HOME/51tzgl/service/tzgl-service/tzgl-service-all/target/tzgl-service.jar tzgl@130.252.102.81:${JARPATH}
        sshpass -p "tzgl\$2016" scp $BASE_HOME/51tzgl-common/common/base-service/target/base-service.jar tzgl@130.252.102.81:${JARPATH}
	if [ $? -ne 0 ];then
		echo jar包同步失败！
	else
       		echo jar包同步完成!!!
	fi
;;
war)
        echo 正在同步所有war包...
	sshpass -p "tzgl\$2016" scp $BASE_HOME/51tzgl/service/tzgl-web/target/tzgl-web.war tzgl@130.252.102.81:${WARPATH}
        sshpass -p "tzgl\$2016" scp $BASE_HOME/51tzgl/service/tzgl-api/target/tzgl-api.war tzgl@130.252.102.81:${WARPATH}
        sshpass -p "tzgl\$2016" scp $BASE_HOME/51tzgl/service/tzgl-console/target/tzgl-console.war tzgl@130.252.102.81:${WARPATH}
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
