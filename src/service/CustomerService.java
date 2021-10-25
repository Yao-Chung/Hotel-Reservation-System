package service;

import model.Customer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerService {
    private List<Customer> allCustomers;
    private Map<String, Customer> mp;
    private static CustomerService customerServiceInstance = null;

    private CustomerService(){
        allCustomers = new ArrayList<>();
        mp = new HashMap<>();
    }
    public static CustomerService getInstance(){
        if(customerServiceInstance == null)
            customerServiceInstance = new CustomerService();
        return customerServiceInstance;
    }
    public void addCustomer(String firstName, String lastName, String email) throws IllegalArgumentException, NullPointerException{
        Customer customer = new Customer(firstName, lastName, email);
        if(mp.containsKey(email))
            throw new IllegalArgumentException("This customer already exists.");
        allCustomers.add(customer);
        mp.put(email, customer);
    }
    public Customer getCustomer(String customerEmail){
        return mp.get(customerEmail);
    }
    public Collection<Customer> getAllCustomers(){
        return allCustomers;
    }
}
