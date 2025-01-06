CREATE DATABASE game_statistics;
GRANT ALL
ON game_statistics.* TO 'root'@'%';
GRANT SHOW
DATABASES ON *.* TO 'root'@'%';
FLUSH PRIVILEGES;
