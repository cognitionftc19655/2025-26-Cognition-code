package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;

@Autonomous(name = "RedAutoL", group = "Autonomous")
public class Autonomous1 extends LinearOpMode{
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
            robot.intakeCollect();
            sleep(500);
            robot.scoreSpecimen();
            robot.backward(0.3);
            sleep(1000);
            robot.turnLeft(0.3);
            sleep(1000);
            robot.forward(0.5);
            sleep(1500);
            robot.backward(0.4);
            sleep(1200);
            robot.zero();
            robot.armPosition = 68 * robot.ARM_TICKS_PER_DEGREE;
            robot.forward(0.3);
            sleep(600);
            robot.intakeDeposit();
            sleep(1000);
            robot.zero();
            robot.strafeRight(0.4);
            sleep(1500);
            robot.backward(0.5);
            sleep(1350);
            robot.strafeLeft(0.3);
            sleep(500);
            robot.zero();
            robot.resetPosition();



        });
        thread.start();

        while (opModeIsActive()) {
            ((DcMotorEx) robot.arm).setVelocity(2100);
            robot.arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.arm.setTargetPosition((int) (robot.armPosition));

        }

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
