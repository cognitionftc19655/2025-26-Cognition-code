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
            robot.conveyorBeltOn();

        });
        thread.start();

        /*
        while (opModeIsActive()) {
            ((DcMotorEx) robot.arm).setVelocity(2100);
            robot.arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.arm.setTargetPosition((int) (robot.armPosition));


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
