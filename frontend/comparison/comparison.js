const data = JSON.parse(localStorage.getItem("comparison"));
console.log(data);

let comparisonResults = [];

function fetchComparison() {
    $.ajax({
        type: "GET",
        url: '', // 요청 url
        data: {},
        success: function(response) {
            console.log(response);
            comparisonResults = response;
        },
        fail: function() {
            console.log('failed');
        }
    });
}

fetchComparison();

// back to main page
$('#back-button').on('click', function () {
    window.location.href = '../index.html';
});

const firstMovieTitle = document.querySelector("#first-movie-title");
const secondMovieTitle = document.querySelector("#second-movie-title");
const firstMovieData = document.querySelector("#first-result");
const secondMovieData = document.querySelector("#second-result");

const DUMMY_DATA = [
    {
        title: "movie1",
        data: "data"
    },
    {
        title: "movie2",
        data: "data"
    }
]

function loadResult() {
    // firstMovieTitle.append(DUMMY_DATA[0].title);
    // secondMovieTitle.append(DUMMY_DATA[1].title);

    firstMovieTitle.append(comparisonResults.title);
    secondMovieTitle.append(comparisonResults.title);
    firstMovieData.append(comparisonResults.data);
    secondMovieData.append(comparisonResults.data);
}



loadResult();