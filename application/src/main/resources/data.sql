-- Insert dummy games into the 'game' table
INSERT INTO store.game (game_id, game_name, price, description) VALUES
                                                                    ('14910372-c39d-7de7-b05a-93f8166cf7af', 'Checkers', 0, 'A classic strategy game for two players.'),
                                                                    ('fde47098-ab1d-11ef-9cd2-0242ac120002', 'Chess', 10.00, 'The timeless game of kings.'),
                                                                    ('12d242be-ab1e-11ef-9cd2-0242ac120002', 'Othello', 7.50, 'A fun and strategic board game.'),
                                                                    ('2e468e92-ab1e-11ef-9cd2-0242ac120002', 'Battle Ship', 8.99, 'Sink your opponents fleet.'),
                                                                    ('6bf497bd-b0a5-4421-a0af-c2d151bddf1f', 'Hello World Clicker', 1.99, 'A clicking game');
-- Insert dummy reviews into the 'reviews' table
INSERT INTO store.reviews (review_id, customer_id, game_id, rating, comment, created_at) VALUES
                                                                                             ('4668df20-ab1e-11ef-9cd2-0242ac120002', '39b5b7d0-ab1e-11ef-9cd2-0242ac120002', '14910372-c39d-7de7-b05a-93f8166cf7af', 4, 'Fun and engaging!', '2024-01-01 12:00:00'),
                                                                                             ('4c1674be-ab1e-11ef-9cd2-0242ac120002', '39b5b7d0-ab1e-11ef-9cd2-0242ac120002', 'fde47098-ab1d-11ef-9cd2-0242ac120002', 3, 'Quite challenging.', '2024-01-03 10:15:00'),
                                                                                             ('5314ba14-ab1e-11ef-9cd2-0242ac120002', '39b5b7d0-ab1e-11ef-9cd2-0242ac120002', '12d242be-ab1e-11ef-9cd2-0242ac120002', 2, 'Could be better.', '2024-01-04 08:45:00'),
                                                                                             ('585d9478-ab1e-11ef-9cd2-0242ac120002', '39b5b7d0-ab1e-11ef-9cd2-0242ac120002', '2e468e92-ab1e-11ef-9cd2-0242ac120002', 5, 'Absolutely love it!', '2024-01-05 19:00:00');
INSERT INTO store.customer (customer_id, game_name)
VALUES ('e4e685be-ed89-42fb-a681-f272149c8218', 'Battle Ship');


INSERT INTO game_statistics.players (id, name, birth_date, gender, location)
VALUES
    ('155f3e06-bdd0-496c-919c-c3ee90dfe162', 'Noah', '1990-01-01', 'MALE', 'New York'),
    ('2aeeaba5-355f-42a7-b215-44d4d0ebfd83', 'Manami', '2001-08-06', 'FEMALE', 'Los Angeles'),
    ('e4e685be-ed89-42fb-a681-f272149c8218', 'William', '2003-09-10', 'MALE', 'Chicago'),
    ('d7e6c5b3-3b3d-4c2a-8b1b-7a9c8b6d5e4a', 'Narjiss', '2001-05-30', 'FEMALE', 'Chicago'),
    ('a7d9b1bc-b94d-4fa1-a1a0-65d7d4359634', 'Josh', '1995-12-25', 'MALE', 'Los Angeles'),
    ('b5c0f1b7-3971-4e66-b5ab-49a0f4a71b4d', 'Alice', '1998-03-15', 'FEMALE', 'New York'),
    ('49b5b7d0-ab1e-11ef-9cd2-0242ac120007', 'Player Seven', '1999-07-20', 'MALE', 'Chicago');

INSERT INTO game_statistics.games (id, name)
VALUES   ('14910372-c39d-7de7-b05a-93f8166cf7af', 'Checkers'),
         ('fde47098-ab1d-11ef-9cd2-0242ac120002', 'Chess'),
         ('1c1182b8-4f62-4a0d-a8ac-92758177cace', 'Go'),
         ('6bf497bd-b0a5-4421-a0af-c2d151bddf1f', 'Hello World Clicker');

-- Insert sample data for game_statistics
INSERT INTO game_statistics.game_statistics (player_id, game_id, total_score, total_games_played, wins, losses, draws, win_loss_ratio, total_time_played, highest_score, moves_made, average_game_duration)
VALUES
    ('2aeeaba5-355f-42a7-b215-44d4d0ebfd83', '14910372-c39d-7de7-b05a-93f8166cf7af', 0, 20, 0, 0, 0, 0, 5, 0, 0, 0),
    ('e4e685be-ed89-42fb-a681-f272149c8218', '14910372-c39d-7de7-b05a-93f8166cf7af', 0, 30, 0, 0, 0, 0, 5, 0, 0, 0),
    ('d7e6c5b3-3b3d-4c2a-8b1b-7a9c8b6d5e4a', '14910372-c39d-7de7-b05a-93f8166cf7af', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
    ('155f3e06-bdd0-496c-919c-c3ee90dfe162', '6bf497bd-b0a5-4421-a0af-c2d151bddf1f', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
    ('49b5b7d0-ab1e-11ef-9cd2-0242ac120007', '6bf497bd-b0a5-4421-a0af-c2d151bddf1f', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);

Insert INTO lobby_management.game (game_id, name) VALUES ('14910372-c39d-7de7-b05a-93f8166cf7af', 'Checkers');
Insert INTO lobby_management.game (game_id, name) VALUES ('6bf497bd-b0a5-4421-a0af-c2d151bddf1f', 'Hello World Clicker');
-- Insert dummy players with online status
INSERT INTO lobby_management.player (player_id, name, last_active) VALUES ('39b5b7d0-ab1e-11ef-9cd2-0242ac120001', 'Player One', GETDATE());
INSERT INTO lobby_management.player (player_id, name, last_active) VALUES ('39b5b7d0-ab1e-11ef-9cd2-0242ac120002', 'Player Two', GETDATE());
INSERT INTO lobby_management.player (player_id, name, last_active) VALUES ('39b5b7d0-ab1e-11ef-9cd2-0242ac120003', 'Player Three', GETDATE());
INSERT INTO lobby_management.player (player_id, name, last_active) VALUES ('39b5b7d0-ab1e-11ef-9cd2-0242ac120004', 'Player Four', GETDATE());
INSERT INTO lobby_management.player (player_id, name, last_active) VALUES ('39b5b7d0-ab1e-11ef-9cd2-0242ac120008', 'Player Eight', GETDATE());
INSERT INTO lobby_management.player (player_id, name, last_active) VALUES ('2aeeaba5-355f-42a7-b215-44d4d0ebfd83', 'Manami', GETDATE());
INSERT INTO lobby_management.player (player_id, name, last_active) VALUES ('155f3e06-bdd0-496c-919c-c3ee90dfe162', 'Noah', GETDATE());


-- Insert lobbies
INSERT INTO lobby_management.lobby (lobby_id, game_id) VALUES ('49b5b7d0-ab1e-11ef-9cd2-0242ac120002','14910372-c39d-7de7-b05a-93f8166cf7af');
INSERT INTO lobby_management.lobby (lobby_id, game_id) VALUES ('49b5b7d0-ab1e-11ef-9cd2-0242ac120003','14910372-c39d-7de7-b05a-93f8166cf7af');
INSERT INTO lobby_management.lobby (lobby_id, game_id) VALUES ('77eb6db3-888d-4b6e-bec5-0313387c5c79', '6bf497bd-b0a5-4421-a0af-c2d151bddf1f');

-- Assign players to lobbies
-- Lobby with 2 players
INSERT INTO lobby_management.player (player_id, name, last_active, lobby_id, game_id) VALUES
                                                                                          ('a7d9b1bc-b94d-4fa1-a1a0-65d7d4359634', 'Player Five', GETDATE(), '49b5b7d0-ab1e-11ef-9cd2-0242ac120002', '14910372-c39d-7de7-b05a-93f8166cf7af'),
                                                                                          ('b5c0f1b7-3971-4e66-b5ab-49a0f4a71b4d', 'Player Six', GETDATE(), '49b5b7d0-ab1e-11ef-9cd2-0242ac120002', '14910372-c39d-7de7-b05a-93f8166cf7af');

-- Lobby with 1 player,
INSERT INTO lobby_management.player (player_id, name, last_active, lobby_id, game_id) VALUES ('49b5b7d0-ab1e-11ef-9cd2-0242ac120007', 'Player Seven', GETDATE(), '77eb6db3-888d-4b6e-bec5-0313387c5c79', '6bf497bd-b0a5-4421-a0af-c2d151bddf1f');

-- Insert players for playerManagementContext
INSERT INTO player_management.players (player_id, name)
VALUES
    ('e4e685be-ed89-42fb-a681-f272149c8218', 'William'),
    ('2aeeaba5-355f-42a7-b215-44d4d0ebfd83', 'Manami'),
    ('155f3e06-bdd0-496c-919c-c3ee90dfe162', 'Noah'),
    ('dc2567de-eaad-4dc1-97fb-e1257374a614', 'Narjiss');


-- Insert additional dummy games into the 'games' table
INSERT INTO player_management.games (game_id, game_name, is_favorite, player_id)
VALUES
    ('12d242be-ab1e-11ef-9cd2-0242ac120003', 'Othello', 1, 'e4e685be-ed89-42fb-a681-f272149c8218'),
    ('14910372-c39d-7de7-b05a-93f8166cf7af', 'Checkers', 0, 'e4e685be-ed89-42fb-a681-f272149c8218'),
    ('2e468e92-ab1e-11ef-9cd2-0242ac120004', 'Battle Ship', 1, '2aeeaba5-355f-42a7-b215-44d4d0ebfd83'),
    ('fde47098-ab1d-11ef-9cd2-0242ac120005', 'Chess', 0, '2aeeaba5-355f-42a7-b215-44d4d0ebfd83'),
    ('12d242be-ab1e-11ef-9cd2-0242ac120006', 'Monopoly', 1, '155f3e06-bdd0-496c-919c-c3ee90dfe162'),
    ('14910372-c39d-7de7-b05a-93f8166cf7af', 'Checkers', 1, '155f3e06-bdd0-496c-919c-c3ee90dfe162'),
    ('6bf497bd-b0a5-4421-a0af-c2d151bddf1f', 'Hello World Clicker', 1, 'dc2567de-eaad-4dc1-97fb-e1257374a614');


-- Insert profiles for the players
INSERT INTO player_management.profiles (player_id, bio, avatar, gender, location, birth_date)
VALUES
    ('e4e685be-ed89-42fb-a681-f272149c8218', 'Loves strategy games.', 'https://img.freepik.com/premium-vector/customer-service-agent-icon-vector-image-can-be-used-digital-nomad_120816-85794.jpg?w=826', 'MALE','New York', '1990-05-15'),
    ('2aeeaba5-355f-42a7-b215-44d4d0ebfd83',  'Casual gamer.', 'https://img.freepik.com/premium-vector/customer-service-agent-icon-vector-image-can-be-used-digital-nomad_120816-85794.jpg?w=826', 'FEMALE','Los Angeles','1992-12-05');
