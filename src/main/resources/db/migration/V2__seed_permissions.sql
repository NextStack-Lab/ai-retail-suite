-- Seed default permissions and super admin role

INSERT INTO permissions (name, code, resource, action, description, created_at, updated_at, active) VALUES
('View Companies', 'COMPANY_READ', 'COMPANY', 'READ', 'View company details', NOW(), NOW(), TRUE),
('Manage Companies', 'COMPANY_WRITE', 'COMPANY', 'WRITE', 'Create and update companies', NOW(), NOW(), TRUE),
('View Branches', 'BRANCH_READ', 'BRANCH', 'READ', 'View branches', NOW(), NOW(), TRUE),
('Manage Branches', 'BRANCH_WRITE', 'BRANCH', 'WRITE', 'Create and update branches', NOW(), NOW(), TRUE),
('View Users', 'USER_READ', 'USER', 'READ', 'View users', NOW(), NOW(), TRUE),
('Manage Users', 'USER_WRITE', 'USER', 'WRITE', 'Create and update users', NOW(), NOW(), TRUE),
('View Products', 'PRODUCT_READ', 'PRODUCT', 'READ', 'View products', NOW(), NOW(), TRUE),
('Manage Products', 'PRODUCT_WRITE', 'PRODUCT', 'WRITE', 'Create and update products', NOW(), NOW(), TRUE),
('View Inventory', 'INVENTORY_READ', 'INVENTORY', 'READ', 'View inventory', NOW(), NOW(), TRUE),
('Manage Inventory', 'INVENTORY_WRITE', 'INVENTORY', 'WRITE', 'Update inventory levels', NOW(), NOW(), TRUE),
('View Sales', 'SALE_READ', 'SALE', 'READ', 'View sales', NOW(), NOW(), TRUE),
('Manage Sales', 'SALE_WRITE', 'SALE', 'WRITE', 'Create and manage sales', NOW(), NOW(), TRUE),
('View Reports', 'REPORT_READ', 'REPORT', 'READ', 'View reports', NOW(), NOW(), TRUE),
('Manage Roles', 'ROLE_WRITE', 'ROLE', 'WRITE', 'Manage roles and permissions', NOW(), NOW(), TRUE);
