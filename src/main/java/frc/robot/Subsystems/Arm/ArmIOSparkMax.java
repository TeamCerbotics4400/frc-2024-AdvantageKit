package frc.robot.Subsystems.Arm;

import com.revrobotics.CANSparkMax;

import edu.wpi.first.math.controller.ArmFeedforward;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.math.trajectory.TrapezoidProfile.State;

import javax.swing.text.Position;

import com.ctre.phoenix6.configs.CANcoderConfiguration;
import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.signals.AbsoluteSensorRangeValue;
import com.ctre.phoenix6.signals.SensorDirectionValue;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;

public class ArmIOSparkMax implements ArmIO{

    private final CANSparkMax leftMotor = new CANSparkMax(9, MotorType.kBrushless);
    private final CANSparkMax rightMotor = new CANSparkMax(10, MotorType.kBrushless);
 
    private final CANcoder m_encoder = new CANcoder(17, "Swerve_Canivore");
 
    private final TrapezoidProfile.Constraints m_constraints =
    new TrapezoidProfile.Constraints(500, 500);

    private final ProfiledPIDController m_controller =
    new ProfiledPIDController(0.32, 0.42, 0.0055, m_constraints, 0.02);

    private final ArmFeedforward m_feedforward = new ArmFeedforward(0.013804, 0.93532, 0.00028699, 0.00052411);
        
    private final CANcoderConfiguration encoderConfig = new CANcoderConfiguration();

    public ArmIOSparkMax(){

    encoderConfig.MagnetSensor.SensorDirection = SensorDirectionValue.Clockwise_Positive;
    encoderConfig.MagnetSensor.AbsoluteSensorRange = AbsoluteSensorRangeValue.Unsigned_0To1;
    encoderConfig.MagnetSensor.MagnetOffset = 0.0;

    m_encoder.getPosition().setUpdateFrequency(100);
    m_encoder.getConfigurator().apply(encoderConfig);

        leftMotor.restoreFactoryDefaults();
        rightMotor.restoreFactoryDefaults();
    
        leftMotor.setInverted(true);
        rightMotor.setInverted(false);
    
        leftMotor.setSmartCurrentLimit(40);
        rightMotor.setSmartCurrentLimit(40);
    
        leftMotor.setCANTimeout(0);
        rightMotor.setCANTimeout(0);
    
        rightMotor.setIdleMode(IdleMode.kBrake);
        leftMotor.setIdleMode(IdleMode.kBrake);

    }

  @Override
  public void updateInputs(ArmIoInputs inputs){
    inputs.leftAppliedVolts = leftMotor.getBusVoltage();
    inputs.rightAppliedVolts = rightMotor.getBusVoltage();
    inputs.leftTempCelcius = leftMotor.getMotorTemperature();
    inputs.rightTempCelcius = rightMotor.getMotorTemperature();

    inputs.currentAngle = getArmAngle();
    inputs.setPoint = getController().getGoal().position;
  }

  public double getArmAngle() {            
    return (m_encoder.getAbsolutePosition().getValueAsDouble() * 360)  + 51.6;
  }

  public ProfiledPIDController getController() {
    return m_controller;
  }

  public void setVoltage(double output, State setpoint){

    double feedfoward =  m_feedforward.calculate(setpoint.position, setpoint.velocity);

    rightMotor.setVoltage(output + feedfoward);
    leftMotor.setVoltage(output + feedfoward);
  }

  @Override
  public void putThisInPeriodicBecauseOtherwiseItWontWorkAndItsReallyImportant(){
    setVoltage(m_controller.calculate(getArmAngle()),m_controller.getSetpoint());
  }

  @Override
  public void setBrakeMode(){
    rightMotor.setIdleMode(IdleMode.kBrake);
    leftMotor.setIdleMode(IdleMode.kBrake);
  }

  @Override
  public void setCoastMode(){
    rightMotor.setIdleMode(IdleMode.kCoast);
    leftMotor.setIdleMode(IdleMode.kCoast);
  }

  @Override
  public void positionFunction(double position){
    getController().reset(getArmAngle());
    m_controller.setGoal(position);
  }
  

}
 