const giftId = JSON.parse(localStorage.getItem("gift-link"));
console.log(giftId);

let gift = {};

function uploadGift() {
    const encodeSpace = data.message.replaceAll(/[" "]/gi, "+");
    const encoded = encodeSpace.replaceAll(/['\n']/gi, "+");
    console.log(encoded);
    const requestURL = 'http://localhost:8080/api/gifts?message=' + encoded + '&movieId=' + data.movie.movieId;
    console.log(requestURL);
    $.ajax({
        type: "POST",
        url: requestURL, // 요청 url
        data: {},
        success: function (response) {
            console.log(response);
            gift = response;
            showGift();
        },
        error: function (response) {
            console.log('uploadGift failed');
            console.log(response);
        }
    });
}

function fetchGift() {
    const requestURL = 'http://localhost:8080/api/gifts/' + giftId;
    $.ajax({
        type: "GET",
        url: requestURL, // 요청 url
        data: {},
        success: function (response) {
            console.log(response);
            gift = response;
            fetchMovie(gift);
        },
        error: function (response) {
            console.log('fetchGift failed');
            console.log(response);
            movieTitle.append("The Gift Link is Invalid");
            message.append("The Gift Link is Invalid");
            expirationDate.append("The Gift Link is Invalid");
            giftLink.append("The Gift Link is Invalid");
            alert('The Gift Link is Invalid')
        }
    });
}

fetchGift();

// back to main page
$('#back-button').on('click', function () {
    window.location.href = '../index.html';
});

const movieTitle = document.querySelector("#movie-title");
const message = document.querySelector("#gift-message");
const expirationDate = document.querySelector("#expiration-date");
const giftLink = document.querySelector("#gift-link");

let movie = {};

function fetchMovie(gift) {
    const requestURL = 'http://localhost:8080/api/gifts/findmovie?movieId' + gift.movieId;
    $.ajax({
        type: "GET",
        url: requestURL, // 요청 url
        data: {},
        success: function (response) {
            console.log(response);
            movie = response;
            showGift(gift, movie);
        },
        error: function (response) {
            console.log('fetchMovie failed');
            console.log(response);
        }
    });
}

function showGift(gift, movie) {
    // movieTitle.append(DUMMY_DATA.title);
    // message.append(DUMMY_DATA.message);
    // expirationDate.append("Expires at ", DUMMY_DATA.expirationDate);
    // giftLink.append(DUMMY_DATA.giftLink);

    movieTitle.append(movie.title);
    message.append(gift.message);
    expirationDate.append("Expires at ", gift.expireDate);
    giftLink.append(gift.giftId);
}


// share gift button handler]
$('#share-link').on('click', async function () {
    console.log('hi');
    let url = gift.giftId;

    try {
        await navigator.clipboard.writeText(url);
        alert('Gift Link is copied to clipboard');
    } catch (err) {
        console.error('Failed to copy text: ', err);
    }
});