package pl.com.psl.java8.function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.Month;
import java.util.HashSet;
import java.util.Set;

/**
 * This class demonstrates usage of simple lambda expression.
 * For the purpose of demonstration, it does not use lambda features provided by Collection API.
 */
public class SimpleLambdaExample {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleLambdaExample.class);

    public static void main(String[] args) {
        Employee bob = new Employee("Bob", 20, 150, LocalDate.of(2016, Month.OCTOBER, 1));
        Employee tom = new Employee("Tom", 30, 80, LocalDate.of(2012, Month.JULY, 1));
        Employee pat = new Employee("Pat", 50, 200, LocalDate.of(2016, Month.OCTOBER, 1));
        Employee joe = new Employee("J0e", 33, 100, LocalDate.of(2013, Month.JULY, 1));

        Department salesDepartment = new Department("Sales");
        salesDepartment.addEmployee(bob);
        salesDepartment.addEmployee(tom);
        salesDepartment.addEmployee(pat);
        salesDepartment.addEmployee(joe);

        LOGGER.info("Department=" + salesDepartment.getName() + ", employees=" + salesDepartment.getEmployees());
        Criterion<Employee> incorrectNameFormatCriterion = e -> e.getName() == null || !e.getName().matches("[A-Za-z]+");
        LOGGER.info("Employees with incorrect name format=" + salesDepartment.getEmployeesByCriterion(incorrectNameFormatCriterion));
        LOGGER.info("Employees earning more than 100=" + salesDepartment.getEmployeesByCriterion(e -> e.getSalary() > 100));
        LOGGER.info("Employees under 40 years old=" + salesDepartment.getEmployeesByCriterion((Employee e) -> {
            return e.getAge() < 40;
        }));
        LOGGER.info("Employees hired in 2016=" + salesDepartment.getEmployeesByCriterion(e -> 2016 == e.getHireDate().getYear()));
    }

    public static class Department {
        private String name;
        private Set<Employee> employees = new HashSet<>();

        public Department(String name) {
            this.name = name;
        }

        public Set<Employee> getEmployeesByCriterion(Criterion<Employee> criterion) {
            Set<Employee> employeesByCriterion = new HashSet<>();
            for (Employee employee : employees) {
                if (employee.meetsCriterion(criterion)) {
                    employeesByCriterion.add(employee);
                }
            }
            return employeesByCriterion;
        }

        public void addEmployee(Employee employee) {
            employees.add(employee);
        }

        public String getName() {
            return name;
        }

        public Set<Employee> getEmployees() {
            return employees;
        }
    }

    public static class Employee {

        private String name;
        private int age;
        private int salary;
        private LocalDate hireDate;


        public Employee(String name, int age, int salary, LocalDate hireDate) {
            this.name = name;
            this.age = age;
            this.salary = salary;
            this.hireDate = hireDate;
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }

        public int getSalary() {
            return salary;
        }

        public LocalDate getHireDate() {
            return hireDate;
        }

        public boolean meetsCriterion(Criterion<Employee> criterion) {
            return criterion.meets(this);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Employee employee = (Employee) o;

            if (age != employee.age) return false;
            if (salary != employee.salary) return false;
            if (!name.equals(employee.name)) return false;
            return hireDate.equals(employee.hireDate);

        }

        @Override
        public int hashCode() {
            int result = name.hashCode();
            result = 31 * result + age;
            result = 31 * result + salary;
            result = 31 * result + hireDate.hashCode();
            return result;
        }

        @Override
        public String toString() {
            return "Employee{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    ", salary=" + salary +
                    ", hireDate=" + hireDate +
                    '}';
        }
    }

    public interface Criterion<T> {
        boolean meets(T t);
    }

}
