echo "Request: localhost:8080/movie"
echo "-----------------------------"
echo "Response: "

curl localhost:8080/movie

echo "-----------------------------"
echo "Request: localhost:8080/Samantaral"
echo "-----------------------------"
echo "Response: "

curl localhost:8080/movie/Samantaral

echo "-----------------------------"
echo "Request: localhost:8080/movie/Samantaral/cast"
echo "-----------------------------"
echo "Response: "

curl localhost:8080/movie/Samantaral/cast

echo "-----------------------------"
echo "Request: localhost:8080/movie/Samantaral/rating"
echo "-----------------------------"
echo "Response: "

curl localhost:8080/movie/Samantaral/rating

echo "-----------------------------"
echo "Request: localhost:8080/series/Stella"
echo "-----------------------------"
echo "Response: "

curl localhost:8080/series/Stella

echo "-----------------------------"
echo "Request: localhost:8080/series/Stella"
echo "-----------------------------"
echo "Response: "

curl localhost:8080/series/Stella/cast

echo "-----------------------------"
echo "Request: localhost:8080/series/Stella/season"
echo "-----------------------------"
echo "Response: "

curl localhost:8080/series/Stella/season

echo "-----------------------------"
echo "Request: localhost:8080/series/Stella/season/6"
echo "-----------------------------"
echo "Response: "

curl localhost:8080/series/Stella/season/6

echo "-----------------------------"
echo "Request: localhost:8080/series/Stella/season/6/rating"
echo "-----------------------------"
echo "Response: "

curl localhost:8080/series/Stella/season/6/rating

echo "-----------------------------"
echo "Request: localhost:8080/series/Stella/season/6/episode/2/rating"
echo "-----------------------------"
echo "Response: "

curl localhost:8080/series/Stella/season/6/episode/2/rating

echo "-----------------------------"
echo "Request: localhost:8080/series/Life on Batteries/season/1/episode/1/rating/8.0 -X PUT"
echo "-----------------------------"
echo "Response: "

echo "Old Rating"
echo "-----------------------------"
curl localhost:8080/series/Life%20on%20Batteries/season/1/episode/1/rating
echo "-----------------------------"
echo "Old Season Rating"
echo "-----------------------------"
curl localhost:8080/series/Life%20on%20Batteries/season/1/rating
echo "-----------------------------"
curl localhost:8080/series/Life%20on%20Batteries/season/1/episode/1/rating/8.0 -X PUT
echo "-----------------------------"
echo 'New Rating'
curl localhost:8080/series/Life%20on%20Batteries/season/1/episode/1/rating
echo "-----------------------------"
echo 'New Season Rating'
echo "-----------------------------"
curl localhost:8080/series/Life%20on%20Batteries/season/1/rating
echo "-----------------------------"
echo 'Restore rating'
echo "-----------------------------"
curl localhost:8080/series/Life%20on%20Batteries/season/1/episode/1/rating/7.3 -X PUT
echo "-----------------------------"
echo "Requests completed"
