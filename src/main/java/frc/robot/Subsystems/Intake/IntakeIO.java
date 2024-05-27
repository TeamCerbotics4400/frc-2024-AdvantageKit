package frc.robot.Subsystems.Intake;

import org.littletonrobotics.junction.AutoLog;

/** Gripper subsystem hardware interface. */
public interface IntakeIO {
  /** Contains all of the input data received from hardware. */
  @AutoLog
  public static class IntakeIOInputs {
    public double positionRad = 0.0;
    public double velocityRadPerSec = 0.0;
    public double appliedVolts = 0.0;
    public double[] currentAmps = new double[] {};
    public double[] tempCelcius = new double[] {};
  }

  /** Updates the set of loggable inputs. */
  public default void updateInputs(IntakeIO inputs) {}

  /** Run the intake open loop at the specified voltage. */
  public default void setVoltage(double volts) {}

  /** Enable or disable brake mode on the intake. */
  public default void setBrakeMode(boolean enable) {}
}