CREATE TABLE challenge_completed (
    id VARCHAR(36) PRIMARY KEY,
    user_id VARCHAR(36) NOT NULL,
    challenge_number INTEGER NOT NULL,
    activity_id VARCHAR(36) NOT NULL,
    when_ TIMESTAMP NOT NULL
);

CREATE INDEX idx_challenge_completed_user ON challenge_completed(user_id);
CREATE INDEX idx_challenge_completed_challenge ON challenge_completed(challenge_number);
CREATE INDEX idx_challenge_completed_when ON challenge_completed(when_); 