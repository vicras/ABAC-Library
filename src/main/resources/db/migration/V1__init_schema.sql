create sequence seq_common_user start with 1 increment by 1;
create sequence seq_document start with 1 increment by 1;

CREATE TABLE common_user
(
    id         BIGINT NOT NULL,
    created_at TIMESTAMP with time zone,
    updated_at TIMESTAMP with time zone,
    login      VARCHAR(255),
    password   VARCHAR(255),
    position   VARCHAR(255),
    department VARCHAR(255),
    CONSTRAINT pk_common_user PRIMARY KEY (id)
);

CREATE TABLE document
(
    id         BIGINT NOT NULL,
    created_at TIMESTAMP with time zone,
    updated_at TIMESTAMP with time zone,
    title      VARCHAR(255),
    text       VARCHAR(255),
    approved   BOOLEAN,
    creator_id BIGINT,
    CONSTRAINT pk_document PRIMARY KEY (id)
);

ALTER TABLE document
    ADD CONSTRAINT FK_DOCUMENT_ON_CREATOR FOREIGN KEY (creator_id) REFERENCES common_user (id);