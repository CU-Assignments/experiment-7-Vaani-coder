import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class EmployeeDataFetcher {
    public static void main(String[] args) {
        // Database credentials
        String url = "jdbc:mysql://localhost:3306/Employee";
        String username = "root";
        String password = "root";

        // SQL query to fetch data
        String query = "SELECT EmpID, Name, Salary FROM Employee";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            System.out.println("Employee Records:");
            System.out.println("-----------------");

            // Process and display the result set
            while (resultSet.next()) {
                int empID = resultSet.getInt("EmpID");
                String name = resultSet.getString("Name");
                double salary = resultSet.getDouble("Salary");

                System.out.printf("EmpID: %d, Name: %s, Salary: %.2f%n", empID, name, salary);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
