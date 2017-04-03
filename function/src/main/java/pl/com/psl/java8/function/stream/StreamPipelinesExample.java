package pl.com.psl.java8.function.stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by psl on 03.04.17.
 */
public class StreamPipelinesExample {

    private static final Logger LOGGER = LoggerFactory.getLogger(StreamPipelinesExample.class);
    private static final Random RANDOM = new Random();

    public static void main(String[] args) {

        List<Employee> employees = Arrays.asList(
                new Employee("tom", Employee.Role.DEVELOPER, 33, 1000),
                new Employee("bob", Employee.Role.DEVELOPER, 23, 400),
                new Employee("pat", Employee.Role.MANAGER, 41, 5000),
                new Employee("tim", Employee.Role.RECRUITER, 39, 3000),
                new Employee("sam", Employee.Role.RECRUITER, 29, 1500),
                new Employee("rob", Employee.Role.MANAGER, 42, 4500),
                new Employee("max", Employee.Role.DEVELOPER, 40, 3000),
                new Employee("ken", Employee.Role.DEVELOPER, 33, 1400));

        LOGGER.info("Number of employees by roles={}", employees.stream()
                .collect(Collectors.groupingBy(Employee::getRole, Collectors.counting())));

        LOGGER.info("Employees names by roles={}", employees.stream()
                .collect(Collectors.groupingBy(Employee::getRole, Collectors.mapping(Employee::getName, Collectors.toSet()))));

        String[] trainings = {"python", "java", "agile", "git", "spring"};
        LOGGER.info("Assigning trainings to developers randomly...");
        employees.stream()
                .filter(e -> Employee.Role.DEVELOPER.equals(e.getRole()))
                .peek(e -> LOGGER.info("-----Assigning trainings to {}-----", e.getName()))
                .forEach(e -> RANDOM.ints(RANDOM.nextInt(trainings.length + 1), 0, trainings.length)
                        .distinct()
                        .peek(i -> LOGGER.info("Generated random training index={}", i))
                        .mapToObj(i -> trainings[i])
                        .forEach(t -> {
                            LOGGER.info("Assigning training={} to {}", t, e.getName());
                            e.getFinishedTrainings().add(t);
                        }));

        LOGGER.info("Developer with highest number of trainings={}", employees.stream()
                .filter(e -> Employee.Role.DEVELOPER.equals(e.getRole()))
                .max((d1, d2) -> Integer.compare(d1.getFinishedTrainings().size(), d2.getFinishedTrainings().size()))
                .map(d -> d.getName() + "(" + d.getFinishedTrainings().size() + ")")
                .get());

        LOGGER.info("Developers by age and salary={}", employees.stream()
                .filter(e -> Employee.Role.DEVELOPER.equals(e.getRole()))
                .sorted(Comparator.comparingInt(Employee::getAge))
                .sorted(Comparator.comparingInt(Employee::getSalary))
                .collect(Collectors.toList()));

        LOGGER.info("Manager with highest salary={}", employees.stream()
                .filter(e -> Employee.Role.MANAGER.equals(e.getRole()))
                .max(Comparator.comparingInt(Employee::getSalary)));
    }
}
