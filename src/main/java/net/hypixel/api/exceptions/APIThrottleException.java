package net.hypixel.api.exceptions;

public class APIThrottleException extends HypixelAPIException {
   private static final String c = "You have passed the API throttle limit!";

   public APIThrottleException() {
      super(c);
   }
}
