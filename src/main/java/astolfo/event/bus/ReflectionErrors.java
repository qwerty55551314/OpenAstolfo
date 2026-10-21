package astolfo.event.bus;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.UndeclaredThrowableException;

public class ReflectionErrors {
   private static int[] r;

   public static void rethrowReflectionException(Exception exception) {
      boolean flag = exception instanceof NoSuchMethodException;
      if (flag) {
         throw new IllegalStateException("Couldn't find the method:\n" + exception.getMessage());
      } else {
         boolean flag1 = exception instanceof IllegalAccessException;
         if (flag1) {
            throw new IllegalStateException("Unable to access method:\n" + exception.getMessage());
         } else {
            boolean flag2 = exception instanceof InvocationTargetException;
            if (flag2) {
               rethrowTargetException((InvocationTargetException)exception);
            }

            if (exception instanceof RuntimeException) {
               throw (RuntimeException)exception;
            } else {
               throw new UndeclaredThrowableException(exception);
            }
         }
      }
   }

   public static void rethrowTargetException(InvocationTargetException invocationTargetException) {
      rethrowUnchecked(invocationTargetException.getTargetException());
   }

   public static void rethrowUnchecked(Throwable throwable) {
      if (throwable instanceof RuntimeException) {
         throw (RuntimeException)throwable;
      } else if (throwable instanceof Error) {
         throw (Error)throwable;
      } else {
         throw new UndeclaredThrowableException(throwable);
      }
   }

   public static void R(int[] iArray) {
      r = iArray;
   }

   public static int[] g() {
      return r;
   }

   static {
      R(new int[4]);
   }
}
