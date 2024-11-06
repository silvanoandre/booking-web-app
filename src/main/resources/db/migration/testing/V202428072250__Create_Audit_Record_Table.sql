-- Drop the rooms table if it exists
DROP TABLE IF EXISTS booking_testing_db.audit_record;

-- Create audit_record table

CREATE TABLE booking_testing_db.audit_record
(
    id             BIGINT PRIMARY KEY AUTO_INCREMENT,
    table_name     VARCHAR(255)  NOT NULL,
    operation      VARCHAR(255)  NOT NULL,
    entity_id      BIGINT,
    logged_in_user VARCHAR(255)  NOT NULL,
    entity         VARCHAR(1000) NOT NULL,
    timestamp      TIMESTAMP     NOT NULL
);

