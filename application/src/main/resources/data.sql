/*-- Insert dummy games into the 'game' table
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

INSERT INTO game_statistics.players (id, name, age, gender, location)
VALUES ('a7d9b1bc-b94d-4fa1-a1a0-65d7d4359634', 'Josh', 25, "MALE", 'New York'),
       ('b5c0f1b7-3971-4e66-b5ab-49a0f4a71b4d', 'Alice', 30, "FEMALE", 'Los Angeles'),
       ('c083a8f2-b2b4-41cc-a84a-6352ec5b6c77', 'Will', 22, "MALE", 'Chicago'),
       ('d7e6c5b3-3b3d-4c2a-8b1b-7a9c8b6d5e4a', 'Mary', 28, "FEMALE", 'Houston');


-- Insert into achievements
INSERT INTO game_statistics.achievements (player_id, game_id, name, description, is_locked)
VALUES
    ('14910372-c39d-7de7-b05a-93f8166cf7af', '14910372-c39d-7de7-b05a-93f8166cf7af', 'Checkers Champion', 'Win 10 games of Checkers.', 0),
    ('fde47098-ab1d-11ef-9cd2-0242ac120002', '14910372-c39d-7de7-b05a-93f8166cf7af', 'Checkers Strategist', 'Win 20 games of Checkers.', 1),
    ('12d242be-ab1e-11ef-9cd2-0242ac120002', '14910372-c39d-7de7-b05a-93f8166cf7af', 'Checkers Veteran', 'Win 50 games of Checkers.', 1),
    ('2e468e92-ab1e-11ef-9cd2-0242ac120002', '14910372-c39d-7de7-b05a-93f8166cf7af', 'Checkers Grandmaster', 'Win 100 games of Checkers.', 1);

-- Insert sample data for Match History
INSERT INTO game_statistics.match_history (game_id, start_time, end_time, is_active, winner, score, moves_made)
VALUES
    ('14910372-c39d-7de7-b05a-93f8166cf7af', '2024-11-01T14:30:00', '2024-11-01T15:00:00', TRUE, 'Player1', 10, 20),
    ('9b836e39-e513-4750-b428-25d5576e4db7', '2024-11-05T12:00:00', '2024-11-05T12:45:00', FALSE, 'Player2', 12, 18),
    ('a1d91ef0-df88-4118-8c09-63d0b64b54fc', '2024-11-10T16:30:00', '2024-11-10T17:15:00', TRUE, 'Player1', 15, 25),
    ('b3fe1b6d-cf7f-4d87-9b30-b0337e601477', '2024-11-15T10:00:00', '2024-11-15T10:50:00', FALSE, 'Draw', 8, 12);

-- Insert sample data for game_statistics
INSERT INTO game_statistics.game_statistics (player_id, game_id, total_score, total_games_played, wins, losses, draws, win_loss_ratio, total_time_played, highest_score, moves_made, average_game_duration, matches_played)
VALUES
    ('a7d9b1bc-b94d-4fa1-a1a0-65d7d4359634', '14910372-c39d-7de7-b05a-93f8166cf7af', 1500, 20, 12, 8, 0, 1.5, 1200, 200, 180, 60, 10),
    ('a7d9b1bc-b94d-4fa1-a1a0-65d7d4359634', '9b836e39-e513-4750-b428-25d5576e4db7', 1300, 18, 10, 8, 0, 1.25, 1100, 180, 170, 55, 8),
    ('a7d9b1bc-b94d-4fa1-a1a0-65d7d4359634', 'a1d91ef0-df88-4118-8c09-63d0b64b54fc', 1600, 25, 15, 5, 5, 2.0, 1300, 220, 200, 65, 12),
    ('a7d9b1bc-b94d-4fa1-a1a0-65d7d4359634', 'b3fe1b6d-cf7f-4d87-9b30-b0337e601477', 1400, 22, 13, 7, 2, 1.86, 1250, 210, 190, 60, 11);

-- Insert sample data for game_statistics_match_history
-- This is the join table, associating game_statistics with match_history
INSERT INTO game_statistics.game_statistics_match_history (game_statistics_id, match_history_id)
VALUES
    ('14910372-c39d-7de7-b05a-93f8166cf7af', 1),
    ('9b836e39-e513-4750-b428-25d5576e4db7', 1),
    ('a1d91ef0-df88-4118-8c09-63d0b64b54fc', 2),
    ('b3fe1b6d-cf7f-4d87-9b30-b0337e601477', 2),
    ('14910372-c39d-7de7-b05a-93f8166cf7af', 3),
    ('9b836e39-e513-4750-b428-25d5576e4db7', 3),
    ('a1d91ef0-df88-4118-8c09-63d0b64b54fc', 4),
    ('b3fe1b6d-cf7f-4d87-9b30-b0337e601477', 4);

*/
-- Insert dummy players with online status
INSERT INTO lobby_management.player (player_id, name, last_active) VALUES (UUID_TO_BIN('39b5b7d0-ab1e-11ef-9cd2-0242ac120002'), 'Player One', NOW());
INSERT INTO lobby_management.player (player_id, name, last_active) VALUES (UUID_TO_BIN('39b5b7d0-ab1e-11ef-9cd2-0242ac120003'), 'Player Two', NOW());
INSERT INTO lobby_management.player (player_id, name, last_active) VALUES (UUID_TO_BIN('39b5b7d0-ab1e-11ef-9cd2-0242ac120004'), 'Player Three', NOW());

-- Insert lobbies
INSERT INTO lobby_management.lobby (lobby_id) VALUES (UUID_TO_BIN('49b5b7d0-ab1e-11ef-9cd2-0242ac120002'));
INSERT INTO lobby_management.lobby (lobby_id) VALUES (UUID_TO_BIN('49b5b7d0-ab1e-11ef-9cd2-0242ac120003'));

-- Assign players to lobbies
-- Lobby with 2 players
INSERT INTO lobby_management.player (player_id, name, last_active, lobby_id) VALUES (UUID_TO_BIN('49b5b7d0-ab1e-11ef-9cd2-0242ac120005'), 'Player One', NOW(), UUID_TO_BIN('49b5b7d0-ab1e-11ef-9cd2-0242ac120002'));
INSERT INTO lobby_management.player (player_id, name, last_active, lobby_id) VALUES (UUID_TO_BIN('49b5b7d0-ab1e-11ef-9cd2-0242ac120006'), 'Player Two', NOW(), UUID_TO_BIN('49b5b7d0-ab1e-11ef-9cd2-0242ac120002'));

-- Lobby with 1 player
INSERT INTO lobby_management.player (player_id, name, last_active, lobby_id) VALUES (UUID_TO_BIN('49b5b7d0-ab1e-11ef-9cd2-0242ac120007'), 'Player Three', NOW(), UUID_TO_BIN('49b5b7d0-ab1e-11ef-9cd2-0242ac120003'));