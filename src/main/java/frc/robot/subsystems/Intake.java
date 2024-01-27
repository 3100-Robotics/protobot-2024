package frc.robot.subsystems;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.intakeConstants;

/**
 * this class contains all the functionality 
 * required to run a simple intake that has two spark max motor controllers
 */
public class Intake extends SubsystemBase{
    // create the two spark maxes with ids from constants and tell them that they are running brushless motors
    private CANSparkMax collector1 = new CANSparkMax(intakeConstants.intakeMotor1ID, MotorType.kBrushless);
    private CANSparkMax collector2 = new CANSparkMax(intakeConstants.intakeMotor2ID, MotorType.kBrushless);

    public Intake() {
        collector1.restoreFactoryDefaults();
        collector2.restoreFactoryDefaults();

        collector1.setInverted(true);

        collector1.setIdleMode(IdleMode.kBrake);
        collector2.setIdleMode(IdleMode.kBrake);

        // tell the second spark max to do exactly the same thing as the first spark max
        collector2.follow(collector1, true);
    }

    /**
     * set the first spark max to the given precent speed
     * subsequently sets the second spark max to the same speed because
     * the second spark max is following the first
     * @param speed the desired %speed (-1.0 to 1.0)
     */
    public void set(double speed) {
        collector1.set(speed);
    }

    /**
     * creates a command using a given speed that, when scheduled, will run the intake at said speed
     * @param speed the %speed at which to run the intake (-1.0 to 1.0)
     * @return the command to run to set the intakes speed
     */
    public Command setCommand(DoubleSupplier speed) {
        return this.run(() -> set(speed.getAsDouble()));
    }

    public Command intakeUntil(BooleanSupplier when) {
        return setCommand(() -> 0.6).raceWith(Commands.waitUntil(when).andThen(Commands.waitSeconds(1)));
    }
}
