CREATE TABLE t_player_game_counts(
pk_playerName VARCHAR(30),
pk_difficulty TINYINT,
games_started SMALLINT UNSIGNED DEFAULT 0,
games_ten_plus_turns SMALLINT UNSIGNED DEFAULT 0,
wins SMALLINT UNSIGNED DEFAULT 0,

CHECK (pk_difficulty > 0 AND pk_difficulty <=5),
CHECK (games_ten_plus_turns <= games_started),
CHECK (wins <= games_started),
CONSTRAINT pk_player_game_counts PRIMARY KEY (pk_playerName, pk_difficulty)
);