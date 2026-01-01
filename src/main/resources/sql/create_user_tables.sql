-- ============================================================
-- Create User Authentication Tables in rites_erc_inspection database
-- Run this script to create user_master and related tables
-- ============================================================

USE rites_erc_inspection;

-- ============================================================
-- TABLE: user_master
-- Stores user credentials and basic information for login
-- ============================================================
CREATE TABLE IF NOT EXISTS user_master (
    userId              INT             AUTO_INCREMENT PRIMARY KEY,
    password            VARCHAR(100)    NOT NULL,
    userName            VARCHAR(100)    NULL,
    email               VARCHAR(255)    NULL,
    mobileNumber        VARCHAR(10)     NULL,
    createdDate         DATETIME        NULL,
    createdBy           VARCHAR(45)     NULL,
    role_name           VARCHAR(255)    NULL,
    employee_id         VARCHAR(50)     NULL
);

-- ============================================================
-- TABLE: role_master
-- Stores available roles in the system
-- ============================================================
CREATE TABLE IF NOT EXISTS role_master (
    ROLEID              INT             AUTO_INCREMENT PRIMARY KEY,
    ROLENAME            VARCHAR(100)    NOT NULL,
    CREATEDBY           VARCHAR(50)     NULL,
    CREATEDDATE         DATETIME        NULL
);

-- ============================================================
-- TABLE: user_role_master
-- Maps users to roles with permissions
-- ============================================================
CREATE TABLE IF NOT EXISTS user_role_master (
    userRoleId          INT             AUTO_INCREMENT PRIMARY KEY,
    userId              INT             NOT NULL,
    roleId              INT             NOT NULL,
    readPermission      TINYINT(1)      NOT NULL,
    writePermission     TINYINT(1)      NOT NULL,
    createdDate         DATETIME        NULL,
    createdBy           VARCHAR(45)     NULL
);

-- ============================================================
-- INSERT DEFAULT ROLES
-- ============================================================
INSERT INTO role_master (ROLEID, ROLENAME, CREATEDBY, CREATEDDATE) VALUES
(1, 'Vendor', 'SYSTEM', NOW()),
(2, 'RIO Help Desk', 'SYSTEM', NOW()),
(3, 'IE', 'SYSTEM', NOW()),
(4, 'IE Secondary', 'SYSTEM', NOW()),
(5, 'Control Manager', 'SYSTEM', NOW()),
(6, 'Rio Finance', 'SYSTEM', NOW()),
(7, 'Process IE', 'SYSTEM', NOW()),
(8, 'SBU Head', 'SYSTEM', NOW())
ON DUPLICATE KEY UPDATE ROLENAME = VALUES(ROLENAME);

-- ============================================================
-- INSERT TEST USERS
-- ============================================================
INSERT INTO user_master (userId, password, userName, email, mobileNumber, createdDate, createdBy, role_name, employee_id) VALUES
(1, 'password', 'vendor_user', 'vendor@example.com', '9876543210', NOW(), 'SYSTEM', 'Vendor', 'EMP001'),
(13, 'password', 'ie_user', 'ie@example.com', '9876543211', NOW(), 'SYSTEM', 'IE', 'EMP013'),
(20, 'password', 'cm_user', 'cm@example.com', '9876543212', NOW(), 'SYSTEM', 'Control Manager', 'EMP020'),
(21, 'password', 'process_ie_user', 'processie@example.com', '9876543213', NOW(), 'SYSTEM', 'Process IE', 'EMP021')
ON DUPLICATE KEY UPDATE userName = VALUES(userName);

-- ============================================================
-- INSERT USER ROLE MAPPINGS
-- ============================================================
INSERT INTO user_role_master (userId, roleId, readPermission, writePermission, createdDate, createdBy) VALUES
(1, 1, 1, 1, NOW(), 'SYSTEM'),
(13, 3, 1, 1, NOW(), 'SYSTEM'),
(20, 5, 1, 1, NOW(), 'SYSTEM'),
(21, 7, 1, 1, NOW(), 'SYSTEM')
ON DUPLICATE KEY UPDATE readPermission = VALUES(readPermission);

-- ============================================================
-- VERIFICATION QUERIES
-- ============================================================
SELECT 'User Master Table Created' AS Status, COUNT(*) AS UserCount FROM user_master;
SELECT 'Role Master Table Created' AS Status, COUNT(*) AS RoleCount FROM role_master;
SELECT 'User Role Master Table Created' AS Status, COUNT(*) AS MappingCount FROM user_role_master;

SELECT '=== TEST USERS ===' AS Info;
SELECT userId, userName, role_name, email FROM user_master;

