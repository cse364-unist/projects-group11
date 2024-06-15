let personId = 0;

const peopleList = [];

// people list in recommendation
const peopleListArea = document.querySelector("#people-list");

// create a row in recommendation
function createRow(gender, age, genres) {
    const row = document.createElement("li");
    const genderDiv = document.createElement("div");
    const genderTitle = document.createElement("span");
    const genderData = document.createElement("span");
    const ageDiv = document.createElement("div");
    const ageTitle = document.createElement("span");
    const ageData = document.createElement("span");
    const genresDiv = document.createElement("div");
    const genresTitle = document.createElement("span");
    const genresData = document.createElement("span");
    const removeBtn = document.createElement("button");

    genderDiv.append(genderTitle);
    genderDiv.append(genderData);
    ageDiv.append(ageTitle);
    ageDiv.append(ageData);
    genresDiv.append(genresTitle);
    genresDiv.append(genresData);
    row.append(genderDiv);
    row.append(ageDiv);
    row.append(genresDiv);
    row.append(removeBtn);

    row.setAttribute("class", "row");
    genderDiv.setAttribute("class", "input-container");
    ageDiv.setAttribute("class", "input-container");
    genresDiv.setAttribute("class", "input-container");
    removeBtn.setAttribute("id", "remove-person");

    genderTitle.innerHTML = "Gender : ";
    genderData.innerHTML = gender;
    ageTitle.innerHTML = "Age : ";
    ageData.innerHTML = age;
    genresTitle.innerHTML = "Genres Not Preferred : ";
    genresData.innerHTML = genres;
    removeBtn.innerHTML = "Remove";

    return row;
}

// show peopleList in recommendation
function listPeople() {
    peopleListArea.innerHTML = "";
    for (var i = 0; i < peopleList.length; i++) {
        const row = createRow(peopleList[i].gender, peopleList[i].age, peopleList[i].genres);
        peopleListArea.append(row);
    }
}


// add person
$('#add-person').on('submit', function (event) {
    event.preventDefault();
    const gender = this.gender.value;
    const age = this.age.value;
    const genres = $('#genres').val();
    console.log(age);
    const id = personId;

    personId++;

    const person = {
        id: id,
        gender: gender,
        age: age,
        genres: genres
    }

    peopleList.push(person);
    listPeople();
    event.target.reset();
});

// remove person data in recommendation
$('#remove-person').on('click', function () {
    // 안 됨
    console.log('remove');
});


// recommendation submit
$('#recommendation-submit').on('click', function (event) {
    if (peopleList.length === 0) {
        event.preventDefault();
        alert("Please add some people data.");
        return;
    }

    localStorage.setItem('list', JSON.stringify(peopleList));
    window.location.href = 'recommendation/recommendation.html';
});

// gift submit
$('#gift-submit').on('submit', function (event) {
    // block refresh
    event.preventDefault();
    // const movie = this.movie.value;
    const movie = JSON.parse(localStorage.getItem("gift-movie"));
    const message = this.message.value;

    const data = {
        movie: movie,
        message: message
    }

    localStorage.setItem('gift', JSON.stringify(data));
    event.target.reset();
    window.location.href = 'gift/gift.html';
});

// comparison submit
$('#comparison-submit').on('click', function (event) {
    // block refresh
    // event.preventDefault();
    const movie1 = JSON.parse(localStorage.getItem("comparison-movie1"));
    const movie2 = JSON.parse(localStorage.getItem("comparison-movie2"));

    const data = {
        movie1: movie1,
        movie2: movie2
    }

    localStorage.setItem('comparison', JSON.stringify(data));
    // event.target.reset();
    window.location.href = 'comparison/comparison.html';
});

// select2
$(document).ready(function () {
    $('#genres').select2({
        placeholder: "Select Genres",
        maximumSelectionLength: 3
    });
});

// movie1 description
let movie1 = {
    title: 'Not selected Yet',
    genre: 'Not selected Yet'
}

// movie2 description
let movie2 = {
    title: 'Not selected Yet',
    genre: 'Not selected Yet'
}

const movie1Title = document.querySelector("#movie1-title");
const movie1Genre = document.querySelector("#movie1-genre");
const movie2Title = document.querySelector("#movie2-title");
const movie2Genre = document.querySelector("#movie2-genre");

function comparisonMovieData() {
    movie1Title.append(movie1.title);
    movie1Genre.append(movie1.genre);
    movie2Title.append(movie2.title);
    movie2Genre.append(movie2.genre);
}


const giftMovieTitle = document.querySelector("#gift_movie_title");
const giftMovieGenre = document.querySelector("#gift_movie_genre");

// search movie from backend by title
$('#gift-movie-search').on('submit', function (event) {
    event.preventDefault();
    const searchInput = this.movie.value;
    console.log(searchInput);

    const requestURL = 'http://localhost:8080/gifts/search?keyword=' + searchInput;
    console.log(requestURL);
    let movie = {};
    // ajax로 영화 객체 받아오기
    $.ajax({
        type: "GET",
        url: requestURL, // 요청 url
        data: {},
        success: function(response) {
            console.log(response);
            movie = response;
        },
        fail: function() {
            console.log('failed');
        }
    });

    // 화면에 영화 정보 (제목, 장르) 표시
    giftMovieTitle.innerHTML = "";
    giftMovieGenre.innerHTML = "";
    giftMovieTitle.append(movie.title);
    giftMovieGenre.append(movie.genres);

    // 영화 정보 localStorgae에 저장 (gift submit 시 데이터 가져갈 수 있도록)
    localStorage.setItem('gift-movie', JSON.stringify(movie));
});


// movie search for comparison
const comparisonMovie1Title = document.querySelector("#movie1-title");
const comparisonMovie1Genre = document.querySelector("#movie1-genre");
const comparisonMovie2Title = document.querySelector("#movie2-title");
const comparisonMovie2Genre = document.querySelector("#movie2-genre");

// search movie from backend by title
$('#comparison-movie1-search').on('submit', function (event) {
    event.preventDefault();
    const searchInput = this.movie1.value;
    console.log(searchInput);

    const requestURL = 'http://localhost:8080/comparisons/search?keyword=' + searchInput;
    console.log(requestURL);
    let movie = {};
    // ajax로 영화 객체 받아오기
    $.ajax({
        type: "GET",
        url: requestURL, // 요청 url
        data: {},
        success: function(response) {
            console.log(response);
            movie = response;
        },
        fail: function() {
            console.log('failed');
        }
    });

    // 화면에 영화 정보 (제목, 장르) 표시
    comparisonMovie1Title.innerHTML = "";
    comparisonMovie1Genre.innerHTML = "";
    comparisonMovie1Title.append(movie.title);
    comparisonMovie1Genre.append(movie.genres);

    // 영화 정보 localStorgae에 저장 (gift submit 시 데이터 가져갈 수 있도록)
    localStorage.setItem('comparison-movie1', JSON.stringify(movie));
});

$('#comparison-movie2-search').on('submit', function (event) {
    event.preventDefault();
    const searchInput = this.movie2.value;
    console.log(searchInput);

    const requestURL = 'http://localhost:8080/comparisons/search?keyword=' + searchInput;
    console.log(requestURL);
    let movie = {};
    // ajax로 영화 객체 받아오기
    $.ajax({
        type: "GET",
        url: requestURL, // 요청 url
        data: {},
        success: function(response) {
            console.log(response);
            movie = response;
        },
        fail: function() {
            console.log('failed');
        }
    });

    // 화면에 영화 정보 (제목, 장르) 표시
    comparisonMovie2Title.innerHTML = "";
    comparisonMovie2Genre.innerHTML = "";
    comparisonMovie2Title.append(movie.title);
    comparisonMovie2Genre.append(movie.genres);

    // 영화 정보 localStorgae에 저장 (gift submit 시 데이터 가져갈 수 있도록)
    localStorage.setItem('comparison-movie2', JSON.stringify(movie));
});




listPeople();
comparisonMovieData();