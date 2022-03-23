package frc.robot;

import edu.wpi.first.wpilibj.CAN;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.motorcontrol.PWMVictorSPX;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.lib.GenericController;
import frc.robot.lib.TorqueIterative;
import frc.robot.lib.WCDDriver;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;


/**
 * @author Jaden, Nigel, Justus
 */
public class Robot extends TorqueIterative {

  private final double INTAKE_SPEED = 1;
  private final double MAG_SPEED = 1;
  private final double CLIMB_SPEED = .8;

  private PWMVictorSPX frontLeft;  // Port 2
  private PWMVictorSPX frontRight; // Port 4
  private PWMVictorSPX backLeft;   // Port 1
  private PWMVictorSPX backRight; // Port 3
  private PWMVictorSPX rotary; // Port 5
  private PWMVictorSPX magRight; // Port 6
  private PWMVictorSPX magLeft; // Port 7
  private CANSparkMax climb;  // Port 8

  
  // Algorithm for converting joystick values to motor values
  private WCDDriver wcd;

  private GenericController driver;
  private GenericController operator; 

  private boolean hasAutoStarted = false;
  private double start = 0;

  @Override
  public void alwaysContinuous() {
    if (DriverStation.isAutonomousEnabled()) {
      if (!hasAutoStarted) {
        hasAutoStarted = true;
        start = Timer.getFPGATimestamp();
      }

      double time = Timer.getFPGATimestamp() - start; 

      // if (Timer.getFPGATimestamp() - start > 8)
      //   if (Timer.getFPGATimestamp() - start > 11)
      //     setSpeeds(0, 0);
      //   else
      //     setSpeeds(.2, .2);
      // else 
      //   setSpeeds(.0, .0);

      if (time < 2)
        setMag(1);
      else if (time < 3) {
        setSpeeds(-.6, -.6);
        setMag(0);
      }
      else if (time < 8)
        setSpeeds(.3, .3);
      else if (time < 15)
        setSpeeds(0, 0);

    }
  }

  @Override
  public void robotInit() {
    wcd = new WCDDriver(.8, 1);

    driver = new GenericController(0, .1);
    operator = new GenericController(1, .1);

    frontLeft = new PWMVictorSPX(3);
    frontRight = new PWMVictorSPX(1);
    backLeft = new PWMVictorSPX(4);
    backRight = new PWMVictorSPX(2);
    rotary = new PWMVictorSPX(5);
    magRight = new PWMVictorSPX(6);
    magLeft = new PWMVictorSPX(7);
    climb = new CANSparkMax(1, MotorType.kBrushless);
  }

  private void setSpeeds(double left, double right) {
    frontLeft.set(left);
    frontRight.set(-right);
    backLeft.set(left);
    backRight.set(-right);
  }

  @Override
  public void teleopContinuous() {
    wcd.update(driver.getRightXAxis(), -driver.getLeftYAxis());

    setSpeeds(wcd.getLeft(), wcd.getRight());

    // frontLeft.set(-driver.getLeftYAxis());
    // frontRight.set(driver.getRightYAxis());
    // backLeft.set(-driver.getLeftYAxis());
    // backRight.set(driver.getRightYAxis());


    if (driver.getRightTrigger()) rotary.set(INTAKE_SPEED); 
    else if (driver.getLeftTrigger()) rotary.set(-INTAKE_SPEED); 
    else rotary.set(0);

    if (operator.getRightTrigger()) setMag(MAG_SPEED); 
    else if (operator.getLeftTrigger()) setMag(-MAG_SPEED); 
    else setMag(0);

    if(operator.getYButton()) setclimb(1);
    else if (operator.getAButton()) setclimb(-1);
    else setclimb(0);

    SmartDashboard.putNumber("Right Speed", wcd.getRight());
    SmartDashboard.putNumber("Left Speed", wcd.getLeft());

  }

  private void setclimb(int i) {
      climb.set(i * CLIMB_SPEED);  
  }


  private void setMag(double speed) {
    magRight.set(speed);
    magLeft.set(speed);
  }


  @Override
  public void autoInit() {
    start = Timer.getFPGATimestamp();
    System.out.println("Auto Init");
  }

  @Override
  public void autoPeriodic() {
    // if (Timer.getFPGATimestamp() - start > 1)
    //   if (Timer.getFPGATimestamp() - start > 3)
    //     setSpeeds(0, 0);
    //   else
    //     setSpeeds(.2, .2);
    // else 
    //   setSpeeds(.0, .0);
    System.out.println("Auto Periodic");
    if (Timer.getFPGATimestamp() - start > 5) {
      //setSpeeds(.2, .2);
    } if (Timer.getFPGATimestamp() - start > 2) {
      setSpeeds(0, 0);
    }

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