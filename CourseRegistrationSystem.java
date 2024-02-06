import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Course {
    private String courseCode;
    private String title;
    private String description;
    private int capacity;
    private String schedule;
    private int registeredStudents;

    public Course(String courseCode, String title, String description, int capacity, String schedule) {
        this.courseCode = courseCode;
        this.title = title;
        this.description = description;
        this.capacity = capacity;
        this.schedule = schedule;
        this.registeredStudents = 0;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getSchedule() {
        return schedule;
    }

    public int getRegisteredStudents() {
        return registeredStudents;
    }

    public void registerStudent() {
        if (registeredStudents < capacity) {
            registeredStudents++;
            System.out.println("Student registered for " + title);
        } else {
            System.out.println("Course is full. Cannot register for " + title);
        }
    }

    public void removeStudent() {
        if (registeredStudents > 0) {
            registeredStudents--;
            System.out.println("Student removed from " + title);
        } else {
            System.out.println("No students registered for " + title);
        }
    }

    @Override
    public String toString() {
        return "Course Code: " + courseCode + "\nTitle: " + title + "\nDescription: " + description +
                "\nCapacity: " + capacity + "\nSchedule: " + schedule + "\nRegistered Students: " + registeredStudents;
    }
}

class Student {
    private int studentID;
    private String name;
    private List<Course> registeredCourses;

    public Student(int studentID, String name) {
        this.studentID = studentID;
        this.name = name;
        this.registeredCourses = new ArrayList<>();
    }

    public int getStudentID() {
        return studentID;
    }

    public String getName() {
        return name;
    }

    public List<Course> getRegisteredCourses() {
        return registeredCourses;
    }

    public void registerCourse(Course course) {
        if (!registeredCourses.contains(course)) {
            registeredCourses.add(course);
            course.registerStudent();
            System.out.println("Course registered: " + course.getTitle());
        } else {
            System.out.println("Already registered for " + course.getTitle());
        }
    }

    public void dropCourse(Course course) {
        if (registeredCourses.contains(course)) {
            registeredCourses.remove(course);
            course.removeStudent();
            System.out.println("Course dropped: " + course.getTitle());
        } else {
            System.out.println("Not registered for " + course.getTitle());
        }
    }

    @Override
    public String toString() {
        return "Student ID: " + studentID + "\nName: " + name + "\nRegistered Courses: " + registeredCourses.size();
    }
}

public class CourseRegistrationSystem {
    public static void main(String[] args) {
        // Create courses
        Course mathCourse = new Course("MATH101", "Introduction to Calculus", "Fundamental concepts of calculus", 30, "Mon/Wed 10:00 AM");
        Course compSciCourse = new Course("CS101", "Introduction to Computer Science", "Basic programming concepts", 25, "Tue/Thu 2:00 PM");
        Course bioCourse = new Course("BIO101", "Biology Basics", "Introduction to biology", 20, "Mon/Wed 1:00 PM");

        // Create students
        Student student1 = new Student(1, "John Doe");
        Student student2 = new Student(2, "Jane Smith");

        // Display course listings
        displayCourseList(List.of(mathCourse, compSciCourse, bioCourse));

        // Register students for courses
        student1.registerCourse(mathCourse);
        student1.registerCourse(compSciCourse);
        student2.registerCourse(bioCourse);

        // Display updated course listings
        displayCourseList(List.of(mathCourse, compSciCourse, bioCourse));

        // Drop a course
        student1.dropCourse(mathCourse);

        // Display updated course listings
        displayCourseList(List.of(mathCourse, compSciCourse, bioCourse));

        // Display student information
        System.out.println("\nStudent Information:\n" + student1);
        System.out.println("\nStudent Information:\n" + student2);
    }

    private static void displayCourseList(List<Course> courses) {
        System.out.println("\nAvailable Courses:");
        for (Course course : courses) {
            System.out.println("\n" + course);
        }
    }
}