-- 初始化用户数据
INSERT INTO users (username, password, phone_number, avatar_url, created_at, updated_at) VALUES
('admin', '$2a$10$wQ8vI6j2C7dGxL0fJ5b7uOqKqK6xHgH7uO1rN8sP9tU0vV1wW2xOy', '13800000000', 'https://example.com/avatar/admin.jpg', 1640995200000, 1640995200000),
('user1', '$2a$10$wQ8vI6j2C7dGxL0fJ5b7uOqKqK6xHgH7uO1rN8sP9tU0vV1wW2xOy', '13800000001', 'https://example.com/avatar/user1.jpg', 1640995200000, 1640995200000),
('user2', '$2a$10$wQ8vI6j2C7dGxL0fJ5b7uOqKqK6xHgH7uO1rN8sP9tU0vV1wW2xOy', '13800000002', 'https://example.com/avatar/user2.jpg', 1640995200000, 1640995200000);

-- 初始化用户经历数据
INSERT INTO user_experiences (user_id, title, company, start_date, end_date, description, created_at, updated_at) VALUES
(1, '高级工程师', '海牙科技', 1577836800000, 1640995200000, '负责后端系统架构设计与开发', 1640995200000, 1640995200000),
(2, '前端工程师', '海牙科技', 1577836800000, 1640995200000, '负责前端界面开发与优化', 1640995200000, 1640995200000);