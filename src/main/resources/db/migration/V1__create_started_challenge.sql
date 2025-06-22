CREATE TABLE started_challenge (
    user_id VARCHAR(36) PRIMARY KEY,
    challenge_number INTEGER NOT NULL,
    when_ TIMESTAMP NOT NULL
);

CREATE INDEX idx_started_challenge_when ON started_challenge(when_); 