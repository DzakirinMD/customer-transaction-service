-- changeset dzakirin:0001-initial-schema.sql

-- Create Customers Table
CREATE TABLE customers (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    customer_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    phone_number VARCHAR(50) UNIQUE NOT NULL
);

-- Create Transactions Table
CREATE TABLE transactions (
    id SERIAL PRIMARY KEY,
    account_number VARCHAR(50) NOT NULL,
    trx_amount DECIMAL(15,2) NOT NULL,
    description TEXT NOT NULL,
    trx_date DATE NOT NULL,
    trx_time TIME NOT NULL,
    customer_id UUID NOT NULL,
    CONSTRAINT fk_customer FOREIGN KEY (customer_id) REFERENCES customers (id) ON DELETE CASCADE
);
