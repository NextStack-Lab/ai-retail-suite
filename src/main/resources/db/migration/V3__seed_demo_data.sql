-- Seed demo company, branch, super admin role, and admin user (password: password)

INSERT INTO companies (name, code, email, phone, address, created_at, updated_at, active)
VALUES ('Demo Retail Co', 'DEMO', 'admin@demo.com', '+1-555-0100', '123 Main Street', NOW(), NOW(), TRUE);

INSERT INTO branches (name, code, address, phone, email, is_main, company_id, created_at, updated_at, active)
VALUES ('Main Store', 'MAIN', '123 Main Street', '+1-555-0101', 'main@demo.com', TRUE,
        (SELECT id FROM companies WHERE code = 'DEMO'), NOW(), NOW(), TRUE);

INSERT INTO roles (name, code, description, company_id, created_at, updated_at, active)
VALUES ('Super Admin', 'ROLE_SUPER_ADMIN', 'Full system access',
        (SELECT id FROM companies WHERE code = 'DEMO'), NOW(), NOW(), TRUE);

INSERT INTO role_permissions (role_id, permission_id)
SELECT r.id, p.id
FROM roles r
CROSS JOIN permissions p
WHERE r.code = 'ROLE_SUPER_ADMIN'
  AND r.company_id = (SELECT id FROM companies WHERE code = 'DEMO');

INSERT INTO users (username, email, password, first_name, last_name, phone,
                   company_id, branch_id, created_at, updated_at, active)
VALUES ('admin', 'admin@demo.com',
        '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy',
        'System', 'Admin', '+1-555-0100',
        (SELECT id FROM companies WHERE code = 'DEMO'),
        (SELECT id FROM branches WHERE code = 'MAIN' AND company_id = (SELECT id FROM companies WHERE code = 'DEMO')),
        NOW(), NOW(), TRUE);

INSERT INTO user_roles (user_id, role_id)
SELECT u.id, r.id
FROM users u, roles r
WHERE u.username = 'admin'
  AND r.code = 'ROLE_SUPER_ADMIN'
  AND r.company_id = (SELECT id FROM companies WHERE code = 'DEMO');
