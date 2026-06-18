package com.scandine.config;

import com.scandine.entity.CafeteriaTable;
import com.scandine.entity.MenuItem;
import com.scandine.entity.Staff;
import com.scandine.enums.Role;
import com.scandine.repository.CafeteriaTableRepository;
import com.scandine.repository.MenuItemRepository;
import com.scandine.repository.StaffRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final CafeteriaTableRepository tableRepository;
    private final MenuItemRepository menuItemRepository;
    private final StaffRepository staffRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (tableRepository.count() == 0) {
            tableRepository.saveAll(List.of(
                    new CafeteriaTable(null, 1),
                    new CafeteriaTable(null, 2),
                    new CafeteriaTable(null, 3),
                    new CafeteriaTable(null, 4),
                    new CafeteriaTable(null, 5),
                    new CafeteriaTable(null, 6),
                    new CafeteriaTable(null, 7),
                    new CafeteriaTable(null, 8),
                    new CafeteriaTable(null, 9),
                    new CafeteriaTable(null, 10)
            ));
            System.out.println("✅ Tables seeded");
        }

        if (menuItemRepository.count() == 0) {
            menuItemRepository.saveAll(List.of(
                    new MenuItem(null, "Cold Coffee",        80.00,  "Beverages", true),
                    new MenuItem(null, "Hot Coffee",         60.00,  "Beverages", true),
                    new MenuItem(null, "Cold Brew",          120.00, "Beverages", true),
                    new MenuItem(null, "Masala Chai",        40.00,  "Beverages", true),
                    new MenuItem(null, "Fresh Lime Soda",    60.00,  "Beverages", true),
                    new MenuItem(null, "Mango Shake",        100.00, "Beverages", true),
                    new MenuItem(null, "Oreo Shake",         120.00, "Beverages", true),
                    new MenuItem(null, "Virgin Mojito",      90.00,  "Beverages", true),
                    new MenuItem(null, "Veg Burger",         120.00, "Snacks",    true),
                    new MenuItem(null, "Chicken Burger",     150.00, "Snacks",    true),
                    new MenuItem(null, "French Fries",       80.00,  "Snacks",    true),
                    new MenuItem(null, "Peri Peri Fries",    100.00, "Snacks",    true),
                    new MenuItem(null, "Garlic Bread",       90.00,  "Snacks",    true),
                    new MenuItem(null, "Cheesy Garlic Bread",110.00, "Snacks",    true),
                    new MenuItem(null, "Veg Sandwich",       100.00, "Snacks",    true),
                    new MenuItem(null, "Grilled Sandwich",   130.00, "Snacks",    true),
                    new MenuItem(null, "Samosa",             30.00,  "Snacks",    true),
                    new MenuItem(null, "Spring Rolls",       120.00, "Snacks",    true),
                    new MenuItem(null, "Margherita Pizza",   220.00, "Mains",     true),
                    new MenuItem(null, "Paneer Pizza",       260.00, "Mains",     true),
                    new MenuItem(null, "Chicken Pizza",      300.00, "Mains",     true),
                    new MenuItem(null, "Veg Pasta",          180.00, "Mains",     true),
                    new MenuItem(null, "Chicken Pasta",      220.00, "Mains",     true),
                    new MenuItem(null, "Paneer Wrap",        160.00, "Mains",     true),
                    new MenuItem(null, "Chicken Wrap",       190.00, "Mains",     true),
                    new MenuItem(null, "Chocolate Brownie",  120.00, "Desserts",  true),
                    new MenuItem(null, "Gulab Jamun",        80.00,  "Desserts",  true),
                    new MenuItem(null, "Ice Cream Sundae",   150.00, "Desserts",  true),
                    new MenuItem(null, "Waffle",             180.00, "Desserts",  true),
                    new MenuItem(null, "Cheesecake",         200.00, "Desserts",  true)
            ));
            System.out.println("✅ Menu items seeded");
        }
        if (staffRepository.count() == 0) {
            staffRepository.saveAll(List.of(
                    new Staff(null, "kitchen1",
                            passwordEncoder.encode("kitchen123"),
                            Role.KITCHEN),
                    new Staff(null, "manager1",
                            passwordEncoder.encode("manager123"),
                            Role.MANAGER)
            ));
            System.out.println("✅ Staff seeded");
        }
    }
}
