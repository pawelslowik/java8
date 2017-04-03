package pl.com.psl.java8.function.stream;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by psl on 01.04.17.
 */
public class Employee {

    enum Role {DEVELOPER, MANAGER, RECRUITER, UNDEFINED}

    private String name;
    private Role role;
    private int age;
    private int salary;
    private List<String> finishedTrainings = new ArrayList<>();

    public Employee(String name, Role role, int age, int salary) {
        this.name = name;
        this.role = role;
        this.age = age;
        this.salary = salary;
    }

    public Employee(String name){
        this.name = name;
        this.role = Role.UNDEFINED;
        this.age = 0;
        this.salary = 0;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public int getSalary() {
        return salary;
    }

    public List<String> getFinishedTrainings() {
        return finishedTrainings;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", role=" + role +
                ", age=" + age +
                ", salary=" + salary +
                ", finishedTrainings=" + finishedTrainings +
                '}';
    }
}
