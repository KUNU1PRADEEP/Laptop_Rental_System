import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Laptop {
    private String laptop_Id;
    private String brand;
    private String model;
    private double basePricePerDay;
    private boolean isAvailable;

    public Laptop(String laptop_Id, String brand, String model, double basePricePerDay) {
        this.laptop_Id = laptop_Id;
        this.brand = brand;
        this.model = model;
        this.basePricePerDay = basePricePerDay;
        this.isAvailable = true;
    }
    public String getlaptop_Id() {
        return laptop_Id;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public double calculatePrice(int rentalDays) {
        return basePricePerDay * rentalDays;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void rent() {
        isAvailable = false;
    }

    public void returnLaptop() {
        isAvailable = true;
    }
}

class Student {
    private String student_Rental_Id;
    private String name;

    public Student(String student_Rental_Id, String name) {
        this.student_Rental_Id = student_Rental_Id;
        this.name = name;
    }

    public String getStudent_Rental_Id() {
        return student_Rental_Id;
    }

    public String getName() {
        return name;
    }
}

class Rental {
    private Laptop laptop;
    private Student student;
    private int days;

    public Rental(Laptop laptop, Student student, int days) {
        this.laptop = laptop;
        this.student = student;
        this.days = days;
    }

    public Laptop getLaptop() {
        return laptop;
    }

    public Student getStudent() {
        return student;
    }

    public int getDays() {
        return days;
    }
}

class LaptopRentalSystem {
    private List<Laptop> laptops;
    private List<Student> students;
    private List<Rental> rentals;

    public LaptopRentalSystem() {
        laptops = new ArrayList<>();
        students = new ArrayList<>();
        rentals = new ArrayList<>();
    }

    public void addLaptop(Laptop laptop) {laptops.add(laptop);}

    public void addStudent(Student laptop) {students.add(laptop);
    }

    public void rentLaptop(Laptop laptop, Student student, int days) {
        if (laptop.isAvailable()) {
            laptop.rent();
            rentals.add(new Rental(laptop, student, days));

        } else {
            System.out.println("That Model 'Laptop' is not available for rent.");
        }
    }

    public void returnLaptop(Laptop laptop) {
        laptop.returnLaptop();
        Rental rentalToRemove = null;
        for (Rental rental : rentals) {
            if (rental.getLaptop() == laptop) {
                rentalToRemove = rental;
                break;
            }
        }
        if (rentalToRemove != null) {
            rentals.remove(rentalToRemove);

        } else {
            System.out.println("Laptop was not rented.");
        }
    }

    public void menu() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("===== Laptop Rental System =====");
            System.out.println("1. Rent the Laptop");
            System.out.println("2. Return the Laptop");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (choice == 1) {
                System.out.println("\n== Rent a Laptop ==\n");
                System.out.print("Enter the student name : ");
                String studentName = scanner.nextLine();

                System.out.println("\nAvailable Laptop:\n");
                System.out.println("+--------------------+--------------+---------------+-----------------+-----------+");
                System.out.println("|   Laptop ID        |     Brand                    -  Model                           ");
                for (Laptop laptop : laptops) {
                    if (laptop.isAvailable()) {
//                        System.out.println(laptop.getlaptop_Id() + " - " + laptop.getBrand() + " " + laptop.getModel());
                        System.out.println("|   "+laptop.getlaptop_Id()+"             |     "+laptop.getBrand()+"            -     "+laptop.getModel()+"      ");

                    }
                }
                System.out.println("+--------------------+--------------+---------------+-----------------+-----------+");

                System.out.print("\nEnter the Laptop ID you want to rent: ");
                String laptopId = scanner.nextLine();

                System.out.print("Enter the number of days for rental: ");
                int rentalDays = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                Student newStudent = new Student("STU" + (students.size() + 1), studentName);
                addStudent(newStudent);

                Laptop selectedLaptop = null;
                for (Laptop laptop : laptops) {
                    if (laptop.getlaptop_Id().equals(laptopId) && laptop.isAvailable()) {
                        selectedLaptop = laptop;
                        break;
                    }
                }

                if (selectedLaptop != null) {
                    double totalPrice = selectedLaptop.calculatePrice(rentalDays);
                    System.out.println("\n== Rental Information ==\n");
                    System.out.println("Student Rental ID: " + newStudent.getStudent_Rental_Id());
                    System.out.println("Student Name: " + newStudent.getName());
                    System.out.println("Laptop: " + selectedLaptop.getBrand() + " " + selectedLaptop.getModel());
                    System.out.println("Rental Days: " + rentalDays);
                    System.out.printf("Total Price: ₹%.2f%n", totalPrice);

                    System.out.print("\nConfirm rental (Y/N): ");
                    String confirm = scanner.nextLine();

                    if (confirm.equalsIgnoreCase("Y")) {
                        rentLaptop(selectedLaptop, newStudent, rentalDays);
                        System.out.println("\nLaptop rented successfully.");
                    } else {
                        System.out.println("\nRental canceled.");
                    }
                } else {
                    System.out.println("\nInvalid Laptop brand selection or Laptop not available for rent.");
                }
            } else if (choice == 2) {
                System.out.println("\n== Return a laptop ==\n");
                System.out.print("Enter the Laptop ID you want to return: ");
                String carId = scanner.nextLine();

                Laptop laptopToReturn = null;
                for (Laptop laptop : laptops) {
                    if (laptop.getlaptop_Id().equals(carId) && !laptop.isAvailable()) {
                        laptopToReturn = laptop;
                        break;
                    }
                }

                if (laptopToReturn != null) {
                    Student student = null;
                    for (Rental rental : rentals) {
                        if (rental.getLaptop() == laptopToReturn) {
                            student = rental.getStudent();
                            break;
                        }
                    }

                    if (student != null) {
                        returnLaptop(laptopToReturn);
                        System.out.println("Laptop returned successfully by " + student.getName());
                    } else {
                        System.out.println("Laptop was not rented or rental information is missing.");
                    }
                } else {
                    System.out.println("Invalid Laptop ID or Laptop is not rented.");
                }
            } else if (choice == 3) {
                break;
            } else {
                System.out.println("Invalid choice. Please enter a valid option.");
            }
        }

        System.out.println("\nThank you for using the Laptop Rental System!");
    }

}
public class Main{
    public static void main(String[] args) {
        LaptopRentalSystem rentalSystem = new LaptopRentalSystem();

        Laptop laptop1 = new Laptop("C001", "lenovo", "Ideapad Gaming 3", 40.0); // Different base price per day for each laptop
        Laptop laptop2 = new Laptop("C002", "HP", "pavilion i3", 35.0);
        Laptop laptop3 = new Laptop("C003", "MACBOOK", "air M1(2020)", 60.0);
        rentalSystem.addLaptop(laptop1);
        rentalSystem.addLaptop(laptop2);
        rentalSystem.addLaptop(laptop3);

        rentalSystem.menu();
    }
}
