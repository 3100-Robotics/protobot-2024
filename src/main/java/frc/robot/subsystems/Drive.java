package frc.robot.subsystems;

import java.util.function.DoubleSupplier;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.driveConstants;

/**
 * this class includes all the functionality needed to run the drivetrain
 */
public class Drive extends SubsystemBase{
    // create 4 TalonSRX's with ids from constants. Two for each side of the drivetrain
    // creating a WPI_TalonSRX instead of a normal TalonSRX allows other classes that depend on being
    // given a class that implements edu.wpi.first.wpilibj.RobotController to work
    private WPI_TalonSRX frontLeftDrive = new WPI_TalonSRX(driveConstants.frontLeftDriveID);
    private WPI_TalonSRX frontRightDrive = new WPI_TalonSRX(driveConstants.frontRightDriveID);
    private WPI_TalonSRX backLeftDrive = new WPI_TalonSRX(driveConstants.backLeftDriveID);
    private WPI_TalonSRX backRightDrive = new WPI_TalonSRX(driveConstants.backRightDriveID);

    // creates a DifferentialDrive instance to be used to drive the front two TalonSRX's
    private DifferentialDrive drive = new DifferentialDrive(frontLeftDrive, frontRightDrive);

    public Drive() {
        // command the rear two motors to follow the two TalonSRX's in front
        backLeftDrive.follow(frontLeftDrive);
        backRightDrive.follow(frontRightDrive);

        // set the right TalonSRX to be inverted
        // also set the left TalonSRX to not be inverted to be safe
        frontLeftDrive.setInverted(false);
        frontRightDrive.setInverted(true);
    }

    /**
     * drive the robot in the arcade style.
     * takes one input for forward speed and another for rotation
     * @param speed the desired speed (% from -1.0 to 1.0)
     * @param rot the desired rotation (% from -1.0 to 1.0)
     */
    public void arcadeDrive(double speed, double rot) {
        // command the DifferentialDrive instance to drive its motors with given speed and rotation
        drive.arcadeDrive(speed, rot);
    }

    /**
     * creates a command using a given speed and rotation suppliers that, 
     * when scheduled, will run the drivetrain with the current values from the suppliers
     * @param speed the function to run to get the desired speed
     * @param rot the function to run to get the desired rotation
     * @return
     */
    public Command driveCommand(DoubleSupplier speed, DoubleSupplier rot) {
        return this.run(() -> arcadeDrive(
            speed.getAsDouble(), 
            rot.getAsDouble()));
    }
}
