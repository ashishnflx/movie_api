CREATE SCHEMA IF NOT EXISTS movie_api_schema;

SET GLOBAL local_infile = 'ON';

SET FOREIGN_KEY_CHECKS=0;

DROP TABLE IF EXISTS movie_api_schema.titles;

CREATE TABLE IF NOT EXISTS movie_api_schema.titles (
    title_id VARCHAR(20) PRIMARY KEY,
    title_type VARCHAR(10) NOT NULL,
    title_name VARCHAR(255) NOT NULL,
    INDEX(title_name)
);

DROP TABLE IF EXISTS movie_api_schema.casts;

CREATE TABLE IF NOT EXISTS movie_api_schema.casts (
    cast_id VARCHAR(20) PRIMARY KEY,
    cast_name VARCHAR(255) NOT NULL,
    INDEX(cast_id)
);

DROP TABLE IF EXISTS movie_api_schema.title_cast;

CREATE TABLE IF NOT EXISTS movie_api_schema.title_cast (
    title_id VARCHAR(20) NOT NULL,
    cast_id VARCHAR(20) NOT NULL,
    PRIMARY KEY(title_id, cast_id),
    FOREIGN KEY (title_id) REFERENCES titles(title_id),
    FOREIGN KEY (cast_id) REFERENCES casts(cast_id)
);

DROP TABLE IF EXISTS movie_api_schema.episode;

CREATE TABLE IF NOT EXISTS movie_api_schema.episode (
    episode_id VARCHAR(20) PRIMARY KEY,
    title_id VARCHAR(20) NOT NULL,
    season_num INT NOT NULL,
    episode_num INT NOT NULL,
    INDEX(title_id,season_num,episode_num),
    FOREIGN KEY (title_id) REFERENCES titles(title_id),
    FOREIGN KEY (episode_id) REFERENCES titles(title_id)
);

DROP TABLE IF EXISTS movie_api_schema.title_rating;

CREATE TABLE IF NOT EXISTS movie_api_schema.title_rating (
    title_id VARCHAR(20) PRIMARY KEY,
    title_rating DOUBLE NOT NULL,
    FOREIGN KEY (title_id) REFERENCES titles(title_id)
);

DROP TABLE IF EXISTS movie_api_schema.season_rating;

CREATE TABLE IF NOT EXISTS movie_api_schema.season_rating (
   season_num INT NOT NULL,
    title_id VARCHAR(20) NOT NULL,
    num_of_episodes INT,
    rating_sum DOUBLE,
    PRIMARY KEY(title_id, season_num),
    FOREIGN KEY (title_id) REFERENCES titles(title_id)
);

SET FOREIGN_KEY_CHECKS=1;
