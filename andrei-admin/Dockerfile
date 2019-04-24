FROM java:8
VOLUME /tmp
ENV PARAMS=""
ENV TZ=PRC
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
ADD ./target/andrei-admin.jar app.jar
ENTRYPOINT ["sh","-c","java -jar /app.jar $PARAMS"]
