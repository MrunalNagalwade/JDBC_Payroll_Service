package sql;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class DatabaseServiceTest {
    @Test
    public  void givenEmployeePayrollDB_WhenRetrieved_ShouldMatchEmployeeCount()
    {
        EmployeePayrollService employeePayrollService = new EmployeePayrollService();
        List<EmployeePayrollData> employeePayrollData = employeePayrollService.readEmployeePayrollData(EmployeePayrollService.IOService.DB_IO);
        Assertions.assertEquals(4, employeePayrollData.size());
    }
    @Test
    public  void  givenNewSalaryForEmployee_WhenUpdated_ShouldMatch()
    {
        EmployeePayrollService employeePayrollService = new EmployeePayrollService();
        List<EmployeePayrollData> employeePayrollData = employeePayrollService.readEmployeePayrollData(EmployeePayrollService.IOService.DB_IO);
        employeePayrollService.updateEmployeeSalary("terisa", 3000000.00);
        boolean result = employeePayrollService.checkEmployeePayrollInSyncWithDB("terisa");
        Assertions.assertTrue(result);
    }
}
