import java.util.List;
import java.util.Scanner;

/**
 * CodeAlpha Java Internship - Task 4: Hotel Reservation System
 * Console-based application to search, book, and manage hotel rooms.
 */
public class Main {
    public static void main(String[] args) {
        Hotel hotel = new Hotel();
        Scanner sc = new Scanner(System.in);
        boolean running = true;

        System.out.println("=================================================");
        System.out.println("   WELCOME TO CODEALPHA HOTEL RESERVATION SYSTEM");
        System.out.println("=================================================");

        while (running) {
            printMenu();
            System.out.print("Enter your choice: ");
            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1":
                    hotel.displayAllRooms();
                    break;

                case "2":
                    System.out.print("Enter category to search (Standard/Deluxe/Suite) or press Enter for all: ");
                    String cat = sc.nextLine().trim();
                    List<Room> available = hotel.searchAvailableRooms(cat.isEmpty() ? null : cat);
                    if (available.isEmpty()) {
                        System.out.println("No available rooms found for that category.");
                    } else {
                        System.out.println("\n----- AVAILABLE ROOMS -----");
                        for (Room r : available) System.out.println(r);
                    }
                    break;

                case "3":
                    System.out.print("Enter your name: ");
                    String guestName = sc.nextLine().trim();
                    System.out.print("Enter room number to book: ");
                    int roomNum = readInt(sc);
                    System.out.print("Enter number of nights: ");
                    int nights = readInt(sc);
                    if (nights <= 0) {
                        System.out.println("Number of nights must be at least 1.");
                        break;
                    }
                    hotel.bookRoom(roomNum, guestName, nights);
                    break;

                case "4":
                    System.out.print("Enter reservation ID to cancel: ");
                    int cancelId = readInt(sc);
                    hotel.cancelReservation(cancelId);
                    break;

                case "5":
                    System.out.print("Enter reservation ID to pay for: ");
                    int payId = readInt(sc);
                    hotel.makePayment(payId);
                    break;

                case "6":
                    hotel.displayAllReservations();
                    break;

                case "7":
                    running = false;
                    System.out.println("Thank you for using CodeAlpha Hotel Reservation System. Goodbye!");
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        sc.close();
    }

    private static void printMenu() {
        System.out.println("\n---------------- MENU ----------------");
        System.out.println("1. View All Rooms");
        System.out.println("2. Search Available Rooms by Category");
        System.out.println("3. Book a Room");
        System.out.println("4. Cancel a Reservation");
        System.out.println("5. Make Payment (Simulation)");
        System.out.println("6. View All Reservations");
        System.out.println("7. Exit");
        System.out.println("---------------------------------------");
    }

    private static int readInt(Scanner sc) {
        while (true) {
            String input = sc.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.print("Please enter a valid number: ");
            }
        }
    }
}
