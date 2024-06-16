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
        error: function (response) {
            console.log('fetchComparison failed');
            console.log(response);
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

const maleRateOne = comparisonResults[2]/comparisonResults[20];
const femaleRateOne = comparisonResults[3]/comparisonResults[21];
const maleRateTwo = comparisonResults[2]/comparisonResults[20];
const femaleRateTwo = comparisonResults[3]/comparisonResults[21];

const age1RateOne = comparisonResults[4]/comparisonResults[22];
const age25RateOne = comparisonResults[5]/comparisonResults[23];
const age35RateOne = comparisonResults[6]/comparisonResults[24];
const age50RateOne = comparisonResults[7]/comparisonResults[25];
const age1RateTwo = comparisonResults[13]/comparisonResults[22];
const age25RateTwo = comparisonResults[14]/comparisonResults[23];
const age35RateTwo = comparisonResults[15]/comparisonResults[24];
const age50RateTwo = comparisonResults[16]/comparisonResults[25];
const sumAgeOne = age1RateOne + age25RateOne + age35RateOne + age50RateOne;
const sumAgeTwo = age1RateTwo + age25RateTwo + age35RateTwo + age50RateTwo;

const ratingOne = comparisonResults[8]/comparisonResults[9];
const ratingTwo = comparisonResults[17]/comparisonResults[18];

let ratingWinnerTitle = (ratingOne > ratingTwo) ? firstMovieTitle : secondMovieTitle;
let conjunction = (ratingWinnerTitle == winnerMovieTitle) ? "Also" : "However";
let reason = (ratingWinnerTitle == winnerMovieTitle) ? "not powerful enough to weaken the rating of" : "much more even(means evenly favored) at";

let firstMovieDescription = `${winnerMovieTitle} won! The average rating is bigger at ${ratingWinnerTitle}. ${conjunction}, The distribution of users was ${reason} ${winnerMovieTitle}!`;
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
            100 * maleRateOne / (maleRateOne + femaleRateOne),
            100 * femaleRateOne / (maleRateOne + femaleRateOne)
        ]
    }, {
        label: movie2.title,
        backgroundColor: "#F7464A",
        data: [
            100 * maleRateTwo / (maleRateTwo + femaleRateTwo),
            100 * femaleRateTwo / (maleRateTwo + femaleRateTwo)
        ]
    }]
};

var ageChartData = {
    labels: ["Under 25", "25~34", "35~49", "Over 50"],
    datasets: [{
        label: movie1.title,
        backgroundColor: "#1E90FF",
        data: [
            100 * age1RateOne / sumAgeOne,
            100 * age25RateOne / sumAgeOne,
            100 * age35RateOne / sumAgeOne,
            100 * age50RateOne / sumAgeOne
        ]
    }, {
        label: movie2.title,
        backgroundColor: "#F7464A",
        data: [
            100 * age1RateTwo / sumAgeTwo,
            100 * age25RateTwo / sumAgeTwo,
            100 * age35RateTwo / sumAgeTwo,
            100 * age50RateTwo / sumAgeTwo
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