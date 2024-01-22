package dev;

public enum RobotType {
    // Define CARRIER with the vision radius squared property
    CARRIER(100, 10), // Replace 100 with the build cost or another property and 10 with the actual vision radius squared value
    HEADQUARTERS(100, 10); // Replace 100 with the build cost or another property and 10 with the actual vision radius squared value

    private final int someProperty; // Replace 'someProperty' with a meaningful name
    private final int visionRadiusSquared;

    RobotType(int someProperty, int visionRadiusSquared) {
        this.someProperty = someProperty;
        this.visionRadiusSquared = visionRadiusSquared;
    }

    // Getter for visionRadiusSquared
    public int getVisionRadiusSquared() {
        return visionRadiusSquared;
    }

    // Optionally, if you want to allow the setting of this value (usually not recommended for enums):
    // private void setVisionRadiusSquared(int visionRadiusSquared) {
    //     this.visionRadiusSquared = visionRadiusSquared;
    // }

    // Getter for someProperty
    public int getSomeProperty() {
        return someProperty;
    }
}
