INSERT INTO common_user (id, created_at, updated_at, login, password, position)
VALUES
    (100, NOW(), NOW(), 'ceo@gmail.com', '$2a$10$/Gk0kX298L6DbddwB4OcKeN7Qv62gMNRT7yuq2kM4oeoIee/Kw422', 'CEO'),
    (101, NOW(), NOW(), 'manager@gmail.com', '$2a$10$/Gk0kX298L6DbddwB4OcKeN7Qv62gMNRT7yuq2kM4oeoIee/Kw422', 'MANAGER'),
    (102, NOW(), NOW(), 'manager2@gmail.com', '$2a$10$/Gk0kX298L6DbddwB4OcKeN7Qv62gMNRT7yuq2kM4oeoIee/Kw422', 'MANAGER'),
    (103, NOW(), NOW(), 'accountant@gmail.com', '$2a$10$/Gk0kX298L6DbddwB4OcKeN7Qv62gMNRT7yuq2kM4oeoIee/Kw422', 'ACCOUNTANT');

INSERT INTO document (id, created_at, updated_at, title, text, creator_id)
VALUES
    (100, NOW(), NOW(), 'Doc1', 'text1', 100),
    (101, NOW(), NOW(), 'Doc2', 'text2', 100),
    (102, NOW(), NOW(), 'Doc3', 'text3', 101),
    (103, NOW(), NOW(), 'Doc4', 'text4', 101);
