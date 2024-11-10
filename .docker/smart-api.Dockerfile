FROM nginx:1.20.0

RUN apt-get update \
  && apt-get install -y nano
