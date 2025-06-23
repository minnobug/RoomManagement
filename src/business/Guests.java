/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package business;

import model.Guest;
import model.Room;
import tools.Inputter;
import tools.Acceptable;
import tools.FileUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import java.util.regex.Pattern;

import static tools.Inputter.capitalizeWords;

public class Guests {

    private final Map<String, Guest> guestMap = new HashMap<>();
    private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final Rooms roomList;

    public Guests(Rooms roomList) {
        this.roomList = roomList;
    }

    public Map<String, Guest> getGuestMap() {
        return this.guestMap;
    }

    public void guestInformation(Room dummyRoom) {
        System.out.println(">>> Entering New Guest Information <<<");

        // 1. Nhập ID và kiểm tra trùng
        String id = getUniqueGuestId();

        // 2. Nhập các thông tin khác
        String name = capitalizeWords(Inputter.inputAndLoop("Enter name: ", Acceptable.NAME_VALID));
        LocalDate dob = Inputter.getValidBirthDate("Enter birthday: ");
        String gender = Inputter.inputAndLoop("Enter gender (Male/Female): ", Acceptable.GENDER_VALID);
        String phone = Inputter.inputAndLoop("Enter phone: ", Acceptable.PHONE_REGEX);

        // 3. Nhập Room ID và kiểm tra tồn tại
        Room room = getValidRoom();
        String roomId = room.getRoomId();

        int rentalDays = Inputter.getInt("Enter rental days: ");
        LocalDate startDate = Inputter.getLocalDate("Enter start date (yyyy-MM-dd): ");
        String coTenant = capitalizeWords(Inputter.inputAndLoop("Enter co-tenant name: ", Acceptable.NAME_VALID));

        // 4. Tạo và thêm Guest
        Guest g = new Guest(id, name, dob, gender, phone, roomId, rentalDays, startDate, coTenant);
        guestMap.put(id, g);

        System.out.printf("Guest registered successfully for room %s%n", roomId);
        System.out.printf("Rental from %s for %d days.%n", startDate, rentalDays);
    }

// Hàm con: nhập ID hợp lệ và không trùng
    private String getUniqueGuestId() {
        String id;
        do {
            id = Inputter.inputAndLoop("Enter ID: ", Acceptable.NATIONAL_ID_VALID);
            if (guestMap.containsKey(id)) {
                System.out.println("This ID already exists. Please enter a different one.");
                id = null;
            }
        } while (id == null);
        return id;
    }

// Hàm con: nhập room ID và kiểm tra tồn tại
    private Room getValidRoom() {
        Room room;
        do {
            String roomId = Inputter.inputAndLoop("Enter room ID: ", Acceptable.ROOM_ID_VALID).toUpperCase();
            room = roomList.getRoomById(roomId);
            if (room == null) {
                System.out.println("Room ID not found. Please try again.");
            }
        } while (room == null);
        return room;
    }

    public Guest searchById(String id) {
        return guestMap.get(id);
    }

    public void updateGuestInformation() {
        String id = Inputter.inputAndLoop("Enter National ID: ", Acceptable.NATIONAL_ID_VALID);
        Guest g = guestMap.get(id);

        if (g == null) {
            System.out.println("No guest found.");
            return;
        }

        System.out.println("Press Enter to keep current value.");

        updateStringField("New name", g.getFullName(), Acceptable.NAME_VALID, newValue -> g.setFullName(capitalizeWords(newValue)));
        updateStringField("New phone", g.getPhone(), Acceptable.PHONE_REGEX, g::setPhone);
        updateIntegerField("New rental days", g.getRentalDays(), g::setRentalDays);
        updateRoomField("New room ID", g.getRoomId(), g::setRoomId);
        updateStringField("New co-tenant name", g.getCoTenant(), Acceptable.NAME_VALID, newValue -> g.setCoTenant(capitalizeWords(newValue)));

        System.out.println("Guest information updated.");
    }

    private void updateStringField(String prompt, String currentValue, String regex, Consumer<String> setter) {
        Pattern pattern = Pattern.compile(regex); // chuyển đổi regex từ String sang Pattern
        String input = Inputter.getOptionalString(prompt + " [" + currentValue + "]: ", pattern);
        if (!input.isEmpty()) {
            setter.accept(input);
        }
    }

// Hỗ trợ cập nhật số nguyên
    private void updateIntegerField(String prompt, int currentValue, IntConsumer setter) {
        String input = Inputter.getOptionalString(prompt + " [" + currentValue + "]: ");
        if (!input.isEmpty()) {
            try {
                int value = Integer.parseInt(input);
                setter.accept(value);
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Value not updated.");
            }
        }
    }

// Hỗ trợ cập nhật Room ID và kiểm tra tồn tại
    private void updateRoomField(String prompt, String currentRoomId, Consumer<String> setter) {
        String input = Inputter.getOptionalString(prompt + " [" + currentRoomId + "]: ");
        if (!input.isEmpty()) {
            Room room = roomList.getRoomById(input.toUpperCase());
            if (room == null) {
                System.out.println("Room ID not found. Room not updated.");
            } else {
                setter.accept(room.getRoomId());
            }
        }
    }

    public void searchGuestByNationalId(Map<String, Room> roomMap) {
        String id = Inputter.inputAndLoop("Enter National ID: ", Acceptable.NATIONAL_ID_VALID);
        Guest g = guestMap.get(id);

        System.out.println("--------------------------------------------------------------------");

        if (g != null) {
            printGuestInfo(g);
            Room room = roomMap.get(g.getRoomId());
            if (room != null) {
                printRoomInfo(room);
            }
        } else {
            System.out.printf("No guest found with the requested ID '%s'%n", id);
        }

        System.out.println("--------------------------------------------------------------------");
        System.out.print("Press Enter to return to Main Menu...");
        new Scanner(System.in).nextLine();
    }

    private void printGuestInfo(Guest g) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        System.out.printf("Guest information [National ID: %s]%n", g.getNationalId());
        System.out.println("--------------------------------------------------------------------");
        System.out.printf("Full name   : %s%n", g.getFullName());
        System.out.printf("Phone number: %s%n", g.getPhone());
        System.out.printf("Birth day   : %s%n", g.getBirthDate().format(dtf));
        System.out.printf("Gender      : %s%n", g.getGender());
        System.out.println("--------------------------------------------------------------------");
        System.out.printf("Rental room : %s%n", g.getRoomId());
        System.out.printf("Check in    : %s%n", g.getStartDate().format(dtf));
        System.out.printf("Rental days : %d%n", g.getRentalDays());
        System.out.printf("Check out   : %s%n", g.getEndDate().format(dtf));
        System.out.println("--------------------------------------------------------------------");
    }

    private void printRoomInfo(Room room) {
        System.out.println("Room information:");
        System.out.printf("+ %-10s : %s%n", "ID", room.getRoomId());
        System.out.printf("+ %-10s : %s%n", "Room", room.getRoomName());
        System.out.printf("+ %-10s : %s%n", "Type", room.getRoomType());
        System.out.printf("+ %-10s : %.0f%n", "Daily rate", room.getDailyRate());
        System.out.printf("+ %-10s : %d%n", "Capacity", room.getCapacity());
        System.out.printf("+ %-10s : %s%n", "Furniture", room.getFurniture());
    }

    public void deleteGuestReservationBeforeArrival(Map<String, Guest> guests, Map<String, Room> rooms, Inputter inputter) {
        if (guests.isEmpty()) {
            System.out.println("No guest information available. Please add guest data first.");
            return;
        }

        while (true) {
            String id = inputter.inputNationalId();

            if (!Acceptable.isValid(id, Acceptable.NATIONAL_ID_VALID)) {
                System.out.println("Invalid National ID format. It must be 12 digits.");
                continue;
            }

            Guest guest = guests.get(id);
            if (guest == null) {
                System.out.printf("No booking found for ID '%s'.%n", id);
            } else if (!guest.getStartDate().isAfter(LocalDate.now())) {
                System.out.println("Cannot cancel bookings that have already started.");
            } else {
                printGuestInfo(guest);
                printRoomInfo(rooms.get(guest.getRoomId()));

                String confirm = Inputter.getString("Do you really want to cancel this booking? [Y/N]: ");
                if (confirm.equalsIgnoreCase("Y")) {
                    guests.remove(id);
                    System.out.printf("Booking for ID '%s' has been successfully canceled.%n", id);
                }
            }

            String cont = Inputter.getString("Do you want to cancel another booking? [Y/N]: ");
            if (cont.equalsIgnoreCase("N")) {
                break;
            }
        }
    }

    public Map<String, Guest> getAllGuests() {
        return guestMap;
    }

    public void monthlyRevenueReport(Map<String, Room> roomMap) {
        String mmYYYY = Inputter.inputAndLoop("Enter month/year (MM/yyyy): ", "\\d{2}/\\d{4}");
        double total = 0;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yyyy");

        System.out.println("\n Monthly Revenue Report - " + mmYYYY);
        System.out.println("---------------------------------------------------------------");
        System.out.printf("%-8s | %-15s | %-10s | %-10s | %-10s\n", "RoomID", "Room Name", "Room Type", "Rate", "Amount");
        System.out.println("---------------------------------------------------------------");

        for (Guest g : guestMap.values()) {
            if (g.getStartDate() != null && g.getStartDate().format(formatter).equals(mmYYYY)) {
                Room room = roomMap.get(g.getRoomId());
                if (room != null) {
                    double amount = room.getDailyRate() * g.getRentalDays();
                    System.out.printf("%-8s | %-15s | %-10s | %10.2f | %10.2f\n",
                            room.getRoomId(), room.getRoomName(), room.getRoomType(), room.getDailyRate(), amount);
                    total += amount;
                }
            }
        }

        if (total == 0) {
            System.out.println("No guest data found for the selected month.");
        } else {
            System.out.println("---------------------------------------------------------------");
            System.out.printf("%-45sTotal: %10.2f\n", "", total);
        }
    }

    public void revenueByRoomTypeReport(Rooms rooms) {
        Map<String, Double> revenueByType = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

        for (Guest g : guestMap.values()) {
            Room r = rooms.getRoomById(g.getRoomId());
            if (r != null) {
                revenueByType.merge(r.getRoomType(), r.getDailyRate() * g.getRentalDays(), Double::sum);
            }
        }

        if (revenueByType.isEmpty()) {
            System.out.println("❗ No guest data found for revenue calculation.");
            return;
        }

        System.out.println("\n Revenue Report by Room Type");
        System.out.println("-----------------------------");
        System.out.printf("%-12s | %-10s\n", "Room Type", "Amount");
        System.out.println("-----------------------------");

        revenueByType.forEach((type, amount)
                -> System.out.printf("%-12s | $%,10.0f\n", type, amount));
    }

    public void saveToFile() {
        String fileName = "guestInfo.dat";
        if (FileUtils.saveMapToFile(guestMap, fileName)) {
            System.out.printf("Guest information saved successfully to \"%s\".%n", fileName);
        } else {
            System.out.println("Failed to save guest information.");
        }
    }
}
