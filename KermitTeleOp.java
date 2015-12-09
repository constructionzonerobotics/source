package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
//import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.util.Range;

public class KermitTeleOp extends OpMode {

    DcMotor motorRight, motorLeft, motorLift, motorWinch, motorArm, motorSlide;
    //Servo armPivot;
    DcMotorController sliders;

    int height;

    public KermitTeleOp() {

    }

    @Override
    public void init() {
        motorLeft = hardwareMap.dcMotor.get("motorLeft");
        motorRight = hardwareMap.dcMotor.get("motorRight");
        motorLift = hardwareMap.dcMotor.get("motorLift");
        motorWinch = hardwareMap.dcMotor.get("motorWinch");
        motorArm = hardwareMap.dcMotor.get("motorArm");
        motorSlide = hardwareMap.dcMotor.get("motorSlide");
        motorRight.setDirection(DcMotor.Direction.REVERSE);

        //armPivot = hardwareMap.servo.get("servo_1");
        sliders = hardwareMap.dcMotorController.get("sliders");
    }

    @Override
    public void loop() {



        float throttle = -gamepad1.left_stick_y;
        float direction = gamepad1.right_stick_x;
        float right = throttle + direction;
        float left = throttle - direction;
        float armPivotPosition = 0;

        //armPivotPosition += 0.01*gamepad2.right_stick_x;
        //armPivotPosition = Range.clip(armPivotPosition, 0, 1);
        //armPivot.setPosition(armPivotPosition);

        right = Range.clip(right, -1, 1);
        left = Range.clip(left, -1, 1);

        right = (float)scaleInput(right);
        left =  (float)scaleInput(left);

        if(gamepad2.right_bumper) {
            motorSlide.setPower(1);
        }
        else if(gamepad2.left_bumper){
            motorSlide.setPower(-1);
        }
        else{
            motorSlide.setPower(0);
        }

        motorArm.setPower(-(0.5)*(gamepad2.left_stick_y));

        // write the values to the motors
        motorRight.setPower(right);
        motorLeft.setPower(left);

        if(gamepad1.a){
            motorWinch.setPower(1);
        }
        else if(gamepad1.b){
            motorWinch.setPower(-0.2);
        }
        else{
            motorWinch.setPower(0);
        }

        if(gamepad1.left_bumper){
            if(!gamepad1.right_bumper)
                motorLift.setPower(-1);
        }
        else if(gamepad1.right_bumper){
            if(!gamepad1.left_bumper)
                motorLift.setPower(0.2);
        }
        else{
            motorLift.setPower(0);
        }

        //motorLift.setPower(-gamepad1.left_stick_y);

        //if(gamepad1.b){
        //    //if(motorLift.getCurrentPosition() < 50){}
        //}
        //sliders.setMotorControllerDeviceMode(DcMotorController.DeviceMode.READ_ONLY);
        //height = motorLift.getCurrentPosition();
        //try {
        //    telemetry.addData("Height: ", height);
        //}
        //catch (Exception e) {
        //    telemetry.addData("Height: ", "ERROR");
        //}

        //sliders.setMotorControllerDeviceMode(DcMotorController.DeviceMode.WRITE_ONLY);
    }

    @Override
    public void stop() {

    }

    double scaleInput(double dVal)  {
        double[] scaleArray = { 0.0, 0.05, 0.09, 0.10, 0.12, 0.15, 0.18, 0.24,
                0.30, 0.36, 0.43, 0.50, 0.60, 0.72, 0.85, 1.00, 1.00 };

        // get the corresponding index for the scaleInput array.
        int index = (int) (dVal * 16.0);
        if (index < 0) {
            index = -index;
        } else if (index > 16) {
            index = 16;
        }

        double dScale = 0.0;
        if (dVal < 0) {
            dScale = -scaleArray[index];
        } else {
            dScale = scaleArray[index];
        }

        return dScale;
    }

}
