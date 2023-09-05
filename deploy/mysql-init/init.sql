CREATE USER 'todoUser'@'localhost' IDENTIFIED BY '1234';
GRANT ALL PRIVILEGES ON todolist.* TO 'todoUser'@'localhost';

CREATE DATABASE todolist DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;