const API_BASE_URL = 'http://localhost:8090';

// DOM Elements
const vehicleList = document.getElementById('vehicleList');
const userList = document.getElementById('userList');
const bookingList = document.getElementById('bookingList');

// Dashboard Elements
const totalBookingsEl = document.getElementById('totalBookings');
const totalRevenueEl = document.getElementById('totalRevenue');
const activeVehiclesEl = document.getElementById('activeVehicles');
const totalUsersEl = document.getElementById('totalUsers');

let allVehicles = [];
let currentUser = null; // Store logged-in user info

// Event Listeners
document.addEventListener('DOMContentLoaded', () => {
    loadVehicles();
    
    // Check if user is already logged in (simulated session)
    const storedUser = localStorage.getItem('currentUser');
    if (storedUser) {
        currentUser = JSON.parse(storedUser);
        updateUIForUser(currentUser);
    }

    // Form Submissions
    const vehicleForm = document.getElementById('vehicleForm');
    if (vehicleForm) vehicleForm.addEventListener('submit', handleVehicleSubmit);
    
    const userForm = document.getElementById('userForm');
    if (userForm) userForm.addEventListener('submit', handleUserSubmit);
    
    const bookingForm = document.getElementById('bookingForm');
    if (bookingForm) bookingForm.addEventListener('submit', handleBookingSubmit);

    const loginForm = document.getElementById('loginForm');
    if (loginForm) loginForm.addEventListener('submit', handleLoginSubmit);

    // Navbar Scroll Effect
    window.addEventListener('scroll', () => {
        const navbar = document.getElementById('navbar');
        if (navbar) {
            if (window.scrollY > 50) {
                navbar.style.background = 'rgba(255, 255, 255, 0.98)';
                navbar.style.boxShadow = '0 4px 6px -1px rgba(0, 0, 0, 0.1)';
            } else {
                navbar.style.background = 'rgba(255, 255, 255, 0.95)';
                navbar.style.boxShadow = '0 1px 3px 0 rgba(0, 0, 0, 0.1)';
            }
        }
    });
});

// Modal Functions
function openModal(modalId) {
    const modal = document.getElementById(modalId);
    if (modal) {
        modal.style.display = 'flex';
        // Reset login form state when opening login modal
        if (modalId === 'loginModal') {
            document.getElementById('loginForm').style.display = 'none';
            document.querySelector('.login-options').style.display = 'flex';
        }
        // Show role selection only if admin is logged in
        if (modalId === 'userModal') {
            const roleFormGroup = document.getElementById('role-form-group');
            if (currentUser && currentUser.role === 'ADMIN') {
                roleFormGroup.style.display = 'block';
            } else {
                roleFormGroup.style.display = 'none';
            }
        }
    }
}

function closeModal(modalId) {
    const modal = document.getElementById(modalId);
    if (modal) modal.style.display = 'none';
}

window.onclick = function(event) {
    if (event.target.classList.contains('modal-overlay')) {
        event.target.style.display = 'none';
    }
}

// Toast Notification
function showToast(message, type = 'success') {
    const container = document.getElementById('toast-container');
    if (!container) return;
    
    const toast = document.createElement('div');
    toast.className = `toast ${type}`;
    toast.innerHTML = `
        <i class="fas ${type === 'success' ? 'fa-check-circle' : 'fa-exclamation-circle'}"></i>
        <span>${message}</span>
    `;
    container.appendChild(toast);
    
    setTimeout(() => {
        toast.style.opacity = '0';
        setTimeout(() => toast.remove(), 300);
    }, 3000);
}

// --- Authentication ---
function showLoginForm(role) {
    document.querySelector('.login-options').style.display = 'none';
    const form = document.getElementById('loginForm');
    form.style.display = 'block';
    document.getElementById('loginRole').value = role;
    document.getElementById('loginSubmitBtn').innerText = role === 'admin' ? 'Login as Admin' : 'Login as User';
}

async function handleLoginSubmit(e) {
    e.preventDefault();
    const formData = new FormData(e.target);
    const email = formData.get('email');
    const role = formData.get('role'); // 'user' or 'admin'
    
    try {
        // In a real app, you would have separate endpoints or verify role on backend.
        // Here we simulate by checking the user list and their role.
        const response = await fetch(`${API_BASE_URL}/users`);
        const users = await response.json();
        const user = users.find(u => u.email === email);
        
        if (user) {
            // Check if the user has the correct role for the selected login type
            if (role === 'admin' && user.role !== 'ADMIN') {
                showToast('Access Denied: You are not an Admin', 'error');
                return;
            }
            
            currentUser = user;
            localStorage.setItem('currentUser', JSON.stringify(user));
            updateUIForUser(user);
            closeModal('loginModal');
            showToast(`Welcome back, ${user.userName}!`);
        } else {
            showToast('User not found. Please sign up.', 'error');
        }
    } catch (error) {
        console.error('Login error:', error);
        showToast('Login failed', 'error');
    }
}

function logout() {
    currentUser = null;
    localStorage.removeItem('currentUser');
    location.reload(); // Refresh to reset UI
}

function updateUIForUser(user) {
    const authButtons = document.getElementById('authButtons');
    const userProfile = document.getElementById('userProfile');
    const userNameDisplay = document.getElementById('userNameDisplay');
    const dashboardLink = document.getElementById('dashboardLink');
    const dashboardSection = document.getElementById('dashboard');
    const addVehicleBtn = document.getElementById('addVehicleBtn');

    if (user) {
        authButtons.style.display = 'none';
        userProfile.style.display = 'flex';
        userNameDisplay.innerText = user.userName;

        if (user.role === 'ADMIN') {
            dashboardLink.style.display = 'block';
            dashboardSection.style.display = 'block';
            addVehicleBtn.style.display = 'inline-flex';
            loadUsers();
            loadBookings();
        } else {
            dashboardLink.style.display = 'none';
            dashboardSection.style.display = 'none';
            addVehicleBtn.style.display = 'none';
        }
    } else {
        authButtons.style.display = 'block';
        userProfile.style.display = 'none';
        dashboardLink.style.display = 'none';
        dashboardSection.style.display = 'none';
        addVehicleBtn.style.display = 'none';
    }
    
    // Re-render vehicles to update delete buttons visibility
    renderVehicles(allVehicles);
}


// --- Vehicles ---
async function loadVehicles() {
    if (!vehicleList) return;
    try {
        const response = await fetch(`${API_BASE_URL}/vehicles`);
        allVehicles = await response.json();
        renderVehicles(allVehicles);
        updateDashboardStats();
    } catch (error) {
        console.error('Error loading vehicles:', error);
        showToast('Failed to load vehicles', 'error');
    }
}

function renderVehicles(vehicles) {
    if (!vehicleList) return;
    
    if (vehicles.length === 0) {
        vehicleList.innerHTML = '<p class="text-center" style="grid-column: 1/-1; color: var(--gray); padding: 2rem;">No vehicles found matching your criteria.</p>';
        return;
    }

    const isAdmin = currentUser && currentUser.role === 'ADMIN';

    vehicleList.innerHTML = vehicles.map(vehicle => `
        <div class="vehicle-card">
            <div class="vehicle-img-wrapper">
                <span class="vehicle-badge">${vehicle.vehicleType}</span>
                <img src="${vehicle.image || 'https://images.unsplash.com/photo-1533473359331-0135ef1b58bf?auto=format&fit=crop&w=800&q=80'}" 
                     alt="${vehicle.vehicleNumber}" 
                     class="vehicle-img"
                     onerror="this.src='https://via.placeholder.com/400x300?text=No+Image'">
            </div>
            <div class="vehicle-content">
                <div class="vehicle-title">
                    <h3>${vehicle.vehicleNumber}</h3>
                    <div class="price-tag">$${vehicle.pricePerDay}<span>/day</span></div>
                </div>
                <div class="vehicle-specs">
                    <div class="spec-item"><i class="fas fa-chair"></i> ${vehicle.numberOfSeats} Seats</div>
                    <div class="spec-item"><i class="fas fa-road"></i> $${vehicle.pricePerKilometer}/km</div>
                </div>
                <div class="vehicle-actions">
                    <button class="btn btn-primary btn-block" onclick="prefillBooking(${vehicle.id})">Book Now</button>
                    ${isAdmin ? `
                    <button class="btn-icon" onclick="deleteVehicle(${vehicle.id})" title="Delete Vehicle">
                        <i class="fas fa-trash-alt"></i>
                    </button>
                    ` : ''}
                </div>
            </div>
        </div>
    `).join('');
}

function filterVehicles(type) {
    // Update active button state
    document.querySelectorAll('.filter-btn').forEach(btn => btn.classList.remove('active'));
    event.target.classList.add('active');

    if (type === 'all') {
        renderVehicles(allVehicles);
    } else {
        const filtered = allVehicles.filter(v => v.vehicleType === type);
        renderVehicles(filtered);
    }
}

async function handleVehicleSubmit(e) {
    e.preventDefault();
    const formData = new FormData(e.target);
    const data = Object.fromEntries(formData.entries());
    
    try {
        const response = await fetch(`${API_BASE_URL}/vehicles`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(data)
        });
        
        if (response.ok) {
            closeModal('vehicleModal');
            e.target.reset();
            loadVehicles();
            showToast('Vehicle added successfully');
        } else {
            showToast('Failed to add vehicle', 'error');
        }
    } catch (error) {
        console.error('Error adding vehicle:', error);
        showToast('Error connecting to server', 'error');
    }
}

async function deleteVehicle(id) {
    if (!confirm('Are you sure you want to remove this vehicle from the fleet?')) return;
    
    try {
        const response = await fetch(`${API_BASE_URL}/vehicles/${id}`, { method: 'DELETE' });
        if (response.ok) {
            loadVehicles();
            showToast('Vehicle removed successfully');
        } else {
            showToast('Failed to delete vehicle', 'error');
        }
    } catch (error) {
        console.error('Error deleting vehicle:', error);
        showToast('Error connecting to server', 'error');
    }
}

// --- Users ---
async function loadUsers() {
    if (!userList) return;
    try {
        const response = await fetch(`${API_BASE_URL}/users`);
        const users = await response.json();
        
        if (totalUsersEl) totalUsersEl.innerText = users.length;

        if (users.length === 0) {
            userList.innerHTML = '<div class="text-center" style="padding: 2rem; color: var(--gray);">No users found</div>';
            return;
        }

        userList.innerHTML = users.map(user => `
            <div class="user-list-item">
                <div class="user-info-group">
                    <div class="user-avatar-sm">${user.userName.charAt(0).toUpperCase()}</div>
                    <div class="user-details">
                        <h4>${user.userName}</h4>
                        <p>${user.email}</p>
                    </div>
                </div>
                <div class="user-info-group">
                    <span class="user-role-badge role-${user.role.toLowerCase()}">${user.role}</span>
                    <div class="user-actions">
                        <button class="action-btn-sm" onclick="deleteUser(${user.id})" title="Delete User">
                            <i class="fas fa-trash-alt"></i>
                        </button>
                    </div>
                </div>
            </div>
        `).join('');
    } catch (error) {
        console.error('Error loading users:', error);
    }
}

async function handleUserSubmit(e) {
    e.preventDefault();
    const formData = new FormData(e.target);
    const data = Object.fromEntries(formData.entries());
    
    // If role is not set (i.e., guest is signing up), default to USER
    if (!data.role) {
        data.role = 'USER';
    }

    try {
        const response = await fetch(`${API_BASE_URL}/users`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(data)
        });
        
        if (response.ok) {
            closeModal('userModal');
            e.target.reset();
            // If admin is logged in, reload user list
            if (currentUser && currentUser.role === 'ADMIN') {
                loadUsers();
            }
            showToast('User registered successfully');
        } else {
            const errorText = await response.text();
            showToast(errorText || 'Failed to register user', 'error');
        }
    } catch (error) {
        console.error('Error registering user:', error);
        showToast('Error connecting to server', 'error');
    }
}

async function deleteUser(id) {
    if (!confirm('Are you sure you want to delete this user?')) return;
    
    try {
        const response = await fetch(`${API_BASE_URL}/users/${id}`, { method: 'DELETE' });
        if (response.ok) {
            loadUsers();
            showToast('User deleted successfully');
        } else {
            showToast('Failed to delete user', 'error');
        }
    } catch (error) {
        console.error('Error deleting user:', error);
        showToast('Error connecting to server', 'error');
    }
}

// --- Bookings ---
async function loadBookings() {
    if (!bookingList) return;
    try {
        const response = await fetch(`${API_BASE_URL}/bookings`);
        const bookings = await response.json();
        
        updateBookingStats(bookings);

        if (bookings.length === 0) {
            bookingList.innerHTML = '<tr><td colspan="7" style="text-align:center; padding: 2rem; color: var(--gray);">No bookings found</td></tr>';
            return;
        }

        bookingList.innerHTML = bookings.map(booking => `
            <tr>
                <td><span style="color: var(--gray); font-size: 0.85rem;">#${booking.id}</span></td>
                <td>
                    <div class="user-cell">
                        <div class="user-avatar" style="width: 32px; height: 32px; font-size: 0.8rem; background: #f1f5f9; color: var(--dark);">
                            <i class="fas fa-user"></i>
                        </div>
                        <span style="font-weight: 500;">${booking.userName}</span>
                    </div>
                </td>
                <td><span style="font-weight: 500; color: var(--primary);">${booking.vehicleName}</span></td>
                <td>
                    <div style="font-size: 0.85rem; line-height: 1.4;">
                        <div style="font-weight: 500;">${formatDate(booking.startDate)}</div>
                        <div style="color:var(--gray); font-size: 0.75rem;">to ${formatDate(booking.endDate)}</div>
                    </div>
                </td>
                <td style="font-weight:700; color: var(--dark);">$${booking.totalPrice.toFixed(2)}</td>
                <td><span class="status-pill status-${booking.status.toLowerCase()}">${booking.status}</span></td>
                <td>
                    <button class="action-btn-sm" onclick="deleteBooking(${booking.id})" title="Cancel Booking">
                        <i class="fas fa-times-circle"></i>
                    </button>
                </td>
            </tr>
        `).join('');
    } catch (error) {
        console.error('Error loading bookings:', error);
    }
}

function formatDate(dateString) {
    const options = { month: 'short', day: 'numeric' };
    return new Date(dateString).toLocaleDateString('en-US', options);
}

function updateBookingStats(bookings) {
    if (totalBookingsEl) totalBookingsEl.innerText = bookings.length;
    
    const revenue = bookings.reduce((sum, booking) => sum + booking.totalPrice, 0);
    if (totalRevenueEl) totalRevenueEl.innerText = `$${revenue.toFixed(0)}`;
}

function updateDashboardStats() {
    if (activeVehiclesEl) activeVehiclesEl.innerText = allVehicles.length;
}

function prefillBooking(vehicleId) {
    if (!currentUser) {
        showToast('Please login to book a vehicle', 'error');
        openModal('loginModal');
        return;
    }
    
    const vehicleInput = document.getElementById('bookingVehicleId');
    const emailInput = document.getElementById('bookingUserEmail');
    
    if (vehicleInput) vehicleInput.value = vehicleId;
    if (emailInput) {
        emailInput.value = currentUser.email;
        emailInput.readOnly = true; // Prevent changing email if logged in
    }
    
    openModal('bookingModal');
}

async function handleBookingSubmit(e) {
    e.preventDefault();
    const formData = new FormData(e.target);
    const data = Object.fromEntries(formData.entries());
    
    // Ensure numeric types are correct
    data.vehicleId = parseInt(data.vehicleId);
    data.totalKilometer = parseFloat(data.totalKilometer);
    
    try {
        const response = await fetch(`${API_BASE_URL}/bookings`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(data)
        });
        
        if (response.ok) {
            closeModal('bookingModal');
e.target.reset();
            if (currentUser && currentUser.role === 'ADMIN') {
                loadBookings();
            }
            showToast('Booking confirmed successfully');
        } else {
            const errorText = await response.text();
            showToast(errorText || 'Failed to create booking', 'error');
        }
    } catch (error) {
        console.error('Error creating booking:', error);
        showToast('Error connecting to server', 'error');
    }
}

async function deleteBooking(id) {
    if (!confirm('Are you sure you want to cancel this booking?')) return;
    
    try {
        const response = await fetch(`${API_BASE_URL}/bookings/${id}`, { method: 'DELETE' });
        if (response.ok) {
            loadBookings();
            showToast('Booking cancelled successfully');
        } else {
            showToast('Failed to cancel booking', 'error');
        }
    } catch (error) {
        console.error('Error deleting booking:', error);
        showToast('Error connecting to server', 'error');
    }
}
