INSERT INTO users (id, email, password)
VALUES (1, 'john@example.com', 'password123'),
       (2, 'sarah@example.com', 'secret456'),
       (3, 'mike@example.com', 'mypass789'),
       (4, 'emily@example.com', 'secure123'),
       (5, 'david@example.com', 'pass1234');

INSERT INTO user_info (user_id, firstname, lastname, birth_date)
VALUES (1, 'John', 'Doe', '1990-05-15'),
       (2, 'Sarah', 'Smith', '1985-09-20'),
       (3, 'Mike', 'Johnson', '1992-03-10'),
       (4, 'Emily', 'Brown', '1988-12-05'),
       (5, 'David', 'Wilson', '1995-07-30');

INSERT INTO portfolio (id, user_id)
VALUES
    (1, 1), (2, 1),
    (3, 2), (4, 2),
    (5, 3), (6, 3),
    (7, 4), (8, 4),
    (9, 5), (10, 5);

INSERT INTO asset (id, portfolio_id, asset_type, name, comments)
VALUES
    -- Portfolio 1
    (1, 1, 'STOCK', 'AAPL', 'Tech company'),
    (2, 1, 'STOCK', 'MSFT', 'Software company'),
    (3, 1, 'STOCK', 'AMZN', 'E-commerce giant'),

   -- Portfolio 2
    (4, 2, 'STOCK', 'GOOGL', 'Parent company of Google'),
    (5, 2, 'STOCK', 'TSLA', 'Electric vehicle manufacturer'),
    (6, 2, 'STOCK', 'FB', 'Social media company'),

    -- Portfolio 3
    (7, 3, 'STOCK', 'JNJ', 'Pharmaceutical company'),
    (8, 3, 'STOCK', 'PG', 'Consumer goods company'),
    (9, 3, 'STOCK', 'DIS', 'Entertainment conglomerate'),

    -- Portfolio 4
    (10, 4, 'STOCK', 'NFLX', 'Streaming service provider'),
    (11, 4, 'STOCK', 'AAPL', 'Tech company'),
    (12, 4, 'STOCK', 'GOOGL', 'Parent company of Google'),

    -- Portfolio 5
    (13, 5, 'STOCK', 'TSLA', 'Electric vehicle manufacturer'),
    (14, 5, 'STOCK', 'AMZN', 'E-commerce giant'),
    (15, 5, 'STOCK', 'JNJ', 'Pharmaceutical company'),

    -- Portfolio 6
    (16, 6, 'STOCK', 'MSFT', 'Software company'),
    (17, 6, 'STOCK', 'AAPL', 'Tech company'),
    (18, 6, 'STOCK', 'FB', 'Social media company'),

    -- Portfolio 7
    (19, 7, 'STOCK', 'PG', 'Consumer goods company'),
    (20, 7, 'STOCK', 'DIS', 'Entertainment conglomerate'),
    (21, 7, 'STOCK', 'NFLX', 'Streaming service provider'),

    -- Portfolio 8
    (22, 8, 'STOCK', 'TSLA', 'Electric vehicle manufacturer'),
    (23, 8, 'STOCK', 'MSFT', 'Software company'),
    (24, 8, 'STOCK', 'JNJ', 'Pharmaceutical company'),

    -- Portfolio 9
    (25, 9, 'STOCK', 'AAPL', 'Tech company'),
    (26, 9, 'STOCK', 'GOOGL', 'Parent company of Google'),
    (27, 9, 'STOCK', 'PG', 'Consumer goods company'),

    -- Portfolio 10
    (28, 10, 'STOCK', 'DIS', 'Entertainment conglomerate'),
    (29, 10, 'STOCK', 'AMZN', 'E-commerce giant'),
    (30, 10, 'STOCK', 'NFLX', 'Streaming service provider');

INSERT INTO asset_transaction (asset_id, position_direction, quantity, open_price, close_price, open_date, close_date)
VALUES
    -- AAPL (0 to 3 entries)
    (1, 'LONG', 10, 150.25, NULL, '2023-01-15 09:30:00', NULL),
    (1, 'SHORT', 5, 155.50, 157.75, '2023-02-10 10:15:00', '2023-02-10 14:30:00'),
    (1, 'LONG', 15, 160.00, NULL, '2023-03-05 08:45:00', NULL),

    -- MSFT (0 to 3 entries)
    (2, 'LONG', 8, 75.50, NULL, '2023-01-20 11:00:00', NULL),
    (2, 'SHORT', 4, 78.25, 76.00, '2023-02-15 09:45:00', '2023-02-15 14:15:00'),

    -- AMZN (0 to 3 entries)
    (3, 'LONG', 12, 30.75, NULL, '2023-01-25 10:30:00', NULL),
    (3, 'SHORT', 6, 33.00, 34.50, '2023-02-20 12:30:00', '2023-02-20 16:45:00'),

    -- GOOGL (0 to 3 entries)
    (4, 'LONG', 7, 1450.75, NULL, '2023-01-30 09:00:00', NULL),
    (4, 'SHORT', 3, 1475.25, 1468.50, '2023-02-25 10:45:00', '2023-02-25 15:30:00'),

    -- TSLA (0 to 3 entries)
    (5, 'LONG', 9, 650.50, NULL, '2023-02-05 13:15:00', NULL),
    (5, 'SHORT', 2, 655.00, 658.75, '2023-03-02 14:30:00', '2023-03-02 16:00:00'),

    -- FB (0 to 3 entries)
    (6, 'LONG', 5, 250.20, NULL, '2023-01-10 08:30:00', NULL),
    (6, 'SHORT', 3, 260.75, 255.50, '2023-02-15 11:45:00', '2023-02-15 14:30:00'),

    -- JNJ (0 to 3 entries)
    (7, 'LONG', 6, 140.50, NULL, '2023-02-20 09:15:00', NULL),
    (7, 'SHORT', 2, 145.25, 143.75, '2023-03-05 10:30:00', '2023-03-05 12:45:00'),

    -- DIS (0 to 3 entries)
    (8, 'LONG', 8, 160.75, NULL, '2023-03-10 08:00:00', NULL),
    (8, 'SHORT', 4, 165.00, 163.25, '2023-04-02 10:45:00', '2023-04-02 14:30:00'),

    -- NFLX (0 to 3 entries)
    (9, 'LONG', 10, 340.50, NULL, '2023-04-10 13:15:00', NULL),
    (9, 'SHORT', 6, 350.25, 348.75, '2023-05-05 14:30:00', '2023-05-05 16:00:00'),

    -- PG (0 to 3 entries)
    (10, 'LONG', 7, 120.25, NULL, '2023-05-15 09:30:00', NULL),
    (10, 'SHORT', 3, 125.50, 123.75, '2023-06-10 10:15:00', '2023-06-10 14:30:00');