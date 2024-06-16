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
        success: function(response) {
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
        success: function(response) {
            console.log(response);
            gift = response;
            showGift(gift);
        },
        error: function (response) {
            console.log('fetchGift failed');
            console.log(response);
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

const DUMMY_DATA = {
    title: "movie1",
    message: "this is a gift",
    expirationDate: "2024-12-31",
    link: "https://movie/b541e319-272f-4c84-bb68-717328806deb"
}

function showGift(gift) {
    // movieTitle.append(DUMMY_DATA.title);
    // message.append(DUMMY_DATA.message);
    // expirationDate.append("Expires at ", DUMMY_DATA.expirationDate);
    // giftLink.append(DUMMY_DATA.giftLink);

    // movieTitle.append(data.movie.title);
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