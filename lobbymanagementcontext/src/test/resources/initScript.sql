CREATE DATABASE lobby_management;
GRANT ALL
ON lobby_management.* TO 'root'@'%';
GRANT SHOW
DATABASES ON *.* TO 'root'@'%';
FLUSH PRIVILEGES;
