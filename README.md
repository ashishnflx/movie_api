# Movie API

Rest service using spring boot to view movie and tv series information. 
The service also has the capability to update episode ratings which would impact overall season rating.

# Instructions

1. Install mysql package on your machine if its not installed already.
   Link: https://dev.mysql.com/doc/refman/8.0/en/installing.html
   Do which mysql to check if its the path. Otherwise link mysql binary to your path
   Mac shortcut to installation cmd: brew install mysql
   
2. Install maven package on your machine if its not installed already.
   Link: https://maven.apache.org/plugins/maven-install-plugin/
   Do which mvn to check if its the path. Otherwise link mvn binary to your path
   Mac shortcut to installation cmd: brew install maven
   
3. Initialise data by running sql script.
   Run - . data/initdata.sh
   I have included preprocessed data here. Incase regeneration of preprocessed data is required follow below steps.
    a. Download data from http://www.imdb.com/interfaces
    b. Run - . data/preprocessor.sh <directory of downloaded files in a.> <output directory>
   Note - I have turned off auto install in application.properties because it reinstalls the data on server restart.
   
   
4. Start service once step #3 is done.
   Run - ./startserver.sh
   

# APIs

Movies:

Request: localhost:8080/movie
Response: It will return all the movies in the database.

Request: localhost:8080/movie/Samantaral
Response: It will return this specific movie related information.

Request: localhost:8080/movie/Samantaral/cast
Response: It will return cast of this movie.

Request: localhost:8080/movie/Samantaral/rating
Response: It will return rating of this movie.

TVSeries:

Request: localhost:8080/series
Response: It will return all the tvSeries in the database.

Request: localhost:8080/series/Stella/season/6
Response: It will return this specific tvSeries related information.

Request: localhost:8080/series/Stella/cast
Response: It will return cast of this tv series.

Request: localhost:8080/series/Stella/season
Response: It will return all available seasons of this tv series. 
          Note as the data is 2017 year only, it might have only 1 season.
          
          
Request: localhost:8080/series/Stella/season/6
Response: It will return this season related information.

Request: localhost:8080/series/Stella/season/6/rating
Response: It will return rating of this season.

Request: localhost:8080/series/Stella/season/6/episode
Response: It will return episodes of this season.

Request: localhost:8080/series/Stella/season/6/episode/5/rating
Response: It will return rating of this episode.

Request: localhost:8080/series/Stella/season/6/episode/5/rating/8.0
Response: It will update the rating of this episode which impacts season ratings.

