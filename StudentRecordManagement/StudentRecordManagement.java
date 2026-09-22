import java.io.*;
import java.util.*;

class Student implements Serializable {
    private int id;
    private String name;
    private int age;
    private String course;
    private double marks;

    public Student(int id, String name, int age, String course, double marks) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.course = course;
        this.marks = marks;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public void setMarks(double marks) {
        this.marks = marks;
    }

    @Override
    public String toString() {
        return "ID: " + id +
                ", Name: " + name +
                ", Age: " + age +
                ", Course: " + course +
                ", Marks: " + marks;
    }
}

public class StudentRecordManagement {

    private static final String FILE_NAME = "students.dat";
    private static final Scanner scanner = new Scanner(System.in);
    private static ArrayList<Student> students = new ArrayList<>();

    public static void main(String[] args) {

        loadStudents();

        while (true) {
            System.out.println("\n===== STUDENT RECORD MANAGEMENT =====");
            System.out.println("1. Add Student");
            System.out.println("2. View All Students");
            System.out.println("3. Search Student");
            System.out.println("4. Update Student");
            System.out.println("5. Delete Student");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addStudent();
                    break;

                case 2:
                    viewStudents();
                    break;

                case 3:
                    searchStudent();
                    break;

                case 4:
                    updateStudent();
                    break;

                case 5:
                    deleteStudent();
                    break;

                case 6:
                    saveStudents();
                    System.out.println("Data saved successfully.");
                    System.out.println("Thank you!");
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void addStudent() {

        System.out.print("Enter Student ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        if (findStudent(id) != null) {
            System.out.println("Student ID already exists.");
            return;
        }

        System.out.print("Enter Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Age: ");
        int age = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter Course: ");
        String course = scanner.nextLine();

        System.out.print("Enter Marks: ");
        double marks = scanner.nextDouble();

        Student student = new Student(id, name, age, course, marks);
        students.add(student);

        saveStudents();

        System.out.println("Student added successfully.");
    }

    private static void viewStudents() {

        if (students.isEmpty()) {
            System.out.println("No student records found.");
            return;
        }

        System.out.println("\n===== STUDENT RECORDS =====");

        for (Student student : students) {
            System.out.println(student);
        }
    }

    private static void searchStudent() {

        System.out.print("Enter Student ID: ");
        int id = scanner.nextInt();

        Student student = findStudent(id);

        if (student != null) {
            System.out.println("\nStudent Found:");
            System.out.println(student);
        } else {
            System.out.println("Student not found.");
        }
    }

    private static void updateStudent() {

    System.out.print("Enter Student ID to update: ");
    int id = scanner.nextInt();
    scanner.nextLine();

    Student student = findStudent(id);

    if (student == null) {
        System.out.println("Student not found.");
        return;
    }

    System.out.println("\nCurrent Student Details:");
    System.out.println(student);

    System.out.println("\nWhat do you want to update?");
    System.out.println("1. Name");
    System.out.println("2. Age");
    System.out.println("3. Course");
    System.out.println("4. Marks");
    System.out.print("Enter your choice: ");

    int choice = scanner.nextInt();
    scanner.nextLine();

    switch (choice) {

        case 1:
            System.out.print("Enter new Name: ");
            String name = scanner.nextLine();
            student.setName(name);
            break;

        case 2:
            System.out.print("Enter new Age: ");
            int age = scanner.nextInt();
            student.setAge(age);
            break;

        case 3:
            System.out.print("Enter new Course: ");
            String course = scanner.nextLine();
            student.setCourse(course);
            break;

        case 4:
            System.out.print("Enter new Marks: ");
            double marks = scanner.nextDouble();
            student.setMarks(marks);
            break;

        default:
            System.out.println("Invalid choice.");
            return;
    }

    saveStudents();

    System.out.println("Student updated successfully.");
    System.out.println("Updated Details:");
    System.out.println(student);
}

    private static void deleteStudent() {

        System.out.print("Enter Student ID to delete: ");
        int id = scanner.nextInt();

        Student student = findStudent(id);

        if (student != null) {
            students.remove(student);
            saveStudents();

            System.out.println("Student deleted successfully.");
        } else {
            System.out.println("Student not found.");
        }
    }

    private static Student findStudent(int id) {

        for (Student student : students) {
            if (student.getId() == id) {
                return student;
            }
        }

        return null;
    }

    private static void saveStudents() {

        try (ObjectOutputStream output =
                     new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {

            output.writeObject(students);

        } catch (IOException e) {
            System.out.println("Error saving student records.");
        }
    }

    @SuppressWarnings("unchecked")
    private static void loadStudents() {

        File file = new File(FILE_NAME);

        if (!file.exists()) {
            return;
        }

        try (ObjectInputStream input =
                     new ObjectInputStream(new FileInputStream(FILE_NAME))) {

            students = (ArrayList<Student>) input.readObject();

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading student records.");
        }
    }
}