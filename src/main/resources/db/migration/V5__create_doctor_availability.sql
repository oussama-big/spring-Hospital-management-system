CREATE TABLE doctor_availabilities (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    doctor_id BIGINT NOT NULL,
    day_of_week VARCHAR(10) NOT NULL, -- MONDAY, TUESDAY, etc.
    start_time TIME NOT NULL,         -- 08:00:00
    end_time TIME NOT NULL,           -- 16:00:00
    
    -- Ensuring a doctor doesn't have two different shifts on the same day
    CONSTRAINT unique_doctor_day UNIQUE (doctor_id, day_of_week),
    
    -- Linking to your specialized Doctor table (Shared ID)
    CONSTRAINT fk_availability_doctor 
        FOREIGN KEY (doctor_id) REFERENCES doctors(id) ON DELETE CASCADE
);