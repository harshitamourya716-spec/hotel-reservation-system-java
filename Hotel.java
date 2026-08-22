import java.io.*;
import java.util.*;

/**
 * Core class that manages rooms and reservations for the hotel.
 * Handles searching, booking, cancellation, payment simulation,
 * and persistence of data to text files (File I/O).
 */
public class Hotel {
    private List<Room> rooms;
    private List<Reservation> reservations;
    private int nextReservationId;

    private static final String ROOMS_FILE = "rooms.txt";
    private static final String RESERVATIONS_FILE = "reservations.txt";

    public Hotel() {
        rooms = new ArrayList<>();
        reservations = new ArrayList<>();
        nextReservationId = 1;
        loadData();
        if (rooms.isEmpty()) {
            initializeDefaultRooms();
            saveRooms();
        }
    }

    /** Pre-populate the hotel with a default set of rooms if none exist yet. */
    private void initializeDefaultRooms() {
        int roomNo = 101;
        for (int i = 0; i < 5; i++) rooms.add(new Room(roomNo++, "Standard", 1500));
        for (int i = 0; i < 3; i++) rooms.add(new Room(roomNo++, "Deluxe", 2800));
        for (int i = 0; i < 2; i++) rooms.add(new Room(roomNo++, "Suite", 5000));
    }

    // ---------- Search ----------

    public List<Room> searchAvailableRooms(String category) {
        List<Room> result = new ArrayList<>();
        for (Room r : rooms) {
            if (!r.isBooked() && (category == null || category.equalsIgnoreCase(r.getCategory()))) {
                result.add(r);
            }
        }
        return result;
    }

    public void displayAllRooms() {
        System.out.println("\n----- ALL ROOMS -----");
        for (Room r : rooms) {
            System.out.println(r);
        }
    }

    // ---------- Booking ----------

    public Reservation bookRoom(int roomNumber, String guestName, int nights) {
        Room room = findRoom(roomNumber);
        if (room == null) {
            System.out.println("Room #" + roomNumber + " does not exist.");
            return null;
        }
        if (room.isBooked()) {
            System.out.println("Room #" + roomNumber + " is already booked.");
            return null;
        }
        double total = room.getPricePerNight() * nights;
        Reservation res = new Reservation(nextReservationId++, guestName, roomNumber,
                room.getCategory(), nights, total);
        room.setBooked(true);
        reservations.add(res);
        saveRooms();
        saveReservations();
        System.out.println("Booking successful!\n" + res);
        return res;
    }

    // ---------- Cancellation ----------

    public boolean cancelReservation(int reservationId) {
        Reservation toCancel = null;
        for (Reservation r : reservations) {
            if (r.getReservationId() == reservationId) {
                toCancel = r;
                break;
            }
        }
        if (toCancel == null) {
            System.out.println("No reservation found with ID " + reservationId);
            return false;
        }
        Room room = findRoom(toCancel.getRoomNumber());
        if (room != null) {
            room.setBooked(false);
        }
        reservations.remove(toCancel);
        saveRooms();
        saveReservations();
        System.out.println("Reservation #" + reservationId + " cancelled successfully.");
        return true;
    }

    // ---------- Payment simulation ----------

    public boolean makePayment(int reservationId) {
        for (Reservation r : reservations) {
            if (r.getReservationId() == reservationId) {
                if (r.isPaymentDone()) {
                    System.out.println("Payment already completed for this reservation.");
                    return false;
                }
                System.out.println("Processing payment of Rs." + r.getTotalAmount() + " ...");
                r.setPaymentDone(true);
                saveReservations();
                System.out.println("Payment successful for Reservation #" + reservationId);
                return true;
            }
        }
        System.out.println("No reservation found with ID " + reservationId);
        return false;
    }

    // ---------- View bookings ----------

    public void displayAllReservations() {
        if (reservations.isEmpty()) {
            System.out.println("No reservations found.");
            return;
        }
        System.out.println("\n----- ALL RESERVATIONS -----");
        for (Reservation r : reservations) {
            System.out.println(r);
        }
    }

    // ---------- Helpers ----------

    private Room findRoom(int roomNumber) {
        for (Room r : rooms) {
            if (r.getRoomNumber() == roomNumber) return r;
        }
        return null;
    }

    // ---------- File I/O: persistence ----------

    private void saveRooms() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(ROOMS_FILE))) {
            for (Room r : rooms) {
                pw.println(r.getRoomNumber() + "," + r.getCategory() + "," +
                        r.getPricePerNight() + "," + r.isBooked());
            }
        } catch (IOException e) {
            System.out.println("Error saving rooms: " + e.getMessage());
        }
    }

    private void saveReservations() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(RESERVATIONS_FILE))) {
            for (Reservation r : reservations) {
                pw.println(r.getReservationId() + "," + r.getGuestName() + "," +
                        r.getRoomNumber() + "," + r.getTotalAmount() + "," +
                        r.getNumberOfNights() + "," + r.getBookingDate() + "," +
                        r.isPaymentDone());
            }
        } catch (IOException e) {
            System.out.println("Error saving reservations: " + e.getMessage());
        }
    }

    private void loadData() {
        File roomsFile = new File(ROOMS_FILE);
        if (roomsFile.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(roomsFile))) {
                String line;
                while ((line = br.readLine()) != null) {
                    if (line.trim().isEmpty()) continue;
                    String[] parts = line.split(",");
                    Room room = new Room(Integer.parseInt(parts[0]), parts[1], Double.parseDouble(parts[2]));
                    room.setBooked(Boolean.parseBoolean(parts[3]));
                    rooms.add(room);
                }
            } catch (IOException e) {
                System.out.println("Error loading rooms: " + e.getMessage());
            }
        }

        File resFile = new File(RESERVATIONS_FILE);
        if (resFile.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(resFile))) {
                String line;
                while ((line = br.readLine()) != null) {
                    if (line.trim().isEmpty()) continue;
                    String[] parts = line.split(",");
                    int id = Integer.parseInt(parts[0]);
                    String guestName = parts[1];
                    int roomNumber = Integer.parseInt(parts[2]);
                    double total = Double.parseDouble(parts[3]);
                    int nights = Integer.parseInt(parts[4]);
                    java.time.LocalDate bookingDate = java.time.LocalDate.parse(parts[5]);
                    boolean paid = Boolean.parseBoolean(parts[6]);

                    String category = "Standard";
                    Room r = findRoom(roomNumber);
                    if (r != null) category = r.getCategory();

                    Reservation res = new Reservation(id, guestName, roomNumber, category, nights, total);
                    res.setBookingDate(bookingDate);
                    res.setPaymentDone(paid);
                    reservations.add(res);

                    if (id >= nextReservationId) nextReservationId = id + 1;
                }
            } catch (IOException e) {
                System.out.println("Error loading reservations: " + e.getMessage());
            }
        }
    }
}
