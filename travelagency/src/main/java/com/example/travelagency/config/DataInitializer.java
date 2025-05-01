package com.example.travelagency.config;

import com.example.travelagency.entities.Country;
import com.example.travelagency.entities.Division;
import com.example.travelagency.entities.Customer;
import com.example.travelagency.dao.CountryRepository;
import com.example.travelagency.dao.DivisionRepository;
import com.example.travelagency.dao.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;
import java.util.List;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(CustomerRepository customerRepository, DivisionRepository divisionRepository, CountryRepository countryRepository) {
        return args -> {
            if (customerRepository.count() > 1) {
                System.out.println("Customers already exist in the database.");
            } else {
                try {
                    // Ensure a Country exists (required for Division)
                    Country country = countryRepository.findById(1L)
                            .orElseGet(() -> {
                                Country newCountry = new Country();
                                newCountry.setCountry_name("USA");
                                newCountry.setCreate_date(new Date());
                                newCountry.setLast_update(new Date());
                                return countryRepository.save(newCountry);
                            });

                    // Ensure a Division exists (required for Customer)
                    Division division = divisionRepository.findById(1L)
                            .orElseGet(() -> {
                                Division newDivision = new Division();
                                newDivision.setDivision_name("California");
                                newDivision.setCreate_date(new Date());
                                newDivision.setCountry(country);
                                return divisionRepository.save(newDivision);
                            });

                    // Create five sample customers
                    List<Customer> sampleCustomers = List.of(
                            new Customer("Mary", "Allen", "123 Main St", "12345", "999-555-0101", new Date(), new Date(), division),
                            new Customer("Jane", "Smith", "456 Oak Ave", "67890", "999-555-0102", new Date(), new Date(), division),
                            new Customer("Alice", "Johnson", "789 Pine Rd", "11223", "999-555-0103", new Date(), new Date(), division),
                            new Customer("Bob", "Williams", "321 Elm St", "44556", "999-555-0104", new Date(), new Date(), division),
                            new Customer("Emily", "Brown", "654 Birch Ln", "77889", "999-555-0105", new Date(), new Date(), division)
                    );

                    // Save the customers to the database
                    customerRepository.saveAll(sampleCustomers);
                    System.out.println("Inserted 5 sample customers into the database.");
                } catch (Exception e) {
                    System.err.println("Error initializing data: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        };
    }
}