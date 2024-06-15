const data = JSON.parse(localStorage.getItem("gift"));
console.log(data);

let gift = {};

function fetchGift() {
    $.ajax({
        type: "GET",
        url: '', // 요청 url
        data: {},
        success: function(response) {
            console.log(response);
            gift = response;
        },
        fail: function() {
            console.log('failed');
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

function loadGift() {
    // movieTitle.append(DUMMY_DATA.title);
    // message.append(DUMMY_DATA.message);
    // expirationDate.append("Expires at ", DUMMY_DATA.expirationDate);
    // giftLink.append(DUMMY_DATA.giftLink);

    movieTitle.append(gift.title);
    message.append(gift.message);
    expirationDate.append("Expires at ", gift.expirationDate);
    giftLink.append(gift.giftLink);
}

loadGift();