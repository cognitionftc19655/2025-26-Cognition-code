package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;

public class HardwarePushbot {
    public DcMotor  upperLeft   = null;
    public DcMotor  upperRight = null;
    public DcMotor  lowerLeft = null;
    public DcMotor  lowerRight = null;
    public DcMotor intakeMotor = null;
    public DcMotor turretLeft = null;
    public DcMotor turretRight = null;
    public DcMotor conveyorBelt = null;
    public CRServo aimingServo = null;
    public Servo turretServo = null;
    //    /* local OpMode members. */
    HardwareMap hwMap           =  null;
    private ElapsedTime period  = new ElapsedTime();

    /* Constructor */
    public HardwarePushbot(){

    }
    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap ahwMap) {
        // Save reference to Hardware map
        hwMap = ahwMap;


        upperLeft = hwMap.dcMotor.get("upperLeft"); //motorFrontLeft
        upperRight = hwMap.dcMotor.get("upperRight"); //motorBackLeft
        lowerLeft = hwMap.dcMotor.get("lowerLeft"); //motorFrontRight
        lowerRight = hwMap.dcMotor.get("lowerRight"); //motorBackRight
        intakeMotor = hwMap.dcMotor.get("intakeMotor");
        turretLeft = hwMap.dcMotor.get("turretLeft");
        turretRight = hwMap.dcMotor.get("turretRight");
        conveyorBelt = hwMap.dcMotor.get("conveyorBelt");
        aimingServo = hwMap.crservo.get("aimingServo");
        turretServo = hwMap.servo.get("turretServo");

        /* A number in degrees that the triggers can adjust the arm position by */

        // Variables that are used to set the arm to a specific position


        intakeMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        ((DcMotorEx) intakeMotor).setCurrentAlert(5, CurrentUnit.AMPS);

        intakeMotor.setTargetPosition(0);
        intakeMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        intakeMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


        // Set all motors to zero power'
        setMotorPowers(0);
        //Reverse front motors and back right motors
        upperLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        // upperRight.setDirection(DcMotorSimple.Direction.REVERSE);
        lowerLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        //  lowerRight.setDirection(DcMotorSimple.Direction.REVERSE);


        /* Make sure that the intake is off, and the wrist is folded in. */
        intakeMotor.setPower(INTAKE_OFF);
        turretServo.setPosition(WRIST_FOLDED_IN);

        conveyorBelt.setPower(conveyorBeltSpeed);

    }


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

    final double ARM_COLLAPSED_INTO_ROBOT  = 0;
    double armPosition = (int)ARM_COLLAPSED_INTO_ROBOT;

    final double ARM_COLLECT               = 100 * ARM_TICKS_PER_DEGREE;
    final double ARM_CLEAR_BARRIER         = 94 * ARM_TICKS_PER_DEGREE;
    final double ARM_SCORE_SPECIMEN        = 35 * ARM_TICKS_PER_DEGREE;
    final double ARM_SCORE_SAMPLE_IN_LOW   = 64 * ARM_TICKS_PER_DEGREE;
    final double ARM_ATTACH_HANGING_HOOK   = 58 * ARM_TICKS_PER_DEGREE;
    final double ARM_WINCH_ROBOT           = 130  * ARM_TICKS_PER_DEGREE;

    /* Variables to store the speed the intake servo should be set at to intake, and deposit game elements. */
    final double INTAKE_COLLECT    = -1.0;
    final double INTAKE_OFF        =  0.0;
    final double INTAKE_DEPOSIT    =  0.5;

    final double conveyorBeltSpeed = 1.0;

    /* Variables to store the positions that the wrist should be set to when folding in, or folding out. */
    final double WRIST_FOLDED_IN   = 0.8333;

    final double TURRET_SERVO_START = 0.5;
    final double TURRET_SERVO_UP = 0.8;
    final double TURRET_SERVO_STOP = 0.5;

    final double TURRET_SERVO_DOWN = 0.2;

    final double WRIST_FOLDED_OUT  = 0.5;
    public void setMotorPowers(double LFPower, double RFPower, double LBPower, double RBPower, double APower) {
        upperLeft.setPower(LFPower);
        upperRight.setPower(RFPower);
        lowerLeft.setPower(LBPower);
        lowerRight.setPower(RBPower);
    }
    public void setMotorPowers(double allPower) {
        setMotorPowers(allPower, allPower, allPower, allPower, allPower);
    }
    public void tester(int motorNumber, double motorPower) {
        if(motorNumber == 1){
            upperLeft.setPower(motorPower);
        }
        if(motorNumber == 2){
            upperRight.setPower(motorPower);
        }
        if(motorNumber == 3){
            lowerLeft.setPower(motorPower);
        }
        if(motorNumber == 4){
            lowerRight.setPower(motorPower);
        }
    }
    public void zero(){
        upperLeft.setPower(0);
        upperRight.setPower(0);
        lowerLeft.setPower(0);
        lowerRight.setPower(0);
    }
    public void forward(double speed){
        upperLeft.setPower(speed);
        upperRight.setPower(speed);
        lowerLeft.setPower(speed);
        lowerRight.setPower(speed);
    }
    public void backward(double speed){
        upperLeft.setPower(-speed);
        upperRight.setPower(-speed);
        lowerLeft.setPower(-speed);
        lowerRight.setPower(-speed);
    }
    public void strafeLeft(double speed){
        upperLeft.setPower(-speed);
        upperRight.setPower(speed);
        lowerLeft.setPower(speed);
        lowerRight.setPower(-speed);
    }
    public void strafeRight(double speed){
        upperLeft.setPower(speed);
        upperRight.setPower(-speed);
        lowerLeft.setPower(-speed);
        lowerRight.setPower(speed);
    }
    public void turnLeft(double speed){
        upperLeft.setPower(-speed);
        upperRight.setPower(speed);
        lowerLeft.setPower(-speed);
        lowerRight.setPower(speed);
    }
    public void turnRight(double speed){
        upperLeft.setPower(speed);
        upperRight.setPower(-speed);
        lowerLeft.setPower(speed);
        lowerRight.setPower(-speed);
    }


    public void intakeStart(){
        intakeMotor.setPower(0.8);


    }

    public void intakeStop(){
        intakeMotor.setPower(0);
    }

    public void outTakeOn(double power){
        turretRight.setPower(power);
        turretLeft.setPower((-power));
    }

    public void outTakeOff(){
        turretRight.setPower(0);
        turretLeft.setPower(0);
    }

    public void conveyorBeltOn(){
        conveyorBelt.setPower(1);
    }

    public void conveyorBeltOff(){
        conveyorBelt.setPower(0);
    }

    public void turretServoBack(){
        turretServo.setPosition(TURRET_SERVO_UP);
    }

    public void turretServoForward(){
        turretServo.setPosition(TURRET_SERVO_DOWN);
    }

    public void turretServoStop(){
        turretServo.setPosition(TURRET_SERVO_STOP);
    }

    // Autonomous code angle
    public void turretAim(double power){
        aimingServo.setPower(power);
    }

    public void turretAimStop(){
        aimingServo.setPower(0);
    }


}
