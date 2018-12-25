/* checks if player name exists */
SELECT EXISTS(SELECT * FROM t_player_game_counts WHERE pk_playerName = 'Name' )

/* Create new Tuple for a name and a difficulty*/
INSERT INTO t_player_game_counts VALUES('Name', 2 , DEFAULT, DEFAULT, DEFAULT)

/*increment games played for name and difficulty*/
UPDATE t_player_game_counts
SET games_started = games_started + 1
WHERE pk_playername = 'Raivo' AND pk_difficulty = 2

/*increment 10+ turn games  for name and difficulty*/
UPDATE t_player_game_counts
SET games_ten_plus_turns = games_ten_plus_turns + 1
WHERE pk_playername = 'Raivo' AND pk_difficulty = 1

/*increment wins  for name and difficulty*/
UPDATE t_player_game_counts
SET wins = wins + 1
WHERE pk_playername = 'Raivo' AND pk_difficulty = 1
