package Simple_Data_Retrieval_Demo.demo;


import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws SQLException, IOException {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter username default (root): ");
        String user = scanner.nextLine().trim();
        user = user.equals("") ? "root" : user;

        System.out.println("Enter username default (root): ");
        String password = scanner.nextLine().trim();
        password = password.equals("") ? "12345" : password;

        Properties props = new Properties();
        String appConfigPath = Main.class.getClassLoader()
                .getResource("Simple_Data_Retrieval_Demo/resources/db.properties").getPath();
        props.load(new FileInputStream(appConfigPath));

//        props.setProperty("user", user);
//        props.setProperty("password", password);


        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver loaded successfully");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        try (
                Connection connection =
                        DriverManager
                                .getConnection("jdbc:mysql://localhost:3306/soft_uni?useSSL=false", props);
//                        .getConnection("jdbc:mysql://gabi:12345@localhost:3306/soft_uni?useSSL=false");
                PreparedStatement stmt = connection.prepareStatement("SELECT * FROM employees WHERE salary > ?")) {

            System.out.println("Connected successfully");

            System.out.println("Enter minimal salary (default 20000): ");
            String salaryStr = scanner.nextLine().trim();
            double salary = salaryStr.equals("") ? 20000 : Double.parseDouble(salaryStr);

            stmt.setDouble(1, salary);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                System.out.printf("| %-15.15s| %-15.15s | %-15.15s | %10.2f |%n",
                        rs.getString("first_name"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getDouble("salary")
                );
            }
        }
    }
}
