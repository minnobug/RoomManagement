package model;

import java.io.Serializable;

public class Room implements Serializable {

    private String roomId;
    private String roomName;
    private String roomType;
    private double dailyRate;
    private int capacity;
    private String furniture;

    public Room(String roomId, String roomName, String roomType, double dailyRate, int capacity, String furniture) {
        this.roomId = roomId;
        this.roomName = roomName;
        this.roomType = roomType;
        this.dailyRate = dailyRate;
        this.capacity = capacity;
        this.furniture = furniture;
    }

    // Default constructor (optional for serialization frameworks)
    public Room() {}

    // Getters and setters
    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public double getDailyRate() {
        return dailyRate;
    }

    public void setDailyRate(double dailyRate) {
        this.dailyRate = dailyRate;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getFurniture() {
        return furniture;
    }

    public void setFurniture(String furniture) {
        this.furniture = furniture;
    }

    @Override
    public String toString() {
        return String.format(
            "%-6s | %-20s | %-9s | %6.2f | %-8d | %s",
            roomId, roomName, roomType, dailyRate, capacity, furniture
        );
    }
}
