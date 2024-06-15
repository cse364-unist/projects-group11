const movie1 = JSON.parse(localStorage.getItem("comparison-movie1"));
const movie2 = JSON.parse(localStorage.getItem("comparison-movie2"));
console.log(movie1, movie2);

let comparisonResults = [];

function fetchComparison() {
    const requestURL = 'http://localhost:8080/api/comparisons?id1=' + movie1.movie_id + '&id2=' + movie2.movie_id;

    $.ajax({
        type: "POST",
        url: requestURL, // 요청 url
        data: {},
        success: function(response) {
            console.log(response);
            comparisonResults = response;
        },
        fail: function() {
            console.log('failed');
        }
    });
    console.log(requestURL);
}

fetchComparison();

// back to main page
$('#back-button').on('click', function () {
    window.location.href = '../index.html';
});

const winnerMovieTitle = document.querySelector("#winner-movie");
const firstMovieTitle = document.querySelector("#first-movie-title");
const secondMovieTitle = document.querySelector("#second-movie-title");
const firstMovieData = document.querySelector("#first-result");
const secondMovieData = document.querySelector("#second-result");

let firstMovieDescription = "The reason why the movie win/lose";
let secondMovieDescription = "The reason why the movie win/lose";

function loadResult() {
    // firstMovieTitle.append(DUMMY_DATA[0].title);
    // secondMovieTitle.append(DUMMY_DATA[1].title);

    // 이긴 영화 id 얻으면 movie1과 movie2의 id 비교한 후 그 영화의 title
    const winner = movie1.movie_id === comparisonResults[0] ? movie1.title : movie2.title;

    winnerMovieTitle.append(winner);
    firstMovieTitle.append(movie1.title);
    secondMovieTitle.append(movie2.title);
    firstMovieData.append(firstMovieDescription);
    secondMovieData.append(secondMovieDescription);
}

var genderChartData = {
    labels: ["Male", "Female"],
    datasets: [{
        label: movie1.title,
        backgroundColor: "#1E90FF",
        data: [
            Math.random() * 90000,
            Math.random() * 90000
        ]
    }, {
        label: movie2.title,
        backgroundColor: "#F7464A",
        data: [
            Math.random() * 90000,
            Math.random() * 90000
        ]
    }]
};

var ageChartData = {
    labels: ["Under 25", "25~34", "35~49", "Over 50"],
    datasets: [{
        label: movie1.title,
        backgroundColor: "#1E90FF",
        data: [
            Math.random() * 90000,
            Math.random() * 90000,
            Math.random() * 90000,
            Math.random() * 90000
        ]
    }, {
        label: movie2.title,
        backgroundColor: "#F7464A",
        data: [
            Math.random() * 90000,
            Math.random() * 90000,
            Math.random() * 90000,
            Math.random() * 90000
        ]
    }]
};
window.onload = function () {
    var ctx1 = $('#gender-chart').get(0).getContext("2d");
    window.theChart = new Chart(ctx1, {
        type: 'bar',
        data: genderChartData,
        options: {

        }
    });
    var ctx2 = $('#age-chart').get(0).getContext("2d");
    window.theChart = new Chart(ctx2, {
        type: 'bar',
        data: ageChartData,
        options: {

        }
    });
}



loadResult();