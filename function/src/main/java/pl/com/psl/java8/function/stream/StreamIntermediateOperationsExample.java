package pl.com.psl.java8.function.stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by psl on 01.04.17.
 */
public class StreamIntermediateOperationsExample {

    private static final Logger LOGGER = LoggerFactory.getLogger(StreamIntermediateOperationsExample.class);

    public static void main(String[] args) {
        Employee tom = new Employee("tom", Employee.Role.DEVELOPER, 33, 1000);
        Employee bob = new Employee("bob", Employee.Role.DEVELOPER, 23, 400);
        Employee pat = new Employee("pat", Employee.Role.MANAGER, 41, 5000);
        Employee tim = new Employee("tim", Employee.Role.RECRUITER, 39, 3000);
        List<Employee> employees = Arrays.asList(tom, bob, pat, tim);

        LOGGER.info("All employees stream=", employees);

        Stream<Employee> developersStream = employees.stream().filter(e -> Employee.Role.DEVELOPER.equals(e.getRole()));
        LOGGER.info("Filtered developers=" + developersStream.collect(Collectors.toList()));

        Stream<Employee> sortedEmployeesStream = employees.stream().sorted((e1, e2) -> Integer.compare(e1.getSalary(), e2.getSalary()));
        LOGGER.info("Sorted all employees salary name={}", sortedEmployeesStream.collect(Collectors.toList()));

        Stream<Employee.Role> rolesStream = employees.stream().map(Employee::getRole);
        LOGGER.info("Roles of all employees={}", rolesStream.collect(Collectors.toList()));

        Stream<Employee.Role> distinctRolesStream = employees.stream().peek(e -> LOGGER.info("Processing employee={}", e))
                .map(Employee::getRole).peek(r -> LOGGER.info("Mapped employee to role={}", r))
                .distinct().peek(r -> LOGGER.info("Distinct role={}", r));
        LOGGER.info("Distinct roles={}", distinctRolesStream.collect(Collectors.toList()));

        List<Employee> generatedEmployeesList = Stream.generate(() -> new Employee("Employee" + new Random().nextInt())).limit(4).collect(Collectors.toList());
        LOGGER.info("Generated {} employees={}", generatedEmployeesList.size(), generatedEmployeesList);
        LOGGER.info("After skipping three generated employees={}", generatedEmployeesList.stream().skip(3).collect(Collectors.toList()));

        LOGGER.info("Assigning trainings to employees...");
        tom.getFinishedTrainings().add("java");
        tom.getFinishedTrainings().add("agile");
        bob.getFinishedTrainings().add("python");
        pat.getFinishedTrainings().add("agile");

        Stream<String> finishedTrainingsStream = employees.stream().flatMap(e -> e.getFinishedTrainings().stream()).distinct();
        LOGGER.info("All trainings finished by employees={}", finishedTrainingsStream.collect(Collectors.toList()));
    }
}
