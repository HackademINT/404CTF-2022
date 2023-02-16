FROM php:7.4-apache
RUN docker-php-ext-install mysqli pdo_mysql

#On se place dans le répertoire de base pour le site web
WORKDIR /var/www/html

#Copie des fichiers
COPY /website/ .

#On expose sur le port 80
EXPOSE 80