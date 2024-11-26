-- Insert dummy games into the 'game' table
INSERT INTO store.game (game_id, name, price, description) VALUES
                                                         (UUID_TO_BIN('14910372-c39d-7de7-b05a-93f8166cf7af'), 'Checkers', 5.00, 'A classic strategy game for two players.'),
                                                         (UUID_TO_BIN('fde47098-ab1d-11ef-9cd2-0242ac120002'), 'Chess', 10.00, 'The timeless game of kings.'),
                                                         (UUID_TO_BIN('12d242be-ab1e-11ef-9cd2-0242ac120002'), 'Othello', 7.50, 'A fun and strategic board game.'),
                                                         (UUID_TO_BIN('2e468e92-ab1e-11ef-9cd2-0242ac120002'), 'Battle Ship', 8.99, 'Sink your opponent\'s fleet.');

-- Insert dummy reviews into the 'reviews' table
INSERT INTO store.reviews (review_id, player_id, game_id, rating, comment, created_at) VALUES
                                                                                     (UUID_TO_BIN('4668df20-ab1e-11ef-9cd2-0242ac120002'), UUID_TO_BIN('39b5b7d0-ab1e-11ef-9cd2-0242ac120002'), UUID_TO_BIN('14910372-c39d-7de7-b05a-93f8166cf7af'), 4, 'Fun and engaging!', '2024-01-01 12:00:00'),
                                                                                     (UUID_TO_BIN('4c1674be-ab1e-11ef-9cd2-0242ac120002'), UUID_TO_BIN('39b5b7d0-ab1e-11ef-9cd2-0242ac120002'), UUID_TO_BIN('fde47098-ab1d-11ef-9cd2-0242ac120002'), 3, 'Quite challenging.', '2024-01-03 10:15:00'),
                                                                                     (UUID_TO_BIN('5314ba14-ab1e-11ef-9cd2-0242ac120002'), UUID_TO_BIN('39b5b7d0-ab1e-11ef-9cd2-0242ac120002'), UUID_TO_BIN('12d242be-ab1e-11ef-9cd2-0242ac120002'), 2, 'Could be better.', '2024-01-04 08:45:00'),
                                                                                     (UUID_TO_BIN('585d9478-ab1e-11ef-9cd2-0242ac120002'), UUID_TO_BIN('39b5b7d0-ab1e-11ef-9cd2-0242ac120002'), UUID_TO_BIN('2e468e92-ab1e-11ef-9cd2-0242ac120002'), 5, 'Absolutely love it!', '2024-01-05 19:00:00');
