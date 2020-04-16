# HDemo
#!/bin/sh



## service name
SERVICE_DIR=/home/doctor-service
SERVICE_NAME=doctor-service-0.0.1-SNAPSHOT
JAR_NAME=$SERVICE_NAME\.jar
PID=$SERVICE_NAME\.pid


cd $SERVICE_DIR


        kill `cat $SERVICE_DIR/$PID`
        rm -rf $SERVICE_DIR/$PID
        echo "=== stop $SERVICE_NAME"

        sleep 5
		##
		## edu-service-aa.jar
		## edu-service-aa-bb.jar
        P_ID=`ps -ef | grep -w "$SERVICE_NAME" | grep -v "grep" | awk '{print $2}'`
        if [ "$P_ID" == "" ]; then
            echo "=== $SERVICE_NAME process not exists or stop success"
        else
            echo "=== $SERVICE_NAME process pid is:$P_ID"
            echo "=== begin kill $SERVICE_NAME process, pid is:$P_ID"
            kill -9 $P_ID
        fi

	currentdate=`date +%Y%m%d%H%M%S`
        nohup java  -jar $JAR_NAME --spring.profiles.active=prod >$SERVICE_DIR/$SERVICE_NAME\.log 2>&1 &
        echo $! > $SERVICE_DIR/$PID
        echo "=== start $SERVICE_NAME"


