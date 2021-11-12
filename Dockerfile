FROM tomcat:10
EXPOSE 8080:8080
RUN mv webapps webapps2 && mv webapps.dist webapps
COPY ./tomcat-config/context.xml /usr/local/tomcat/webapps/manager/META-INF/context.xml
COPY ./tomcat-config/context.xml /usr/local/tomcat/webapps/host-manager/META-INF/context.xml
COPY ./tomcat-config/tomcat-users.xml /usr/local/tomcat/conf/tomcat-users.xml
# COPY ./todo-web-app/target/todo-web-app.war /usr/local/tomcat/webapps/todo-web-app.war