package pl.com.psl.java8.function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Created by psl on 11.03.17.
 */
public class MethodReferenceExample {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodReferenceExample.class);

    public static void main(String[] args) {
        String[] names = {"bob", "pat", "tom", "tim", null};
        LOGGER.info("Creating employees with names={}", Arrays.toString(names));
        Function<String[], List<Employee>> namesToEmployeesFunction = Employee::createEmployees; //class::static method
        List<Employee> employees = namesToEmployeesFunction.apply(names);
        LOGGER.info("Created employees={}", employees);
        employees.removeIf(Employee::hasInvalidName); //class::instance method
        LOGGER.info("After removing invalid names={}", employees);
        EmployeeDAO employeeDAO = new EmployeeDAO();
        LOGGER.info("Saving all employees...");
        employees.forEach(employeeDAO::save); //instance::instance method
        LOGGER.info("All employees saved!");
        Supplier<Employee> employeeSupplier = Employee::new; //constructor
        LOGGER.info("Supplied new employee={}", employeeSupplier.get());
        Function<String, Employee> nameToEmployeeFunction = Employee::new; //constructor
        LOGGER.info("Converted name to employee={}", nameToEmployeeFunction.apply("jim"));
    }

    private static class Employee {

        private String name;

        public Employee(String name) {
            this.name = name;
        }

        public Employee() {
            this("Unknown");
        }

        public boolean hasInvalidName() {
            return name == null || name.isEmpty();
        }

        public static List<Employee> createEmployees(String[] names) {
            if (names != null) {
                List<Employee> employees = new ArrayList<>(names.length);
                for (String name : names) {
                    employees.add(new Employee(name));
                }
                return employees;
            }
            return Collections.emptyList();
        }

        @Override
        public String toString() {
            return "Employee{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }

    private static class EmployeeDAO {

        public void save(Employee employee) {
            LOGGER.info("Saving employee={}...", employee);
        }
    }
}
