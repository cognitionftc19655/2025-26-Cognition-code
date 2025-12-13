package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;

@Autonomous(name = "BlueAutoL", group = "Autonomous")
public class BlueAutoL extends LinearOpMode{
    HardwarePushbot robot = new HardwarePushbot();
    private ElapsedTime runtime = new ElapsedTime(); //Don't use right now
    @Override
    public void runOpMode() {

        robot.init(hardwareMap);

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Status", "Ready to run");
        telemetry.update();


        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        Thread thread = new Thread(() ->{

            robot.conveyorBeltOn();
            robot.forward(0.4);
            sleep(1000);
            robot.zero();
            robot.turretAim(0.2);
            sleep(4000);
            robot.turretAimStop();

            sleep(1000);

            robot.outTakeOn(0.5);
            robot.turretServoForward();
            sleep(500);
            robot.turretServoBack();
            robot.outTakeOff();
            sleep(500);
            robot.turretServoStop();

            sleep(1000);

            robot.backward(1.0);
            sleep(500);
            robot.zero();
            robot.turnRight(0.5);
            sleep(750);
            robot.zero();
            robot.backward(1.0);
            sleep(800); //change
            robot.zero();

            sleep(1000);

            robot.turnRight(0.5);
            sleep(500);
            robot.zero();
            robot.intakeStart();
            robot.backward(1.0);
            sleep(1000);
            robot.zero();
            sleep(500);
            robot.intakeStop();

            sleep(1000);

            robot.forward(1.0);
            sleep(1000);
            robot.zero();
            robot.turnLeft(0.5);
            sleep(500);
            robot.zero();
            robot.forward(1.0);
            sleep(800); // change
            robot.zero();

            sleep(1000);

            robot.turnLeft(0.5);
            sleep(750);
            robot.zero();
            robot.backward(1.0);
            sleep(500);
            robot.zero();
            robot.outTakeOn(0.5);
            robot.turretServoForward();
            sleep(500);
            robot.turretServoBack();
            robot.outTakeOff();
            sleep(500);
            robot.turretServoStop();

            sleep(1000);

            robot.backward(1.0);
            sleep(500);
            robot.zero();
            robot.turnRight(0.5);
            sleep(750);
            robot.zero();

            sleep(1000);

            robot.backward(1.0);
            sleep(2000); //change
            robot.zero();
            robot.turnRight(0.5);
            sleep(500);
            robot.zero();
            robot.intakeStart();
            robot.backward(1.0);
            sleep(1000);
            robot.zero();
            sleep(500);
            robot.intakeStop();

            sleep(1000);

            robot.forward(1.0);
            sleep(1000);
            robot.zero();
            robot.turnLeft(0.5);
            sleep(500);
            robot.zero();
            robot.forward(1.0);
            sleep(2000); // change
            robot.zero();
            robot.turnLeft(0.5);
            sleep(750);
            robot.zero();

            sleep(1000);

            robot.backward(1.0);
            sleep(500);
            robot.zero();
            robot.outTakeOn(0.5);
            robot.turretServoForward();
            sleep(500);
            robot.turretServoBack();
            robot.outTakeOff();
            sleep(500);
            robot.turretServoStop();


//aiming servo: right; (positive) forward  / left;backward (negative)

        });
        thread.start();

        /*
        while (opModeIsActive()) {
            ((DcMotorEx) robot.arm).setVelocity(2100);
            robot.arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.arm.setTargetPosition((int) (robot.armPosition));

        }

         */

//        robot.intake();
//        sleep(5000);
//        robot.intake();
//        sleep(5000);
//        robot.backward(0.3);
//        sleep(1000);
//        robot.zero();
//        robot.intakeOff();
//        robot.scoreSampleLow();
//        robot.forward(0.3);
//        sleep(1000);
//        robot.zero();
//        robot.intakeDeposit();
//        robot.resetPosition();

    }


}
