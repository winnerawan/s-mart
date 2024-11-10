#!/usr/bin/env bash

export $(xargs < ./script.env)

case $1 in
  up)
    docker-compose -p ${NAME}:${TAG} up --build --force-recreate --detach
  ;;
  run)
    docker-compose run ${@:2}
  ;;
  exec)
    docker exec -it ${NAME}-${2} ${@:3}
  ;;
  app)
    docker exec -it ${NAME} ${@:2}
  ;;
  down)
    docker kill $(docker ps -q)
  ;;
  *)
    echo "${0} \"${@:1}\" not found."
  ;;
esac