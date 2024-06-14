const data = JSON.parse(localStorage.getItem("comparison"));

console.log(data);

// back to main page
$('#back-button').on('click', function () {
    window.location.href = '../index.html';
});