const data = JSON.parse(localStorage.getItem("gift"));
console.log(data);

let gift = {};

function uploadGift() {
    const encodeSpace = data.message.replaceAll(/[" "]/gi, "+");
    const encoded = encodeSpace.replaceAll(/['\n']/gi, "+");
    console.log(encoded);
    const requestURL = 'http://localhost:8080/api/gifts?message=' + encoded + '&movieId=' + data.movie.movie_id;
    console.log(requestURL);
    $.ajax({
        type: "POST",
        url: requestURL, // 요청 url
        data: {},
        success: function(response) {
            console.log(response);
            gift = response;
        },
        error: function (response) {
            console.log('uploadGift failed');
            console.log(response);
        }
    });
}

function fetchGift() {
    const requestURL = 'http://localhost:8080/api/gifts/' + gift.gift_id;
    $.ajax({
        type: "GET",
        url: requestURL, // 요청 url
        data: {},
        success: function(response) {
            console.log(response);
            gift = response;
        },
        error: function (response) {
            console.log('fetchGift failed');
            console.log(response);
        }
    });
}

uploadGift();

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

function showGift() {
    // movieTitle.append(DUMMY_DATA.title);
    // message.append(DUMMY_DATA.message);
    // expirationDate.append("Expires at ", DUMMY_DATA.expirationDate);
    // giftLink.append(DUMMY_DATA.giftLink);

    movieTitle.append(data.movie.title);
    message.append(data.message);
    expirationDate.append("Expires at ", gift.expire_date);
    giftLink.append(gift.gift_id);
}

showGift();


// share gift button handler]
$('#share-link').on('click', async function () {
    console.log('hi');
    let url = gift.gift_id;

    try {
        await navigator.clipboard.writeText(data.message);
        alert('Text copied to clipboard');
    } catch (err) {
        console.error('Failed to copy text: ', err);
    }
});