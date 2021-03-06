package com.acmerobotics.opmodes.AutoOpModes;

import com.acmerobotics.robot.Drive;
import com.acmerobotics.robot.FoundationMover;
import com.acmerobotics.robot.armEncoder;
import com.acmerobotics.robot.liftEncoder;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="Red Foundation")
public class RedFoundation extends LinearOpMode {
    private int state;

    // FtcDashboard dashboard  = FtcDashboard.getInstance();
    //Telemetry dashboardTelemetry = dashboard.getTelemetry();

    @Override
    public void runOpMode() throws InterruptedException {
        Drive drive = new Drive(hardwareMap, false);
        FoundationMover foundationMover = new FoundationMover(hardwareMap);
        armEncoder arm = new armEncoder(hardwareMap);
        liftEncoder lift = new liftEncoder(hardwareMap);
        ElapsedTime time = new ElapsedTime();


        state = 0;
        drive.resetEncoders();
        drive.resetAngle();
        drive.resetEncoderOmni();
        time.reset();
        drive.update();

        telemetry.addData("state", state);
        telemetry.addData("current pos", drive.getCurrentPos());
        telemetry.update();

        waitForStart();

        telemetry.clearAll();

        while(!isStopRequested()) {

            switch (state) {

                case 0:

                    drive.goToStrafingPos(40, 0.5, "right");
                    state++;

                    break;

                case 1:

                    if(drive.atStrafingPos()){
                        drive.stopMotors();
                        drive.resetEncoders();

                        state++;
                    }

                    break;


                case 2:

                    drive.goToPosition(29, 0.25);
                    state++;

                    break;

                case 3:

                    if(drive.atLinearPos()){
                        drive.moveForward(0.18);
                        foundationMover.moveToGrab();

                        Thread.sleep(1000);

                        state++;

                    }

                    break;


                case 4:

                    if(drive.atLinearPos()){
                        drive.stopMotors();

                    } else {

                        drive.resetAngle();
                        state++;
                    }

                    break;


                case 5:



                    drive.setDegrees(179);


                    if(drive.getAngle() == 0) {
                        drive.clockwise();
                    }

                    if(drive.getDegrees() > 0) {

                        if(drive.getAngle() < drive.getDegrees()){
                            drive.counterClockwise();

                        } else {

                            drive.stopMotors();
                            state++;
                        }

                    } else {

                        if(drive.getAngle() > drive.getDegrees()){
                            drive.clockwise();

                        } else {

                            drive.stopMotors();
                            state++;
                        }

                    }

                    break;

                case 6:

                    drive.resetEncoders();

                    drive.goToPosition(20, 0.75);

                    state++;

                    break;

                case 7:

                    if(drive.atLinearPos()){
                        drive.stopMotors();

                        state++;
                    }

                    break;

                case 8:

                    foundationMover.moveToStore();

                    state++;

                    break;


                case 9:

                    time.reset();

                    state++;

                    break;

                case 10:

                    if(time.seconds() < 0.2){
                        drive.moveBack(0.5);


                    } else {

                        drive.resetAngle();
                        state++;
                    }

                    break;


                case 11:

                    drive.setDegrees(-75);

                    drive.getDegrees();

                    if(drive.getAngle() == 0) {
                        drive.clockwise();
                    }

                    if(drive.getDegrees() > 0) {

                        if(drive.getAngle() < drive.getDegrees()){
                            drive.counterClockwise();

                        } else {

                            drive.stopMotors();
                            state++;
                        }

                    } else {

                        if(drive.getAngle() > drive.getDegrees()){
                            drive.clockwise();

                        } else {

                            drive.stopMotors();
                            state++;
                        }

                    }

                    break;


                case 12:

                    drive.resetEncoders();

                    drive.goToPosition(41, -0.5);

                    state++;

                    break;

                case 13:

                    if(drive.atLinearPos()){
                        drive.stopMotors();

                        state++;
                    }

                    break;


                case 14:

                    arm.runTo(110);

                    if (lift.bottomSet){
                        state++;
                    }

                    else{
                        lift.tightenLiftString();

                        lift.goToBottom();
                    }

                    break;


            }



            telemetry.addData("state", state);
            telemetry.addData("current pos", drive.getCurrentPos());
            telemetry.addData("target pos", drive.getTargetMotorPos());
            telemetry.addData("motors stopped", drive.areMotorsStopped());
            telemetry.addData("current angle", drive.getCurrentAngle());
            telemetry.addData("current omni pos inches", drive.getCurrentTrackerPosInches());
            telemetry.addData("target omni pos", drive.getTargetOmniPos());
            telemetry.update();

        }


    }
}
