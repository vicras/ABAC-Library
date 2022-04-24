INSERT INTO common_user (id, created_at, updated_at, login, password, position, department)
VALUES (100, NOW(), NOW(), 'author@gmail.com',
        '$2a$10$/Gk0kX298L6DbddwB4OcKeN7Qv62gMNRT7yuq2kM4oeoIee/Kw422', 'AUTHOR', 'WEST'),

       (101, NOW(), NOW(), 'author2@gmail.com',
        '$2a$10$/Gk0kX298L6DbddwB4OcKeN7Qv62gMNRT7yuq2kM4oeoIee/Kw422', 'AUTHOR', 'EAST'),

       (102, NOW(), NOW(), 'redactor@gmail.com',
        '$2a$10$/Gk0kX298L6DbddwB4OcKeN7Qv62gMNRT7yuq2kM4oeoIee/Kw422', 'REDACTOR', 'WEST'),

       (103, NOW(), NOW(), 'redactor2@gmail.com',
        '$2a$10$/Gk0kX298L6DbddwB4OcKeN7Qv62gMNRT7yuq2kM4oeoIee/Kw422', 'REDACTOR', 'EAST'),

       (104, NOW(), NOW(), 'reader@gmail.com',
        '$2a$10$/Gk0kX298L6DbddwB4OcKeN7Qv62gMNRT7yuq2kM4oeoIee/Kw422', 'READER', 'GLOBAL');

INSERT INTO document (id, created_at, updated_at, title, text, creator_id, approved)
VALUES (100, NOW(), NOW(), 'Doc1', 'text1', 100, false),
       (101, NOW(), NOW(), 'Doc2', 'text2', 100, true),
       (102, NOW(), NOW(), 'Doc3', 'text3', 101, false),
       (103, NOW(), NOW(), 'Doc4', 'text4', 101, true);
