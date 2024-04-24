/recommendations

input : [ {"gender": "M|F", "age": "1~24|25~34|35~49|50~65", "genre": "18개", "occupation": "21개"}, ... ]
output: [ list of movies ]

/gifts/

input: {"message": "str", "movie_id": int}
output: {"link": "str"} - movie barcode, message, ticket

/comparisons/

input: {"movie_ids": [1, 2]}
