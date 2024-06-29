package frc.robot;

import com.ctre.phoenix6.signals.InvertedValue;

public class Constants {

    public static final boolean tuningMode = true;

    public static class Shooter{
        /* IDs */

        public static final int UPPER_SHOOTER_ID = 13;
        public static final int LOWER_SHOOTER_ID = 14;

        /* SetPoints and Threesholds */

        public static final int SHOOTER_THREESHOLD = 300;
        
        public static final int UPPER_SHOOTER_IDLE_RPM = 0;
        public static final int LOWER_SHOOTER_IDLE_RPM = 0;

        public static final int UPPER_SHOOTER_AMP_RPM = 2000;
        public static final int LOWER_SHOOTER_AMP_RPM = 500;

        public static final int UPPER_SHOOTER_FEEDER_OVER_RPM = 4500;
        public static final int LOWER_SHOOTER_FEEDER_OVER_RPM = 4500;

        public static final int UPPER_SHOOTER_FEEDER_UNDER_RPM = 6000;
        public static final int LOWER_SHOOTER_FEEDER_UNDER_RPM = 6000;

        public static final int UPPER_SHOOTER_SPEAKER_RPM = 3000;
        public static final int LOWER_SHOOTER_SPEAKER_RPM = 3000;

        /*Physical Measurements */

        public static InvertedValue UPPER_SHOOTER_INVERSION = InvertedValue.Clockwise_Positive;
        public static InvertedValue LOWER_SHOOTER_INVERSION = InvertedValue.Clockwise_Positive;

        /* PID */

        public static double
        ukP = 1.4,     
        ukI = 0,        
        ukD = 0.025,    
        ukS = 0.50189,
        ukV = 0.0016926,
        ukA = 0.00059492,    
        lkP = 1.4,     
        lkI = 0,        
        lkD = 0.025,    
        lkS = 0.50189,
        lkV = 0.0016926,
        lkA = 0.00059492;

    }

      public static class Intake{
       /*ID */
        public static final int INTAKE_ID = 12;
        public static final int INTAKE_SENSOR_ID = 0;

       /*Physical Measurements */
        public static final boolean INTAKE_INVERSION = false;

    }

      public static class Arm{
        /*ID */
         public static final int LEFT_ARM_MOTOR_ID = 9 ;
         public static final int RIGHT_ARM_MOTOR_ID = 10;

      }

  /*   _________                    
    /   _____/__  __ ____   ____  
    \_____  \\  \/ // __ \ /    \ 
   /_______  /\   /\  ___/|   |  \
           \/  \_/  \___  >___|  /
                        \/     \/
           */
}
