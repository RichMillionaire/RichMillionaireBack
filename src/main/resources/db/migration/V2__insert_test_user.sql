-- Insert test user with password 'password123'
-- The password is BCrypt hashed
INSERT INTO users (username, email, password, first_name, last_name, enabled)
VALUES ('testuser', 'test@example.com', '$2a$10$xn3LI/AjqicFYZFruSwve.681477XaVNaUQbr1gioaWPn4t1KsnmG', 'Test', 'User', true)
ON CONFLICT (username) DO NOTHING;

-- Get the user and role IDs
INSERT INTO user_roles (user_id, role_id)
SELECT u.id, r.id
FROM users u, roles r
WHERE u.username = 'testuser' AND r.name = 'ROLE_USER'
ON CONFLICT DO NOTHING;

