FROM mysql:8.0.32
ENV MYSQL_USER=user
ENV MYSQL_ROOT_PASSWORD=root
ENV MYSQL_DATABASE=usermanager
WORKDIR opt/app

COPY data.sql docker/mysql/
COPY schema.sql docker/mysql/

