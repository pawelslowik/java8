package pl.com.psl.java8.function.stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StreamTerminalOperationsExample {

    private static final Logger LOGGER = LoggerFactory.getLogger(StreamTerminalOperationsExample.class);

    public static void main(String[] args) {
        List<Employee> employees = Arrays.asList(
                new Employee("tom", Employee.Role.DEVELOPER, 33, 1000),
                new Employee("bob", Employee.Role.DEVELOPER, 23, 400),
                new Employee("pat", Employee.Role.MANAGER, 41, 5000),
                new Employee("tim", Employee.Role.RECRUITER, 39, 3000));

        LOGGER.info("Created stream of {} employees", employees.stream().count());
        LOGGER.info("The oldest of all employees is {} years old",
                employees.stream().max((e1, e2) -> Integer.compare(e1.getAge(), e2.getAge())).get());
        LOGGER.info("The youngest of all employees is {} years old",
                employees.stream().max((e1, e2) -> Integer.compare(e2.getAge(), e1.getAge())).get());
        LOGGER.info("All employees are developers={}",
                employees.stream().allMatch(e -> Employee.Role.DEVELOPER.equals(e.getRole())));
        LOGGER.info("Converting all non-developers into developers");
        employees.stream().forEach(e ->
        {
            if (!Employee.Role.DEVELOPER.equals(e.getRole())) {
                LOGGER.info("Making {} a developer", e);
                e.setRole(Employee.Role.DEVELOPER);
            }
        });
        LOGGER.info("All employees are developers={}",
                employees.stream().allMatch(e -> Employee.Role.DEVELOPER.equals(e.getRole())));
        LOGGER.info("Employee with highest salary={}", employees.stream().reduce((e1, e2) -> e1.getSalary() > e2.getSalary() ? e1 : e2));
        LOGGER.info("Salary statistics for all employees={}", employees.stream().collect(Collectors.summarizingInt(Employee::getSalary)));
    }
}
