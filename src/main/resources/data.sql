-- src/main/resources/data.sql

-- Insert initial roles if they don't exist
INSERT INTO roles (name, description)
SELECT 'ROLE_USER', 'Default user role'
WHERE NOT EXISTS (SELECT 1 FROM roles WHERE name = 'ROLE_USER');

-- Add other roles if needed, e.g., ROLE_ADMIN
-- INSERT INTO roles (name, description)
-- SELECT 'ROLE_ADMIN', 'Administrator role'
-- WHERE NOT EXISTS (SELECT 1 FROM roles WHERE name = 'ROLE_ADMIN'); 