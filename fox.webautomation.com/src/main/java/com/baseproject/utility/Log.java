package com.baseproject.utility;

import org.apache.log4j.Logger;
import org.testng.Reporter;

import com.aventstack.extentreports.Status;
import com.baseproject.global.Config;

public class Log extends Config {

      private static int StepCount = 1;

      public static synchronized void info(String msg) {
            try {
                  Logger info = Log.getLogger("info");
                  info.info(msg);
//                  Reporter.log(msg + "<br />");
                  /*
                  * if (!getExtentTestThreadSafe().equals(null)) {
                  * getExtentTestThreadSafe().log(Status.INFO, msg); }
                  */

            } catch (NullPointerException e) {

            }
      }

      public static synchronized void warn(String msg) {
            Logger info = Log.getLogger("info");
            info.warn(msg);
            if (!getExtentTestThreadSafe().equals(null)) {
                  getExtentTestThreadSafe().log(Status.WARNING, msg);
            }
            Reporter.log(msg + "<br />");
      }

      public static synchronized void error(String msg) {
            Logger info = Log.getLogger("info");
            info.error(msg);
            Reporter.log(msg + "<br />");
      }

      public static synchronized void error(String msg, Throwable t) {
            Logger info = Log.getLogger("info");
            info.error(msg, t);
            Reporter.log(msg + "<br />");
      }

      public static void debug(String msg) {
            Logger debug = Log.getLogger("debug");
            debug.debug(msg);
            if (!getExtentTestThreadSafe().equals(null)) {
                  getExtentTestThreadSafe().log(Status.INFO, msg);
            }
            Reporter.log(msg + "<br />");
      }

      public static synchronized void step(String msg) {
            Logger info = Log.getLogger("info");
            info.info("[step " + Log.StepCount + "] " + msg);
//            Reporter.log(msg + "<br />");
            Log.StepCount++;
      }

}


