CREATE TABLE TRANSACTION (
    id UUID PRIMARY KEY,
    external_transaction_id VARCHAR(255) NOT NULL,
    customer_id UUID NOT NULL,
    customer_age INT NOT NULL,
    amount NUMERIC(19, 2) NOT NULL,
    currency VARCHAR(3) NOT NULL,
    country VARCHAR(255) NOT NULL,
    status VARCHAR(50) NOT NULL,
    created_at TIMESTAMP NOT NULL,
)

create index idx_transaction_customer_id on TRANSACTION (customer_id);
create index idx_transaction_created_at on TRANSACTION (created_at);

CREATE TABLE RISK_ASSESSMENTS (
    id UUID PRIMARY KEY,
    transaction_id UUID NOT NULL UNIQUE REFERENCES TRANSACTION(id),
    score INT NOT NULL,
    level VARCHAR(16) NOT NULL,
    reasons TEXT NOT NULL,
    assessed_at TIMESTAMP NOT NULL
)

