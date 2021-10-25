import java.util.Collection;
import java.util.Scanner;
import api.AdminResource;
import model.*;
public class AdminMenu {

    public static void option1(){
        Collection<Customer> allCustomers = AdminResource.getAllCustomers();
        System.out.printf("There are %d customers now:%n", allCustomers.size());
        for(Customer c: allCustomers)
            System.out.println(c);
    }
    public static void option2(){
        Collection<IRoom> allRooms = AdminResource.getAllRooms();
        System.out.printf("There are %d rooms now:%n", allRooms.size());
        for(IRoom r: allRooms)
            System.out.println(r);
    }
    public static void option3(){
        AdminResource.displayAllReservations();
    }
    public static void option4(Scanner scanner){
        while(true){
            System.out.println("Do you want to add a room? (Please type Yes/No)");
            String want = scanner.nextLine();
            if(want.equals("No"))
                break;
            else if(want.equals("Yes")){
                try{
                    System.out.println("Please enter room number:");
                    String roomNumber = scanner.nextLine();
                    System.out.println("Please enter room price:");
                    Double roomPrice = Double.parseDouble(scanner.nextLine());
                    System.out.println("Please enter room type:(SINGLE, DOUBLE)");
                    String typeStr = scanner.nextLine();
                    if(!typeStr.equals("SINGLE") && !typeStr.equals("DOUBLE"))
                        throw new IllegalArgumentException("Room type error. Please try again.");
                    RoomType roomType = (typeStr.equals("SINGLE")) ? RoomType.SINGLE : RoomType.DOUBLE;
                    Room room = new Room(roomNumber, roomPrice, roomType);
                    AdminResource.addRoom(room);
                    System.out.println("The room successfully created.");
                }catch(Exception ex){
                    System.out.println(ex.getMessage());
                }
            }
        }
    }
    public static void intoAdminMenu(Scanner scanner){
        while(true) {
            System.out.println("Admin Menu");
            System.out.println("------------------------------");
            System.out.println("1. See all Customers");
            System.out.println("2. See all Rooms");
            System.out.println("3. See all reservations");
            System.out.println("4. Add a Room");
            System.out.println("5. Back to Main Menu");
            System.out.println("------------------------------");
            System.out.println("Please select a number for the menu option");
            String opt = scanner.nextLine();
            if(opt.equals("1"))
                option1();
            else if(opt.equals("2"))
                option2();
            else if(opt.equals("3"))
                option3();
            else if(opt.equals("4"))
                option4(scanner);
            else if(opt.equals("5"))
                break;
            else
                System.out.println("The option should be 1~5. Please try again.");
        }
    }
}
