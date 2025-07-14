
import java.io.*;
import java.util.*;

class Student {

    private String rollNo;
    private String name;
    private int age;

    public Student(String rollNo, String name, int age) {
        this.rollNo = rollNo;
        this.name = name;
        this.age = age;
    }

    public String getRollNo() {
        return rollNo;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String toFileString() {
        return rollNo + "," + name + "," + age;
    }

    public static Student fromFileString(String line) {
        String[] parts = line.split(",");
        return new Student(parts[0], parts[1], Integer.parseInt(parts[2]));
    }

    @Override
    public String toString() {
        return "Roll No: " + rollNo + ", Name: " + name + ", Age: " + age;
    }
}

public class StudentManagementSystem {

    static final String FILE_NAME = "students.txt";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n----- Student Management System -----");
            System.out.println("1. Add Student");
            System.out.println("2. View All Students");
            System.out.println("3. Search Student by Roll No");
            System.out.println("4. Update Student");
            System.out.println("5. Delete Student");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 ->
                    addStudent(sc);
                case 2 ->
                    viewStudents();
                case 3 ->
                    searchStudent(sc);
                case 4 ->
                    updateStudent(sc);
                case 5 ->
                    deleteStudent(sc);
                case 6 ->
                    System.out.println("Exiting system.");
                default ->
                    System.out.println("Invalid choice.");
            }
        } while (choice != 6);

        sc.close();
    }

    static void addStudent(Scanner sc) {
        System.out.print("Enter Roll No: ");
        String roll = sc.nextLine();
        System.out.print("Enter Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Age: ");
        int age = sc.nextInt();
        sc.nextLine();

        Student student = new Student(roll, name, age);

        try (FileWriter fw = new FileWriter(FILE_NAME, true); BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write(student.toFileString());
            bw.newLine();
            System.out.println("Student added.");
        } catch (IOException e) {
            System.out.println("Error writing to file.");
        }
    }

    static void viewStudents() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            System.out.println("\n--- Student List ---");
            while ((line = br.readLine()) != null) {
                Student s = Student.fromFileString(line);
                System.out.println(s);
            }
        } catch (IOException e) {
            System.out.println("Error reading file.");
        }
    }

    static void searchStudent(Scanner sc) {
        System.out.print("Enter Roll No to search: ");
        String roll = sc.nextLine();
        boolean found = false;

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                Student s = Student.fromFileString(line);
                if (s.getRollNo().equals(roll)) {
                    System.out.println("Student Found: " + s);
                    found = true;
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file.");
        }

        if (!found) {
            System.out.println("Student not found.");
        }
    }

    static void updateStudent(Scanner sc) {
        System.out.print("Enter Roll No to update: ");
        String roll = sc.nextLine();

        List<Student> students = new ArrayList<>();
        boolean updated = false;

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                Student s = Student.fromFileString(line);
                if (s.getRollNo().equals(roll)) {
                    System.out.print("Enter new name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter new age: ");
                    int age = sc.nextInt();
                    sc.nextLine();
                    s.setName(name);
                    s.setAge(age);
                    updated = true;
                }
                students.add(s);
            }
        } catch (IOException e) {
            System.out.println("Error reading file.");
            return;
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Student s : students) {
                bw.write(s.toFileString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing to file.");
        }

        if (updated) {
            System.out.println("Student updated successfully."); 
        }else {
            System.out.println("Student not found.");
        }
    }

    static void deleteStudent(Scanner sc) {
        System.out.print("Enter Roll No to delete: ");
        String roll = sc.nextLine();

        List<Student> students = new ArrayList<>();
        boolean deleted = false;

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                Student s = Student.fromFileString(line);
                if (!s.getRollNo().equals(roll)) {
                    students.add(s);
                } else {
                    deleted = true;
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file.");
            return;
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Student s : students) {
                bw.write(s.toFileString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing to file.");
        }

        if (deleted) {
            System.out.println("Student deleted."); 
        }else {
            System.out.println("Student not found.");
        }
    }
}
