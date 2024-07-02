package frc.robot.Subsystems.Swerve;

import static frc.robot.Constants.Module.*;

import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.math.util.Units;

import frc.Util.LoggedTunableNumber;

import org.littletonrobotics.junction.Logger;

public class Module  {

  private int index;
  private final ModuleIO io;
  private final ModuleIOInputsAutoLogged inputs = new ModuleIOInputsAutoLogged();
  private SimpleMotorFeedforward ff = new SimpleMotorFeedforward(drivekS.get(),drivekV.get(),0.0);
  private SwerveModuleState setpointState = new SwerveModuleState();

  private static final LoggedTunableNumber drivekP =
  new LoggedTunableNumber("Drive/Module/DrivekP", mkP);
private static final LoggedTunableNumber drivekD =
  new LoggedTunableNumber("Drive/Module/DrivekD", mkD);
private static final LoggedTunableNumber drivekS =
  new LoggedTunableNumber("Drive/Module/DrivekS", mkS);
private static final LoggedTunableNumber drivekV =
  new LoggedTunableNumber("Drive/Module/DrivekV", mkV);
private static final LoggedTunableNumber turnkP =
  new LoggedTunableNumber("Drive/Module/TurnkP", tkP);
private static final LoggedTunableNumber turnkD =
  new LoggedTunableNumber("Drive/Module/TurnkD", tkD);
private static final String[] moduleNames = new String[] {"FL", "FR", "BL", "BR"};

  public Module(ModuleIO io, int index) {
    this.io = io;

  }

  public void periodic() {
    io.updateInputs(inputs);
    Logger.processInputs("Drive/Module" +index, inputs);

    //Updates if controller changed
    LoggedTunableNumber.ifChanged(
      hashCode(),
      () -> ff = new SimpleMotorFeedforward(drivekS.get(), drivekV.get(), 0),
      drivekS,
      drivekV);
  LoggedTunableNumber.ifChanged(
      hashCode(), () -> io.setDrivePID(drivekP.get(), 0, drivekD.get()), drivekP, drivekD);
  LoggedTunableNumber.ifChanged(
      hashCode(), () -> io.setTurnPID(turnkP.get(), 0, turnkD.get()), turnkP, turnkD);

  }


  /** Runs characterization volts or amps depending on using voltage or current control. */
  public void runCharacterization(double turnSetpointRads, double input) {
    io.runTurnPositionSetpoint(turnSetpointRads);
    io.runCharacterization(input);
  }

  /** Sets brake mode to {@code enabled}. */
  public void setBrakeMode(boolean enabled) {
    io.setDriveBrakeMode(enabled);
    io.setTurnBrakeMode(enabled);
  }

  /** Stops motors. */
  public void stop() {
    io.stop();
  }

    /** Get all latest {@link SwerveModulePosition}'s from last cycle. */
    public SwerveModulePosition[] getModulePositions() {
      int minOdometryPositions = Math.min(inputs.odometryDrivePositionsMeters.length, inputs.odometryTurnPositions.length);
        SwerveModulePosition[] positions = new SwerveModulePosition[minOdometryPositions];
        for(int i = 0; i<minOdometryPositions; i++){
          positions[i] = new SwerveModulePosition(inputs.odometryDrivePositionsMeters[i],inputs.odometryTurnPositions[i]);
        }
        return positions;
    }

    public Rotation2d getAngle() {
      return inputs.turnAbsolutePosition;
    }
  
    /** Get position of wheel rotations in radians */
    public double getPositionRads() {
      return inputs.drivePositionRads;
    }
  
    /** Get position of wheel in meters. */
    public double getPositionMeters() {
      return inputs.drivePositionRads * Units.inchesToMeters(2);
    }
  
    /** Get velocity of wheel in m/s. */
    public double getVelocityMetersPerSec() {
      return inputs.driveVelocityRadsPerSec * Units.inchesToMeters(2);
    }
  
    /** Get current {@link SwerveModulePosition} of module. */
    public SwerveModulePosition getPosition() {
      return new SwerveModulePosition(getPositionMeters(), getAngle());
    }
  
    /** Get current {@link SwerveModuleState} of module. */
    public SwerveModuleState getState() {
      return new SwerveModuleState(getVelocityMetersPerSec(), getAngle());
    }
  
    /** Get velocity of drive wheel for characterization */
    public double getCharacterizationVelocity() {
      return inputs.driveVelocityRadsPerSec;
    }

}