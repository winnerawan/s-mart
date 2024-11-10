FROM php:7.4-fpm

WORKDIR /app/

RUN apt-get update && apt-get install -y \
   zip \
   zlib1g-dev \
   libzip-dev \
   g++ \
   unzip \
   curl \
   libxml2-dev \
   unzip \
   libicu-dev \
   libbz2-dev \
   libpng-dev \
   libjpeg-dev \
   libmcrypt-dev \
   libreadline-dev \
   libfreetype6-dev \
   wkhtmltopdf \
   python3 \
   python3-pip  \
   python3-xlrd \
   nano \
    && docker-php-ext-configure intl \
    && docker-php-ext-configure gd --with-freetype --with-jpeg \
    && docker-php-ext-install -j$(nproc) json gd opcache mysqli pdo_mysql zip pdo_mysql

RUN apt-get update \
  && curl -sS https://getcomposer.org/installer | php -- --install-dir=/usr/local/bin --filename=composer \
  && apt-get autoremove
