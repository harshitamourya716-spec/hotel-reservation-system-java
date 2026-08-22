import java.io.Serializable;
import java.time.LocalDate;

/**
 * Represents a booking made by a guest for a specific room.
 */
public class Reservation implements Serializable {
    private static final long serialVersionUID = 1L;

    private int reservationId;
    private String guestName;
    private int roomNumber;
    private String roomCategory;
    private int numberOfNights;
    private double totalAmount;
    private LocalDate bookingDate;
    private boolean paymentDone;

    public Reservation(int reservationId, String guestName, int roomNumber,
                        String roomCategory, int numberOfNights, double totalAmount) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomNumber = roomNumber;
        this.roomCategory = roomCategory;
        this.numberOfNights = numberOfNights;
        this.totalAmount = totalAmount;
        this.bookingDate = LocalDate.now();
        this.paymentDone = false;
    }

    public int getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public int getNumberOfNights() {
        return numberOfNights;
    }

    public LocalDate getBookingDate() {
        return bookingDate;
    }

    public boolean isPaymentDone() {
        return paymentDone;
    }

    public void setBookingDate(LocalDate bookingDate) {
        this.bookingDate = bookingDate;
    }

    public void setPaymentDone(boolean paymentDone) {
        this.paymentDone = paymentDone;
    }

    @Override
    public String toString() {
        String payStatus = paymentDone ? "PAID" : "PENDING";
        return String.format(
                "Reservation #%d | Guest: %-15s | Room #%d (%s) | %d night(s) | Total: Rs.%.2f | Payment: %s | Date: %s",
                reservationId, guestName, roomNumber, roomCategory, numberOfNights,
                totalAmount, payStatus, bookingDate);
    }
}
