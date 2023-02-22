FROM mysql:5.7
WORKDIR /docker-entrypoint-initdb.d
RUN echo -e '\
use mysql;\n\
ALTER USER `root`@`%` IDENTIFIED WITH mysql_native_password BY "123456";\n\
flush privileges;\
' > init.sql
