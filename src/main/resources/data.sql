-- Datos de ejemplo para el Amaris Knowledge Center

INSERT INTO technologies (name) VALUES ('Java');
INSERT INTO technologies (name) VALUES ('Spring Boot');
INSERT INTO technologies (name) VALUES ('Angular');
INSERT INTO technologies (name) VALUES ('React');
INSERT INTO technologies (name) VALUES ('JWT');

INSERT INTO technology_versions (technology_id, version) VALUES (1, '17');
INSERT INTO technology_versions (technology_id, version) VALUES (1, '21');
INSERT INTO technology_versions (technology_id, version) VALUES (2, '3.3');
INSERT INTO technology_versions (technology_id, version) VALUES (2, '3.2');
INSERT INTO technology_versions (technology_id, version) VALUES (2, '3.5');
INSERT INTO technology_versions (technology_id, version) VALUES (3, '18');
INSERT INTO technology_versions (technology_id, version) VALUES (3, '17');
INSERT INTO technology_versions (technology_id, version) VALUES (4, '18');
INSERT INTO technology_versions (technology_id, version) VALUES (4, '19');
INSERT INTO technology_versions (technology_id, version) VALUES (5, 'HS256');
INSERT INTO technology_versions (technology_id, version) VALUES (5, 'RS256');

INSERT INTO employees (name, email, position) VALUES ('Ana García', 'ana.garcia@amaris.com', 'Backend Developer');
INSERT INTO employees (name, email, position) VALUES ('Luis Pérez', 'luis.perez@amaris.com', 'Full Stack Developer');
INSERT INTO employees (name, email, position) VALUES ('María López', 'maria.lopez@amaris.com', 'Frontend Developer');

INSERT INTO employee_skills (employee_id, skill) VALUES (1, 'Java');
INSERT INTO employee_skills (employee_id, skill) VALUES (1, 'Spring Boot');
INSERT INTO employee_skills (employee_id, skill) VALUES (1, 'Microservicios');
INSERT INTO employee_skills (employee_id, skill) VALUES (2, 'Angular');
INSERT INTO employee_skills (employee_id, skill) VALUES (2, 'Spring Boot');
INSERT INTO employee_skills (employee_id, skill) VALUES (2, 'DevOps');
INSERT INTO employee_skills (employee_id, skill) VALUES (3, 'React');
INSERT INTO employee_skills (employee_id, skill) VALUES (3, 'TypeScript');

INSERT INTO employee_technologies (employee_id, technology_id) VALUES (1, 1);
INSERT INTO employee_technologies (employee_id, technology_id) VALUES (1, 2);
INSERT INTO employee_technologies (employee_id, technology_id) VALUES (2, 2);
INSERT INTO employee_technologies (employee_id, technology_id) VALUES (2, 3);
INSERT INTO employee_technologies (employee_id, technology_id) VALUES (3, 4);

-- Ejemplo: autenticación con JWT
INSERT INTO implementations (title, description) VALUES (
    'Autenticación con JWT',
    'Implementación de autenticación stateless con JWT en una API REST de Spring Boot,'
    || ' con emisión de tokens, refresco y filtros de seguridad.'
);

INSERT INTO implementation_employees (implementation_id, employee_id) VALUES (1, 1);
INSERT INTO implementation_employees (implementation_id, employee_id) VALUES (1, 2);

INSERT INTO implementation_tags (implementation_id, tag) VALUES (1, 'jwt');
INSERT INTO implementation_tags (implementation_id, tag) VALUES (1, 'autenticación');
INSERT INTO implementation_tags (implementation_id, tag) VALUES (1, 'seguridad');
INSERT INTO implementation_tags (implementation_id, tag) VALUES (1, 'oauth2');

INSERT INTO implementation_technology_versions (implementation_id, technology_id, version_id) VALUES (1, 2, 2);
INSERT INTO implementation_technology_versions (implementation_id, technology_id, version_id) VALUES (1, 5, 10);