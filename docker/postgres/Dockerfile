FROM postgres:12-alpine
WORKDIR /docker-entrypoint-initdb.d
RUN echo 'create schema test;' > init.sql
