package com_tnsif_onlineshopping.services;

import com_tnsif_onlineshopping.entities.*;
import java.util.HashMap;
import java.util.Map;

public class AdminService {
    private static final AdminService INSTANCE = new AdminService();
    private final Map<Integer, Admin> admins = new HashMap<>();

    private AdminService() {}

    public static AdminService getInstance() { return INSTANCE; }

    public boolean createAdmin(Admin a) {
        if (admins.containsKey(a.getUserId())) return false;
        admins.put(a.getUserId(), a);
        return true;
    }

    public void viewAdmins() {
        if (admins.isEmpty()) {
            System.out.println("No admins present.");
            return;
        }
        System.out.println("Admins:");
        for (Admin a : admins.values()) {
            System.out.println(a);
        }
    }
    
}
