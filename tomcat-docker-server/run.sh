#!/usr/bin/env /bin/bash
if [ $# -eq 0 ]
  then echo please profice full path to .war file
  exit 1
fi
PATH_TO_WAR_FILE=$1
APP_NAME=$2
DOCKER_BUILDKIT=1
docker build --tag tomcat-todo .
docker run \
    --name tomcat -it --rm \
    -p 8080:8080 \
    -v ${PATH_TO_WAR_FILE}:/usr/local/tomcat/webapps/${APP_NAME}.war \
    tomcat-todo
