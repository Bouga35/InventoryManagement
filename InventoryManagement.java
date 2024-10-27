import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Item {
    private final String name;
    private int quantity;
    private final double price;

    public Item(String name, int quantity, double price) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Item: " + name + ", Quantity: " + quantity + ", Price: " + price;
    }
}

public class InventoryManagement {
    private final List<Item> inventory;

    public InventoryManagement() {
        this.inventory = new ArrayList<>();
    }

    public void addItem(String name, int quantity, double price) {
        Item item = new Item(name, quantity, price);
        inventory.add(item);
        System.out.println("Added: " + item);
    }

    public void viewInventory() {
        System.out.println("Current Inventory:");
        for (Item item : inventory) {
            System.out.println(item);
        }
    }

    public void updateItemQuantity(String name, int newQuantity) {
        for (Item item : inventory) {
            if (item.getName().equalsIgnoreCase(name)) {
                item.setQuantity(newQuantity);
                System.out.println("Updated: " + item);
                return;
            }
        }
        System.out.println("Item not found: " + name);
    }

    public void searchItem(String name) {
        for (Item item : inventory) {
            if (item.getName().equalsIgnoreCase(name)) {
                System.out.println("Found: " + item);
                return;
            }
        }
        System.out.println("Item not found: " + name);
    }

    public void removeItem(String name) {
        for (Item item : inventory) {
            if (item.getName().equalsIgnoreCase(name)) {
                inventory.remove(item);
                System.out.println("Removed: " + item);
                return;
            }
        }
        System.out.println("Item not found: " + name);
    }

    public void saveInventory(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Item item : inventory) {
                writer.write(item.getName() + "," + item.getQuantity() + "," + item.getPrice());
                writer.newLine();
            }
            System.out.println("Inventory saved to " + filename);
        } catch (IOException e) {
            System.out.println("Error saving inventory: " + e.getMessage());
        }
    }

    public void loadInventory(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String name = parts[0];
                    int quantity = Integer.parseInt(parts[1]);
                    double price = Double.parseDouble(parts[2]);
                    addItem(name, quantity, price);
                }
            }
            System.out.println("Inventory loaded from " + filename);
        } catch (IOException e) {
            System.out.println("Error loading inventory: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Invalid quantity or price format in file.");
        }
    }

    private boolean login(String username, String password) {
        // Simple hardcoded username and password for demonstration
        return "admin".equals(username) && "password".equals(password);
    }

    public static void main(String[] args) {
        InventoryManagement inventoryManagement = new InventoryManagement();

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter username: ");
            String username = scanner.nextLine();
            System.out.print("Enter password: ");
            String password = scanner.nextLine();

            if (!inventoryManagement.login(username, password)) {
                System.out.println("Invalid login. Exiting.");
                return;
            }

            System.out.println("Welcome to Inventory Management!");

            boolean running = true;
            while (running) {
                System.out.println("\nChoose an action: add, view, update, search, remove, save, load, exit");
                String action = scanner.nextLine();

                switch (action.toLowerCase()) {
                    case "add" -> {
                        System.out.print("Enter item name: ");
                        String name = scanner.nextLine();
                        System.out.print("Enter quantity: ");
                        int quantity = Integer.parseInt(scanner.nextLine());
                        System.out.print("Enter price: ");
                        double price = Double.parseDouble(scanner.nextLine());
                        inventoryManagement.addItem(name, quantity, price);
                    }
                    case "view" -> inventoryManagement.viewInventory();
                    case "update" -> {
                        System.out.print("Enter item name to update: ");
                        String nameToUpdate = scanner.nextLine();
                        System.out.print("Enter new quantity: ");
                        int newQuantity = Integer.parseInt(scanner.nextLine());
                        inventoryManagement.updateItemQuantity(nameToUpdate, newQuantity);
                    }
                    case "search" -> {
                        System.out.print("Enter item name to search: ");
                        String nameToSearch = scanner.nextLine();
                        inventoryManagement.searchItem(nameToSearch);
                    }
                    case "remove" -> {
                        System.out.print("Enter item name to remove: ");
                        String nameToRemove = scanner.nextLine();
                        inventoryManagement.removeItem(nameToRemove);
                    }
                    case "save" -> {
                        System.out.print("Enter filename to save inventory: ");
                        String filenameToSave = scanner.nextLine();
                        inventoryManagement.saveInventory(filenameToSave);
                    }
                    case "load" -> {
                        System.out.print("Enter filename to load inventory: ");
                        String filenameToLoad = scanner.nextLine();
                        inventoryManagement.loadInventory(filenameToLoad);
                    }
                    case "exit" -> {
                        running = false;
                        System.out.println("Exiting Inventory Management. Goodbye!");
                    }
                    default -> System.out.println("Unknown action. Please try again.");
                }
            }
        }
    }
}
