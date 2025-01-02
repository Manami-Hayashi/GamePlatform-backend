CREATE DATABASE player_management;
GRANT ALL
ON player_management.* TO 'root'@'%';
GRANT SHOW
DATABASES ON *.* TO 'root'@'%';
FLUSH PRIVILEGES;