package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.motorcontrol.PWMVictorSPX;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.lib.GenericController;
import frc.robot.lib.TorqueIterative;
import frc.robot.lib.WCDDriver;

public class Robot extends TorqueIterative {
  
  private PWMVictorSPX frontLeft;  // Port 2
  private PWMVictorSPX frontRight; // Port 4
  private PWMVictorSPX backLeft;   // Port 1
  private PWMVictorSPX backRight;  // Port 3
  
  // Algorithm for converting joystick values to motor values
  private WCDDriver wcd;

  private GenericController driver;
  private GenericController operator;

  @Override
  public void robotInit() {
    wcd = new WCDDriver(.8, .6);

    driver = new GenericController(0, .1);
    operator = new GenericController(1, .1);

    frontLeft = new PWMVictorSPX(2);
    frontRight = new PWMVictorSPX(4);
    backLeft = new PWMVictorSPX(1);
    backRight = new PWMVictorSPX(3);
  }

  @Override
  public void teleopContinuous() {
    wcd.update(driver.getLeftXAxis(), -driver.getRightYAxis());

    frontLeft.set(wcd.getLeft());
    frontRight.set(-wcd.getRight());
    backLeft.set(wcd.getLeft());
    backRight.set(-wcd.getRight());
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