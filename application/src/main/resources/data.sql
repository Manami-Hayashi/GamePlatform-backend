-- Insert dummy games into the 'game' table
INSERT INTO store.game (game_id, name, price, description) VALUES
                                                         (UUID_TO_BIN('14910372-c39d-7de7-b05a-93f8166cf7af'), 'Checkers', 5.00, 'A classic strategy game for two players.'),
                                                         (UUID_TO_BIN('fde47098-ab1d-11ef-9cd2-0242ac120002'), 'Chess', 10.00, 'The timeless game of kings.'),
                                                         (UUID_TO_BIN('12d242be-ab1e-11ef-9cd2-0242ac120002'), 'Othello', 7.50, 'A fun and strategic board game.'),
                                                         (UUID_TO_BIN('2e468e92-ab1e-11ef-9cd2-0242ac120002'), 'Battle Ship', 8.99, "Sink your opponent's fleet.");

-- Insert dummy reviews into the 'reviews' table
INSERT INTO store.reviews (review_id, player_id, game_id, rating, comment, created_at) VALUES
                                                                                     (UUID_TO_BIN('4668df20-ab1e-11ef-9cd2-0242ac120002'), UUID_TO_BIN('39b5b7d0-ab1e-11ef-9cd2-0242ac120002'), UUID_TO_BIN('14910372-c39d-7de7-b05a-93f8166cf7af'), 4, 'Fun and engaging!', '2024-01-01 12:00:00'),
                                                                                     (UUID_TO_BIN('4c1674be-ab1e-11ef-9cd2-0242ac120002'), UUID_TO_BIN('39b5b7d0-ab1e-11ef-9cd2-0242ac120002'), UUID_TO_BIN('fde47098-ab1d-11ef-9cd2-0242ac120002'), 3, 'Quite challenging.', '2024-01-03 10:15:00'),
                                                                                     (UUID_TO_BIN('5314ba14-ab1e-11ef-9cd2-0242ac120002'), UUID_TO_BIN('39b5b7d0-ab1e-11ef-9cd2-0242ac120002'), UUID_TO_BIN('12d242be-ab1e-11ef-9cd2-0242ac120002'), 2, 'Could be better.', '2024-01-04 08:45:00'),
                                                                                     (UUID_TO_BIN('585d9478-ab1e-11ef-9cd2-0242ac120002'), UUID_TO_BIN('39b5b7d0-ab1e-11ef-9cd2-0242ac120002'), UUID_TO_BIN('2e468e92-ab1e-11ef-9cd2-0242ac120002'), 5, 'Absolutely love it!', '2024-01-05 19:00:00');

INSERT INTO game_statistics.players (id, name, age, gender, location)
VALUES ('a7d9b1bc-b94d-4fa1-a1a0-65d7d4359634', 'Josh', 25, "MALE", 'New York'),
       ('b5c0f1b7-3971-4e66-b5ab-49a0f4a71b4d', 'Alice', 30, "FEMALE", 'Los Angeles'),
       ('c083a8f2-b2b4-41cc-a84a-6352ec5b6c77', 'Will', 22, "MALE", 'Chicago'),
       ('d7e6c5b3-3b3d-4c2a-8b1b-7a9c8b6d5e4a', 'Mary', 28, "FEMALE", 'Houston');

-- Insert into achievements
INSERT INTO game_statistics.achievements (player_id, game_id, name, description, is_locked)
VALUES
    ('a7d9b1bc-b94d-4fa1-a1a0-65d7d4359634', '14910372-c39d-7de7-b05a-93f8166cf7af', 'Checkers Champion', 'Win 10 games of Checkers.', 0),
    ('a7d9b1bc-b94d-4fa1-a1a0-65d7d4359634', '14910372-c39d-7de7-b05a-93f8166cf7af', 'Checkers Strategist', 'Win 20 games of Checkers.', 1),
    ('a7d9b1bc-b94d-4fa1-a1a0-65d7d4359634', '14910372-c39d-7de7-b05a-93f8166cf7af', 'Checkers Veteran', 'Win 50 games of Checkers.', 1),
    ('a7d9b1bc-b94d-4fa1-a1a0-65d7d4359634', '14910372-c39d-7de7-b05a-93f8166cf7af', 'Checkers Grandmaster', 'Win 100 games of Checkers.', 1);

-- Insert sample data for game_statistics
INSERT INTO game_statistics.game_statistics (player_id, game_id, total_score, total_games_played, wins, losses, draws, win_loss_ratio, total_time_played, highest_score, moves_made, average_game_duration, matches_played)
VALUES
    ('a7d9b1bc-b94d-4fa1-a1a0-65d7d4359634', '14910372-c39d-7de7-b05a-93f8166cf7af', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
    ('b5c0f1b7-3971-4e66-b5ab-49a0f4a71b4d', '14910372-c39d-7de7-b05a-93f8166cf7af', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
    ('c083a8f2-b2b4-41cc-a84a-6352ec5b6c77', '14910372-c39d-7de7-b05a-93f8166cf7af', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
    ('d7e6c5b3-3b3d-4c2a-8b1b-7a9c8b6d5e4a', '14910372-c39d-7de7-b05a-93f8166cf7af', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);




Insert INTO lobby_management.game (game_id, name) VALUES (UUID_TO_BIN('49b5b7d0-ab1e-11ef-9cd2-0242ac120002'), 'Checkers');
-- Insert dummy players with online status
INSERT INTO lobby_management.player (player_id, name, last_active) VALUES (UUID_TO_BIN('39b5b7d0-ab1e-11ef-9cd2-0242ac120001'), 'Player One', NOW());
INSERT INTO lobby_management.player (player_id, name, last_active) VALUES (UUID_TO_BIN('39b5b7d0-ab1e-11ef-9cd2-0242ac120002'), 'Player Two', NOW());
INSERT INTO lobby_management.player (player_id, name, last_active) VALUES (UUID_TO_BIN('39b5b7d0-ab1e-11ef-9cd2-0242ac120003'), 'Player Three', NOW());
INSERT INTO lobby_management.player (player_id, name, last_active) VALUES (UUID_TO_BIN('39b5b7d0-ab1e-11ef-9cd2-0242ac120004'), 'Player Four', NOW());
INSERT INTO lobby_management.player (player_id, name, last_active) VALUES (UUID_TO_BIN('39b5b7d0-ab1e-11ef-9cd2-0242ac120008'), 'Player Eight', NOW());

-- Insert lobbies
INSERT INTO lobby_management.lobby (lobby_id, game_id) VALUES (UUID_TO_BIN('49b5b7d0-ab1e-11ef-9cd2-0242ac120002'),UUID_TO_BIN('49b5b7d0-ab1e-11ef-9cd2-0242ac120002'));
INSERT INTO lobby_management.lobby (lobby_id, game_id) VALUES (UUID_TO_BIN('49b5b7d0-ab1e-11ef-9cd2-0242ac120003'), UUID_TO_BIN('49b5b7d0-ab1e-11ef-9cd2-0242ac120002'));

-- Assign players to lobbies
-- Lobby with 2 players
INSERT INTO lobby_management.player (player_id, name, last_active, lobby_id, game_id) VALUES (UUID_TO_BIN('49b5b7d0-ab1e-11ef-9cd2-0242ac120005'), 'Player Five', NOW(), UUID_TO_BIN('49b5b7d0-ab1e-11ef-9cd2-0242ac120002'), UUID_TO_BIN('49b5b7d0-ab1e-11ef-9cd2-0242ac120002'));
INSERT INTO lobby_management.player (player_id, name, last_active, lobby_id, game_id) VALUES (UUID_TO_BIN('49b5b7d0-ab1e-11ef-9cd2-0242ac120006'), 'Player Six', NOW(), UUID_TO_BIN('49b5b7d0-ab1e-11ef-9cd2-0242ac120002'), UUID_TO_BIN('49b5b7d0-ab1e-11ef-9cd2-0242ac120002'));

-- Lobby with 1 player
INSERT INTO lobby_management.player (player_id, name, last_active, lobby_id, game_id) VALUES (UUID_TO_BIN('49b5b7d0-ab1e-11ef-9cd2-0242ac120007'), 'Player Seven', NOW(), UUID_TO_BIN('49b5b7d0-ab1e-11ef-9cd2-0242ac120003'), UUID_TO_BIN('49b5b7d0-ab1e-11ef-9cd2-0242ac120002'));
