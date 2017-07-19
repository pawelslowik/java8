package pl.com.psl.java8.function.stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

/**
 * Created by psl on 18.07.17.
 */
public class CollectorsExample {

    private static final Logger LOGGER = LoggerFactory.getLogger(CollectorsExample.class);

    public static void main(String[] args) {
        Employee tom = new Employee("tom", Employee.Role.DEVELOPER, 33, 1000);
        Employee bob = new Employee("bob", Employee.Role.DEVELOPER, 23, 400);
        Employee pat = new Employee("pat", Employee.Role.MANAGER, 41, 5000);
        Employee tim = new Employee("tim", Employee.Role.RECRUITER, 39, 3000);
        List<Employee> employees = Arrays.asList(tom, bob, pat, tim);
        LOGGER.info("All employees={}", employees);

        String employeesNames = employees.stream().map(Employee::getName).collect(joining(",", "<", ">"));
        LOGGER.info("Employees names={}", employeesNames);

        Map<Employee.Role, List<Employee>> employeesByRole = employees.stream()
                .collect(groupingBy(Employee::getRole));
        LOGGER.info("Employees grouped by role={}", employeesByRole);

        Map<Employee.Role, List<String>> employeesNamesByRole = employees.stream()
                .collect(groupingBy(Employee::getRole, mapping(Employee::getName, toList())));
        LOGGER.info("Employees names grouped by role={}", employeesNamesByRole);

        Map<Boolean, List<String>> richAndPoorEmployees = employees.stream()
                .collect(partitioningBy(e -> e.getSalary() > 2000, mapping(Employee::getName, toList())));
        LOGGER.info("Employees partitioned by salary > 2000={}", richAndPoorEmployees);

        int employeeMaxAge = employees.stream()
                .collect(collectingAndThen(maxBy(Comparator.comparing(Employee::getAge)), e -> e.isPresent() ? e.get().getAge() : 0));
        LOGGER.info("Employee max age={}", employeeMaxAge);

        IntSummaryStatistics ageStatistics = employees.stream().collect(Collectors.summarizingInt(Employee::getAge));
        LOGGER.info("Employees age statistics={}", ageStatistics);

        Integer totalSalaries = employees.stream().collect(Collectors.summingInt(Employee::getSalary));
        LOGGER.info("Total salaries={}", totalSalaries);

        Integer maxSalary = employees.stream().collect(reducing(0, Employee::getSalary, Integer::max));
        LOGGER.info("Max salary={}", maxSalary);

        Double averageSalary = employees.stream().collect(averagingInt(Employee::getSalary));
        LOGGER.info("Average salary={}", averageSalary);

        Long numberOfDevelopers = employees.stream().filter(e -> Employee.Role.DEVELOPER.equals(e.getRole())).collect(counting());
        LOGGER.info("There are {} developers among all employees", numberOfDevelopers);

        Map<String, Employee.Role> roleByEmployeeName = employees.stream().collect(toMap(Employee::getName, Employee::getRole));
        LOGGER.info("Role by employee name={}", roleByEmployeeName);

        Set<Employee.Role> rolesSet = employees.stream().map(Employee::getRole).collect(toSet());
        LOGGER.info("Roles set={}", rolesSet);

        List<Employee.Role> rolesList = rolesSet.stream().collect(toList());
        LOGGER.info("Roles set converted to list={}", rolesList);

        TreeSet<Employee.Role> rolesTreeSet = rolesList.stream().collect(toCollection(TreeSet::new));
        LOGGER.info("Roles list converted to TreeSet={}", rolesTreeSet);
    }
}
