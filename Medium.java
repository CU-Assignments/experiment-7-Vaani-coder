import java.sql.*;
import java.util.Scanner;

public class ProductCRUD {
    private static final String URL = "jdbc:mysql://localhost:3306/Product";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Create Product");
            System.out.println("2. Read Products");
            System.out.println("3. Update Product");
            System.out.println("4. Delete Product");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> createProduct(scanner);
                case 2 -> readProducts();
                case 3 -> updateProduct(scanner);
                case 4 -> deleteProduct(scanner);
                case 5 -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Invalid choice! Please try again.");
            }
        }
    }

    private static void createProduct(Scanner scanner) {
        System.out.print("Enter Product Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Price: ");
        double price = scanner.nextDouble();
        System.out.print("Enter Quantity: ");
        int quantity = scanner.nextInt();

        String query = "INSERT INTO Product (ProductName, Price, Quantity) VALUES (?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query)) {

            connection.setAutoCommit(false); // Start transaction
            statement.setString(1, name);
            statement.setDouble(2, price);
            statement.setInt(3, quantity);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                connection.commit();
                System.out.println("Product created successfully!");
            } else {
                connection.rollback();
                System.out.println("Failed to create product.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void readProducts() {
        String query = "SELECT * FROM Product";

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            System.out.println("\nProduct List:");
            System.out.println("-------------");
            while (resultSet.next()) {
                int id = resultSet.getInt("ProductID");
                String name = resultSet.getString("ProductName");
                double price = resultSet.getDouble("Price");
                int quantity = resultSet.getInt("Quantity");

                System.out.printf("ID: %d, Name: %s, Price: %.2f, Quantity: %d%n", id, name, price, quantity);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void updateProduct(Scanner scanner) {
        System.out.print("Enter Product ID to update: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter new Price: ");
        double price = scanner.nextDouble();
        System.out.print("Enter new Quantity: ");
        int quantity = scanner.nextInt();

        String query = "UPDATE Product SET Price = ?, Quantity = ? WHERE ProductID = ?";

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query)) {

            connection.setAutoCommit(false); // Start transaction
            statement.setDouble(1, price);
            statement.setInt(2, quantity);
            statement.setInt(3, id);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                connection.commit();
                System.out.println("Product updated successfully!");
            } else {
                connection.rollback();
                System.out.println("Failed to update product.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void deleteProduct(Scanner scanner) {
        System.out.print("Enter Product ID to delete: ");
        int id = scanner.nextInt();

        String query = "DELETE FROM Product WHERE ProductID = ?";

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query)) {

            connection.setAutoCommit(false); // Start transaction
            statement.setInt(1, id);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                connection.commit();
                System.out.println("Product deleted successfully!");
            } else {
                connection.rollback();
                System.out.println("Failed to delete product.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
