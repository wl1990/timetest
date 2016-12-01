#!/bin/bash

#取当前目录
BASE_PATH=`cd "$(dirname "$0")"; pwd`

#设置java运行参数
DEFAULT_JAVA_OPTS=" -server -Xmx1g -Xms1g -Xmn256m -XX:PermSize=256m -Xss256k -XX:+DisableExplicitGC -XX:+UseConcMarkSweepGC -XX:+CMSParallelRemarkEnabled -XX:+UseCMSCompactAtFullCollection -XX:LargePageSizeInBytes=128m -XX:+UseFastAccessorMethods -XX:+UseCMSInitiatingOccupancyOnly -XX:CMSInitiatingOccupancyFraction=70 "

#引入外部参数配置文件:
SHELL_PARAMS="$BASE_PATH/params.conf"
if [ -f "$SHELL_PARAMS" ]; then 
	. $SHELL_PARAMS
fi

#定义变量:
APP_PATH=${APP_PATH:-`dirname "$BASE_PATH"`}
CLASS_PATH=${CLASS_PATH:-$APP_PATH/config:$APP_PATH/lib/*}
JAVA_OPTS=${JAVA_OPTS:-$DEFAULT_JAVA_OPTS}
MAIN_CLASS=${MAIN_CLASS:-"com.wl.timer.Main"}


exist(){
			if test $( pgrep -f "$MAIN_CLASS $APP_PATH" | wc -l ) -eq 0 
			then
				return 1
			else
				return 0
			fi
}

start(){
		if exist; then
				echo "Pay Check Timer is already running."
				exit 1
		else
	    	cd $APP_PATH
	    	mkdir $APP_PATH/logs
				nohup java -Dfile.encoding=UTF-8 $JAVA_OPTS -cp $CLASS_PATH $MAIN_CLASS $APP_PATH & 
				echo "Pay Check Timer is started."
		fi
}

stop(){
		runningPID=`pgrep -f "$MAIN_CLASS $APP_PATH"`
		if [ "$runningPID" ]; then
				echo "Pay Check Timer pid: $runningPID"
        count=0
        kwait=5
        echo "Pay Check Timer is stopping, please wait..."
        kill -15 $runningPID
					until [ `ps --pid $runningPID 2> /dev/null | grep -c $runningPID 2> /dev/null` -eq '0' ] || [ $count -gt $kwait ]
		        do
		            sleep 1
		            let count=$count+1;
		        done

	        if [ $count -gt $kwait ]; then
	            kill -9 $runningPID
	        fi
        clear
        echo "Pay Check Timer is stopped."
    else
    		echo "Pay Check Timer has not been started."
    fi
}

check(){
   if exist; then
   	 echo "Pay Check Timer is alive."
   	 exit 0
   else
   	 echo "Pay Check Timer is dead."
   	 exit -1
   fi
}

restart(){
        stop
        start
}

case "$1" in

start)
        start
;;
stop)
        stop
;;
restart)
        restart
;;
check)
        check
;;
*)
        echo "available operations: [start|stop|restart|check]"
        exit 1
;;
esac