package dispatcher;

import business.Guests;
import business.Rooms;
import tools.Inputter;

public class Main {

    private static final Rooms rooms = new Rooms();
    private static final Guests guests = new Guests(rooms);

    public static void main(String[] args) {
        int choice;
        do {
            showMenu();
            choice = Inputter.getInt("Enter your choice: ");

            switch (choice) {
                case 1:
                    rooms.importFromFile();
                    break;
                case 2:
                    rooms.displayAvailableRooms();
                    break;
                case 3:
                    guests.guestInformation(null);
                    break;
                case 4:
                    guests.updateGuestInformation();
                    break;
                case 5:
                    guests.searchGuestByNationalId(rooms.getRoomMap());
                    break;
                case 6:
                    guests.deleteGuestReservationBeforeArrival(guests.getGuestMap(), rooms.getRoomMap(), new Inputter());
                    break;
                case 7:
                    rooms.listVacantRooms(guests);
                    break;
                case 8:
                    guests.monthlyRevenueReport(rooms.getRoomMap());
                    break;
                case 9:
                    guests.revenueByRoomTypeReport(rooms);
                    break;
                case 10:
                    guests.saveToFile();
                    break;
                case 0:
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }
            System.out.println();
        } while (choice != 0);
    }

    private static void showMenu() {
        System.out.println("========= ROOM MANAGEMENT SYSTEM =========");
        System.out.println("1.  Import Room Data from File");
        System.out.println("2.  Display Available Room List");
        System.out.println("3.  Enter Guest Information");
        System.out.println("4.  Update Guest Stay Information");
        System.out.println("5.  Search Guest by National ID");
        System.out.println("6.  Delete Guest Reservation Before Arrival");
        System.out.println("7.  List Vacant Rooms");
        System.out.println("8.  Monthly Revenue Report");
        System.out.println("9.  Revenue Report by Room Type");
        System.out.println("10. Save Guest Information to File");
        System.out.println("0.  Quit");
        System.out.println("==========================================");
    }
}
