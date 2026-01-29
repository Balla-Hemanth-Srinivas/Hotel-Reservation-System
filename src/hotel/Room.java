package hotel;

import java.io.Serializable;

/**
 * Represents a hotel room with its properties.
 */
public class Room implements Serializable {
    private static final long serialVersionUID = 1L;
    
    public enum RoomType {
        SINGLE("Single", 2000),
        DOUBLE("Double", 3500),
        DELUXE("Deluxe", 5000),
        SUITE("Suite", 8000);
        
        private final String displayName;
        private final double pricePerNight;
        
        RoomType(String displayName, double pricePerNight) {
            this.displayName = displayName;
            this.pricePerNight = pricePerNight;
        }
        
        public String getDisplayName() { return displayName; }
        public double getPricePerNight() { return pricePerNight; }
    }
    
    private int roomNumber;
    private RoomType roomType;
    private boolean isAvailable;
    private boolean hasAC;
    private boolean hasWifi;
    
    public Room(int roomNumber, RoomType roomType, boolean hasAC, boolean hasWifi) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.isAvailable = true;
        this.hasAC = hasAC;
        this.hasWifi = hasWifi;
    }
    
    // Getters and Setters
    public int getRoomNumber() { return roomNumber; }
    public RoomType getRoomType() { return roomType; }
    public boolean isAvailable() { return isAvailable; }
    public void setAvailable(boolean available) { isAvailable = available; }
    public boolean hasAC() { return hasAC; }
    public boolean hasWifi() { return hasWifi; }
    
    public double getPricePerNight() {
        double price = roomType.getPricePerNight();
        if (hasAC) price += 500;
        if (hasWifi) price += 200;
        return price;
    }
    
    @Override
    public String toString() {
        return String.format("Room %d | %-8s | ₹%.0f/night | AC: %s | WiFi: %s | %s",
            roomNumber,
            roomType.getDisplayName(),
            getPricePerNight(),
            hasAC ? "Yes" : "No",
            hasWifi ? "Yes" : "No",
            isAvailable ? "Available" : "Occupied");
    }
}
