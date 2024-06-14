const data = JSON.parse(localStorage.getItem("gift"));

console.log(data);

// back to main page
$('#back-button').on('click', function () {
    window.location.href = '../index.html';
});