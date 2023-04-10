package models;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

//TODO ADD JOIN DATE FOR PORTFOLIO PAGE.
//TODO Remove Vendor type upon registering, when they create job for the first time then change their type to isVendor. Gives them Vendor and user privileges.
@Entity
public class User {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
    @Type(type = "uuid-char")
    public UUID id;
    public String firstname;
    public String lastname;
    public String password;
    public String email;
    public String address;

    @Enumerated(EnumType.STRING)
    private UserType userType;

    @OneToMany
    public List<PurchaseHistory> purchaseHistory;

    public User() {

    }

    public User(String firstname, String lastname, String password, String email, String address, UserType userType, List <PurchaseHistory> purchaseHistory) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.password = password;
        this.email = email;
        this.address = address;
        this.userType = userType;
        this.purchaseHistory = purchaseHistory;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public List <PurchaseHistory> getPurchaseHistory() {
        return purchaseHistory;
    }

    public void setPurchaseHistory(List <PurchaseHistory> purchaseHistory) {
        this.purchaseHistory = purchaseHistory;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", userType=" + userType +
                ", purchaseHistory=" + purchaseHistory +
                '}';
    }

    public enum UserType {
        ADMIN,
        CUSTOMER,
    }
}
