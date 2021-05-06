package sql;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmployeePayrollDBService {
    private int connectionCounter = 0;
    private static PreparedStatement employeePayrollDataStatement;
    private static EmployeePayrollDBService employeePayrollDBService;
    private PreparedStatement updateEmployeeSalary;
    private PreparedStatement prepareStatement;
    private PreparedStatement employeeSalary;
    EmployeePayrollDBService()
    {

    }
    public static EmployeePayrollDBService getInstance()
    {
        if (employeePayrollDBService == null)
            employeePayrollDBService = new EmployeePayrollDBService();
        return employeePayrollDBService;
    }
    private synchronized Connection getConnection() throws SQLException
    {
        connectionCounter++;
        String url = "jdbc:mysql://localhost:3306/payroll_service";
        String username = "root";
        String password = "MrunalNagalwade123";
        Connection connection;
        System.out.println("Processing Thread : " +Thread.currentThread().getName() + "Connecting to database with id :"+ connectionCounter);
        System.out.println("Connecting to database:" + url);
        connection = DriverManager.getConnection(url, username, password);
        System.out.println("Processing Thread : "+Thread.currentThread().getName() + " Id:" + connectionCounter +"Connection is successful!" + connection);
        System.out.println("Connection is successful!" + connection);
        return connection;
    }
    public List<EmployeePayrollData> readData()
    {
        String sql = "SELECT * FROM  employee_payroll; ";
        return this.getEmployeePayrollDataUsingDB(sql);
    }
    private List<EmployeePayrollData> getEmployeePayrollDataUsingDB(String sql)
    {
        List<EmployeePayrollData> employeePayrollList = new ArrayList<>();
        try (Connection connection = this.getConnection())
        {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            employeePayrollList = this.getEmployeePayrollData(resultSet);

        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return employeePayrollList;
    }
    private List<EmployeePayrollData> getEmployeePayrollData(ResultSet resultSet)
    {
        List<EmployeePayrollData> employeePayrollList = new ArrayList<>();
        try
        {
            while (resultSet.next())
            {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                System.out.println(resultSet.getString("name"));
                double  basic_pay = resultSet.getDouble("basic_pay");
                LocalDate start = resultSet.getDate("start").toLocalDate();
                employeePayrollList.add(new EmployeePayrollData(id, name,  basic_pay, start));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employeePayrollList;
    }


}
