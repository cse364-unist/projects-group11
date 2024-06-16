let movie1 = JSON.parse(localStorage.getItem("comparison-movie1"));
let movie2 = JSON.parse(localStorage.getItem("comparison-movie2"));
console.log(movie1, movie2);

// dummy data
// movie1 = {
//     title: "movie1",
//     movie_id: 1
// }
// movie2 = {
//     title: "movie2",
//     movie_id: 2
// }

// load winner data
function loadResult() {
    // firstMovieTitle.append(DUMMY_DATA[0].title);
    // secondMovieTitle.append(DUMMY_DATA[1].title);

    // 이긴 영화 id 얻으면 movie1과 movie2의 id 비교한 후 그 영화의 title

    winnerMovieTitle.append(winner);
    // firstMovieTitle.append(movie1.title);
    // secondMovieTitle.append(movie2.title);
    firstMovieData1.innerHTML = "";
    firstMovieData1.append(firstMovieDescription1);
    firstMovieData2.append(firstMovieDescription2);
    firstMovieData3.append(firstMovieDescription3);
    // secondMovieData.append(secondMovieDescription);
}


// let comparisonResults = [];
let comparisonResults = [];

let winner = 0;

function fetchComparison() {
    const requestURL = 'http://localhost:8080/api/comparisons?id1=' + movie1.movieId + '&id2=' + movie2.movieId;

    $.ajax({
        type: "POST",
        url: requestURL, // 요청 url
        data: {},
        success: function (response) {
            console.log(response);
            comparisonResults = response;
            console.log(comparisonResults);
            winner = movie1.movieId === comparisonResults[0] ? movie1.title : movie2.title;

            calculation();

            loadResult();
        },
        error: function (response) {
            console.log('fetchComparison failed');
            console.log(response);
        }
    });
    console.log(requestURL);
}

fetchComparison();

// dummy data
// comparisonResults = [1, -1, 1431, 569, 532, 761, 554, 153, 8475, 2077, -1, 409, 142, 139, 206, 162, 44, 1986, 701, -1, 4331, 1709, 1325, 2096, 1743, 876];

// back to main page
$('#back-button').on('click', function () {
    console.log('back is clicked');
    window.location.href = '../index.html';
});

const winnerMovieTitle = document.querySelector("#winner-movie");
const firstMovieTitle = document.querySelector("#first-movie-title");
const firstMovieData1 = document.querySelector("#first-result1");
const firstMovieData2 = document.querySelector("#first-result2");
const firstMovieData3 = document.querySelector("#first-result3");

firstMovieData1.innerHTML = "Loading comparison result...";

let firstMovieDescription1 = "";
let firstMovieDescription2 = "";
let firstMovieDescription3 = "";

function calculation() {
    console.log('calculation started');
    const maleRateOne = comparisonResults[2] / comparisonResults[20];
    const femaleRateOne = comparisonResults[3] / comparisonResults[21];
    const maleRateTwo = comparisonResults[11] / comparisonResults[20];
    const femaleRateTwo = comparisonResults[12] / comparisonResults[21];

    const age1RateOne = comparisonResults[4] / comparisonResults[22];
    const age25RateOne = comparisonResults[5] / comparisonResults[23];
    const age35RateOne = comparisonResults[6] / comparisonResults[24];
    const age50RateOne = comparisonResults[7] / comparisonResults[25];
    const age1RateTwo = comparisonResults[13] / comparisonResults[22];
    const age25RateTwo = comparisonResults[14] / comparisonResults[23];
    const age35RateTwo = comparisonResults[15] / comparisonResults[24];
    const age50RateTwo = comparisonResults[16] / comparisonResults[25];
    const sumAgeOne = age1RateOne + age25RateOne + age35RateOne + age50RateOne;
    const sumAgeTwo = age1RateTwo + age25RateTwo + age35RateTwo + age50RateTwo;

    console.log('check1');

    const ratingOne = comparisonResults[8] / comparisonResults[9];
    const ratingTwo = comparisonResults[17] / comparisonResults[18];

    console.log('check2');

    let ratingWinnerTitle = (ratingOne > ratingTwo) ? movie1.title : movie2.title;
    let conjunction = (ratingWinnerTitle == winner) ? "Also" : "However";
    let reason = (ratingWinnerTitle == winner) ? "not powerful enough to weaken the rating of" : "much more even(means evenly favored) at";

    console.log('check3');
    firstMovieDescription1 = `${winner} won!`;
    firstMovieDescription2 = `The average rating is bigger at ${ratingWinnerTitle}.`;
    firstMovieDescription3 = `${conjunction}, The distribution of users was ${reason} ${winner}!`;
    console.log(firstMovieDescription1);


    var genderChartData = {
        labels: [movie1.title, movie2.title],
        datasets: [{
            label: "Male",
            backgroundColor: "#1E90FF",
            data: [
                100 * maleRateOne / (maleRateOne + femaleRateOne),
                100 * maleRateTwo / (maleRateTwo + femaleRateTwo),
            ]
        }, {
            label: "Female",
            backgroundColor: "#F7464A",
            data: [
                100 * femaleRateOne / (maleRateOne + femaleRateOne),
                100 * femaleRateTwo / (maleRateTwo + femaleRateTwo)
            ]
        }]
    };

    var ageChartData = {
        labels: [movie1.title, movie2.title],
        datasets: [{
            label: "Under 25",
            backgroundColor: "#1E90FF",
            data: [
                100 * age1RateOne / sumAgeOne,
                100 * age1RateTwo / sumAgeTwo,
            ]
        }, {
            label: "25-34",
            backgroundColor: "#F7464A",
            data: [
                100 * age25RateTwo / sumAgeTwo,
                100 * age25RateOne / sumAgeOne,
            ]
        }, {
            label: "35-49",
            backgroundColor: "green",
            data: [
                100 * age35RateOne / sumAgeOne,
                100 * age35RateTwo / sumAgeTwo,
            ]
        }, {
            label: "Over 50",
            backgroundColor: "yellow",
            data: [
                100 * age50RateOne / sumAgeOne,
                100 * age50RateTwo / sumAgeTwo,
            ]
        }]
    };

    console.log('graphing start');

    // window.onload = function () {
        var ctx1 = $('#gender-chart').get(0).getContext("2d");
        window.theChart = new Chart(ctx1, {
            type: 'bar',
            data: genderChartData,
            options: {
                scales: {
                    yAxes: [{
                        display: true,
                        ticks: {
                            // suggestedMax: 53,
                            beginAtZero: true
                        }
                    }]
                }
            }
        });

        var ctx2 = $('#age-chart').get(0).getContext("2d");
        window.theChart = new Chart(ctx2, {
            type: 'bar',
            data: ageChartData,
            options: {
                scales: {
                    yAxes: [{
                        display: true,
                        ticks: {
                            // suggestedMax: 53,
                            beginAtZero: true
                        }
                    }]
                }
            }
        });
    // }
}







