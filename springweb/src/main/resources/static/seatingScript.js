const container = document.querySelector(".seating");
const seats = document.querySelectorAll(".row .seat");
const count = document.getElementById("count");

populateUI();

function updateSelectedCount() {
    const selectedSeats = document.querySelectorAll(".row .seat.selected");
    const seatsIndex = [...selectedSeats].map((seat) => [...seats].indexOf(seat));
    //localStorage.setItem("selectedSeats", JSON.stringify(seatsIndex));
    const selectedSeatsCount = selectedSeats.length;
    count.innerText = selectedSeatsCount;;
}

function populateUI() {
    const selectedSeats = JSON.parse(localStorage.getItem("selectedSeats"));
    if (selectedSeats !== null && selectedSeats.length > 0) {
        seats.forEach((seat, index) => {
            if (selectedSeats.indexOf(index) > -1) {
                seat.classList.add("selected");
            }
        });
    }
}

container.addEventListener("click", (e) => {
    if (e.target.classList.contains("seat")) {
        e.target.classList.toggle("selected");
        updateSelectedCount();
    }
});

updateSelectedCount();