
INSERT INTO user (username, password, account_non_expired,
                  credentials_non_expired, account_non_locked, enabled) VALUES (
  'admin@example.com',
  '$2a$10$Gk3cjYgN0GJd04VkG5R8/OxZ.QVuR.ZH.fN.1z9Jqy8wdgiWoDzqq',
   true, true, true, true);

INSERT INTO user (username, password, account_non_expired,
                  credentials_non_expired, account_non_locked, enabled) VALUES (
  'grouse@example.com',
  '$2a$10$Gk3cjYgN0GJd04VkG5R8/OxZ.QVuR.ZH.fN.1z9Jqy8wdgiWoDzqq',
   true, true, true, true);


INSERT INTO authority (authority_name) VALUES ('ROLE_ADMIN');
INSERT INTO authority (authority_name) VALUES ('ROLE_USER');

INSERT INTO user_authority (username, authority) VALUES ('grouse@example.com', 'ROLE_USER');
INSERT INTO user_authority (username, authority) VALUES ('admin@example.com', 'ROLE_USER');
INSERT INTO user_authority (username, authority) VALUES ('admin@example.com', 'ROLE_ADMIN');



