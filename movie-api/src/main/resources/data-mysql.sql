CREATE SCHEMA IF NOT EXISTS movie_api_schema;

SET GLOBAL local_infile = 'ON';

CREATE TABLE IF NOT EXISTS movie_api_schema.titles (
    title_id VARCHAR(20) PRIMARY KEY,
    title_type VARCHAR(10) NOT NULL,
    title_name VARCHAR(255) NOT NULL,
    INDEX(title_name)
);

LOAD DATA LOCAL INFILE 'titles_filter.tsv'
INTO TABLE movie_api_schema.titles
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
;

CREATE TABLE IF NOT EXISTS movie_api_schema.casts (
    cast_id VARCHAR(20) PRIMARY KEY,
    cast_name VARCHAR(255) NOT NULL,
    INDEX(cast_id)
);

LOAD DATA LOCAL INFILE 'cast_filter.tsv'
INTO TABLE movie_api_schema.casts
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
IGNORE 1 LINES;

CREATE TABLE IF NOT EXISTS movie_api_schema.title_cast (
    title_id VARCHAR(20) NOT NULL,
    cast_id VARCHAR(20) NOT NULL,
    PRIMARY KEY(title_id, cast_id),
    FOREIGN KEY (title_id) REFERENCES titles(title_id),
    FOREIGN KEY (cast_id) REFERENCES casts(cast_id)
);

LOAD DATA LOCAL INFILE 'titles_cast_filter.tsv'
INTO TABLE movie_api_schema.title_cast
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
;

CREATE TABLE IF NOT EXISTS movie_api_schema.episode (
    episode_id VARCHAR(20) PRIMARY KEY,
    title_id VARCHAR(20) NOT NULL,
    season_num INT NOT NULL,
    episode_num INT NOT NULL,
    INDEX(title_id,season_num,episode_num),
    FOREIGN KEY (title_id) REFERENCES titles(title_id),
    FOREIGN KEY (episode_id) REFERENCES titles(title_id)
);

LOAD DATA LOCAL INFILE 'titles_episode_filter.tsv'
INTO TABLE movie_api_schema.episode
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
IGNORE 1 LINES;

CREATE TABLE IF NOT EXISTS movie_api_schema.title_rating (
    title_id VARCHAR(20) PRIMARY KEY,
    title_rating DOUBLE NOT NULL,
    FOREIGN KEY (title_id) REFERENCES titles(title_id)
);

LOAD DATA LOCAL INFILE 'titles_ratings_filter.tsv'
INTO TABLE movie_api_schema.title_rating
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
IGNORE 1 LINES;

CREATE TABLE IF NOT EXISTS movie_api_schema.season_rating (
   season_num INT NOT NULL,
    title_id VARCHAR(20) NOT NULL,
    num_of_episodes INT,
    rating_sum DOUBLE,
    PRIMARY KEY(title_id, season_num),
    FOREIGN KEY (title_id) REFERENCES titles(title_id)
);

INSERT INTO movie_api_schema.season_rating
SELECT e.season_num, e.title_id, COUNT(e.episode_id), SUM(t.title_rating)
FROM movie_api_schema.title_rating t
INNER JOIN movie_api_schema.episode e
ON t.title_id = e.episode_id
GROUP BY e.season_num, e.title_id;