-- 1. Insertion des Rôles
INSERT INTO roles (name) VALUES ('ROLE_ADMIN'), ('ROLE_DOCTOR'), ('ROLE_PATIENT'), ('ROLE_SECRETARY');

-- 2. Insertion des Départements
INSERT INTO departments (name, description) 
VALUES ('Cardiologie', 'Spécialité des maladies du cœur'),
       ('Pédiatrie', 'Soins médicaux pour enfants');

-- 3. Insertion des Utilisateurs (Mots de passe en clair pour le test, à hasher plus tard)
-- ID 1: Docteur / ID 2: Patient
INSERT INTO users (first_name, last_name, email, password, enabled) 
VALUES ('Jean', 'Dupont', 'dr.dupont@hsm.com', 'password123', 1),
       ('Oussama', 'Patient', 'oussama.test@gmail.com', 'password123', 1);

-- 4. Attribution des Rôles
INSERT INTO user_roles (user_id, role_id) VALUES (1, 2), (2, 3);

-- 5. Création du profil Docteur (Lié à l'User 1 et au Département 1)
INSERT INTO doctors (user_id, department_id, specialization, license_number)
VALUES (1, 1, 'Cardiologue', 'MED-12345');

-- 6. Création du profil Patient (Lié à l'User 2)
INSERT INTO patients (user_id, date_of_birth, gender, blood_type, emergency_contact)
VALUES (2, '1995-05-15', 'Masculin', 'A+', '0612345678');