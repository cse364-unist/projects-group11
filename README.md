
## /recommendations

### input
```
[ {"gender": "M|F", "age": "1~24|25~34|35~49|50~65", "dislike_genre": "18개 장르 중 하나", "occupation": "21개 직업 중 하나"}, ... ]
```
### output
```
[ list of movies ]
```

## /gifts/

### input
```
{"message": "str", "movie_id": int}
```
### output
```
{"link": "str"}
```
link has movie barcode, message, ticket

## /comparisons/

### input
```
{"movie_ids": [1, 2]}
```
### output
```
{
  "winner": 1,
  "reason": [
    {"avg_rating", "num_male_over_3": "10/15", "num_female_over_3": "20/30", "num_1_over_3": "10/15", "num_25_over_3": "12/25", "num_35_over_3": "12/25", "num_35_over_50": "12/25"},
    {...}
  ]
}
```
