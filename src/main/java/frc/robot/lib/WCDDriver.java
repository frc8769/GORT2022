/**
 * This file was part of TorqueLib, Texas Torque's internal library
 * of useful functions and classes for the robot.
 * 
 * Copyright (c) 2011 - 2022 Texas Torque.
 */
package frc.robot.lib;

/**
 * Improvments to the algorithm used to drive a West Coast Drivetrain
 * durring teleop. Encapsulated in a class.
 * 
 * @author Justus
 */
public class WCDDriver {
    private double forward = 3;
    private double turn = .2;

    private final double power = 4;
    private double coef = 1;

    double left = 0;
    double right = 0;

    public WCDDriver(double forward, double turn) {
        this.forward = forward;
        this.turn = turn;
    }

    /**
     * This will be removed after fully debugged
     * 
     * @param coef
     * @deprecated
     */
    @Deprecated
    public void setCoef(double coef) {
        this.coef = coef;
    }

    public void update(double x, double y) {
        left = -coef * (-turn * y - forward * Math.pow(x, power) * Math.signum(x));
        right = coef * (turn * y - forward * Math.pow(x, power) * Math.signum(x));
    }

    public double getLeft() {
        return left;
    }

    public double getRight() {
        return right;
    }

    public void setForwardCoef(double forward) {
        this.forward = forward;
    }

    public void setTurnCoef(double turn) {
        this.turn = turn;
    }

    public void setLeft(double left) {
        this.left = left;
    }

    public void setRight(double right) {
        this.right = right;
    }
}