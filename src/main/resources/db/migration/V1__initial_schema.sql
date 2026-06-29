-- V1__initial_schema.sql
-- Initial database schema for AI Retail Suite

CREATE TABLE companies (
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(200) NOT NULL,
    code        VARCHAR(50)  NOT NULL UNIQUE,
    email       VARCHAR(200),
    phone       VARCHAR(20),
    address     VARCHAR(500),
    tax_id      VARCHAR(50),
    logo_url    VARCHAR(500),
    created_at  TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    updated_at  TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    created_by  VARCHAR(255),
    updated_by  VARCHAR(255),
    active      BOOLEAN      NOT NULL DEFAULT TRUE
);

CREATE TABLE permissions (
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(100) NOT NULL UNIQUE,
    code        VARCHAR(100) NOT NULL UNIQUE,
    resource    VARCHAR(100) NOT NULL,
    action      VARCHAR(50)  NOT NULL,
    description VARCHAR(500),
    created_at  TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    updated_at  TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    created_by  VARCHAR(255),
    updated_by  VARCHAR(255),
    active      BOOLEAN      NOT NULL DEFAULT TRUE
);

CREATE TABLE roles (
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(100) NOT NULL,
    code        VARCHAR(100) NOT NULL,
    description VARCHAR(500),
    company_id  BIGINT REFERENCES companies(id),
    created_at  TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    updated_at  TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    created_by  VARCHAR(255),
    updated_by  VARCHAR(255),
    active      BOOLEAN      NOT NULL DEFAULT TRUE,
    UNIQUE (company_id, code)
);

CREATE TABLE role_permissions (
    role_id       BIGINT NOT NULL REFERENCES roles(id),
    permission_id BIGINT NOT NULL REFERENCES permissions(id),
    PRIMARY KEY (role_id, permission_id)
);

CREATE TABLE branches (
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(200) NOT NULL,
    code        VARCHAR(50)  NOT NULL,
    address     VARCHAR(500),
    phone       VARCHAR(20),
    email       VARCHAR(200),
    is_main     BOOLEAN      NOT NULL DEFAULT FALSE,
    company_id  BIGINT       NOT NULL REFERENCES companies(id),
    created_at  TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    updated_at  TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    created_by  VARCHAR(255),
    updated_by  VARCHAR(255),
    active      BOOLEAN      NOT NULL DEFAULT TRUE,
    UNIQUE (company_id, code)
);

CREATE TABLE users (
    id            BIGSERIAL PRIMARY KEY,
    username      VARCHAR(100) NOT NULL UNIQUE,
    email         VARCHAR(200) NOT NULL UNIQUE,
    password      VARCHAR(255) NOT NULL,
    first_name    VARCHAR(100),
    last_name     VARCHAR(100),
    phone         VARCHAR(20),
    last_login_at TIMESTAMPTZ,
    company_id    BIGINT       NOT NULL REFERENCES companies(id),
    branch_id     BIGINT REFERENCES branches(id),
    created_at    TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    updated_at    TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    created_by    VARCHAR(255),
    updated_by    VARCHAR(255),
    active        BOOLEAN      NOT NULL DEFAULT TRUE
);

CREATE TABLE user_roles (
    user_id BIGINT NOT NULL REFERENCES users(id),
    role_id BIGINT NOT NULL REFERENCES roles(id),
    PRIMARY KEY (user_id, role_id)
);

CREATE TABLE refresh_tokens (
    id          BIGSERIAL PRIMARY KEY,
    token       VARCHAR(500) NOT NULL UNIQUE,
    expiry_date TIMESTAMPTZ  NOT NULL,
    user_id     BIGINT       NOT NULL REFERENCES users(id),
    created_at  TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    updated_at  TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    created_by  VARCHAR(255),
    updated_by  VARCHAR(255),
    active      BOOLEAN      NOT NULL DEFAULT TRUE
);

CREATE TABLE password_reset_tokens (
    id          BIGSERIAL PRIMARY KEY,
    token       VARCHAR(500) NOT NULL UNIQUE,
    expiry_date TIMESTAMPTZ  NOT NULL,
    used        BOOLEAN      NOT NULL DEFAULT FALSE,
    user_id     BIGINT       NOT NULL REFERENCES users(id),
    created_at  TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    updated_at  TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    created_by  VARCHAR(255),
    updated_by  VARCHAR(255),
    active      BOOLEAN      NOT NULL DEFAULT TRUE
);

CREATE TABLE categories (
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(200) NOT NULL,
    description VARCHAR(500),
    company_id  BIGINT       NOT NULL REFERENCES companies(id),
    parent_id   BIGINT REFERENCES categories(id),
    created_at  TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    updated_at  TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    created_by  VARCHAR(255),
    updated_by  VARCHAR(255),
    active      BOOLEAN      NOT NULL DEFAULT TRUE
);

CREATE TABLE brands (
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(200) NOT NULL,
    description VARCHAR(500),
    logo_url    VARCHAR(500),
    company_id  BIGINT       NOT NULL REFERENCES companies(id),
    created_at  TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    updated_at  TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    created_by  VARCHAR(255),
    updated_by  VARCHAR(255),
    active      BOOLEAN      NOT NULL DEFAULT TRUE
);

CREATE TABLE products (
    id            BIGSERIAL PRIMARY KEY,
    sku           VARCHAR(100) NOT NULL,
    name          VARCHAR(300) NOT NULL,
    description   VARCHAR(2000),
    cost_price    NUMERIC(19, 4),
    selling_price NUMERIC(19, 4) NOT NULL,
    barcode       VARCHAR(100),
    unit          VARCHAR(50),
    company_id    BIGINT         NOT NULL REFERENCES companies(id),
    category_id   BIGINT REFERENCES categories(id),
    brand_id      BIGINT REFERENCES brands(id),
    created_at    TIMESTAMPTZ    NOT NULL DEFAULT NOW(),
    updated_at    TIMESTAMPTZ    NOT NULL DEFAULT NOW(),
    created_by    VARCHAR(255),
    updated_by    VARCHAR(255),
    active        BOOLEAN        NOT NULL DEFAULT TRUE,
    UNIQUE (company_id, sku)
);

CREATE TABLE inventory (
    id               BIGSERIAL PRIMARY KEY,
    quantity         NUMERIC(19, 4) NOT NULL DEFAULT 0,
    reorder_level    NUMERIC(19, 4),
    reorder_quantity NUMERIC(19, 4),
    branch_id        BIGINT         NOT NULL REFERENCES branches(id),
    product_id       BIGINT         NOT NULL REFERENCES products(id),
    created_at       TIMESTAMPTZ    NOT NULL DEFAULT NOW(),
    updated_at       TIMESTAMPTZ    NOT NULL DEFAULT NOW(),
    created_by       VARCHAR(255),
    updated_by       VARCHAR(255),
    active           BOOLEAN        NOT NULL DEFAULT TRUE,
    UNIQUE (branch_id, product_id)
);

CREATE TABLE customers (
    id             BIGSERIAL PRIMARY KEY,
    name           VARCHAR(200) NOT NULL,
    email          VARCHAR(200),
    phone          VARCHAR(20),
    address        VARCHAR(500),
    loyalty_points INTEGER      NOT NULL DEFAULT 0,
    company_id     BIGINT       NOT NULL REFERENCES companies(id),
    created_at     TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    updated_at     TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    created_by     VARCHAR(255),
    updated_by     VARCHAR(255),
    active         BOOLEAN      NOT NULL DEFAULT TRUE
);

CREATE TABLE sales (
    id          BIGSERIAL PRIMARY KEY,
    sale_number VARCHAR(50)    NOT NULL UNIQUE,
    sale_date   TIMESTAMPTZ    NOT NULL,
    subtotal    NUMERIC(19, 4) NOT NULL,
    tax         NUMERIC(19, 4) NOT NULL DEFAULT 0,
    discount    NUMERIC(19, 4) NOT NULL DEFAULT 0,
    total       NUMERIC(19, 4) NOT NULL,
    status      VARCHAR(30)    NOT NULL DEFAULT 'COMPLETED',
    branch_id   BIGINT         NOT NULL REFERENCES branches(id),
    customer_id BIGINT REFERENCES customers(id),
    cashier_id  BIGINT         NOT NULL REFERENCES users(id),
    created_at  TIMESTAMPTZ    NOT NULL DEFAULT NOW(),
    updated_at  TIMESTAMPTZ    NOT NULL DEFAULT NOW(),
    created_by  VARCHAR(255),
    updated_by  VARCHAR(255),
    active      BOOLEAN        NOT NULL DEFAULT TRUE
);

CREATE TABLE sale_items (
    id         BIGSERIAL PRIMARY KEY,
    quantity   NUMERIC(19, 4) NOT NULL,
    unit_price NUMERIC(19, 4) NOT NULL,
    discount   NUMERIC(19, 4) NOT NULL DEFAULT 0,
    total      NUMERIC(19, 4) NOT NULL,
    sale_id    BIGINT         NOT NULL REFERENCES sales(id),
    product_id BIGINT         NOT NULL REFERENCES products(id),
    created_at TIMESTAMPTZ    NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ    NOT NULL DEFAULT NOW(),
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    active     BOOLEAN        NOT NULL DEFAULT TRUE
);

CREATE TABLE payments (
    id         BIGSERIAL PRIMARY KEY,
    method     VARCHAR(30)    NOT NULL,
    amount     NUMERIC(19, 4) NOT NULL,
    reference  VARCHAR(200),
    status     VARCHAR(30)    NOT NULL DEFAULT 'COMPLETED',
    sale_id    BIGINT         NOT NULL REFERENCES sales(id),
    created_at TIMESTAMPTZ    NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ    NOT NULL DEFAULT NOW(),
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    active     BOOLEAN        NOT NULL DEFAULT TRUE
);

CREATE TABLE audit_logs (
    id         BIGSERIAL PRIMARY KEY,
    action     VARCHAR(100) NOT NULL,
    entity     VARCHAR(100) NOT NULL,
    entity_id  BIGINT,
    user_id    BIGINT,
    details    VARCHAR(2000),
    ip_address VARCHAR(50),
    created_at TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    active     BOOLEAN      NOT NULL DEFAULT TRUE
);

CREATE INDEX idx_branches_company ON branches(company_id);
CREATE INDEX idx_users_company ON users(company_id);
CREATE INDEX idx_products_company ON products(company_id);
CREATE INDEX idx_inventory_branch ON inventory(branch_id);
CREATE INDEX idx_sales_branch ON sales(branch_id);
CREATE INDEX idx_sales_date ON sales(sale_date);
CREATE INDEX idx_audit_logs_entity ON audit_logs(entity, entity_id);
