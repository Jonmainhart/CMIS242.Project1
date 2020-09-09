/*
 * Weight.Java
 * 01 September 2020
 * Jonathan Mainhart - CMIS 242
 * Create a Class of Weight Objects that contain pound and ounce parameters with
 * methods to interact with the objects.
 */
package project1;

/**
 *
 * @author jonmainhart
 */
public class Weight {

    private final double CONVRATE = 16.0;
    private double pounds;
    private double ounces;

    public Weight(double lbs, double oz) {
        this.pounds = lbs;
        this.ounces = oz;
    }

    private void toOunces() {
        /**
         * Converts object's weight to ounces.
         */
        double n = this.pounds * CONVRATE;
        this.ounces += n;
        this.pounds = 0.0;
    }

    private void normalize() {
        /**
         * converts weights to lbs-oz. Oz > 16 will convert to lbs while
         * remainder is oz
         */
        if (this.ounces >= CONVRATE) {
            // cast to int so we only get whole numbers for the lbs
            this.pounds = (int) this.ounces / (int) CONVRATE;
            this.ounces = this.ounces % CONVRATE;
        }
    } // normalize

    @Override
    public String toString() {
        this.normalize();
        String rtn = String.valueOf(this.pounds)
                + " lbs-" + String.valueOf(this.ounces) + " oz";
        return rtn;
    }

    public void divide(int divisor) {
        /**
         * Divides object's weight by divisor.
         */
        this.toOunces();
        this.ounces = this.ounces / divisor;
        this.normalize();
    }

    public void addTo(Weight wt) {
        /**
         * Adds argument object's weight to the object which called the method.
         * Uses .toOunces() method to make the math easier then normalizes the
         * result.
         */
        Weight param = wt;
        param.toOunces();
        this.toOunces();
        this.ounces += param.ounces;
        this.normalize();
    }

    public boolean lessThan(Weight wt) {
        /**
         * determines whether the object that called the method is less than the
         * object passed as argument
         */
        boolean value;
        this.toOunces();
        wt.toOunces();
        value = this.ounces < wt.ounces;
        this.normalize();
        wt.normalize();

        return value;
    }

} // end Weight.java
