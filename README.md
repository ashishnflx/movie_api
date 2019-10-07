# Movie API

Rest service using spring boot to view movie and tv series information.  
The service also has the capability to update episode ratings which would impact overall season rating.  

# Instructions

1. Install mysql package on your machine if its not installed already.  
   Link: https://dev.mysql.com/doc/refman/8.0/en/installing.html  
   Do `which mysql` to check if its in the path. Otherwise link the binary to your path.  
   Mac shortcut to install: `brew install mysql`
   
2. Install maven package on your machine if its not installed already.  
   Link: https://maven.apache.org/plugins/maven-install-plugin/  
   Do `which mvn` to check if its in the path. Otherwise link mvn binary to your path  
   Mac shortcut to installation cmd: `brew install maven`

3. Start service once step #3 is done.
   Run - `./startserver.sh` 
   
   
   STEP FOR PRODUCTION MIGRATION:	
4. Initialise data by running sql script. (NOT NEEDED)  

   THIS STEP IS NOT NEEDED NOW. ITS ONLY USED DURING PRODUCTION MIGRATION.   
   Run - `. data/initdata.sh`  
   I have included preprocessed data here. Incase regeneration of preprocessed data is required follow below steps.  
   a. Download data from http://www.imdb.com/interfaces  
   b. Run - `. data/preprocessor.sh <directory of downloaded files in a.> <output directory>`  
    

# APIs

####Movies:

|Req type        |Resource               | Response           |
|----------------|-----------------------|--------------------|
|GET             |/movie                 |Returns all the movies in the database|
|GET             |/movie/{name}          |Returns information about the movie {name}|
|GET             |/movie/{name}/cast     |Returns the cast list of the movie {name}|
|GET             |/movie/{name}/rating   |Returns the rating of the movie {name}|


####Series

|Req type        |Resource               | Response           |
|----------------|-----------------------|--------------------|
|GET             |/series                |Returns all the tvseries in the database|
|GET             |/series/{name}         |Returns information about the tvseries {name}|
|GET             |/series/{name}/cast    |Returns the cast list of the tvseries {name}|
|GET             |/series/{name}/season  |Returns all available seasons of the series {name} (Note as the data is 2017 year only, it might have only 1 season.)|
|GET             |/series/{name}/season/{seasonName}            |Returns all information of the season {seasonName}|
|GET             |/series/{name}/season/{seasonName}/rating     |Returns the rating of the season|
|GET             |/series/{name}/season/{seasonName}/episode    |Returns all the episodes of the season|
|GET             |/series/{name}/season/{seasonName|/episode/{episodeName}/rating |Returns the rating of the episode|
|PUT             |/series/{name}/season/{seasonName}/episode/{episodeName}/rating/{newRating} | Updates rating of the episode to {newRating}|

# Database Design
   
  https://github.com/ashishnflx/movie_api/blob/master/movie-api/model.pdf
  
  I created two columns, rating_sum and num_of_episodes for a season.  
  This will prevent us on locking the table to get total episodes and doing the total sum of ratings for that season. 
  Now only thing to worry about here is that commit to both episode rating and season rating should happen together. 
  
  Note - The tool used for generation of entity diagram removed Primary Key and Foreign Key from the diagrams.  
  

# Code for rating update

  Steps:
  1. Get titleId and episodeId. 
  2. Open a transaction which either commits both episode rating and season rating. 
  3. Calculate difference between current episode rating and new episode rating. 
  4. Update episode rating. 
  5. Add the difference to season rating (rating_sum)  
  
  Whenever season rating call is made, it picks up total episodes for that season and total rating sum for that season from  the database and query does division between them.
  

