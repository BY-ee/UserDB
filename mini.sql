CREATE TABLE user_info(
    no NUMBER(4) NOT NULL PRIMARY KEY,
    id VARCHAR2(20) UNIQUE NOT NULL,
    password VARCHAR2(20) NOT NULL,
    name VARCHAR2(15) NOT NULL,
    birth VARCHAR2(6) NOT NULL,
    email VARCHAR2(35) NOT NULL,
    address VARCHAR2(30) NOT NULL,
    rec_date TIMESTAMP);

CREATE SEQUENCE user_info_seq
START WITH 1
INCREMENT BY 1;

DELETE FROM user_info;

SELECT * FROM user_info;

RENAME mini TO user_info;
RENAME mini_seq TO user_info_seq;