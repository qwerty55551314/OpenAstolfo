package astolfo.script;

public class ScriptTimeout {
   public Object result = null;
   private boolean finished = false;
   public final Object lock = new Object();
   private static boolean N;

   // $VF: Could not verify finally blocks. A semaphore variable has been added to preserve control flow.
   // $VF: Could not create synchronized statement, marking monitor enters and exits
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public void runWithTimeout(Thread thread, int i) {
      boolean flag1 = false;
      thread.start();
      Object object = this.lock;
      synchronized (this.lock){} // $VF: monitorenter 
      boolean flag = false /* VF: Semaphore variable */;

      try {
         flag = true;
         if (!this.finished) {
            try {
               if (i < 1) {
                  this.lock.wait();
               } else {
                  this.lock.wait((long)(1000 * i));
               }
            } catch (InterruptedException interruptedexception) {
               throw new RuntimeException(interruptedexception);
            }
         }

         // $VF: monitorexit
         flag = false;
      } finally {
         if (flag) {
            // $VF: monitorexit
         }
      }

      if (!this.finished) {
         thread.interrupt();
         thread.stop();
         throw new RuntimeException("Script was forced to termination: Execution time bigger then " + i + " seconds");
      }
   }

   public void markFinished() {
      synchronized (this.lock) {
         this.finished = true;
         this.lock.notifyAll();
      }
   }

   public static boolean Q() {
      return N;
   }

   public static boolean A() {
      return true;
   }
}
