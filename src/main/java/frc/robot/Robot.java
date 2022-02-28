package frc.robot;

import edu.wpi.first.wpilibj.motorcontrol.PWMVictorSPX;
import frc.robot.lib.GenericController;
import frc.robot.lib.TorqueIterative;
import frc.robot.lib.WCDDriver;

public class Robot extends TorqueIterative {

  private final double INTAKE_SPEED = 1;
  private final double MAG_SPEED = 1;
  private final double LEFT_DECREASE = .6;

  private PWMVictorSPX frontLeft;  // Port 2
  private PWMVictorSPX frontRight; // Port 4
  private PWMVictorSPX backLeft;   // Port 1
  private PWMVictorSPX backRight; // Port 3
  private PWMVictorSPX rotary; // Port 5
  private PWMVictorSPX magRight; // Port 6
  private PWMVictorSPX magLeft; // Port 7

  
  // Algorithm for converting joystick values to motor values
  private WCDDriver wcd;

  private GenericController driver;
  private GenericController operator; 

  @Override
  public void robotInit() {
    wcd = new WCDDriver(.8, .6);

    driver = new GenericController(0, .1);
    operator = new GenericController(1, .1);

    frontLeft = new PWMVictorSPX(3);
    frontRight = new PWMVictorSPX(1);
    backLeft = new PWMVictorSPX(4);
    backRight = new PWMVictorSPX(2);
    rotary = new PWMVictorSPX(5);
    magRight = new PWMVictorSPX(6);
    magLeft = new PWMVictorSPX(7);
  }

  @Override
  public void teleopContinuous() {
    wcd.update(driver.getRightXAxis(), -driver.getLeftYAxis());

    frontLeft.set(wcd.getLeft() * LEFT_DECREASE);
    frontRight.set(-wcd.getRight());
    backLeft.set(wcd.getLeft() * LEFT_DECREASE);
    backRight.set(-wcd.getRight());

    // frontLeft.set(-driver.getLeftYAxis());
    // frontRight.set(driver.getRightYAxis());
    // backLeft.set(-driver.getLeftYAxis());
    // backRight.set(driver.getRightYAxis());


    if (operator.getRightTrigger()) rotary.set(INTAKE_SPEED); 
    else if (operator.getLeftTrigger()) rotary.set(-INTAKE_SPEED); 
    else rotary.set(0);

    if (operator.getRightTrigger()) setMag(MAG_SPEED); 
    else if (operator.getLeftTrigger()) setMag(-MAG_SPEED); 
    else setMag(0);
  }

  private void setMag(double speed) {
    magRight.set(speed);
    magLeft.set(speed);
  }

  // Ignore the below methods
  @Override
  public void disabledInit() {}
  @Override
  public void disabledPeriodic() {}
  @Override
  public void testInit() {}
  @Override
  public void testPeriodic() {}
  @Override
  public void endCompetition() {}

}