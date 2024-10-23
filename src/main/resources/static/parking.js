const API_URL = '/api/parking';

async function parkVehicle(event) {
    event.preventDefault();
    const licensePlate = document.getElementById('licensePlate').value;
    const vehicleType = document.getElementById('vehicleType').value;

    try {
        const response = await fetch(`${API_URL}/park`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ licensePlate, vehicleType }),
        });

        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }

        const data = await response.json();
        alert(`Vehicle parked successfully in spot ${data.id}`);
        updateParkingSpots();
    } catch (error) {
        console.error('Error parking vehicle:', error);
        
        let errorMessage = 'An error occurred while parking the vehicle. ';
        
        if (error.message.includes('HTTP error!')) {
            errorMessage += 'The server returned an error. Please try again later.';
        } else if (error.name === 'TypeError') {
            errorMessage += 'There might be a network issue. Please check your internet connection.';
        } else {
            errorMessage += 'Please try again or contact support if the problem persists.';
        }
        
        alert(errorMessage);
    }
}

async function removeVehicle(event) {
    event.preventDefault();
    const licensePlate = document.getElementById('removeLicensePlate').value;

    try {
        const response = await fetch(`${API_URL}/remove`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            },
            body: `licensePlate=${licensePlate}`,
        });
        const data = await response.json();
        if (data) {
            alert('Vehicle removed successfully');
            updateParkingSpots();
        } else {
            alert('Vehicle not found');
        }
    } catch (error) {
        alert('Error removing vehicle');
        console.error('Error removing vehicle:', error);
    }
}

async function updateParkingSpots() {
    try {
        const response = await fetch(`${API_URL}/spots`);
        const spots = await response.json();
        const tbody = document.querySelector('#parkingSpots tbody');
        tbody.innerHTML = '';
        spots.forEach(spot => {
            const row = tbody.insertRow();
            row.insertCell(0).textContent = spot.id;
            row.insertCell(1).textContent = spot.spotType; // Assuming the type is stored in the 'type' property
            row.insertCell(2).textContent = spot.occupied ? 'Yes' : 'No';
            row.insertCell(3).textContent = spot.vehicle ? spot.vehicle.licensePlate : '-';
        });
    } catch (error) {
        console.error('Error fetching parking spots', error);
    }
}

document.addEventListener('DOMContentLoaded', () => {
    document.getElementById('parkForm').addEventListener('submit', parkVehicle);
    document.getElementById('removeForm').addEventListener('submit', removeVehicle);
    updateParkingSpots();
});