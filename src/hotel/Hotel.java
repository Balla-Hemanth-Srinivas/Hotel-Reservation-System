package hotel;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Main hotel management class that handles all operations.
 */
public class Hotel {
    private String hotelName;
    private List<Room> rooms;
    private List<Guest> guests;
    private List<Reservation> reservations;
    private int reservationCounter;
    private int guestCounter;
    private static final String DATA_FILE = "hotel_data.ser";
    
    public Hotel(String hotelName) {
        this.hotelName = hotelName;
        this.rooms = new ArrayList<>();
        this.guests = new ArrayList<>();
        this.reservations = new ArrayList<>();
        this.reservationCounter = 1000;
        this.guestCounter = 100;
        
        // Load existing data or initialize with default rooms
        if (!loadData()) {
            initializeRooms();
        }
    }
    
    /**
     * Initialize default rooms for the hotel.
     */
    private void initializeRooms() {
        // Single Rooms (101-105)
        for (int i = 101; i <= 105; i++) {
            rooms.add(new Room(i, Room.RoomType.SINGLE, i % 2 == 0, true));
        }
        // Double Rooms (201-208)
        for (int i = 201; i <= 208; i++) {
            rooms.add(new Room(i, Room.RoomType.DOUBLE, true, true));
        }
        // Deluxe Rooms (301-306)
        for (int i = 301; i <= 306; i++) {
            rooms.add(new Room(i, Room.RoomType.DELUXE, true, true));
        }
        // Suites (401-403)
        for (int i = 401; i <= 403; i++) {
            rooms.add(new Room(i, Room.RoomType.SUITE, true, true));
        }
        saveData();
    }
    
    // ==================== ROOM OPERATIONS ====================
    
    public List<Room> getAllRooms() {
        return new ArrayList<>(rooms);
    }
    
    public List<Room> getAvailableRooms() {
        return rooms.stream()
            .filter(Room::isAvailable)
            .collect(Collectors.toList());
    }
    
    public List<Room> getAvailableRoomsByType(Room.RoomType type) {
        return rooms.stream()
            .filter(r -> r.isAvailable() && r.getRoomType() == type)
            .collect(Collectors.toList());
    }
    
    public Room getRoomByNumber(int roomNumber) {
        return rooms.stream()
            .filter(r -> r.getRoomNumber() == roomNumber)
            .findFirst()
            .orElse(null);
    }
    
    // ==================== GUEST OPERATIONS ====================
    
    public Guest addGuest(String name, String phone, String email, String idProof, String address) {
        String guestId = "G" + (++guestCounter);
        Guest guest = new Guest(guestId, name, phone, email, idProof, address);
        guests.add(guest);
        saveData();
        return guest;
    }
    
    public Guest findGuestByPhone(String phone) {
        return guests.stream()
            .filter(g -> g.getPhone().equals(phone))
            .findFirst()
            .orElse(null);
    }
    
    public List<Guest> getAllGuests() {
        return new ArrayList<>(guests);
    }
    
    // ==================== RESERVATION OPERATIONS ====================
    
    public Reservation makeReservation(Guest guest, Room room, 
                                        LocalDate checkIn, LocalDate checkOut, double advance) {
        String reservationId = "RES" + (++reservationCounter);
        Reservation reservation = new Reservation(reservationId, guest, room, checkIn, checkOut);
        reservation.setAdvancePaid(advance);
        room.setAvailable(false);
        reservations.add(reservation);
        saveData();
        return reservation;
    }
    
    public Reservation findReservationById(String reservationId) {
        return reservations.stream()
            .filter(r -> r.getReservationId().equalsIgnoreCase(reservationId))
            .findFirst()
            .orElse(null);
    }
    
    public Reservation findReservationByRoom(int roomNumber) {
        return reservations.stream()
            .filter(r -> r.getRoom().getRoomNumber() == roomNumber)
            .filter(r -> r.getStatus() == Reservation.Status.CONFIRMED || 
                        r.getStatus() == Reservation.Status.CHECKED_IN)
            .findFirst()
            .orElse(null);
    }
    
    public List<Reservation> getAllReservations() {
        return new ArrayList<>(reservations);
    }
    
    public List<Reservation> getActiveReservations() {
        return reservations.stream()
            .filter(r -> r.getStatus() == Reservation.Status.CONFIRMED || 
                        r.getStatus() == Reservation.Status.CHECKED_IN)
            .collect(Collectors.toList());
    }
    
    public void checkIn(Reservation reservation) {
        reservation.checkIn();
        saveData();
    }
    
    public void checkOut(Reservation reservation) {
        reservation.checkOut();
        saveData();
    }
    
    public void cancelReservation(Reservation reservation) {
        reservation.cancel();
        saveData();
    }
    
    // ==================== DATA PERSISTENCE ====================
    
    @SuppressWarnings("unchecked")
    private boolean loadData() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
            rooms = (List<Room>) ois.readObject();
            guests = (List<Guest>) ois.readObject();
            reservations = (List<Reservation>) ois.readObject();
            reservationCounter = ois.readInt();
            guestCounter = ois.readInt();
            return true;
        } catch (FileNotFoundException e) {
            return false;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading data: " + e.getMessage());
            return false;
        }
    }
    
    private void saveData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            oos.writeObject(rooms);
            oos.writeObject(guests);
            oos.writeObject(reservations);
            oos.writeInt(reservationCounter);
            oos.writeInt(guestCounter);
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }
    
    // ==================== REPORTS ====================
    
    public void printRoomSummary() {
        int total = rooms.size();
        long available = rooms.stream().filter(Room::isAvailable).count();
        long occupied = total - available;
        
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║           ROOM SUMMARY                 ║");
        System.out.println("╠════════════════════════════════════════╣");
        System.out.printf("║ Total Rooms     : %-20d ║\n", total);
        System.out.printf("║ Available       : %-20d ║\n", available);
        System.out.printf("║ Occupied        : %-20d ║\n", occupied);
        System.out.println("╠════════════════════════════════════════╣");
        
        for (Room.RoomType type : Room.RoomType.values()) {
            long typeTotal = rooms.stream().filter(r -> r.getRoomType() == type).count();
            long typeAvailable = rooms.stream()
                .filter(r -> r.getRoomType() == type && r.isAvailable())
                .count();
            System.out.printf("║ %-15s : %d/%d available        ║\n", 
                type.getDisplayName(), typeAvailable, typeTotal);
        }
        System.out.println("╚════════════════════════════════════════╝");
    }
    
    public String getHotelName() {
        return hotelName;
    }
}
