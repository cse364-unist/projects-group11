// passed data
const data = JSON.parse(localStorage.getItem("list"));

console.log(data);

const movieListArea = document.querySelector("#movie-list");

// db에서 불러와야함
const movieList = [
    {
        title: "movie1",
        genres: ["genre1, genre2"],
        rating: 4.38
    },
    {
        title: "movie2",
        genres: ["genre3, genre2"],
        rating: 1.23
    }
];

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
    row.setAttribute("class", "row");

    titleTitle.innerHTML = "Title :";
    titleData.innerHTML = title;
    genresTitle.innerHTML = "Genre :";
    genresData.innerHTML = genres;
    ratingTitle.innerHTML = "Rating :";
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