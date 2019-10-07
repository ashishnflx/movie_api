SET UNIQUE_CHECKS = 0;
SET FOREIGN_KEY_CHECKS = 0;
SET SQL_LOG_BIN=0;

LOAD DATA LOCAL INFILE 'data/titles_filter.tsv'
INTO TABLE movie_api_schema.titles
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
;

LOAD DATA LOCAL INFILE 'data/cast_filter.tsv'
INTO TABLE movie_api_schema.casts
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
IGNORE 1 LINES;

LOAD DATA LOCAL INFILE 'data/titles_cast_filter.tsv'
INTO TABLE movie_api_schema.title_cast
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
;

LOAD DATA LOCAL INFILE 'data/titles_episode_filter.tsv'
INTO TABLE movie_api_schema.episode
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
IGNORE 1 LINES;

LOAD DATA LOCAL INFILE 'data/titles_ratings_filter.tsv'
INTO TABLE movie_api_schema.title_rating
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
IGNORE 1 LINES;

INSERT INTO movie_api_schema.season_rating
SELECT e.season_num, e.title_id, COUNT(e.episode_id), SUM(t.title_rating)
FROM movie_api_schema.title_rating t
INNER JOIN movie_api_schema.episode e
ON t.title_id = e.episode_id
GROUP BY e.season_num, e.title_id;
