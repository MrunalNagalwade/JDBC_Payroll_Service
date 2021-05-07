package sql;
import java.sql.*;
import static java.sql.DriverManager.getConnection;
public class DatabaseService {
    public static void main(String[] args) throws SQLException {
        String url = "jdbc:mysql://localhost:3306/payroll_service";
        String username = "root";
        String password = "MrunalNagalwade123";
        Connection connection = null;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = getConnection(url, username, password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from employee_payroll");
            while (resultSet.next())
            {
                System.out.print(resultSet.getInt("id"));
                System.out.println(resultSet.getString("name"));
            }
        }catch (ClassNotFoundException exception)
        {

        }finally {
            if (connection != null){
                connection.close();
            }
        }
    }
}
