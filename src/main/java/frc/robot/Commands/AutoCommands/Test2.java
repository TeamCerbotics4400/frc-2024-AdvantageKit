package frc.robot.Commands.AutoCommands;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.path.PathPlannerPath;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Commands;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Test2 extends AutoCommand {

  private boolean test = false;
  private final PathPlannerPath startToFirst;
  private final PathPlannerPath firstToFarShoot;

  public Test2() {
    startToFirst = PathPlannerPath.fromPathFile("Copy of Path1");
    firstToFarShoot = PathPlannerPath.fromPathFile("Copy of Path2");
              test = true;

    addCommands(
        Commands.deadline(
            Commands.sequence(
                AutoBuilder.followPath(startToFirst),
                AutoBuilder.followPath(startToFirst))));

                SmartDashboard.putBoolean("t", test);
  }


  @Override
  public List<Pose2d> getAllPathPoses() {
    return Stream.of(
            startToFirst.getPathPoses(),
            firstToFarShoot.getPathPoses()
    )
        .flatMap(Collection::stream)
        .collect(Collectors.toList());
  }

  @Override
  public Pose2d getStartingPose() {
    return startToFirst
        .getTrajectory(new ChassisSpeeds(), new Rotation2d())
        .getInitialTargetHolonomicPose();
  }
}