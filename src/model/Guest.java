package model;

import java.io.Serializable;
import java.time.LocalDate;

public class Guest implements Serializable {

    private String nationalId;
    private String fullName;
    private LocalDate birthDate;
    private String gender;
    private String phone;
    private String roomId;
    private int rentalDays;
    private LocalDate startDate;
    private String coTenant;

    // Constructor
    public Guest(String nationalId, String fullName, LocalDate birthDate, String gender, String phone,
            String roomId, int rentalDays, LocalDate startDate, String coTenant) {
        this.nationalId = nationalId;
        this.fullName = fullName;
        this.birthDate = birthDate;
        this.gender = gender;
        this.phone = phone;
        this.roomId = roomId;
        this.rentalDays = rentalDays;
        this.startDate = startDate;
        this.coTenant = coTenant;
    }

    // Default constructor (useful for serialization or frameworks)
    public Guest() {
    }

    // Getters and setters
    public String getNationalId() {
        return nationalId;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public int getRentalDays() {
        return rentalDays;
    }

    public void setRentalDays(int rentalDays) {
        this.rentalDays = rentalDays;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public String getCoTenant() {
        return coTenant;
    }

    public void setCoTenant(String coTenant) {
        this.coTenant = coTenant;
    }

    // Dynamic computed end date
    public LocalDate getEndDate() {
        return startDate.plusDays(rentalDays);
    }

    @Override
    public String toString() {
        return String.format(
                "National ID : %s\n"
                + "Full Name   : %s\n"
                + "Birth Date  : %s\n"
                + "Gender      : %s\n"
                + "Phone       : %s\n"
                + "Room ID     : %s\n"
                + "Start Date  : %s\n"
                + "Rental Days : %d\n"
                + "Co-Tenant   : %s\n",
                nationalId, fullName, birthDate, gender, phone, roomId,
                startDate, rentalDays, (coTenant != null ? coTenant : "N/A")
        );
    }
}
