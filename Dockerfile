FROM maven:3-jdk-8 as builder
COPY . /src
RUN cd /src && mvn clean install 
FROM alpine:3.10
COPY --from=builder /src/target/*.jar /
RUN mkdir /dist
RUN cp mineduino-*.jar /dist
