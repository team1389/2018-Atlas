package com.team1389.robot;

import com.team1389.hardware.inputs.software.AngleIn;
import com.team1389.hardware.inputs.software.RangeIn;
import com.team1389.hardware.outputs.software.RangeOut;
import com.team1389.hardware.value_types.Percent;
import com.team1389.hardware.value_types.Position;
import com.team1389.system.drive.SixDriveOut;

//all mapped to meters
public class RobotSoftware extends RobotHardware {
	private static RobotSoftware INSTANCE = new RobotSoftware();

	// misc
	public final AngleIn<Position> robotAngle = gyro.getAngleInput().invert();

	// Drivetrain

	public final RangeOut<Percent> right = driveRightT.getVoltageController()
			.addFollowers(driveRightVA.getVoltageController().addFollowers(driveRightVB.getVoltageController()));
	public final RangeOut<Percent> left = driveLeftT.getVoltageController()
			.addFollowers(driveLeftVA.getVoltageController().addFollowers(driveLeftVB.getVoltageController()));
	public final SixDriveOut<Percent> drive = new SixDriveOut<Percent>(driveLeftT.getVoltageController(),
			driveRightT.getVoltageController(), driveLeftVA.getVoltageController(), driveRightVA.getVoltageController(),
			driveLeftVB.getVoltageController(), driveRightVB.getVoltageController());
	public final RangeOut<Percent> armIntake = armIntakeA.getVoltageController()
			.addFollowers(armIntakeB.getVoltageController());
	public final RangeOut<Percent> elevator = elevatorLeft.getVoltageController()
			.addFollowers(elevatorRight.getVoltageController());
	// in meters
	public final RangeIn<Position> lPos = driveLeftT.getSensorPositionStream().mapToRange(0, 1)
			.scale(RobotConstants.WheelDiameter);
	public final RangeIn<Position> rPos = driveRightT.getSensorPositionStream().mapToRange(0, 1)
			.scale(RobotConstants.WheelDiameter);

	public static RobotSoftware getInstance() {
		return INSTANCE;
	}

	public RobotSoftware() {
	}

}
