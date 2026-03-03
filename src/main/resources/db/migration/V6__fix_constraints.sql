-- STEP 1: Remove the old, broken constraints by name
ALTER TABLE appointments DROP FOREIGN KEY fk_app_doctor;
ALTER TABLE appointments DROP FOREIGN KEY fk_app_patient;

-- STEP 2: Add them back, pointing to the CORRECT tables
ALTER TABLE appointments 
ADD CONSTRAINT fk_app_doctor 
FOREIGN KEY (doctor_id) REFERENCES doctors(id);

ALTER TABLE appointments 
ADD CONSTRAINT fk_app_patient 
FOREIGN KEY (patient_id) REFERENCES patients(id);

-- STEP 3: Verify the links
SHOW CREATE TABLE appointments;