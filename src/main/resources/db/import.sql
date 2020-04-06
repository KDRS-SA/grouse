
INSERT INTO user (username, password, account_non_expired,
                  credentials_non_expired, account_non_locked, enabled, version) VALUES (
  'admin@example.com',
  '$2a$10$CMniUbEi69xAXd5FTl66wO4cBvVwI/zJGNoZkvx9.U5dWeltzsd2y',
   true, true, true, true, 0);

INSERT INTO user (username, password, account_non_expired,
                  credentials_non_expired, account_non_locked, enabled, version) VALUES (
  'user@example.com',
  '$2a$10$CMniUbEi69xAXd5FTl66wO4cBvVwI/zJGNoZkvx9.U5dWeltzsd2y',
   true, true, true, true, 0);

INSERT INTO user (username, password, account_non_expired,
                  credentials_non_expired, account_non_locked, enabled, version) VALUES (
  'user1@example.com',
  '$2a$10$CMniUbEi69xAXd5FTl66wO4cBvVwI/zJGNoZkvx9.U5dWeltzsd2y',
   true, true, true, true, 0);

INSERT INTO user (username, password, account_non_expired,
                  credentials_non_expired, account_non_locked, enabled, version) VALUES (
  'user2@example.com',
  '$2a$10$CMniUbEi69xAXd5FTl66wO4cBvVwI/zJGNoZkvx9.U5dWeltzsd2y',
   true, true, true, true, 0);

INSERT INTO user (username, password, account_non_expired,
                  credentials_non_expired, account_non_locked, enabled, version) VALUES (
  'template@example.com',
  '$2a$10$CMniUbEi69xAXd5FTl66wO4cBvVwI/zJGNoZkvx9.U5dWeltzsd2y',
   true, true, true, true, 0);

INSERT INTO authority (authority_name) VALUES ('ROLE_USER');
INSERT INTO authority (authority_name) VALUES ('ROLE_TEMPLATE');
INSERT INTO authority (authority_name) VALUES ('ROLE_ADMIN');

INSERT INTO user_authority (username, authority) VALUES ('user@example.com', 'ROLE_USER');
INSERT INTO user_authority (username, authority) VALUES ('user1@example.com', 'ROLE_USER');
INSERT INTO user_authority (username, authority) VALUES ('user2@example.com', 'ROLE_USER');
INSERT INTO user_authority (username, authority) VALUES ('template@example.com', 'ROLE_USER');
INSERT INTO user_authority (username, authority) VALUES ('template@example.com', 'ROLE_TEMPLATE');
INSERT INTO user_authority (username, authority) VALUES ('admin@example.com', 'ROLE_USER');
INSERT INTO user_authority (username, authority) VALUES ('admin@example.com', 'ROLE_TEMPLATE');
INSERT INTO user_authority (username, authority) VALUES ('admin@example.com', 'ROLE_ADMIN');



