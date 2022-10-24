CREATE SEQUENCE departments_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE departments
(
    id       NUMBER(3) DEFAULT departments_seq.nextval NOT NULL PRIMARY KEY,
    name     VARCHAR2(30)                              NOT NULL,
    location VARCHAR2(20)                              NOT NULL
);
