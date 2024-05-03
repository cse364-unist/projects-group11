
# milestone 1 API

<details>

<summary>Click me</summary>

```
$ curl -X GET http://localhost:8080/movies
$ curl -X GET http://localhost:8080/movies/1
$ curl -X GET http://localhost:8080/movies/99999
$ curl -X POST http://localhost:8080/movies -H 'Content-type:application/json' -d '{"movie_id": 99999, "title": "Title", "genres": "Genres"}'
$ curl -X PUT http://localhost:8080/movies/99999 -H 'Content-type:application/json' -d '{"movie_id": 99999, "title": "Title", "genres": "Genres"}'

$ curl -X GET http://localhost:8080/users
$ curl -X GET http://localhost:8080/users/1
$ curl -X GET http://localhost:8080/users/99999
$ curl -X POST http://localhost:8080/users -H 'Content-type:application/json' -d '{"user_id": 99999, "gender": "Gender", "age": 100, "occupation": "Occupation", "zip_code": "Zip_Code"}'
$ curl -X PUT http://localhost:8080/users/99999 -H 'Content-type:application/json' -d '{"user_id": 99999, "gender": "Gender", "age": 100, "occupation": "Occupation", "zip_code": "Zip_Code"}'

$ curl -X GET http://localhost:8080/ratings/4
$ curl -X POST http://localhost:8080/ratings -H 'Content-type:application/json' -d '{"user_id": 1, "movie_id": 2, "rate": 3}'
$ curl -X PUT http://localhost:8080/ratings -H 'Content-type:application/json' -d '{"user_id": 1, "movie_id": 2, "rate": 4}'
```

</details>



# milestone 2 API

## /recommendations

### input
```
[ {"gender": "M|F", "age": "1~24|25~34|35~49|50~65", "dislike_genre": "18개 장르 중 하나", "occupation": "21개 직업 중 하나"}, ... ]
```
### output
```
[ list of movies ] (5개에서 10개정도 추천해주면 될듯)
```
### test
```
$ curl -X POST http://localhost:8080/recommendations -H 'Content-type:application/json' -d '{"gender": "F", "age": 1, "numOfPeople": 1}' //시청하는 사람정보 추가
$ curl -X PUT http://localhost:8080/recommendations -H 'Content-type:application/json' -d '{"gender": "F", "age": 1, "numOfPeople": 1}' //시청하는 사람 정보 변경
$ curl -X GET http://localhost:8080/recommendations     //예측점수가 높은순으로 정렬된 영화의 List 반환
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
{"id1": 1, "id2":2}
```
### output
```
["이긴 영화",-1("정보간 분리용"),
(id1 남성 레이팅>3 수),(id1 여성 레이팅>3 수),(id1 1세~24세 레이팅>3 수),(id1 25세~34세 레이팅>3수),(id1 35세~49세 레이팅>3수),(id1 50세~ 레이팅>3수),(id1 모든 레이팅 합),(id1 모든 레이팅 수 합),-1("정보간 분리용"),
(id2 남성 레이팅>3 수),(id2 여성 레이팅>3 수),(id2 1세~24세 레이팅>3 수),(id2 25세~34세 레이팅>3수),(id2 35세~49세 레이팅>3수),(id2 50세~ 레이팅>3수),(id2 모든 레이팅 합),(id2 모든 레이팅 수 합),-1("정보간 분리용"),
(남성 회원 수),(여성 회원 수),(1세~24세 회원 수),(25세~34세 회원 수),(35세~49세 회원 수),(50세 회원 수)]
```
### limitation
2개 영화 id 인풋
