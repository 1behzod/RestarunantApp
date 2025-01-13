document.addEventListener("DOMContentLoaded", function() {
    // Countdown Timer Logic
    function updateCountdown() {
        fetch("/v1/orders/countdown")
            .then(response => response.json())
            .then(data => {
                let secondsLeft = parseInt(data);
                let hours = Math.floor(secondsLeft / 3600);
                let minutes = Math.floor((secondsLeft % 3600) / 60);
                let seconds = secondsLeft % 60;

                document.getElementById('countdown-timer').textContent = `${hours}h ${minutes}m ${seconds}s`;
            })
            .catch(error => {
                console.error('Error fetching countdown:', error);
            });
    }

    // Refresh the countdown every second
    setInterval(updateCountdown, 1000);

    // Fetch and display sales data for a specific date range
    function fetchSalesData(startDate, endDate) {
        fetch(`/v1/orders/menu-item-sales/range?start=${startDate}&end=${endDate}`)
            .then(response => response.json())
            .then(data => {
                const salesTableBody = document.getElementById("sales-table").getElementsByTagName("tbody")[0];
                salesTableBody.innerHTML = ''; // Clear existing rows

                data.items.forEach(item => {
                    const row = salesTableBody.insertRow();
                    row.insertCell(0).textContent = item.menuItemId;  // Menu item ID or name
                    row.insertCell(1).textContent = item.totalOrders;
                    row.insertCell(2).textContent = item.totalQtySold;
                    row.insertCell(3).textContent = item.totalSales.toFixed(2);  // Format sales as a number
                });
            })
            .catch(error => {
                console.error('Error fetching sales data:', error);
            });
    }

    // Attach event listener to fetch sales on button click
    document.getElementById("fetch-sales").addEventListener("click", function() {
        const startDate = document.getElementById("start-date").value;
        const endDate = document.getElementById("end-date").value;

        if (startDate && endDate) {
            fetchSalesData(startDate, endDate);
        } else {
            alert("Please select both start and end dates.");
        }
    });
});
