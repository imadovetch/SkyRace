// Parent Class: Vehicle
class Vehicle {
    private String name;
    public static int maxSpeed;

    // Constructor of the Vehicle class
    public Vehicle(String name, int maxSpeed) {
        this.name = name;
        this.maxSpeed = maxSpeed;
    }

    // Method in Vehicle class
    public void displayDetails() {
        System.out.println("Vehicle Name: " + name);
        System.out.println("Max Speed: " + maxSpeed + " km/h");
    }

    // Getter method for maxSpeed
    public int getMaxSpeed() {
        return maxSpeed;
    }
}

// Child Class: Car
class Car extends Vehicle {
    private String fuelType;

    // Constructor of the Car class
    public Car(String name, int maxSpeed, String fuelType) {
        // Call the parent class (Vehicle) constructor using super
        super(name, maxSpeed);
        this.fuelType = fuelType;
        this.maxSpeed = maxSpeed;
    }

    // Overriding the displayDetails method
    @Override
    public void displayDetails() {
        // Call the parent class's displayDetails method using super
        super.displayDetails();  // This will print the name and maxSpeed from Vehicle
        System.out.println("Fuel Type: " + fuelType);
    }

    // Additional method specific to Car class
    public void showCarFeatures() {
        System.out.println("This car runs on " + fuelType + " and has a max speed of " + getMaxSpeed() + " km/h.");
    }
}

// Main class to test the inheritance
public class Main {
    public static void main(String[] args) {
        // Create a Car object
        Car myCar = new Car("Toyota", 180, "Petrol");

        // Display details using the overridden method
        myCar.displayDetails();  // Calls the overridden method in Car

        // Show specific car features
        myCar.showCarFeatures();
    }
}
