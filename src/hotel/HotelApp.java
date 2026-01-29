package hotel;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

/**
 * Main application class with menu-driven interface.
 */
public class HotelApp {
    private static Hotel hotel;
    private static Scanner scanner;
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    
    public static void main(String[] args) {
        hotel = new Hotel("Grand Royal Hotel");
        scanner = new Scanner(System.in);
        
        printWelcome();
        
        boolean running = true;
        while (running) {
            printMainMenu();
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1: viewRooms(); break;
                case 2: makeReservation(); break;
                case 3: checkIn(); break;
                case 4: checkOut(); break;
                case 5: viewReservations(); break;
                case 6: searchReservation(); break;
                case 7: cancelReservation(); break;
                case 8: generateBill(); break;
                case 9: hotel.printRoomSummary(); break;
                case 0: 
                    running = false;
                    printGoodbye();
                    break;
                default:
                    System.out.println("\n❌ Invalid choice! Please try again.");
            }
        }
        
        scanner.close();
    }
    
    private static void printWelcome() {
        System.out.println("\n");
        System.out.println("╔══════════════════════════════════════════════════════════╗");
        System.out.println("║                                                          ║");
        System.out.println("║     🏨  WELCOME TO GRAND ROYAL HOTEL  🏨                ║");
        System.out.println("║                                                          ║");
        System.out.println("║         Hotel Reservation Management System              ║");
        System.out.println("║                                                          ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝");
    }
    
    private static void printGoodbye() {
        System.out.println("\n╔══════════════════════════════════════════════════════════╗");
        System.out.println("║     Thank you for using Grand Royal Hotel System!        ║");
        System.out.println("║                    Goodbye! 👋                           ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝\n");
    }
    
    private static void printMainMenu() {
        System.out.println("\n╔══════════════════════════════════════════════════════════╗");
        System.out.println("║                    MAIN MENU                             ║");
        System.out.println("╠══════════════════════════════════════════════════════════╣");
        System.out.println("║  1. View Available Rooms                                 ║");
        System.out.println("║  2. Make a Reservation                                   ║");
        System.out.println("║  3. Check-In                                             ║");
        System.out.println("║  4. Check-Out                                            ║");
        System.out.println("║  5. View All Reservations                                ║");
        System.out.println("║  6. Search Reservation                                   ║");
        System.out.println("║  7. Cancel Reservation                                   ║");
        System.out.println("║  8. Generate Bill                                        ║");
        System.out.println("║  9. Room Summary                                         ║");
        System.out.println("║  0. Exit                                                 ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝");
    }
    
    // ==================== VIEW ROOMS ====================
    
    private static void viewRooms() {
        System.out.println("\n╔══════════════════════════════════════════════════════════╗");
        System.out.println("║                  ROOM OPTIONS                            ║");
        System.out.println("╠══════════════════════════════════════════════════════════╣");
        System.out.println("║  1. View All Rooms                                       ║");
        System.out.println("║  2. View Available Rooms Only                            ║");
        System.out.println("║  3. View Rooms by Type                                   ║");
        System.out.println("║  0. Back to Main Menu                                    ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝");
        
        int choice = getIntInput("Enter your choice: ");
        
        switch (choice) {
            case 1:
                printRoomList(hotel.getAllRooms(), "ALL ROOMS");
                break;
            case 2:
                printRoomList(hotel.getAvailableRooms(), "AVAILABLE ROOMS");
                break;
            case 3:
                viewRoomsByType();
                break;
            case 0:
                return;
            default:
                System.out.println("\n❌ Invalid choice!");
        }
    }
    
    private static void viewRoomsByType() {
        System.out.println("\nSelect Room Type:");
        System.out.println("1. Single (₹2000/night)");
        System.out.println("2. Double (₹3500/night)");
        System.out.println("3. Deluxe (₹5000/night)");
        System.out.println("4. Suite (₹8000/night)");
        
        int choice = getIntInput("Enter choice: ");
        Room.RoomType type;
        
        switch (choice) {
            case 1: type = Room.RoomType.SINGLE; break;
            case 2: type = Room.RoomType.DOUBLE; break;
            case 3: type = Room.RoomType.DELUXE; break;
            case 4: type = Room.RoomType.SUITE; break;
            default:
                System.out.println("❌ Invalid choice!");
                return;
        }
        
        printRoomList(hotel.getAvailableRoomsByType(type), type.getDisplayName() + " ROOMS");
    }
    
    private static void printRoomList(List<Room> rooms, String title) {
        System.out.println("\n╔══════════════════════════════════════════════════════════════════════════╗");
        System.out.printf("║  %-72s ║\n", title);
        System.out.println("╠══════════════════════════════════════════════════════════════════════════╣");
        
        if (rooms.isEmpty()) {
            System.out.println("║  No rooms found.                                                         ║");
        } else {
            for (Room room : rooms) {
                System.out.printf("║  %-72s ║\n", room.toString());
            }
        }
        System.out.println("╚══════════════════════════════════════════════════════════════════════════╝");
    }
    
    // ==================== MAKE RESERVATION ====================
    
    private static void makeReservation() {
        System.out.println("\n═══════════════ MAKE A RESERVATION ═══════════════\n");
        
        // Check available rooms first
        List<Room> available = hotel.getAvailableRooms();
        if (available.isEmpty()) {
            System.out.println("❌ Sorry, no rooms are available at the moment.");
            return;
        }
        
        printRoomList(available, "AVAILABLE ROOMS");
        
        // Get room selection
        int roomNumber = getIntInput("\nEnter Room Number to book: ");
        Room room = hotel.getRoomByNumber(roomNumber);
        
        if (room == null) {
            System.out.println("❌ Room not found!");
            return;
        }
        
        if (!room.isAvailable()) {
            System.out.println("❌ This room is not available!");
            return;
        }
        
        // Get guest details
        System.out.println("\n--- Guest Details ---");
        System.out.print("Enter Guest Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Phone Number: ");
        String phone = scanner.nextLine();
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();
        System.out.print("Enter ID Proof (Aadhar/Passport): ");
        String idProof = scanner.nextLine();
        System.out.print("Enter Address: ");
        String address = scanner.nextLine();
        
        Guest guest = hotel.addGuest(name, phone, email, idProof, address);
        
        // Get dates
        System.out.println("\n--- Booking Dates ---");
        LocalDate checkIn = getDateInput("Enter Check-in Date (DD-MM-YYYY): ");
        LocalDate checkOut = getDateInput("Enter Check-out Date (DD-MM-YYYY): ");
        
        if (checkIn == null || checkOut == null) {
            System.out.println("❌ Invalid date format!");
            return;
        }
        
        if (!checkOut.isAfter(checkIn)) {
            System.out.println("❌ Check-out date must be after check-in date!");
            return;
        }
        
        // Calculate and show amount
        long nights = java.time.temporal.ChronoUnit.DAYS.between(checkIn, checkOut);
        double total = nights * room.getPricePerNight();
        
        System.out.println("\n--- Booking Summary ---");
        System.out.printf("Room: %d (%s)\n", room.getRoomNumber(), room.getRoomType().getDisplayName());
        System.out.printf("Duration: %d nights\n", nights);
        System.out.printf("Rate: ₹%.0f per night\n", room.getPricePerNight());
        System.out.printf("Total Amount: ₹%.2f\n", total);
        
        // Get advance payment
        double advance = getDoubleInput("\nEnter Advance Payment Amount: ₹");
        
        // Create reservation
        Reservation reservation = hotel.makeReservation(guest, room, checkIn, checkOut, advance);
        
        System.out.println("\n✅ RESERVATION SUCCESSFUL!");
        System.out.println(reservation.getFullDetails());
    }
    
    // ==================== CHECK-IN ====================
    
    private static void checkIn() {
        System.out.println("\n═══════════════ CHECK-IN ═══════════════\n");
        
        System.out.print("Enter Reservation ID: ");
        String resId = scanner.nextLine();
        
        Reservation reservation = hotel.findReservationById(resId);
        
        if (reservation == null) {
            System.out.println("❌ Reservation not found!");
            return;
        }
        
        if (reservation.getStatus() == Reservation.Status.CHECKED_IN) {
            System.out.println("❌ Guest is already checked in!");
            return;
        }
        
        if (reservation.getStatus() == Reservation.Status.CHECKED_OUT) {
            System.out.println("❌ This reservation has already been checked out!");
            return;
        }
        
        if (reservation.getStatus() == Reservation.Status.CANCELLED) {
            System.out.println("❌ This reservation has been cancelled!");
            return;
        }
        
        System.out.println(reservation.getFullDetails());
        
        System.out.print("Confirm Check-In? (Y/N): ");
        String confirm = scanner.nextLine();
        
        if (confirm.equalsIgnoreCase("Y")) {
            hotel.checkIn(reservation);
            System.out.println("\n✅ CHECK-IN SUCCESSFUL!");
            System.out.printf("Room %d is now assigned to %s\n", 
                reservation.getRoom().getRoomNumber(), reservation.getGuest().getName());
        } else {
            System.out.println("Check-in cancelled.");
        }
    }
    
    // ==================== CHECK-OUT ====================
    
    private static void checkOut() {
        System.out.println("\n═══════════════ CHECK-OUT ═══════════════\n");
        
        System.out.print("Enter Reservation ID or Room Number: ");
        String input = scanner.nextLine();
        
        Reservation reservation = null;
        
        // Try to find by reservation ID first
        reservation = hotel.findReservationById(input);
        
        // If not found, try room number
        if (reservation == null) {
            try {
                int roomNum = Integer.parseInt(input);
                reservation = hotel.findReservationByRoom(roomNum);
            } catch (NumberFormatException e) {
                // Not a room number
            }
        }
        
        if (reservation == null) {
            System.out.println("❌ Reservation not found!");
            return;
        }
        
        if (reservation.getStatus() != Reservation.Status.CHECKED_IN) {
            System.out.println("❌ Guest is not checked in!");
            return;
        }
        
        // Generate and show bill
        System.out.println(reservation.generateBill());
        
        System.out.print("Confirm Check-Out? (Y/N): ");
        String confirm = scanner.nextLine();
        
        if (confirm.equalsIgnoreCase("Y")) {
            hotel.checkOut(reservation);
            System.out.println("\n✅ CHECK-OUT SUCCESSFUL!");
            System.out.printf("Room %d is now available.\n", reservation.getRoom().getRoomNumber());
        } else {
            System.out.println("Check-out cancelled.");
        }
    }
    
    // ==================== VIEW RESERVATIONS ====================
    
    private static void viewReservations() {
        System.out.println("\n╔══════════════════════════════════════════════════════════╗");
        System.out.println("║                 VIEW RESERVATIONS                        ║");
        System.out.println("╠══════════════════════════════════════════════════════════╣");
        System.out.println("║  1. All Reservations                                     ║");
        System.out.println("║  2. Active Reservations Only                             ║");
        System.out.println("║  0. Back                                                 ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝");
        
        int choice = getIntInput("Enter your choice: ");
        List<Reservation> reservations;
        
        switch (choice) {
            case 1:
                reservations = hotel.getAllReservations();
                break;
            case 2:
                reservations = hotel.getActiveReservations();
                break;
            default:
                return;
        }
        
        if (reservations.isEmpty()) {
            System.out.println("\n❌ No reservations found.");
            return;
        }
        
        System.out.println("\n═══════════════ RESERVATIONS ═══════════════\n");
        for (Reservation res : reservations) {
            System.out.println(res);
            System.out.println("─".repeat(80));
        }
    }
    
    // ==================== SEARCH RESERVATION ====================
    
    private static void searchReservation() {
        System.out.println("\n═══════════════ SEARCH RESERVATION ═══════════════\n");
        
        System.out.print("Enter Reservation ID: ");
        String resId = scanner.nextLine();
        
        Reservation reservation = hotel.findReservationById(resId);
        
        if (reservation == null) {
            System.out.println("❌ Reservation not found!");
        } else {
            System.out.println(reservation.getFullDetails());
        }
    }
    
    // ==================== CANCEL RESERVATION ====================
    
    private static void cancelReservation() {
        System.out.println("\n═══════════════ CANCEL RESERVATION ═══════════════\n");
        
        System.out.print("Enter Reservation ID: ");
        String resId = scanner.nextLine();
        
        Reservation reservation = hotel.findReservationById(resId);
        
        if (reservation == null) {
            System.out.println("❌ Reservation not found!");
            return;
        }
        
        if (reservation.getStatus() == Reservation.Status.CANCELLED) {
            System.out.println("❌ This reservation is already cancelled!");
            return;
        }
        
        if (reservation.getStatus() == Reservation.Status.CHECKED_OUT) {
            System.out.println("❌ Cannot cancel a completed reservation!");
            return;
        }
        
        System.out.println(reservation.getFullDetails());
        
        System.out.print("Are you sure you want to cancel this reservation? (Y/N): ");
        String confirm = scanner.nextLine();
        
        if (confirm.equalsIgnoreCase("Y")) {
            hotel.cancelReservation(reservation);
            System.out.println("\n✅ RESERVATION CANCELLED!");
            if (reservation.getAdvancePaid() > 0) {
                System.out.printf("Refund amount: ₹%.2f\n", reservation.getAdvancePaid() * 0.5);
                System.out.println("(50% cancellation charge applied)");
            }
        } else {
            System.out.println("Cancellation aborted.");
        }
    }
    
    // ==================== GENERATE BILL ====================
    
    private static void generateBill() {
        System.out.println("\n═══════════════ GENERATE BILL ═══════════════\n");
        
        System.out.print("Enter Reservation ID: ");
        String resId = scanner.nextLine();
        
        Reservation reservation = hotel.findReservationById(resId);
        
        if (reservation == null) {
            System.out.println("❌ Reservation not found!");
        } else {
            System.out.println(reservation.generateBill());
        }
    }
    
    // ==================== UTILITY METHODS ====================
    
    private static int getIntInput(String prompt) {
        System.out.print(prompt);
        try {
            int value = Integer.parseInt(scanner.nextLine().trim());
            return value;
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    
    private static double getDoubleInput(String prompt) {
        System.out.print(prompt);
        try {
            return Double.parseDouble(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }
    
    private static LocalDate getDateInput(String prompt) {
        System.out.print(prompt);
        try {
            String dateStr = scanner.nextLine().trim();
            return LocalDate.parse(dateStr, DATE_FORMAT);
        } catch (DateTimeParseException e) {
            return null;
        }
    }
}
