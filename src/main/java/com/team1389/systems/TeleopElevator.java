package com.team1389.systems;

import com.team1389.hardware.inputs.software.DigitalIn;
import com.team1389.hardware.inputs.software.RangeIn;
import com.team1389.hardware.outputs.software.RangeOut;
import com.team1389.hardware.value_types.Percent;
import com.team1389.hardware.value_types.Position;
import com.team1389.hardware.value_types.Speed;
import com.team1389.hardware.value_types.Value;

/**
 * Teleop Elevator System, implements buttons for manipulator control
 * 
 * @author Raffi
 *
 */
public class TeleopElevator extends Elevator
{

	// can have max bound to start, decided that low and high switch can just be
	// high switch
	DigitalIn zeroBtn, switchBtn, scaleLowBtn, scaleMiddleBtn, scaleHighBtn;
	DigitalIn manualBtn;
	RangeIn<Value> ctrlAxis;
	boolean manual;

	public TeleopElevator(DigitalIn zero, RangeIn<Position> elevPos, RangeIn<Speed> elevVel, RangeOut<Percent> elevVolt,
			DigitalIn zeroBtn, DigitalIn switchBtn, DigitalIn scaleLowBtn, DigitalIn scaleMiddleBtn,
			DigitalIn scaleHighBtn, DigitalIn manualBtn, RangeIn<Value> ctrlAxis)
	{
		super(zero, elevPos, elevVel, elevVolt);
		this.zeroBtn = zeroBtn;
		this.switchBtn = switchBtn;
		this.scaleLowBtn = scaleLowBtn;
		this.scaleMiddleBtn = scaleMiddleBtn;
		this.scaleHighBtn = scaleHighBtn;
		this.manualBtn = manualBtn;
		this.ctrlAxis = ctrlAxis;
	}

	/**
	 * If we're in a manual or the manual mode is being pressed, update manual
	 * mode, else update advanced mode
	 */

	@Override
	public void update()
	{
		manual = manual ^ manualBtn.get();
		if (manual)
		{
			updateManual();
		} else
		{
			updateAdvanced();
		}

	}

	/**
	 * updates the mode that uses motion profiles to go to certain heights
	 */
	private void updateAdvanced()
	{
		if (zeroBtn.get())
		{
			goToZero();
		} else if (switchBtn.get())
		{
			goToSwitch(true);
		} else if (scaleLowBtn.get())
		{
			goToScaleLow(true);
		} else if (scaleHighBtn.get())
		{
			goToScaleHigh(true);
		}

		super.update();
	}

	/**
	 * controls the height of the elevator directly proportional to how much the
	 * manipulator's stick is being pushed
	 */
	private void updateManual()
	{
		elevVolt.set(ctrlAxis.get());
	}

}
