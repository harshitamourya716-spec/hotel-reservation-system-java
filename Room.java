import java.io.Serializable;

// Basic room model - just holds data, no real logic here.
// Kept it Serializable in case I switch to object streams later instead of plain text files.
public class Room implements Serializable {
    private static final long serialVersionUID = 1L;

    private int roomNumber;
    private String category;   // Standard, Deluxe, Suite
    private double pricePerNight;
    private boolean isBooked;

    public Room(int roomNumber, String category, double pricePerNight) {
        this.roomNumber = roomNumber;
        this.category = category;
        this.pricePerNight = pricePerNight;
        this.isBooked = false;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public String getCategory() {
        return category;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }

    public boolean isBooked() {
        return isBooked;
    }

    public void setBooked(boolean booked) {
        this.isBooked = booked;
    }

    @Override
    public String toString() {
        String status = isBooked ? "Booked" : "Available";
        return String.format("Room #%-4d | %-8s | Rs.%-8.2f/night | %s",
                roomNumber, category, pricePerNight, status);
    }
}
