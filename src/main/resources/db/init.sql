DROP TABLE IF EXISTS roles cascade ;
CREATE TABLE roles(
                      id               serial not null primary key,
                      name             varchar(50)
);

INSERT INTO roles (name) VALUES ('ROLE_USER'), ('ROLE_ADMIN');
DROP TABLE IF EXISTS user_roles cascade ;
CREATE TABLE user_roles(
                           user_id int not null,
                           role_id int not null,

                           primary key (user_id, role_id),
                           FOREIGN KEY (user_id) references users (id) ON DELETE NO ACTION ON UPDATE NO ACTION,
                           FOREIGN KEY (role_id) references roles (id) ON DELETE NO ACTION ON UPDATE NO ACTION
);

INSERT INTO users (login, password, first_name, second_name, email) VALUES (
'admin','$2a$10$i0OAZ6RAUOfXKBLrNPH5P.6GoqTPrV1x7Q8yvhl0Mitvc00R1hmxy' , 'dima', 'dimasik', 'dima.atzo@gmail.com');
INSERT INTO users (login, password, first_name, second_name, email) VALUES (
'user','$2a$10$i0OAZ6RAUOfXKBLrNPH5P.6GoqTPrV1x7Q8yvhl0Mitvc00R1hmxy' ,'user', 'hyuseer', 'hyemail@mail.ru');

INSERT INTO user_roles (user_id, role_id) VALUES (1, 2), (2, 1);