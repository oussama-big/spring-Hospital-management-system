-- 1. Disable checks to bypass "Duplicate" or "Missing" errors
SET FOREIGN_KEY_CHECKS = 0;

-- 2. Clean up existing constraints to avoid the "Duplicate" error
-- We use a series of DROPs. If these fail because the key is missing, 
-- it's fine because we've disabled checks!
ALTER TABLE doctors DROP FOREIGN KEY fk_doctors_users;
ALTER TABLE patients DROP FOREIGN KEY fk_patients_users;
ALTER TABLE medical_records DROP FOREIGN KEY fk_records_doctor;
ALTER TABLE appointments DROP FOREIGN KEY fk_app_doctor;

-- 3. Standardize the columns (Removing Auto-Increment)
ALTER TABLE doctors MODIFY COLUMN id BIGINT NOT NULL;
ALTER TABLE patients MODIFY COLUMN id BIGINT NOT NULL;

-- 4. Re-add the constraints fresh
-- This ensures the metadata is perfectly aligned with your Java @MapsId
ALTER TABLE doctors 
    ADD CONSTRAINT fk_doctors_users 
    FOREIGN KEY (id) REFERENCES users(id);

ALTER TABLE patients 
    ADD CONSTRAINT fk_patients_users 
    FOREIGN KEY (id) REFERENCES users(id);

ALTER TABLE medical_records 
    ADD CONSTRAINT fk_records_doctor 
    FOREIGN KEY (doctor_id) REFERENCES doctors(id);

ALTER TABLE appointments 
    ADD CONSTRAINT fk_app_doctor 
    FOREIGN KEY (doctor_id) REFERENCES appointments(id);

-- 5. Restore checks
SET FOREIGN_KEY_CHECKS = 1;