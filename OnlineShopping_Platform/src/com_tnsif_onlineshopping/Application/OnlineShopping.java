package com_tnsif_onlineshopping.Application;

import com_tnsif_onlineshopping.entities.*;
import com_tnsif_onlineshopping.services.*;
import java.util.Scanner;

public class OnlineShopping {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Welcome to Online Shopping App");

        ProductService productService = ProductService.getInstance();
        AdminService adminService = AdminService.getInstance();
        CustomerService customerService = CustomerService.getInstance();
        OrderService orderService = OrderService.getInstance();

        seedSampleData(productService, adminService);

        boolean running = true;
        while (running) {
            System.out.println();
            System.out.println("1. Admin Menu");
            System.out.println("2. Customer Menu");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");

            int choice = readInt();
            switch (choice) {
                case 1 -> adminMenu(productService, adminService, orderService);
                case 2 -> customerMenu(productService, customerService, orderService);
                case 3 -> {
                    System.out.println("Exiting...");
                    running = false;
                }
                default -> System.out.println("Invalid option. Try again.");
            }
        }
        scanner.close();
    }

    private static void adminMenu(ProductService productService, AdminService adminService, OrderService orderService) {
        boolean adminRunning = true;
        while (adminRunning) {
            System.out.println();
            System.out.println("Admin Menu:");
            System.out.println("1. Add Product");
            System.out.println("2. Remove Product");
            System.out.println("3. View Products");
            System.out.println("4. Create Admin");
            System.out.println("5. View Admins");
            System.out.println("6. Update Order Status");
            System.out.println("7. View Orders");
            System.out.println("8. Return");
            System.out.print("Choose an option: ");

            int choice = readInt();
            switch (choice) {
                case 1 -> {
                    System.out.print("Enter Product ID: ");
                    int id = readInt();
                    System.out.print("Enter Product Name: ");
                    String name = readLine();
                    System.out.print("Enter Product Price: ");
                    double price = readDouble();
                    System.out.print("Enter Stock Quantity: ");
                    int qty = readInt();
                    Product p = new Product(id, name, price, qty);
                    boolean added = productService.addProduct(p);
                    System.out.println(added ? "Product added successfully!" : "Product ID already exists.");
                }
                case 2 -> {
                    System.out.print("Enter Product ID to remove: ");
                    int id = readInt();
                    boolean removed = productService.removeProduct(id);
                    System.out.println(removed ? "Product removed." : "Product not found.");
                }
                case 3 -> productService.viewProducts();
                case 4 -> {
                    System.out.print("Enter Admin ID: ");
                    int id = readInt();
                    System.out.print("Enter Username: ");
                    String username = readLine();
                    System.out.print("Enter Email: ");
                    String email = readLine();
                    Admin admin = new Admin(id, username, email);
                    boolean created = adminService.createAdmin(admin);
                    System.out.println(created ? "Admin created successfully!" : "Admin ID already exists.");
                }
                case 5 -> adminService.viewAdmins();
                case 6 -> {
                    System.out.print("Enter Order ID: ");
                    int orderId = readInt();
                    System.out.print("Enter new status (Pending/Completed/Delivered/Cancelled): ");
                    String status = readLine();
                    boolean ok = orderService.updateOrderStatus(orderId, status);
                    System.out.println(ok ? "Order status updated." : "Order not found or invalid status.");
                }
                case 7 -> orderService.viewAllOrders();
                case 8 -> {
                    System.out.println("Exiting Admin...");
                    adminRunning = false;
                }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private static void customerMenu(ProductService productService, CustomerService customerService, OrderService orderService) {
        boolean custRunning = true;
        while (custRunning) {
            System.out.println();
            System.out.println("Customer Menu:");
            System.out.println("1. Create Customer");
            System.out.println("2. View Customers");
            System.out.println("3. Place Order");
            System.out.println("4. View Orders");
            System.out.println("5. View Products");
            System.out.println("6. Return");
            System.out.print("Choose an option: ");

            int choice = readInt();
            switch (choice) {
                case 1 -> {
                    System.out.print("Enter User ID: ");
                    int id = readInt();
                    System.out.print("Enter Username: ");
                    String username = readLine();
                    System.out.print("Enter Email: ");
                    String email = readLine();
                    System.out.print("Enter Address: ");
                    String address = readLine();
                    Customer c = new Customer(id, username, email, address);
                    boolean created = customerService.createCustomer(c);
                    System.out.println(created ? "Customer created successfully!" : "Customer ID already exists.");
                }
                case 2 -> customerService.viewCustomers();
                case 3 -> {
                    System.out.print("Enter Customer ID: ");
                    int customerId = readInt();
                    Customer customer = customerService.getCustomerById(customerId);
                    if (customer == null) {
                        System.out.println("Customer not found.");
                        break;
                    }
                    ShoppingCart cart = customer.getShoppingCart();
                    cart.clear();
                    while (true) {
                        productService.viewProducts();
                        System.out.print("Enter Product ID to add to order (or -1 to complete): ");
                        int pid = readInt();
                        if (pid == -1) break;
                        Product p = productService.getProductById(pid);
                        if (p == null) {
                            System.out.println("Product not found.");
                            continue;
                        }
                        System.out.print("Enter quantity: ");
                        int q = readInt();
                        if (q <= 0) {
                            System.out.println("Quantity should be > 0.");
                            continue;
                        }
                        if (q > p.getStockQuantity()) {
                            System.out.println("Not enough stock. Available: " + p.getStockQuantity());
                            continue;
                        }
                        cart.addItem(p, q);
                        System.out.println("Added to cart.");
                    }

                    if (cart.isEmpty()) {
                        System.out.println("Cart empty. No order placed.");
                    } else {
                        boolean placed = orderService.placeOrder(customer);
                        System.out.println(placed ? "Order placed successfully!" : "Order could not be placed.");
                    }
                }
                case 4 -> {
                    System.out.print("Enter Customer ID: ");
                    int custId = readInt();
                    orderService.viewOrdersForCustomer(custId);
                }
                case 5 -> productService.viewProducts();
                case 6 -> {
                    System.out.println("Exiting Customer Menu...");
                    custRunning = false;
                }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    // --- input helpers ---
    private static int readInt() {
        while (true) {
            try {
                String line = scanner.nextLine().trim();
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.print("Please enter a valid integer: ");
            }
        }
    }

    private static double readDouble() {
        while (true) {
            try {
                String line = scanner.nextLine().trim();
                return Double.parseDouble(line);
            } catch (NumberFormatException e) {
                System.out.print("Please enter a valid number: ");
            }
        }
    }

    private static String readLine() {
        return scanner.nextLine().trim();
    }

    private static void seedSampleData(ProductService productService, AdminService adminService) {
        productService.addProduct(new Product(101, "T-Shirt", 560.0, 100));
        productService.addProduct(new Product(102, "Trouser", 1400.0, 50));
        adminService.createAdmin(new Admin(1, "superadmin", "admin@shop.com"));
    }
}
