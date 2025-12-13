package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;



@TeleOp(name="Teleop2026 Cognition", group ="Test")
public class Teleop2026 extends LinearOpMode{
    // Declare our motors
    // Make sure your ID's match your configuration
    DcMotor motorFrontLeft;
    DcMotor motorBackLeft;
    DcMotor motorFrontRight;
    DcMotor motorBackRight;
    // DcMotor arm = null;
    // Servo intake = null;
    // Servo wrist = null;
    DcMotor intakeMotor = null;
    DcMotor turretLeft = null;
    DcMotor turretRight = null;
    CRServo aimingServo = null;
    DcMotor conveyorBelt = null;
    Servo turretServo = null;

    // Declare variables
    boolean secondHalf = false;                 // Use to hint the drivers for end game start
    final double HALF_TIME = 60.0;              // Wait this many seconds before alert for half-time
    ElapsedTime runtime = new ElapsedTime();    // Use to determine when end game is starting.

    final double conveyorBeltSpeed = 1.0;
    final double ARM_TICKS_PER_DEGREE =
            28 // number of encoder ticks per rotation of the bare motor
                    * 250047.0 / 4913.0 // This is the exact gear ratio of the 50.9:1 Yellow Jacket gearbox
                    * 100.0 / 20.0 // This is the external gear reduction, a 20T pinion gear that drives a 100T hub-mount gear
                    * 1/360.0; // we want ticks per degree, not per rotation
//    In these variables you'll see a number in degrees, multiplied by the ticks per degree of the arm.
//    This results in the number of encoder ticks the arm needs to move in order to achieve the ideal
//    set position of the arm. For example, the ARM_SCORE_SAMPLE_IN_LOW is set to
//    160 * ARM_TICKS_PER_DEGREE. This asks the arm to move 160° from the starting position.
//    If you'd like it to move further, increase that number. If you'd like it to not move
//    as far from the starting position, decrease it.






    final double INTAKE_COLLECT    = -1.0;
    final double INTAKE_OFF        =  0.0;
    final double OUTTAKE_POWER    =  0.6;


    final double TURRET_SERVO_START = 0.5;
    final double TURRET_SERVO_UP = 0.8;
    final double TURRET_SERVO_STOP = 0.5;

    final double TURRET_SERVO_DOWN = 0.2;
    final double FUDGE_FACTOR = 15 * ARM_TICKS_PER_DEGREE;

    // Variables that are used to set the arm to a specific position
    double armPositionFudgeFactor;
    double mainPower = 0.8; // maintain ratio, change this to change speed of robot
    boolean fastMode = true;
    boolean intakeOn = false;
    boolean aWasPressed = false;

    boolean outtakeOn = false;
    boolean outtakeWasPressed = false;
    boolean reverseOuttakeOn = false;
    boolean reverseOuttakeWasPressed = false;

    boolean conveyorBeltOn = false;
    boolean conveyorBeltWasPressed = false;

    public void runOpMode() throws InterruptedException {

        motorFrontLeft = hardwareMap.dcMotor.get("upperLeft"); //motorFrontLeft
        motorBackLeft = hardwareMap.dcMotor.get("lowerLeft"); //motorBackLeft
        motorFrontRight = hardwareMap.dcMotor.get("upperRight"); //motorFrontRight
        motorBackRight = hardwareMap.dcMotor.get("lowerRight"); //motorBackRight
        intakeMotor = hardwareMap.dcMotor.get("intakeMotor");
        turretLeft = hardwareMap.dcMotor.get("turretLeft");
        turretRight = hardwareMap.dcMotor.get("turretRight");
        aimingServo = hardwareMap.crservo.get("aimingServo");
        conveyorBelt = hardwareMap.dcMotor.get("conveyorBelt");
        turretServo = hardwareMap.servo.get("turretServo");

        // arm = hardwareMap.dcMotor.get("arm");


        // arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

       // ((DcMotorEx) arm).setCurrentAlert(5,CurrentUnit.AMPS);
        //Reverse motors if necessary
        //  motorFrontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        motorFrontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        motorFrontRight.setDirection(DcMotorSimple.Direction.FORWARD);
        //  motorBackLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        motorBackLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        motorBackRight.setDirection(DcMotorSimple.Direction.REVERSE);
        //arm.setDirection(DcMotorSimple.Direction.REVERSE);

        /*
        arm.setTargetPosition(0);
        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

         */

        //Define and initialize servos
        // intake = hardwareMap.servo.get("intake");
        // wrist = hardwareMap.servo.get("wrist");

        /* Make sure that the intake is off, and the wrist is folded in. */
        // intake.setPower(INTAKE_OFF);
        turretServo.setPosition(TURRET_SERVO_START);
        conveyorBelt.setPower(0);


        telemetry.addData("TeleOp>", "Press Start");
        telemetry.update();
        waitForStart();
        runtime.reset();    // Start game timer.

        telemetry.addData("TeleOp>", "Stage 1");
        telemetry.update();

        if (isStopRequested()) return;


        while (opModeIsActive()) {

            double y = -gamepad1.left_stick_y; // Remember, this is reversed!
            double rx = -gamepad1.left_stick_x*1.25; // Counteract imperfect strafing

            //switched names rx and x

            double x = gamepad1.right_stick_x*1.15;

            if ((runtime.seconds() > HALF_TIME) && !secondHalf) {
                secondHalf = true;
            }

            if (!secondHalf) {
                telemetry.addData(">", "Halftime Alert Countdown: %3.0f Sec \n", (HALF_TIME - runtime.seconds()));
            }



//            if(gamepad1.right_bumper && fastMode){
//
//                mainPower= mainPower - 0.3;
//                fastMode = false;
//
//            }
//            else if(gamepad1.right_bumper && !fastMode){
//
//                mainPower= mainPower +0.3;
//                fastMode = true;
//
//            }

            if (gamepad1.right_bumper) {
                aimingServo.setPower(0.6);
            } else if (gamepad1.left_bumper) {
                aimingServo.setPower(-0.6);
            } else {
                aimingServo.setPower(0);  // stop
            }


            if (gamepad1.b && !conveyorBeltWasPressed) {
                if (!conveyorBeltOn) {
                    conveyorBelt.setPower(conveyorBeltSpeed);
                    conveyorBeltOn = true;
                } else {
                    conveyorBelt.setPower(0);
                    conveyorBeltOn = false;
                }
            }

            conveyorBeltWasPressed = gamepad1.b;






            if (gamepad2.a && !aWasPressed) {
                if (!intakeOn) {
                    intakeMotor.setPower(0.8);
                    intakeOn = true;
                } else {
                    intakeMotor.setPower(0);
                    intakeOn = false;
                }
            }

            aWasPressed = gamepad2.a;


            // Toggle outtake
            if (gamepad2.b && !outtakeWasPressed) {
                if (!outtakeOn) {
  //                  ((DcMotorEx)turretRight).setVelocity(57);
    //                ((DcMotorEx)turretLeft).setVelocity(-60);
                    turretRight.setPower(OUTTAKE_POWER);
                    turretLeft.setPower(-(OUTTAKE_POWER));
                    outtakeOn = true;
                } else {
                    turretRight.setPower(0);
                    turretLeft.setPower(0);
                    outtakeOn = false;
                }
            }

            outtakeWasPressed = gamepad2.b;



            if (gamepad2.x && !reverseOuttakeWasPressed) {
                if (!reverseOuttakeOn) {
    //                ((DcMotorEx)turretRight).setVelocity(-60);
    //                ((DcMotorEx)turretLeft).setVelocity(60);
                    turretRight.setPower(-(OUTTAKE_POWER));
                    turretLeft.setPower(OUTTAKE_POWER);
                    reverseOuttakeOn = true;
                } else {
                    turretRight.setPower(0);
                    turretLeft.setPower(0);
                    reverseOuttakeOn = false;
                }
            }

            reverseOuttakeWasPressed = gamepad2.x;

           /*
            if (gamepad2.x) {
                    turretRight.setPower(OUTTAKE_POWER);
                    turretLeft.setPower(-(OUTTAKE_POWER));
                }
                else if (gamepad2.b) {
                    turretRight.setPower(0);
                    turretLeft.setPower(0);
                }
*/


                if (gamepad2.right_bumper){
                    turretServo.setPosition(TURRET_SERVO_UP);
                }
//down then up
                else if (gamepad2.left_bumper){
                    turretServo.setPosition(TURRET_SERVO_DOWN);
                }

                 else {
                    turretServo.setPosition(TURRET_SERVO_STOP);
                }

                if (gamepad2.y){
                    /* This is the correct height to score the sample in the LOW BASKET */
                }
                if (gamepad2.dpad_left) {
                    /* This turns off the intake, folds in the wrist, and moves the arm
                    back to folded inside the robot. This is also the starting configuration */
                    // intake.setPower(INTAKE_OFF);
                    // wrist.setPosition(WRIST_FOLDED_IN);
                }

                if (gamepad2.dpad_right){
                    /* This is the correct height to score SPECIMEN on the HIGH CHAMBER */
                    // wrist.setPosition(WRIST_FOLDED_IN);
                }

                else if (gamepad2.dpad_up){
                    /* This sets the arm to vertical to hook onto the LOW RUNG for hanging */
                    // intake.setPower(INTAKE_OFF);
                    // wrist.setPosition(WRIST_FOLDED_IN);
                }

                else if (gamepad2.dpad_down){
                    /* this moves the arm down to lift the robot up once it has been hooked */
                    // intake.setPower(INTAKE_OFF);
                    // wrist.setPosition(WRIST_FOLDED_IN);
                }


             /* Here we create a "fudge factor" for the arm position.
            This allows you to adjust (or "fudge") the arm position slightly with the gamepad triggers.
            We want the left trigger to move the arm up, and right trigger to move the arm down.
            So we add the right trigger's variable to the inverse of the left trigger. If you pull
            both triggers an equal amount, they cancel and leave the arm at zero. But if one is larger
            than the other, it "wins out". This variable is then multiplied by our FUDGE_FACTOR.
            The FUDGE_FACTOR is the number of degrees that we can adjust the arm by with this function. */

                //armPositionFudgeFactor = FUDGE_FACTOR * (gamepad2.right_trigger + (-gamepad2.left_trigger));


            /* Here we set the target position of our arm to match the variable that was selected
            by the driver.
            We also set the target velocity (speed) the motor runs at, and use setMode to run it.*/
            /*
            arm.setTargetPosition((int) (armPosition + armPositionFudgeFactor));

            ((DcMotorEx) arm).setVelocity(2100);
            arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);

             */


                /* send telemetry to the driver of the arm's current position and target position */

            /*
            telemetry.addData("armTarget: ", arm.getTargetPosition());
            telemetry.addData("Sudheer", 546);
            telemetry.addData("arm Encoder: ", arm.getCurrentPosition());
            telemetry.update();

             */



                // Denominator is the largest motor power (absolute value) or 1
                // This ensures all the powers maintain the same ratio, but only when
                // at least one is out of the range [-1, 1]
                double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
                double frontLeftPower = (y - x - rx) / denominator;
                double backLeftPower = (y + x - rx) / denominator;
                double frontRightPower = (y + x + rx) / denominator;
                double backRightPower = (y - x + rx) / denominator;
                //Slower speed so that is easier to control
                motorFrontLeft.setPower(frontLeftPower * mainPower);
                motorBackLeft.setPower(backLeftPower * mainPower);
                motorFrontRight.setPower(frontRightPower * mainPower);
                motorBackRight.setPower(backRightPower * mainPower);

                telemetry.update();



                telemetry.addData("Game>", "Over");

                telemetry.update();


            }


    }

}