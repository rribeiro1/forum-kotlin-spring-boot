FROM eclipse-temurin:17.0.4.1_1-jre-focal

RUN mkdir -p /app

WORKDIR /app

RUN groupadd -r deploy && \
    useradd -r -s /bin/false -g deploy deploy

COPY build/libs/forum.jar /app/
COPY libs/dd-java-agent.jar /app/

RUN chown -R deploy:deploy /app

USER deploy

EXPOSE 8080

ENTRYPOINT [ "java" ]

CMD [ "-Djava.security.egd=file:/dev/./urandom", \
      "-javaagent:dd-java-agent.jar", \
      "-jar", "forum.jar" ]
