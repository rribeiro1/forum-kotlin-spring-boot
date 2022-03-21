INSERT INTO AUTHOR(name, email, pass) VALUES('Student', 'aluno@email.com', '$2a$10$JDsao6aF92byDOip4It4JOylqhKmYZegaMs0zl68o1tKGYnYNEdg6');
INSERT INTO AUTHOR(name, email, pass) VALUES('Teacher', 'teacher@email.com', '123456');

INSERT INTO COURSE(name, category) VALUES('Spring Boot', 'Development');
INSERT INTO COURSE(name, category) VALUES('HTML 5', 'Front-end');

INSERT INTO TOPIC(title, message, creation_date, status, author_id, course_id) VALUES('Doubt', 'Error while creating the project', '2019-05-05 18:00:00', 'NOT_ANSWERED', 1, 1);
INSERT INTO TOPIC(title, message, creation_date, status, author_id, course_id) VALUES('Doubt 2', 'Project does not compile', '2019-05-05 19:00:00', 'NOT_ANSWERED', 1, 1);
INSERT INTO TOPIC(title, message, creation_date, status, author_id, course_id) VALUES('Doubt 3', 'Tag HTML', '2019-05-05 20:00:00', 'NOT_ANSWERED', 1, 2);

INSERT INTO ANSWER(message, creation_date, topic_id, author_id) VALUES ('Try to do it', '2019-05-05 18:00:00', 1, 2);
INSERT INTO ANSWER(message, creation_date, topic_id, author_id) VALUES ('Does not work', '2019-05-05 18:00:00', 1, 1);
INSERT INTO ANSWER(message, creation_date, topic_id, author_id) VALUES ('Hmm so we have a problem', '2019-05-05 18:00:00', 1, 2);

INSERT INTO PRIVILEGE(id, name) VALUES (1, 'READ_TOPIC');
INSERT INTO PRIVILEGE(id, name) VALUES (2, 'CREATE_TOPIC');
INSERT INTO PRIVILEGE(id, name) VALUES (3, 'EDIT_TOPIC');
INSERT INTO PRIVILEGE(id, name) VALUES (4, 'DELETE_TOPIC');

INSERT INTO ROLE(id, name) VALUES (1, 'ROLE_USER');
INSERT INTO ROLE(id, name) VALUES (2, 'ROLE_STAFF');
INSERT INTO ROLE(id, name) VALUES (3, 'ROLE_ADMIN');

-- USER ROLE (READ_TOPIC)
INSERT INTO ROLE_PRIVILEGES(role_id, privilege_id) VALUES (1, 1);

-- STAFF ROLE (READ_TOPIC, CREATE_TOPIC, EDIT_TOPIC)
INSERT INTO ROLE_PRIVILEGES(role_id, privilege_id) VALUES (2, 2);
INSERT INTO ROLE_PRIVILEGES(role_id, privilege_id) VALUES (2, 3);
INSERT INTO ROLE_PRIVILEGES(role_id, privilege_id) VALUES (2, 4);

-- ADMIN ROLE (READ_TOPIC, CREATE_TOPIC, EDIT_TOPIC, DELETE_TOPIC)
INSERT INTO ROLE_PRIVILEGES(role_id, privilege_id) VALUES (3, 1);
INSERT INTO ROLE_PRIVILEGES(role_id, privilege_id) VALUES (3, 2);
INSERT INTO ROLE_PRIVILEGES(role_id, privilege_id) VALUES (3, 3);
INSERT INTO ROLE_PRIVILEGES(role_id, privilege_id) VALUES (3, 4);

-- User 1 has ADMIN staff.
INSERT INTO AUTHOR_ROLES(author_id, role_id) VALUES (1, 2);