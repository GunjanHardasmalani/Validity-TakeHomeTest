FROM openjdk:11
VOLUME /tmp

COPY /target/csvDuplicateReader-*.jar csvDuplicateReader.jar

ENTRYPOINT java -javaagent:/newrelic/newrelic.jar -jar /csvDuplicateReader.jar

EXPOSE 8080
