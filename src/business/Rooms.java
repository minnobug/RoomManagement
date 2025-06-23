/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package business;

import model.Room;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Rooms {

    private final String filePath = "data/Active_Room_List.txt";
    private final Map<String, Room> roomMap = new HashMap<>();

    public Map<String, Room> getRoomMap() {
        return roomMap;
    }

    public Room getRoomById(String id) {
        return roomMap.get(id);
    }

    public boolean containsRoom(String id) {
        return roomMap.containsKey(id);
    }

    public void importFromFile() {
        int success = 0, fail = 0;

        try ( BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.trim().split(";");
                if (parts.length != 6) {
                    fail++;
                    continue;
                }

                String id = parts[0].trim();
                String name = parts[1].trim();
                String type = parts[2].trim();
                String furniture = parts[5].trim();

                try {
                    double rate = Double.parseDouble(parts[3].trim());
                    int capacity = Integer.parseInt(parts[4].trim());

                    if (rate <= 0 || capacity <= 0 || roomMap.containsKey(id)) {
                        fail++;
                        continue;
                    }

                    roomMap.put(id, new Room(id, name, type, rate, capacity, furniture));
                    success++;
                } catch (NumberFormatException e) {
                    fail++;
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

        System.out.printf("%d rooms successfully loaded.\n%d entries failed.\n", success, fail);
    }

    public void displayAvailableRooms() {
        if (roomMap.isEmpty()) {
            System.out.println("Room list is currently empty or not loaded.");
            return;
        }

        printRoomHeader();
        for (Room r : roomMap.values()) {
            printRoomRow(r);
        }
    }

    public void listVacantRooms(Guests guests) {
        boolean hasVacant = false;

        for (Room r : roomMap.values()) {
            boolean isOccupied = guests.getAllGuests().values().stream()
                    .anyMatch(g -> g.getRoomId().equals(r.getRoomId()));

            if (!isOccupied) {
                if (!hasVacant) {
                    System.out.println("Available Room List");
                    printRoomHeader();
                    hasVacant = true;
                }
                printRoomRow(r);
            }
        }

        if (!hasVacant) {
            System.out.println("All rooms have been rented out. No rooms are currently available.");
        }
    }

    private void printRoomHeader() {
        System.out.printf("%-6s | %-20s | %-9s | %-6s | %-8s | %s\n",
                "RoomID", "RoomName", "Type", "Rate", "Capacity", "Furniture");
        System.out.println("-------+----------------------+-----------+--------+----------+-------------------------------");
    }

    private void printRoomRow(Room r) {
        System.out.printf("%-6s | %-20s | %-9s | %-6.2f | %-8d | %s\n",
                r.getRoomId(), r.getRoomName(), r.getRoomType(),
                r.getDailyRate(), r.getCapacity(), r.getFurniture());
    }
}
