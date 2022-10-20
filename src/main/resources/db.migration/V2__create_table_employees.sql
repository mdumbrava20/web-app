CREATE SEQUENCE employees_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE employees
(
    id           INT DEFAULT employees_seq.nextval NOT NULL PRIMARY KEY,
    first_name   VARCHAR2(20)                      NOT NULL,
    last_name    VARCHAR2(20)                      NOT NULL,
    department   INT                               NOT NULL,
    email        VARCHAR2(50)                      NOT NULL UNIQUE,
    phone_number VARCHAR2(20)                      NOT NULL UNIQUE,
    salary       DECIMAL                           NOT NULL,
    CONSTRAINT employees_departments FOREIGN KEY (department) REFERENCES departments (id),
    CONSTRAINT salary_check CHECK ( salary >= 1.0 )
);