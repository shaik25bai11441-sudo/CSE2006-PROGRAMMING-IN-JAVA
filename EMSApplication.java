
import java.sql.*;
import java.io.*;
import java.util.Scanner;


    class EmployeeNotFoundException extends Exception {
        public EmployeeNotFoundException(String message) {
            super(message);
        }
    }


    abstract class Employee {
        protected int id;
        protected String name;
        protected double baseSalary;

        public Employee(int id, String name, double baseSalary) {
            this.id = id;
            this.name = name;
            this.baseSalary = baseSalary;
        }

        public abstract double calculateMonthlyPay();

        public int getId() { return id; }
        public String getName() { return name; }
        public double getBaseSalary() { return baseSalary; }
    }

    class FullTimeEmployee extends Employee {
        private double bonus;

        public FullTimeEmployee(int id, String name, double baseSalary, double bonus) {
            super(id, name, baseSalary);
            this.bonus = bonus;
        }

        @Override
        public double calculateMonthlyPay() {
            return getBaseSalary() + bonus;
        }
    }

    class DatabaseManager {
        private static final String URL = "jdbc:sqlite:ems.db";

        public static void initializeDatabase() {
            String sql = "CREATE TABLE IF NOT EXISTS employees ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "name TEXT NOT NULL, "
                    + "salary REAL NOT NULL);";
            try (Connection conn = DriverManager.getConnection(URL);
                 Statement stmt = conn.createStatement()) {
                stmt.execute(sql);
            } catch (SQLException e) {
                System.err.println("Database initialization failed: " + e.getMessage());
            }
        }

        public static void insertEmployee(String name, double salary) {
            String sql = "INSERT INTO employees(name, salary) VALUES(?, ?)";
            try (Connection conn = DriverManager.getConnection(URL);
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, name);
                pstmt.setDouble(2, salary);
                pstmt.executeUpdate();
                System.out.println("Success: Employee record saved to database.");
            } catch (SQLException e) {
                System.err.println("Insertion failed: " + e.getMessage());
            }
        }
    }


    class ReportGenerator implements Runnable {
        private String dataToExport;

        public ReportGenerator(String dataToExport) {
            this.dataToExport = dataToExport;
        }

        @Override
        public void run() {
            System.out.println("\n[Background Thread] Initiating report export...");
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("employee_report.txt"))) {
                Thread.sleep(2000); // Simulate processing time
                writer.write("--- CONFIDENTIAL EMPLOYEE REPORT ---\n");
                writer.write(dataToExport);
                System.out.println("\n[Background Thread] Export complete. Saved as 'employee_report.txt'.");
            } catch (IOException | InterruptedException e) {
                System.err.println("Report generation encountered an error: " + e.getMessage());
            }
        }
    }


    public class EMSApplication {
        public static void main(String[] args) {
            DatabaseManager.initializeDatabase();
            Scanner scanner = new Scanner(System.in);
            boolean isRunning = true;

            System.out.println("---------------------------------------");
            System.out.println("  ENTERPRISE EMPLOYEE MANAGER V1.0");
            System.out.println("---------------------------------------");

            while (isRunning) {
                System.out.println("\nMain Menu:");
                System.out.println("1. Register New Employee");
                System.out.println("2. Run System Report (Async Task)");
                System.out.println("3. Terminate Session");
                System.out.print("Enter your selection (1-3): ");

                String choice = scanner.nextLine();

                switch (choice) {
                    case "1":
                        System.out.print("Enter employee full name: ");
                        String empName = scanner.nextLine();
                        System.out.print("Enter base salary (e.g., 60000.50): ");
                        try {
                            double empSalary = Double.parseDouble(scanner.nextLine());
                            DatabaseManager.insertEmployee(empName, empSalary);
                        } catch (NumberFormatException e) {
                            System.out.println("Error: Salary must be a valid number.");
                        }
                        break;
                    case "2":
                        String dummyDatabaseExtract = "ID: 101 | Name: Default User | Salary: $75,000\n";
                        Thread exportThread = new Thread(new ReportGenerator(dummyDatabaseExtract));
                        exportThread.start();
                        break;
                    case "3":
                        isRunning = false;
                        System.out.println("Safely closing database connections. Goodbye!");
                        break;
                    default:
                        System.out.println("Unrecognized command. Please enter 1, 2, or 3.");
                }
            }
            scanner.close();
        }
    }

