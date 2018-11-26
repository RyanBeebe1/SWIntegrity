package com.tutorialspoint;

import java.util.*;

public class ObjectDemo extends GregorianCalendar {

   public static void main(String[] args) {
      try {
         // create a new ObjectDemo object
         ObjectDemo cal = new ObjectDemo();

         // print current time
         System.out.println("" + cal.getTime());

         // finalize cal
         System.out.println("Finalizing...");
         cal.finalize();
         System.out.println("Finalized.");

      } catch (Throwable ex) {
         ex.printStackTrace();
      }
   }
}