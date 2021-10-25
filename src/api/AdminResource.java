package api;

import model.*;
import service.*;

import java.util.Collection;
import java.util.List;

public class AdminResource {
    private static ReservationService reservationService = ReservationService.getInstance();
    private static CustomerService customerService = CustomerService.getInstance();
    public static Customer getCustomer(String email) throws IllegalArgumentException{
        Customer customer = customerService.getCustomer(email);
        if(customer == null)
            throw new IllegalArgumentException("This customer has not registered");
        return customer;
    }
    public static void addRoom(Room room) throws IllegalArgumentException, NullPointerException{
        reservationService.addRoom(room);
    }
    public static List<IRoom> getAllRooms() {
        return reservationService.getAllRooms();
    }
    public static Collection<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }
    public static void displayAllReservations() {
        reservationService.printAllReservation();
    }
}
