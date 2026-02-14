CREATE TABLE transactions (
    id UUID PRIMARY KEY,
    external_transaction_id VARCHAR(255) NOT NULL,
    customer_id VARCHAR(64) NOT NULL,
    customer_age INT NOT NULL,
    amount NUMERIC(19, 2) NOT NULL,
    currency VARCHAR(3) NOT NULL,
    country VARCHAR(2) NOT NULL,
    status VARCHAR(16) NOT NULL,
    created_at TIMESTAMP NOT NULL
);

create index idx_transaction_customer_id on transactions (customer_id);
create index idx_transaction_created_at on transactions (created_at);

CREATE TABLE risk_assessments (
    id UUID PRIMARY KEY,
    transaction_id UUID NOT NULL UNIQUE REFERENCES transactions(id),
    score INT NOT NULL,
    level VARCHAR(16) NOT NULL,
    reasons TEXT NOT NULL,
    assessed_at TIMESTAMP NOT NULL
);

