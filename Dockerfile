FROM tomcat:8.0.46-jre8-alpine

RUN adduser -D appuser && \
    mkdir /data && \
    chown -R appuser.appuser /usr/local/tomcat && \
    chown appuser.appuser /data
USER appuser

#RUN mkdir /data
COPY ./target/post2file-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/post2file.war


