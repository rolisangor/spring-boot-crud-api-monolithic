CREATE TABLE IF NOT EXISTS users (
       id bigserial NOT NULL,
       created_at TIMESTAMP,
       modified_at TIMESTAMP,
       version INT NOT NULL,
       password VARCHAR(300),
       first_name VARCHAR(50),
       last_name VARCHAR(50),
       email VARCHAR(250) UNIQUE NOT NULL,
       gender VARCHAR(50),
       company VARCHAR(150),
       avatar VARCHAR (500),
       job_title VARCHAR(150),
       role_id bigserial NOT NULL,
       PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS role (
       id bigserial NOT NULL,
       created_at TIMESTAMP,
       modified_at TIMESTAMP,
       version INT NOT NULL,
       name VARCHAR(50),
       PRIMARY KEY (id)
);

INSERT INTO role (created_at, modified_at, version, name)VALUES('2022-07-04T13:08:21.899448819Z', '2022-07-04T13:08:21.899454817Z', 0, 'ROLE_ADMIN');
INSERT INTO role (created_at, modified_at, version, name)VALUES('2022-07-04T13:08:21.899448819Z', '2022-07-04T13:08:21.899454817Z', 0, 'ROLE_USER');

INSERT INTO users (role_id, password, created_at, modified_at, version, first_name, last_name, email, gender, company, avatar, job_title)VALUES (1, '$2a$10$ZM3xc87L4QjvcdRU7Q1zaOBPzzk.kTu9AAw5akL8l74.dvV/zsUWW', '2022-07-04T13:08:21.899448819Z', '2022-07-04T13:08:21.899454817Z', 0, 'Odette', 'Bracer', 'admin@email.com','Female', 'Devify', 'https://robohash.org/quiatqueest.png?size=50x50&set=set1', 'Compensation Analyst');
INSERT INTO users (role_id, password, created_at, modified_at, version, first_name, last_name, email, gender, company, avatar, job_title)VALUES (2, '$2a$10$lqSsIHHdzgbkW8ZWPFqIt.zZHykj3/s.Y/DT3lyl7geUiktw7va7O', '2022-07-04T13:08:21.899448819Z', '2022-07-04T13:08:21.899454817Z', 0, 'Anastasie', 'Davidge','user@email.com', 'Female', 'Fivechat', 'https://robohash.org/estveniamtotam.png?size=50x50&set=set1','Occupational Therapist');
INSERT INTO users (role_id, password, created_at, modified_at, version, first_name, last_name, email, gender, company, avatar, job_title)VALUES (2, '$2a$10$lqSsIHHdzgbkW8ZWPFqIt.zZHykj3/s.Y/DT3lyl7geUiktw7va7O', '2022-07-04T13:08:21.899448819Z', '2022-07-04T13:08:21.899454817Z', 0, 'Tammie', 'Planque','tplanque2@google.de', 'Male', 'Youbridge', 'https://robohash.org/utestaccusantium.png?size=50x50&set=set1', 'Cost Accountant');
INSERT INTO users (role_id, password, created_at, modified_at, version, first_name, last_name, email, gender, company, avatar, job_title)VALUES (2, '$2a$10$lqSsIHHdzgbkW8ZWPFqIt.zZHykj3/s.Y/DT3lyl7geUiktw7va7O', '2022-07-04T13:08:21.899448819Z', '2022-07-04T13:08:21.899454817Z', 0, 'Frazier', 'Sollas',        'fsollas3@ocn.ne.jp', 'Male', 'Topiczoom', 'https://robohash.org/fugitetconsequatur.png?size=50x50&set=set1',        'Chemical Engineer');
INSERT INTO users (role_id, password, created_at, modified_at, version, first_name, last_name, email, gender, company, avatar, job_title)VALUES (2, '$2a$10$lqSsIHHdzgbkW8ZWPFqIt.zZHykj3/s.Y/DT3lyl7geUiktw7va7O', '2022-07-04T13:08:21.899448819Z', '2022-07-04T13:08:21.899454817Z', 0, 'Raye', 'Paladini',        'rpaladini4@imdb.com', 'Non-binary', 'Topicshots',        'https://robohash.org/ipsadelenitidolores.png?size=50x50&set=set1', 'Tax Accountant');
