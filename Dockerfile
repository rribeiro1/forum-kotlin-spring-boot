FROM openjdk:8-jre

RUN mkdir -p /app

WORKDIR /app

RUN groupadd -r deploy && \
    useradd -r -s /bin/false -g deploy deploy

COPY build/libs/forum.jar /app/

RUN chown -R deploy:deploy /app

USER deploy

EXPOSE 8080

ENTRYPOINT [ "java" ]
CMD [ "-Djava.security.egd=file:/dev/./urandom", \
      "-d64", \
      "-jar", "forum.jar" ]
