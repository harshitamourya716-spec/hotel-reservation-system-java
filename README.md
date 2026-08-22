# CodeAlpha Hotel Reservation System

**Author:** Harshita Mourya | B.Tech CSE, Technocrats Institute of Technology, Bhopal (2nd Year)
**Internship:** CodeAlpha Java Programming Internship — Task 4

A console-based Java application to search, book, and manage hotel rooms.
This was my second task for the internship, so I focused on getting the
file persistence part right since Task 2 (Stock Trading) already covered
the OOP basics for me.

## Features
- View all rooms with category, price, and availability
- Search available rooms by category (Standard / Deluxe / Suite)
- Book a room with guest name and number of nights
- Cancel an existing reservation
- Simulate payment for a reservation
- View all current reservations
- **Persistent storage**: room and reservation data is saved to `rooms.txt`
  and `reservations.txt` using Java File I/O, so data survives across runs

## Concepts Used
- Object-Oriented Programming (Room, Reservation, Hotel classes)
- Collections (ArrayList)
- File I/O (BufferedReader / PrintWriter) for data persistence
- Java Time API (LocalDate) for booking dates

## How to Run
```bash
javac *.java
java Main
```

## Project Structure
```
CodeAlpha_HotelReservationSystem/
├── Room.java          # Represents a hotel room
├── Reservation.java   # Represents a booking
├── Hotel.java         # Core business logic + file persistence
├── Main.java          # Console menu / entry point
└── README.md
```

## Sample Menu
```
1. View All Rooms
2. Search Available Rooms by Category
3. Book a Room
4. Cancel a Reservation
5. Make Payment (Simulation)
6. View All Reservations
7. Exit
```

## What I Learned
Working on this taught me how to actually persist data between runs using
plain text files instead of a database — splitting fields with commas and
parsing them back was a bit fiddly at first (had to be careful with the
order of fields while reading vs writing), but it works reliably now.
I also got more comfortable structuring a project into separate classes
where each one has just one job (Room = data, Hotel = logic, Main = UI).

---
Built for the **CodeAlpha Java Programming Internship**.
Harshita Mourya, B.Tech CSE, Technocrats Institute of Technology, Bhopal
