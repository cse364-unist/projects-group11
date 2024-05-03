
# milestone 2 API

## /recommendations

### input
```
[ List of the members in group who requested the recommendation : {"gender": "M|F", "age": "1|18|25|35|45|50|56", "numOfPeople": integer},
List of genres you want to avoid : { one of the 18 genre } ]

Since it is a list, you can enter multiple elements at once.
```
### output
```
[ List of 10 movies with high predict scores. 
There are some movies which with 5 points for all ratings
Their predict score is always 5, so we except these movies.]
```
### implemented REST API
```
In this recommendation feature, there are only one REST API, GET.
When a list of users to watch a movie and a list of genres to avoid are inputted, a list of 10 movies with a high prediction score is returned.
```
### test
```
$ curl -X GET http://localhost:8080/recommendations -H 'Content-type:application/json' -d '{"userList": [{"gender": "M", "age": 25, "numOfPeople": 1}], "genreList": ["Drama"]}'

// Return the list of 10 films in the order of the highest predicted score among films, excluding the "Drama" genre, when the group is one 25-year-old male

expect output :
[{"title":"Return with Honor (1998)","genres":"Documentary","movieId":2930},{"title":"Seven Chances (1925)","genres":"Comedy","movieId":3232},{"title":"Sanjuro (1962)","genres":"Action|Adventure","movieId":2905},{"title":"Late Bloomers (1996)","genres":"Comedy","movieId":1553},{"title":"Fever Pitch (1997)","genres":"Comedy|Romance","movieId":2962},{"title":"Wrong Trousers, The (1993)","genres":"Animation|Comedy","movieId":1148},{"title":"Close Shave, A (1995)","genres":"Animation|Comedy|Thriller","movieId":745},{"title":"Usual Suspects, The (1995)","genres":"Crime|Thriller","movieId":50},{"title":"Rear Window (1954)","genres":"Mystery|Thriller","movieId":904},{"title":"Butterfly Kiss (1995)","genres":"Thriller","movieId":696}]
```

## /gifts/

### input
```
{"message": "str", "movieId": int}
```
### output
```
{"message": "string", "movieId": long, "giftId": "string: uuid", "expireDate": "string, datetime"}
```
### test
```
$ curl -X POST http://localhost:8080/gifts -H 'Content-type:application/json' -d '{"message": "영화 선물", "movieId": 5}'
{"message":"영화 선물","movieId":5,"giftId":"b541e319-272f-4c84-bb68-717328806deb","expireDate":"2024-07-27 23:59:59"}
$ curl -X GET http://localhost:8080/gifts/b541e319-272f-4c84-bb68-717328806deb
{"message":"영화 선물","movieId":5,"giftId":"b541e319-272f-4c84-bb68-717328806deb","expireDate":"2024-07-27 23:59:59"}
```

link has movie barcode, message, ticket

## /comparisons/

### input
```
{"id1": (ID of the first movie), "id2": (ID of the second movie)}
```
### output
```
["winner movie",
-1(just for seperating the informations),
// informations related to id1
(number of male rating over 3),(number of female rating over 3),(number of age 1~24 rating over 3),(number of age 25~34 rating over 3),(number of age 1~24 rating over 3),(number of age 50~ rating over 3),(summation of all ratings),(number of all ratings),
-1(just for seperating the informations),
// informations related to id1
(number of male rating over 3),(number of female rating over 3),(number of age 1~24 rating over 3),(number of age 25~34 rating over 3),(number of age 1~24 rating over 3),(number of age 50~ rating over 3),(summation of total ratings),(number of total ratings),
-1(just for seperating the informations),
// information of total users
(number of male users),(number of female users),(number of age 1~24 users),(number of age 25~34 users),(number of age 35~49 users),(number of age 50~ users)]
```
### test
```
$ curl -X POST http://localhost:8080/comparisons -H 'Content-type:application/json' -d '{"id1": 1, "id2": 2}'
```
output:
```
[1,-1,1431,569,532,761,554,153,8475,2077,-1,409,142,139,206,162,44,1986,701,-1,4331,1709,1325,2096,1743,876]
```
