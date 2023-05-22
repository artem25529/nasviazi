INSERT INTO authorities (id, authority) VALUES (1, 'ADMIN');
INSERT INTO authorities (id, authority) VALUES (2, 'USER');

INSERT INTO users (username, enabled, password) VALUES ('James', 1, '$2a$10$yoSiCKt7y4A5Eo.epwWV5ubWseP.pzwg5mSp7nmMmqNHwO1cIdy4m');
INSERT INTO users (username, enabled, password) VALUES ('Kirk', 1, '$2a$10$yoSiCKt7y4A5Eo.epwWV5ubWseP.pzwg5mSp7nmMmqNHwO1cIdy4m');

INSERT INTO user_authority(user_id, authority_id) VALUES ('James', 1);
INSERT INTO user_authority(user_id, authority_id) VALUES ('Kirk', 2);
