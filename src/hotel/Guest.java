package hotel;

import java.io.Serializable;

/**
 * Represents a hotel guest with their personal details.
 */
public class Guest implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String guestId;
    private String name;
    private String phone;
    private String email;
    private String idProof; // Aadhar/Passport number
    private String address;
    
    public Guest(String guestId, String name, String phone, String email, String idProof, String address) {
        this.guestId = guestId;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.idProof = idProof;
        this.address = address;
    }
    
    // Getters
    public String getGuestId() { return guestId; }
    public String getName() { return name; }
    public String getPhone() { return phone; }
    public String getEmail() { return email; }
    public String getIdProof() { return idProof; }
    public String getAddress() { return address; }
    
    // Setters
    public void setName(String name) { this.name = name; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setEmail(String email) { this.email = email; }
    public void setAddress(String address) { this.address = address; }
    
    @Override
    public String toString() {
        return String.format("Guest: %s | Name: %s | Phone: %s | Email: %s",
            guestId, name, phone, email);
    }
    
    public String getDetails() {
        return String.format(
            "╔════════════════════════════════════════╗\n" +
            "║          GUEST INFORMATION             ║\n" +
            "╠════════════════════════════════════════╣\n" +
            "║ Guest ID   : %-25s ║\n" +
            "║ Name       : %-25s ║\n" +
            "║ Phone      : %-25s ║\n" +
            "║ Email      : %-25s ║\n" +
            "║ ID Proof   : %-25s ║\n" +
            "║ Address    : %-25s ║\n" +
            "╚════════════════════════════════════════╝",
            guestId, name, phone, email, idProof, address);
    }
}
