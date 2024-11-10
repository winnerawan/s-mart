FROM nginx:1.20.0

WORKDIR /app/smart-storage

RUN apt-get update \
  && apt-get install -y nano

# RUN chmod -R guo+w storage/