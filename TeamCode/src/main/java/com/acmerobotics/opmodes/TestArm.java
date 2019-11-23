package com.acmerobotics.opmodes;

import com.acmerobotics.robot.Arm;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;

@TeleOp(name="ArmTeleOp")
public class TestArm extends LinearOpMode {

    public boolean isYPressed = false;
    public boolean isAPressed = false;

    @Override
    public void runOpMode() throws InterruptedException {
        Arm arm = new Arm();

        arm.init(hardwareMap);

        arm.resetEncoder();

        waitForStart();

        while (!isStopRequested()) {

            if (gamepad1.y) {

                isYPressed = true;
            }

            else if (isYPressed) {
                /////////////////move 20 degrees from resting point
                arm.goToPosition(0);
                //target position should be 31.111

                isYPressed = false;
            }


            if (gamepad1.a) {

                isAPressed = true;
            }

            else if (isAPressed) {
                /////////////////move x degrees from resting point
                arm.goToPosition(0);

                isAPressed = false;
            }
            telemetry.addData("encoder: ", arm.armMotor.getCurrentPosition());
            telemetry.addData("target_position: ", arm.targetPosition);
            telemetry.addData("power", arm.armMotor.getPower());
            telemetry.update();


        }
    }
}