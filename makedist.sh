#!/bin/bash

docker build -t dister . 
docker run -it --rm --entrypoint /bin/ash --name dister -d dister
docker exec dister /bin/ash -c "cp /dist/mineduino* /mineduino.jar"
docker cp dister:/mineduino.jar .
docker rm -f dister
docker image rm dister
