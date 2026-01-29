# 🏨 Hotel Reservation System

A comprehensive console-based hotel management application built using **Core Java** with Object-Oriented Programming principles.

![Java](https://img.shields.io/badge/Java-17%2B-orange?style=flat&logo=openjdk)
![License](https://img.shields.io/badge/License-MIT-green?style=flat)
![Status](https://img.shields.io/badge/Status-Complete-brightgreen?style=flat)

---

## 📋 Table of Contents

- [Features](#-features)
- [Prerequisites](#-prerequisites)
- [Installation](#-installation)
- [How to Run](#-how-to-run)
- [Project Structure](#-project-structure)
- [Room Types & Pricing](#-room-types--pricing)
- [Screenshots](#-screenshots)
- [Technologies Used](#-technologies-used)

---

## ✨ Features

| Feature | Description |
|---------|-------------|
| � **Room Management** | 22 pre-configured rooms across 4 categories |
| 📝 **Guest Registration** | Complete guest details with ID proof |
| � **Reservations** | Book rooms with check-in/out dates |
| ✅ **Check-In/Out** | Manage guest arrivals and departures |
| 💰 **Billing System** | Auto-generated invoices with GST & Service Tax |
| � **Search** | Find reservations by ID or room number |
| ❌ **Cancellation** | Cancel bookings with refund calculation |
| �💾 **Data Persistence** | All data saved automatically to file |

---

## � Prerequisites

- **Java JDK 8** or higher
- Command Line / Terminal

To check if Java is installed:
```bash
java -version
javac -version
```

---

## 📥 Installation

1. **Clone or Download** the project folder
2. Navigate to the project directory:
   ```bash
   cd HotelReservationSystem
   ```

---

## 🚀 How to Run

### **Windows (PowerShell)**

```powershell
# Step 1: Navigate to project folder
cd "c:\Users\bhema\OneDrive\Desktop\My Projects\HCJ\HotelReservationSystem"

# Step 2: Create bin directory (if not exists)
New-Item -ItemType Directory -Path bin -Force

# Step 3: Compile
javac -d bin src/hotel/*.java

# Step 4: Run
java -cp bin hotel.HotelApp
```

### **Windows (Command Prompt)**

```cmd
cd "c:\Users\bhema\OneDrive\Desktop\My Projects\HCJ\HotelReservationSystem"
mkdir bin
javac -d bin src\hotel\*.java
java -cp bin hotel.HotelApp
```

### **Linux / macOS**

```bash
cd HotelReservationSystem
mkdir -p bin
javac -d bin src/hotel/*.java
java -cp bin hotel.HotelApp
```

---

## 📁 Project Structure

```
HotelReservationSystem/
│
├── src/
│   └── hotel/
│       ├── Room.java           # Room entity with types & pricing
│       ├── Guest.java          # Guest entity with details
│       ├── Reservation.java    # Booking & billing logic
│       ├── Hotel.java          # Core hotel operations
│       └── HotelApp.java       # Main app (menu interface)
│
├── bin/                        # Compiled .class files
├── hotel_data.ser              # Saved data (auto-generated)
└── README.md
```

---

## 💵 Room Types & Pricing

| Room Type | Base Price | Rooms Available | Amenities |
|-----------|------------|-----------------|-----------|
| **Single** | ₹2,000/night | 5 (101-105) | TV, Bathroom |
| **Double** | ₹3,500/night | 8 (201-208) | TV, Bathroom, AC, WiFi |
| **Deluxe** | ₹5,000/night | 6 (301-306) | TV, Bathroom, AC, WiFi, Mini Bar |
| **Suite** | ₹8,000/night | 3 (401-403) | All amenities + Living Area |

**Additional Charges:**
- AC: +₹500/night
- WiFi: +₹200/night
- GST: 12%
- Service Tax: 5%

---

## � Screenshots

### Main Menu
```
╔══════════════════════════════════════════════════════════╗
║                    MAIN MENU                             ║
╠══════════════════════════════════════════════════════════╣
║  1. View Available Rooms                                 ║
║  2. Make a Reservation                                   ║
║  3. Check-In                                             ║
║  4. Check-Out                                            ║
║  5. View All Reservations                                ║
║  6. Search Reservation                                   ║
║  7. Cancel Reservation                                   ║
║  8. Generate Bill                                        ║
║  9. Room Summary                                         ║
║  0. Exit                                                 ║
╚══════════════════════════════════════════════════════════╝
```

### Sample Bill
```
╔══════════════════════════════════════════════════════════╗
║              🏨 GRAND ROYAL HOTEL 🏨                    ║
║                  INVOICE / BILL                          ║
╠══════════════════════════════════════════════════════════╣
║ Guest Name     : John Doe                                ║
║ Room Number    : 301                                     ║
║ Room Type      : Deluxe                                  ║
║ Check-in       : 2026-01-29                              ║
║ Check-out      : 2026-01-31                              ║
╠══════════════════════════════════════════════════════════╣
║ Room Charges   : 2 nights × ₹5700 = ₹11,400.00           ║
║ GST (12%)      : ₹1,368.00                               ║
║ Service Tax(5%): ₹570.00                                 ║
╠══════════════════════════════════════════════════════════╣
║ GRAND TOTAL    : ₹13,338.00                              ║
╚══════════════════════════════════════════════════════════╝
```

---

## 🛠 Technologies Used

- **Language:** Java (JDK 8+)
- **Concepts:** OOP, File I/O, Collections, Enums
- **Data Storage:** Java Serialization (`.ser` file)
- **Interface:** Console-based Menu System

---

## 👨‍💻 Author

Built with ❤️ as a medium-level Java project.

---

## 📄 License

This project is open source and available under the [MIT License](LICENSE).
