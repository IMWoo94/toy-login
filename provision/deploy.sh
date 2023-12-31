#!/bin/bash
REPOSITORY=/home
PROJECT_NAME=login
LOG_FILE=standard

cd $REPOSITORY/

echo "> 현재 구동중인 애플리케이션 PID 확인"
CURRENT_PID=$(lsof -i tcp:8080 | awk 'NR!=1 {print$2}')

echo "현재 구동중인 애플리케이션 PID : $CURRENT_PID"
if [ -z "$CURRENT_PID" ]; then
  echo "> 현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다."
else
  echo "> kill -9 $CURRENT_PID"
  kill -9 $CURRENT_PID
  sleep 5
fi

CUR_DTTM=$(date +%Y%m%d%H%M%S)

if [ -e $LOG_FILE.out ]; then
  echo "nohup 백업 : standard-${CUR_DTTM}.out"
  mv $REPOSITORY/standard.out $REPOSITORY/log/standard-${CUR_DTTM}.out
fi

if [ -e $LOG_FILE.err ]; then
  echo "nohup 백업 : standard-${CUR_DTTM}.err"
  mv $REPOSITORY/standard.err $REPOSITORY/log/standard-${CUR_DTTM}.err
fi

echo "> 새 애플리케이션 배포"
# JAR_NAME=#(ls -tr $REPOSITORY/ |grep jar|tail -n 1)
JAR_NAME=$(ls -tr $REPOSITORY/*.jar | tail -n 1)
echo "> JAR Name : $JAR_NAME"
nohup java -jar $JAR_NAME -spring.profiles.active=dev 1> standard.out 2> standard.err &
