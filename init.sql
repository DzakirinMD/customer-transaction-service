-- Create databases for customer-transaction-service
CREATE DATABASE customer_transaction_service_db;

-- Connect to customer_transaction_service_db and create schema
\c customer_transaction_service_db;
CREATE SCHEMA IF NOT EXISTS customer_transaction_service;
