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
       // System.out.println("Processing Thread : " +Thread.currentThread().getName() + "Connecting to database with id :"+ connectionCounter);
        System.out.println("Connecting to database:" + url);
        connection = DriverManager.getConnection(url, username, password);
        System.out.println("Processing Thread : "+Thread.currentThread().getName() + " Id:" + connectionCounter +"Connection is successful!" + connection);
        System.out.println("Connection is successful!" + connection);
        return connection;
    }
    public List<EmployeePayrollData> readData()
    {
        String sql = "SELECT * FROM employee_payroll; ";
        List<EmployeePayrollData> employeePayrollDataList = new ArrayList<>();
        try
        {
            Connection connection = this.getConnection();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            while(result.next())

            {
                int id  = result.getInt("id");
                String name = result.getString("name");
                double salary = result.getDouble("salary");
                LocalDate startDate = result.getDate("start").toLocalDate();
                employeePayrollDataList.add(new EmployeePayrollData(id, name, salary, startDate));
            }
            connection.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return employeePayrollDataList;
    }
    /*public List<EmployeePayrollData> getEmployeePayrollDataUsingDB(String sql)
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
    }*/
    private List<EmployeePayrollData> getEmployeePayrollData(ResultSet resultSet)
    {
        List<EmployeePayrollData> employeePayrollList = new ArrayList<>();
        try {
            while (resultSet.next())
            {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                double salary = resultSet.getDouble("salary");
                LocalDate startDate = resultSet.getDate("start").toLocalDate();
                employeePayrollList.add(new EmployeePayrollData(id, name, salary, startDate));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employeePayrollList;
    }
    int updateEmployeeData(String name, Double salary)
    {
        return this.updateEmployeeDataUsingStatement(name, salary);
    }

    private int updateEmployeeDataUsingStatement(String name, Double salary)
    {
        String sql = String.format("update employee_payroll set salary = %.2f where name = '%s';", salary, name);
        try (Connection connection = this.getConnection())
        {
            Statement statement = connection.createStatement();
            return statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    public List<EmployeePayrollData> getEmployeePayrollData(String name)
    {
        List<EmployeePayrollData> employeePayrollList = null;
        if (this.employeePayrollDataStatement == null)
            this.prepareStatementForEmployeeData();
        try
        {
            employeePayrollDataStatement.setString(1, name);
            ResultSet resultSet = employeePayrollDataStatement.executeQuery();
            employeePayrollList = this.getEmployeePayrollData(resultSet);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return employeePayrollList;
    }
    private void prepareStatementForEmployeeData()
    {
        try
        {
            Connection connection = this.getConnection();
            String sql = "SELECT * FROM employee_payroll WHERE name = ?";
            employeePayrollDataStatement = connection.prepareStatement(sql);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
}
