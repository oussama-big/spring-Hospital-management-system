-- 1. Disable checks to prevent issues with existing data
SET FOREIGN_KEY_CHECKS = 0;

-- 2. CREATE the column (This is what was missing or failing)
ALTER TABLE users ADD COLUMN role_id BIGINT;

-- 3. LINK the column to the roles table
ALTER TABLE users 
    ADD CONSTRAINT fk_users_roles 
    FOREIGN KEY (role_id) REFERENCES roles(id);

-- 4. CLEAN UP: Delete the old join table that we don't need anymore
DROP TABLE IF EXISTS user_roles;

-- 5. Restore checks
SET FOREIGN_KEY_CHECKS = 1;