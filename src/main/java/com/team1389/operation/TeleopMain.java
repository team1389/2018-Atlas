package com.team1389.operation;

import com.team1389.hardware.controls.ControlBoard;
import com.team1389.hardware.inputs.software.RangeIn;
import com.team1389.hardware.value_types.Speed;
import com.team1389.robot.RobotSoftware;
import com.team1389.system.Subsystem;
import com.team1389.system.SystemManager;
import com.team1389.system.drive.CheesyDriveSystem;
import com.team1389.systems.SimpleElevator;
import com.team1389.systems.TeleopElevator;
import com.team1389.watch.Watcher;

public class TeleopMain
{
	SystemManager manager;
	ControlBoard controls;
	RobotSoftware robot;
	Watcher watcher;
	Subsystem driveSystem;
	Subsystem elevatorSystem;

	public TeleopMain(RobotSoftware robot)
	{
		this.robot = robot;
	}

	public void init()
	{
		controls = ControlBoard.getInstance();
		driveSystem = setUpDriveSystem();
		elevatorSystem = setUpSimpleElevatorSystem();
		manager = new SystemManager(driveSystem, elevatorSystem);
		manager.init();

	}

	private Subsystem setUpDriveSystem()
	{

		return new CheesyDriveSystem(robot.drive.getAsTank(), controls.xLeftDriveY(), controls.xDriveX(),
				controls.xDriveBtn());

	}

	private Subsystem setUpSimpleElevatorSystem()
	{
		return new SimpleElevator(controls.leftStickYAxis().copy().invert(), robot.elevator,
				controls.rightStickYAxis().copy().invert(), robot.armIntake, robot.elevatorServoPosition,
				controls.yButton());
	}

	private Subsystem setUpElevatorSystem()
	{

		return new TeleopElevator(robot.elevatorZero.getSwitchInput(), robot.elevatorPosition,
				new RangeIn<Speed>(Speed.class, () -> 0.0, 0, 1), robot.elevator, robot.elevatorServoPosition,
				robot.armIntake, controls.startButton(), controls.xButton(), controls.aButton(), controls.bButton(),
				controls.yButton(), controls.startButton(), controls.leftStickYAxis(), controls.rightStickYAxis());
	}

	public void periodic()
	{

		manager.update();

	}
}
