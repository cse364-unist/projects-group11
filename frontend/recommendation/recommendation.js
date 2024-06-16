// passed data
const genderList = JSON.parse(localStorage.getItem("genderList"));
const ageList = JSON.parse(localStorage.getItem("ageList"));
const genresList = JSON.parse(localStorage.getItem("genresList"));

console.log(genderList);
console.log(ageList);
console.log(genresList);

const movieListArea = document.querySelector("#movie-list");

// db에서 불러와야함
let movieList = [
    // {
    //     title: "movie1",
    //     genres: ["genre1, genre2"],
    //     rating: 4.38
    // },
    // {
    //     title: "movie2",
    //     genres: ["genre3, genre2"],
    //     rating: 1.23
    // }
];

function fetchMovieList() {
    //setting up URL
    let requestURL = 'http://localhost:8080/api/recommendations?recomList=';

    for (let i = 0; i < genderList.length; i++) {
        requestURL += genderList[i] + ',';
        requestURL += ageList[i] + ',';
    }
    requestURL += '-1,';
    for (let i = 0; i < genresList.length; i++) {
        requestURL += genresList[i];
        if (i + 1 !== genresList.length)
            requestURL += ',';
    }

    console.log(requestURL);
    $.ajax({
        type: "GET",
        url: requestURL, // 요청 url
        data: {},
        success: function(response) {
            console.log(response);
            movieList = response;
        },
        error: function (response) {
            console.log('fetchMovieList failed');
            console.log(response);
        }
    });
}

fetchMovieList();

// create a row of movie list
function createRow(title, genres, rating) {
    const row = document.createElement("li");
    const titleDiv = document.createElement("div");
    const titleTitle = document.createElement("span");
    const titleData = document.createElement("span");
    const genresDiv = document.createElement("div");
    const genresTitle = document.createElement("span");
    const genresData = document.createElement("span");
    const ratingDiv = document.createElement("div");
    const ratingTitle = document.createElement("span");
    const ratingData = document.createElement("span");

    titleDiv.append(titleTitle);
    titleDiv.append(titleData);
    genresDiv.append(genresTitle);
    genresDiv.append(genresData);
    ratingDiv.append(ratingTitle);
    ratingDiv.append(ratingData);
    row.append(titleDiv);
    row.append(genresDiv);
    row.append(ratingDiv);

    titleDiv.setAttribute("class", "result-container");
    genresDiv.setAttribute("class", "result-container");
    ratingDiv.setAttribute("class", "result-container");

    titleDiv.setAttribute("style", "width: 30%");
    genresDiv.setAttribute("style", "width: 45%");
    ratingDiv.setAttribute("style", "width: 25%");

    titleTitle.setAttribute("class", "data");
    genresTitle.setAttribute("class", "data");
    ratingTitle.setAttribute("class", "data");
    row.setAttribute("class", "row");

    titleTitle.innerHTML = "Title";
    titleData.innerHTML = title;
    genresTitle.innerHTML = "Genre";
    genresData.innerHTML = genres;
    ratingTitle.innerHTML = "Rating";
    ratingData.innerHTML = rating;

    return row;
}

function listMovie() {
    movieListArea.innerHTML = "";
    for (var i = 0; i < movieList.length; i++) {
        const row = createRow(movieList[i].title, movieList[i].genres, movieList[i].rating);
        movieListArea.append(row);
    }
}

// back to main page
$('#back-button').on('click', function () {
    window.location.href = '../index.html';
});



listMovie();