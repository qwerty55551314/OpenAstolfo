package net.hypixel.api.exceptions;

public class HypixelAPIException extends RuntimeException {
   private static int f;

   public HypixelAPIException() {
   }

   public HypixelAPIException(String message) {
      super(message);
   }

   public HypixelAPIException(String message, Throwable cause) {
      super(message, cause);
   }

   public HypixelAPIException(Throwable cause) {
      super(cause);
   }

   public HypixelAPIException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
      super(message, cause, enableSuppression, writableStackTrace);
   }

   public static void l(int i) {
      f = i;
   }

   public static int c() {
      return f;
   }

   public static int d() {
      return 0;
   }

   static {
      l(93);
   }
}
