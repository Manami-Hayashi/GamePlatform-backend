CREATE DATABASE game_management;
GRANT ALL
ON game_management.* TO 'root'@'%';
GRANT SHOW
DATABASES ON *.* TO 'root'@'%';
FLUSH PRIVILEGES;