package sql;

import java.util.List;

public class EmployeePayrollService {
    public enum IOService {DB_IO, REST_IO}
    public EmployeePayrollDBService employeePayrollDBService;
    private List<EmployeePayrollData> employeePayrollList;
    private List<EmployeePayrollData> employeePayrollDataList;
    public List<EmployeePayrollData> readEmployeePayrollData(IOService ioService)
    {
        if(ioService.equals(IOService.DB_IO))
            this.employeePayrollDataList = new EmployeePayrollDBService().readData();
        return this.employeePayrollDataList;
    }
    private EmployeePayrollData getEmployeePayrollData(String name)
    {
        employeePayrollList = this.readEmployeePayrollData(EmployeePayrollService.IOService.DB_IO);
        return this.employeePayrollList.stream().filter(employeePayrollDataItem -> employeePayrollDataItem.name.equals(name)).findFirst().orElse(null);
    }
    public void updateEmployeeSalary(String name, Double salary)
    {
        EmployeePayrollDBService employeePayrollDBService = new EmployeePayrollDBService();
        int result = employeePayrollDBService.updateEmployeeData(name, salary);
        System.out.println(result);
        if (result == 0) return;
        EmployeePayrollData employeePayrollData = this.getEmployeePayrollData(name);
        if (employeePayrollData != null)
            EmployeePayrollData.salary = salary;
    }
    public boolean checkEmployeePayrollInSyncWithDB(String name)
    {
         employeePayrollDBService = new EmployeePayrollDBService();
        List<EmployeePayrollData> employeePayrollDataList = employeePayrollDBService.getEmployeePayrollData(name);
        return employeePayrollDataList.get(0).equals(getEmployeePayrollData(name));
    }

}
