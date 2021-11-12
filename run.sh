#!/usr/bin/env /bin/bash
DOCKER_BUILDKIT=1
docker build --tag tomcat-todo .
docker run \
    --name tomcat -it --rm \
    -p 8080:8080 \
    -v ${PWD}/todo-rest-service/target/todo-rest-service.war:/usr/local/tomcat/webapps/todo-rest-service.war \
    tomcat-todo