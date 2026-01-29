package hotel;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Represents a hotel reservation/booking.
 */
public class Reservation implements Serializable {
    private static final long serialVersionUID = 1L;
    
    public enum Status {
        CONFIRMED, CHECKED_IN, CHECKED_OUT, CANCELLED
    }
    
    private String reservationId;
    private Guest guest;
    private Room room;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private Status status;
    private double totalAmount;
    private double advancePaid;
    private LocalDate bookingDate;
    
    public Reservation(String reservationId, Guest guest, Room room, 
                       LocalDate checkInDate, LocalDate checkOutDate) {
        this.reservationId = reservationId;
        this.guest = guest;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.status = Status.CONFIRMED;
        this.bookingDate = LocalDate.now();
        this.advancePaid = 0;
        calculateTotalAmount();
    }
    
    private void calculateTotalAmount() {
        long nights = ChronoUnit.DAYS.between(checkInDate, checkOutDate);
        this.totalAmount = nights * room.getPricePerNight();
    }
    
    public long getNumberOfNights() {
        return ChronoUnit.DAYS.between(checkInDate, checkOutDate);
    }
    
    // Getters
    public String getReservationId() { return reservationId; }
    public Guest getGuest() { return guest; }
    public Room getRoom() { return room; }
    public LocalDate getCheckInDate() { return checkInDate; }
    public LocalDate getCheckOutDate() { return checkOutDate; }
    public Status getStatus() { return status; }
    public double getTotalAmount() { return totalAmount; }
    public double getAdvancePaid() { return advancePaid; }
    public LocalDate getBookingDate() { return bookingDate; }
    
    // Setters
    public void setStatus(Status status) { this.status = status; }
    public void setAdvancePaid(double amount) { this.advancePaid = amount; }
    
    public double getBalanceAmount() {
        return totalAmount - advancePaid;
    }
    
    public void checkIn() {
        this.status = Status.CHECKED_IN;
        this.room.setAvailable(false);
    }
    
    public void checkOut() {
        this.status = Status.CHECKED_OUT;
        this.room.setAvailable(true);
    }
    
    public void cancel() {
        this.status = Status.CANCELLED;
        this.room.setAvailable(true);
    }
    
    @Override
    public String toString() {
        return String.format("Reservation: %s | Room: %d | Guest: %s | %s to %s | Status: %s",
            reservationId, room.getRoomNumber(), guest.getName(),
            checkInDate, checkOutDate, status);
    }
    
    public String getFullDetails() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n╔══════════════════════════════════════════════════════════╗\n");
        sb.append("║                  RESERVATION DETAILS                      ║\n");
        sb.append("╠══════════════════════════════════════════════════════════╣\n");
        sb.append(String.format("║ Reservation ID : %-40s ║\n", reservationId));
        sb.append(String.format("║ Booking Date   : %-40s ║\n", bookingDate));
        sb.append(String.format("║ Status         : %-40s ║\n", status));
        sb.append("╠══════════════════════════════════════════════════════════╣\n");
        sb.append("║                    GUEST DETAILS                          ║\n");
        sb.append("╠══════════════════════════════════════════════════════════╣\n");
        sb.append(String.format("║ Name           : %-40s ║\n", guest.getName()));
        sb.append(String.format("║ Phone          : %-40s ║\n", guest.getPhone()));
        sb.append(String.format("║ Email          : %-40s ║\n", guest.getEmail()));
        sb.append("╠══════════════════════════════════════════════════════════╣\n");
        sb.append("║                    ROOM DETAILS                           ║\n");
        sb.append("╠══════════════════════════════════════════════════════════╣\n");
        sb.append(String.format("║ Room Number    : %-40d ║\n", room.getRoomNumber()));
        sb.append(String.format("║ Room Type      : %-40s ║\n", room.getRoomType().getDisplayName()));
        sb.append(String.format("║ Price/Night    : ₹%-39.0f ║\n", room.getPricePerNight()));
        sb.append("╠══════════════════════════════════════════════════════════╣\n");
        sb.append("║                    STAY DETAILS                           ║\n");
        sb.append("╠══════════════════════════════════════════════════════════╣\n");
        sb.append(String.format("║ Check-in Date  : %-40s ║\n", checkInDate));
        sb.append(String.format("║ Check-out Date : %-40s ║\n", checkOutDate));
        sb.append(String.format("║ Number of Nights: %-39d ║\n", getNumberOfNights()));
        sb.append("╠══════════════════════════════════════════════════════════╣\n");
        sb.append("║                    PAYMENT DETAILS                        ║\n");
        sb.append("╠══════════════════════════════════════════════════════════╣\n");
        sb.append(String.format("║ Total Amount   : ₹%-39.2f ║\n", totalAmount));
        sb.append(String.format("║ Advance Paid   : ₹%-39.2f ║\n", advancePaid));
        sb.append(String.format("║ Balance Due    : ₹%-39.2f ║\n", getBalanceAmount()));
        sb.append("╚══════════════════════════════════════════════════════════╝\n");
        return sb.toString();
    }
    
    public String generateBill() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        sb.append("╔══════════════════════════════════════════════════════════╗\n");
        sb.append("║              🏨 GRAND ROYAL HOTEL 🏨                     ║\n");
        sb.append("║                  INVOICE / BILL                          ║\n");
        sb.append("╠══════════════════════════════════════════════════════════╣\n");
        sb.append(String.format("║ Bill Date      : %-40s ║\n", LocalDate.now()));
        sb.append(String.format("║ Reservation ID : %-40s ║\n", reservationId));
        sb.append("╠══════════════════════════════════════════════════════════╣\n");
        sb.append(String.format("║ Guest Name     : %-40s ║\n", guest.getName()));
        sb.append(String.format("║ Phone          : %-40s ║\n", guest.getPhone()));
        sb.append("╠══════════════════════════════════════════════════════════╣\n");
        sb.append(String.format("║ Room Number    : %-40d ║\n", room.getRoomNumber()));
        sb.append(String.format("║ Room Type      : %-40s ║\n", room.getRoomType().getDisplayName()));
        sb.append(String.format("║ Check-in       : %-40s ║\n", checkInDate));
        sb.append(String.format("║ Check-out      : %-40s ║\n", checkOutDate));
        sb.append("╠══════════════════════════════════════════════════════════╣\n");
        sb.append("║                    CHARGES                               ║\n");
        sb.append("╠══════════════════════════════════════════════════════════╣\n");
        sb.append(String.format("║ Room Charges   : %d nights × ₹%.0f = ₹%-16.2f ║\n", 
            getNumberOfNights(), room.getPricePerNight(), totalAmount));
        
        double gst = totalAmount * 0.12;
        double serviceTax = totalAmount * 0.05;
        double grandTotal = totalAmount + gst + serviceTax;
        
        sb.append(String.format("║ GST (12%%)      : ₹%-39.2f ║\n", gst));
        sb.append(String.format("║ Service Tax(5%%): ₹%-39.2f ║\n", serviceTax));
        sb.append("╠══════════════════════════════════════════════════════════╣\n");
        sb.append(String.format("║ GRAND TOTAL    : ₹%-39.2f ║\n", grandTotal));
        sb.append(String.format("║ Advance Paid   : ₹%-39.2f ║\n", advancePaid));
        sb.append(String.format("║ AMOUNT DUE     : ₹%-39.2f ║\n", grandTotal - advancePaid));
        sb.append("╠══════════════════════════════════════════════════════════╣\n");
        sb.append("║           Thank you for staying with us!                 ║\n");
        sb.append("║              We hope to see you again!                   ║\n");
        sb.append("╚══════════════════════════════════════════════════════════╝\n");
        return sb.toString();
    }
}
