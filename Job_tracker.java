import java.sql.*;
import java.util.Scanner;

public class Job_tracker {

    private static final String URL =
            "jdbc:mysql://localhost:3306/job_tracker";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "archit";

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        try {
            // Load JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver loaded successfully!!!");

            // Create Connection
            Connection con =
                    DriverManager.getConnection(URL, USERNAME, PASSWORD);

            System.out.println("Database connected successfully!!!");

            while (true) {

                System.out.println("\n=================================");
                System.out.println("       JOB APPLICATION TRACKER");
                System.out.println("=================================");
                System.out.println("1. Add Job Application");
                System.out.println("2. View All Applications");
                System.out.println("3. Search by Company");
                System.out.println("4. Update Application Status");
                System.out.println("5. Delete Application");
                System.out.println("6. View Applications by Status");
                System.out.println("7. View Interview Applications");
                System.out.println("8. Application Statistics");
                System.out.println("9. Exit");
                System.out.println("=================================");

                System.out.print("Enter your choice: ");

                int choice;

                try {
                    choice = Integer.parseInt(sc.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("Please enter a valid number!");
                    continue;
                }

                switch (choice) {

                    case 1:
                        addApplication(con, sc);
                        break;

                    case 2:
                        viewAllApplications(con);
                        break;

                    case 3:
                        searchByCompany(con, sc);
                        break;

                    case 4:
                        updateStatus(con, sc);
                        break;

                    case 5:
                        deleteApplication(con, sc);
                        break;

                    case 6:
                        viewByStatus(con, sc);
                        break;

                    case 7:
                        viewInterviewApplications(con);
                        break;

                    case 8:
                        applicationStatistics(con);
                        break;

                    case 9:
                        System.out.println("Thank you for using Job Application Tracker!");
                        con.close();
                        sc.close();
                        return;

                    default:
                        System.out.println("Invalid choice!");
                }
            }

        } catch (ClassNotFoundException e) {

            System.out.println("JDBC Driver not found!");
            System.out.println(e.getMessage());

        } catch (SQLException e) {

            System.out.println("Database Error!");
            System.out.println(e.getMessage());
        }
    }


    // ==========================================
    // 1. ADD JOB APPLICATION
    // ==========================================

    public static void addApplication(Connection con, Scanner sc) {

        String query =
                "INSERT INTO applications " +
                        "(company_name, job_role, status, applied_date, interview_date, notes) " +
                        "VALUES (?, ?, ?, ?, ?, ?)";

        try {

            System.out.println("\n===== ADD JOB APPLICATION =====");

            System.out.print("Enter company name: ");
            String companyName = sc.nextLine();

            System.out.print("Enter job role: ");
            String jobRole = sc.nextLine();

            System.out.println("\nStatus Options:");
            System.out.println("1. Applied");
            System.out.println("2. Shortlisted");
            System.out.println("3. Online Assessment");
            System.out.println("4. Interview");
            System.out.println("5. Selected");
            System.out.println("6. Rejected");

            System.out.print("Enter status: ");
            int statusChoice = Integer.parseInt(sc.nextLine());

            String status = getStatus(statusChoice);

            if (status == null) {
                System.out.println("Invalid status!");
                return;
            }

            System.out.print("Enter applied date (YYYY-MM-DD): ");
            String appliedDate = sc.nextLine();

            System.out.print("Enter interview date (YYYY-MM-DD) or leave blank: ");
            String interviewDate = sc.nextLine();

            System.out.print("Enter notes: ");
            String notes = sc.nextLine();

            PreparedStatement pstmt = con.prepareStatement(query);

            pstmt.setString(1, companyName);
            pstmt.setString(2, jobRole);
            pstmt.setString(3, status);
            pstmt.setDate(4, Date.valueOf(appliedDate));

            if (interviewDate.isEmpty()) {
                pstmt.setNull(5, Types.DATE);
            } else {
                pstmt.setDate(5, Date.valueOf(interviewDate));
            }

            pstmt.setString(6, notes);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Application added successfully!!!");
            }

            pstmt.close();

        } catch (SQLException e) {

            System.out.println("Error while adding application!");
            System.out.println(e.getMessage());

        } catch (IllegalArgumentException e) {

            System.out.println("Invalid date format!");
            System.out.println("Use YYYY-MM-DD format.");
        }
    }


    // ==========================================
    // 2. VIEW ALL APPLICATIONS
    // ==========================================

    public static void viewAllApplications(Connection con) {

        String query = "SELECT * FROM applications ORDER BY id DESC";

        try {

            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery(query);

            System.out.println("\n================ ALL APPLICATIONS ================");

            boolean found = false;

            while (rs.next()) {

                found = true;

                displayApplication(rs);
            }

            if (!found) {
                System.out.println("No applications found.");
            }

            rs.close();
            stmt.close();

        } catch (SQLException e) {

            System.out.println("Error while fetching applications!");
            System.out.println(e.getMessage());
        }
    }


    // ==========================================
    // 3. SEARCH BY COMPANY
    // ==========================================

    public static void searchByCompany(Connection con, Scanner sc) {

        String query =
                "SELECT * FROM applications WHERE company_name LIKE ?";

        try {

            System.out.print("Enter company name to search: ");
            String company = sc.nextLine();

            PreparedStatement pstmt = con.prepareStatement(query);

            pstmt.setString(1, "%" + company + "%");

            ResultSet rs = pstmt.executeQuery();

            boolean found = false;

            System.out.println("\n========== SEARCH RESULT ==========");

            while (rs.next()) {

                found = true;

                displayApplication(rs);
            }

            if (!found) {
                System.out.println("No application found for this company.");
            }

            rs.close();
            pstmt.close();

        } catch (SQLException e) {

            System.out.println("Search failed!");
            System.out.println(e.getMessage());
        }
    }


    // ==========================================
    // 4. UPDATE APPLICATION STATUS
    // ==========================================

    public static void updateStatus(Connection con, Scanner sc) {

        String query =
                "UPDATE applications SET status = ? WHERE id = ?";

        try {

            System.out.print("Enter application ID: ");
            int id = Integer.parseInt(sc.nextLine());

            System.out.println("\nStatus Options:");
            System.out.println("1. Applied");
            System.out.println("2. Shortlisted");
            System.out.println("3. Online Assessment");
            System.out.println("4. Interview");
            System.out.println("5. Selected");
            System.out.println("6. Rejected");

            System.out.print("Enter new status: ");
            int statusChoice = Integer.parseInt(sc.nextLine());

            String status = getStatus(statusChoice);

            if (status == null) {
                System.out.println("Invalid status!");
                return;
            }

            PreparedStatement pstmt = con.prepareStatement(query);

            pstmt.setString(1, status);
            pstmt.setInt(2, id);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Application status updated successfully!!!");
            } else {
                System.out.println("Application ID not found.");
            }

            pstmt.close();

        } catch (SQLException e) {

            System.out.println("Update failed!");
            System.out.println(e.getMessage());

        } catch (NumberFormatException e) {

            System.out.println("Please enter valid numbers!");
        }
    }


    // ==========================================
    // 5. DELETE APPLICATION
    // ==========================================

    public static void deleteApplication(Connection con, Scanner sc) {

        String query =
                "DELETE FROM applications WHERE id = ?";

        try {

            System.out.print("Enter application ID to delete: ");
            int id = Integer.parseInt(sc.nextLine());

            PreparedStatement pstmt = con.prepareStatement(query);

            pstmt.setInt(1, id);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Application deleted successfully!!!");
            } else {
                System.out.println("Application ID not found.");
            }

            pstmt.close();

        } catch (SQLException e) {

            System.out.println("Delete failed!");
            System.out.println(e.getMessage());

        } catch (NumberFormatException e) {

            System.out.println("Please enter a valid ID!");
        }
    }


    // ==========================================
    // 6. VIEW APPLICATIONS BY STATUS
    // ==========================================

    public static void viewByStatus(Connection con, Scanner sc) {

        String query =
                "SELECT * FROM applications WHERE status = ?";

        try {

            System.out.println("\nStatus Options:");
            System.out.println("1. Applied");
            System.out.println("2. Shortlisted");
            System.out.println("3. Online Assessment");
            System.out.println("4. Interview");
            System.out.println("5. Selected");
            System.out.println("6. Rejected");

            System.out.print("Enter status: ");

            int statusChoice = Integer.parseInt(sc.nextLine());

            String status = getStatus(statusChoice);

            if (status == null) {
                System.out.println("Invalid status!");
                return;
            }

            PreparedStatement pstmt = con.prepareStatement(query);

            pstmt.setString(1, status);

            ResultSet rs = pstmt.executeQuery();

            boolean found = false;

            System.out.println("\n========== " + status + " APPLICATIONS ==========");

            while (rs.next()) {

                found = true;

                displayApplication(rs);
            }

            if (!found) {
                System.out.println("No applications found.");
            }

            rs.close();
            pstmt.close();

        } catch (SQLException e) {

            System.out.println("Error!");
            System.out.println(e.getMessage());
        }
    }


    // ==========================================
    // 7. VIEW INTERVIEW APPLICATIONS
    // ==========================================

    public static void viewInterviewApplications(Connection con) {

        String query =
                "SELECT * FROM applications " +
                        "WHERE interview_date IS NOT NULL " +
                        "ORDER BY interview_date";

        try {

            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery(query);

            boolean found = false;

            System.out.println("\n========== INTERVIEW APPLICATIONS ==========");

            while (rs.next()) {

                found = true;

                displayApplication(rs);
            }

            if (!found) {
                System.out.println("No interview applications found.");
            }

            rs.close();
            stmt.close();

        } catch (SQLException e) {

            System.out.println("Error!");
            System.out.println(e.getMessage());
        }
    }


    // ==========================================
    // 8. APPLICATION STATISTICS
    // ==========================================

    public static void applicationStatistics(Connection con) {

        String query =
                "SELECT status, COUNT(*) AS total " +
                        "FROM applications " +
                        "GROUP BY status";

        try {

            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery(query);

            System.out.println("\n========== APPLICATION STATISTICS ==========");

            int totalApplications = 0;

            while (rs.next()) {

                String status = rs.getString("status");
                int total = rs.getInt("total");

                System.out.println(status + " : " + total);

                totalApplications += total;
            }

            System.out.println("--------------------------------------------");
            System.out.println("Total Applications : " + totalApplications);

            rs.close();
            stmt.close();

        } catch (SQLException e) {

            System.out.println("Error while calculating statistics!");
            System.out.println(e.getMessage());
        }
    }


    // ==========================================
    // DISPLAY APPLICATION
    // ==========================================

    public static void displayApplication(ResultSet rs)
            throws SQLException {

        System.out.println("--------------------------------------------");

        System.out.println("ID              : " + rs.getInt("id"));
        System.out.println("Company         : " + rs.getString("company_name"));
        System.out.println("Job Role        : " + rs.getString("job_role"));
        System.out.println("Status          : " + rs.getString("status"));
        System.out.println("Applied Date    : " + rs.getDate("applied_date"));
        System.out.println("Interview Date  : " + rs.getDate("interview_date"));
        System.out.println("Notes           : " + rs.getString("notes"));

        System.out.println("--------------------------------------------");
    }


    // ==========================================
    // STATUS METHOD
    // ==========================================

    public static String getStatus(int choice) {

        switch (choice) {

            case 1:
                return "Applied";

            case 2:
                return "Shortlisted";

            case 3:
                return "Online Assessment";

            case 4:
                return "Interview";

            case 5:
                return "Selected";

            case 6:
                return "Rejected";

            default:
                return null;
        }
    }
}
