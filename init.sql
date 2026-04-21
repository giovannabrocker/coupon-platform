CREATE DATABASE IF NOT EXISTS identity_db;
CREATE DATABASE IF NOT EXISTS coupon_db;

CREATE USER IF NOT EXISTS 'app_user'@'%' IDENTIFIED BY 'sua_senha';

GRANT ALL PRIVILEGES ON identity_db.* TO 'app_user'@'%';
GRANT ALL PRIVILEGES ON coupon_db.* TO 'app_user'@'%';

FLUSH PRIVILEGES;