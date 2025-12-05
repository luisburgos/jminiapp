package com.jminiapp.examples.bmi;

/**
 * Model representing BMI calculation data.
 *
 * This class stores weight, height, and calculated BMI values.
 * BMI is calculated using the formula: weight (kg) / (height (m))²
 */
public class BmiState {
    private double weight; // Weight in kilograms
    private double height; // Height in meters
    private double bmi;    // Calculated BMI value

    /**
     * Create a new BMI state with default values (0).
     */
    public BmiState() {
        this.weight = 0.0;
        this.height = 0.0;
        this.bmi = 0.0;
    }

    /**
     * Create a new BMI state with specific weight and height.
     * Automatically calculates BMI.
     *
     * @param weight the weight in kilograms
     * @param height the height in meters
     */
    public BmiState(double weight, double height) {
        this.weight = weight;
        this.height = height;
        this.bmi = calculateBmi();
    }

    /**
     * Get the weight value.
     *
     * @return the weight in kilograms
     */
    public double getWeight() {
        return weight;
    }

    /**
     * Set the weight value.
     *
     * @param weight the new weight in kilograms
     */
    public void setWeight(double weight) {
        this.weight = weight;
        this.bmi = calculateBmi();
    }

    /**
     * Get the height value.
     *
     * @return the height in meters
     */
    public double getHeight() {
        return height;
    }

    /**
     * Set the height value.
     *
     * @param height the new height in meters
     */
    public void setHeight(double height) {
        this.height = height;
        this.bmi = calculateBmi();
    }

    /**
     * Get the calculated BMI value.
     *
     * @return the BMI value
     */
    public double getBmi() {
        return bmi;
    }

    /**
     * Calculate BMI using the formula: weight / (height²)
     * 
     * @return the calculated BMI value, or 0 if height is 0
     */
    public double calculateBmi() {
        if (height <= 0) {
            return 0.0;
        }
        this.bmi = weight / (height * height);
        return this.bmi;
    }

    /**
     * Get the BMI category based on WHO classification.
     * 
     * @return the BMI category as a String
     */
    public String getBmiCategory() {
        if (bmi == 0.0) {
            return "Not calculated";
        } else if (bmi < 18.5) {
            return "Underweight";
        } else if (bmi < 25.0) {
            return "Normal weight";
        } else if (bmi < 30.0) {
            return "Overweight";
        } else {
            return "Obese";
        }
    }

    /**
     * Reset all values to 0.
     */
    public void reset() {
        this.weight = 0.0;
        this.height = 0.0;
        this.bmi = 0.0;
    }

    @Override
    public String toString() {
        return String.format("BmiState{weight=%.2f kg, height=%.2f m, bmi=%.2f, category=%s}", 
                           weight, height, bmi, getBmiCategory());
    }
}
