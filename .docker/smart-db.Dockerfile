FROM mysql:latest
# FROM mariadb:lts

# RUN apk add --update nano
  
WORKDIR /app

COPY ./mysql/etc/mysql/conf.d/mysql.cnf /etc/mysql/conf.d/mysql.cnf

RUN chmod 644 /etc/mysql/conf.d/mysql.cnf
