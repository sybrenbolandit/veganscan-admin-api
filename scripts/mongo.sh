#!/usr/bin/env bash
docker run -d -p 27017-27019:27017-27019 --name mongodb mongo:4.0.6
docker exec -it mongodb bash

# mongo
# use vegandb
# execute all migration scripts