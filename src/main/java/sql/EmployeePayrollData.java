package sql;

import java.time.LocalDate;
import java.util.Objects;

public class EmployeePayrollData {
    public static Double  basic_pay;
    public static Double salary;
    public String name;
    public int id;
    public String gender;
    public LocalDate start;
    public EmployeePayrollData(Integer id, String name, Double  basic_pay)
    {
        this.id = id;
        this.name = name;
        this. basic_pay =  basic_pay;
        this.start = null;
    }
    public EmployeePayrollData(Integer id, String name, Double basic_pay, LocalDate start)
    {
        this(id, name, basic_pay);
        this.start = start;
    }
    public EmployeePayrollData(Integer id, String name, String gender, Double salary, LocalDate start)
    {
        this(id, name, salary, start);
        this.gender = gender;
    }
    @Override
    public int hashCode() {
        return Objects.hash(name, gender, basic_pay, start);
    }
    @Override
    public String toString()
    {
        return "id=" + id +
                ", salary=" + basic_pay +
                ", name='" + name + '\'' +
                ", start=" + start +
                '}';
    }
    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeePayrollData that = (EmployeePayrollData) o;
        return id == that.id && Double.compare(that.basic_pay, basic_pay) == 0 && Objects.equals(name, that.name);
    }
}
