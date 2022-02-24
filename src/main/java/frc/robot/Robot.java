package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import edu.wpi.first.wpilibj.motorcontrol.PWMVictorSPX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.lib.GenericController;
import frc.robot.lib.TorqueIterative;
import frc.robot.lib.WCDDriver;

public class Robot extends TorqueIterative {
  
  private PWMVictorSPX frontLeft;  // Port 2
  private PWMVictorSPX frontRight; // Port 4
  private PWMVictorSPX backLeft;   // Port 1
  private PWMVictorSPX backRight; // Port 3
  private CANSparkMax rotary;
  
  // Algorithm for converting joystick values to motor values
  private WCDDriver wcd;

  private GenericController driver;
  // private GenericController operator; Will implement later

  @Override
  public void robotInit() {
    wcd = new WCDDriver(.8, .6);

    driver = new GenericController(0, .1);
    // operator = new GenericController(1, .1);

    frontLeft = new PWMVictorSPX(3);
    frontRight = new PWMVictorSPX(1);
    backLeft = new PWMVictorSPX(4);
    backRight = new PWMVictorSPX(2);
    rotary = new CANSparkMax(1, MotorType.kBrushless);
  }

  @Override
  public void teleopContinuous() {
    wcd.update(driver.getLeftXAxis(), -driver.getRightYAxis());

    frontLeft.set(wcd.getLeft());
    frontRight.set(-wcd.getRight());
    backLeft.set(wcd.getLeft());
    backRight.set(-wcd.getRight());

    if (driver.getAButton()) rotary.set(0.5); else rotary.set(0.5);
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