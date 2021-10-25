import java.util.Date;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.Calendar;
import java.util.Collection;
import java.util.regex.Pattern;
import api.*;
import model.*;

public class MainMenu {
    public static Date dateConvert(String dateFormat, Calendar calendar) throws NumberFormatException, IllegalArgumentException{
        String[] s1 = dateFormat.split("-");
        int[] days = new int[]{31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        int year = Integer.parseInt(s1[0]);
        int month = Integer.parseInt(s1[1])-1;
        int day = Integer.parseInt(s1[2]);
        if(month < 0 || month > 11 || day > days[month] || day < 0 || year < 0)
            throw new IllegalArgumentException("Month is invalid.");
        calendar.set(year, month, day, 0, 0, 0);
        return calendar.getTime();
    }
    public static void option1(Scanner scanner){
        while(true) {
            System.out.println("Do you want to find and reserve a room? (Please type Yes/No)");
            String want = scanner.nextLine();
            if(want.equals("No"))
                break;
            else if(want.equals("Yes")){
                try{
                    String dateRegex = "^[0-9]{4}-[0-9]{2}-[0-9]{2}$";
                    System.out.println("Please type the check in date in yyyy-mm-dd format:");
                    String strCheckIn = scanner.nextLine();
                    System.out.println("Please type the check out date in yyyy-mm-dd format:");
                    String strCheckOut = scanner.nextLine();
                    Pattern pattern = Pattern.compile(dateRegex);
                    if (!pattern.matcher(strCheckIn).matches() || !pattern.matcher(strCheckOut).matches())
                        throw new IllegalArgumentException("Date format does not match. Please try again.");
                    Calendar calendar = Calendar.getInstance();
                    Date checkIn = dateConvert(strCheckIn, calendar);
                    Date checkOut = dateConvert(strCheckOut, calendar);
                    Collection<IRoom> Rooms = HotelResource.findARoom(checkIn, checkOut);
                    if(Rooms.isEmpty())
                        throw new IllegalArgumentException("There is no available room. Please select another date.");
                    Set<String> set = new HashSet<>();
                    System.out.printf("There are %d rooms available: %n", Rooms.size());
                    for(IRoom r: Rooms){
                        System.out.println(r.toString());
                        set.add(r.getRoomNumber());
                    }
                    System.out.println("Please type the your email.");
                    String email = scanner.nextLine();
                    System.out.println("Please type the the room number you want to make a reservation.(You can type -1 to cancel this action)");
                    String roomNumber = scanner.nextLine();
                    if(!set.contains(roomNumber))
                        throw new IllegalArgumentException("Please choose the available rooms listed above.");
                    IRoom room = HotelResource.getRoom(roomNumber);
                    Reservation ret = HotelResource.bookARoom(email, room, checkIn, checkOut);
                    System.out.println("Your reservation is: ");
                    System.out.println(ret);
                }catch(Exception ex){
                    System.out.println(ex.getMessage());
                }
            }
        }
    }
    public static void option2(Scanner scanner){
        String customerEmail = "";
        Collection<Reservation> reservations;
        while(true){
            System.out.println("Do you want to see your reservations? (Please type Yes/No)");
            String want = scanner.nextLine();
            if(want.equals("No"))
                break;
            else if(want.equals("Yes")){
                System.out.println("Please type your email.");
                customerEmail = scanner.nextLine();
                try{
                    reservations = HotelResource.getCustomersReservations(customerEmail);
                    System.out.println("Your reservations is below:");
                    for(Reservation r: reservations)
                        System.out.println(r);
                }catch(Exception ex){
                    System.out.println(ex.getMessage());
                    System.out.println("Please try again.");
                }
            }
        }
    }
    public static void option3(Scanner scanner){
        while(true){
            System.out.println("Do you want to create an account? (Please type Yes/No)");
            String want = scanner.nextLine();
            if(want.equals("No"))
                break;
            else if(want.equals("Yes")){
                System.out.println("Please enter your first name:");
                String firstName = scanner.nextLine();
                System.out.println("Please enter your last name:");
                String lastName = scanner.nextLine();
                System.out.println("Please enter your email:");
                String email = scanner.nextLine();
                try{
                    HotelResource.createACustomer(firstName, lastName, email);
                    System.out.println("Successfully creat an account.");
                }catch(Exception ex){
                    System.out.println(ex.getMessage());
                }
            }
        }
    }
    public static void intoMainMenu() {
        Scanner scanner = new Scanner(System.in);
        while(true) {
            System.out.println("Welcome to the Hotel Reservation Application");
            System.out.println("------------------------------");
            System.out.println("1. Find and reserve a room");
            System.out.println("2. See my reservations");
            System.out.println("3. Create an Account");
            System.out.println("4. Admin");
            System.out.println("5. Exit");
            System.out.println("------------------------------");
            System.out.println("Please select a number for the menu option");
            String opt = scanner.nextLine();
            if(opt.equals("1"))
                option1(scanner);
            else if(opt.equals("2"))
                option2(scanner);
            else if(opt.equals("3"))
                option3(scanner);
            else if(opt.equals("4"))
                AdminMenu.intoAdminMenu(scanner);
            else if(opt.equals("5"))
                break;
            else
                System.out.println("The option should be 1~5. Please try again.");
        }
    }
    public static void main(String[] args){
        MainMenu.intoMainMenu();
    }
}