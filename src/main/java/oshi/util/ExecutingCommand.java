package oshi.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import oshi.annotation.concurrent.ThreadSafe;

@ThreadSafe
public final class ExecutingCommand {
   private ExecutingCommand() {
   }

   public static List<String> runNative(String cmdToRun) {
      String[] astring = cmdToRun.split(" ");
      return runNative(astring);
   }

   // $VF: Could not verify finally blocks. A semaphore variable has been added to preserve control flow.
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public static List<String> runNative(String[] cmdToRunWithArgs) {
      Process process;
      try {
         process = Runtime.getRuntime().exec(cmdToRunWithArgs);
      } catch (IOException | SecurityException securityexception) {
         return new ArrayList<>(0);
      }

      ArrayList arraylist = new ArrayList();

      try {
         BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(process.getInputStream(), Charset.defaultCharset()));
         Throwable throwable = null;

         while (true) {
            boolean flag = false /* VF: Semaphore variable */;

            try {
               flag = true;
               String s;
               if ((s = bufferedreader.readLine()) == null) {
                  process.waitFor();
                  flag = false;
                  break;
               }

               arraylist.add(s);
            } catch (Throwable throwable2) {
               throwable = throwable2;
               throw throwable2;
            } finally {
               if (flag) {
                  if (bufferedreader != null) {
                     if (throwable != null) {
                        try {
                           bufferedreader.close();
                        } catch (Throwable throwable1) {
                           throwable.addSuppressed(throwable1);
                        }
                     } else {
                        bufferedreader.close();
                     }
                  }
               }
            }
         }

         if (bufferedreader != null) {
            bufferedreader.close();
         }
      } catch (IOException ioexception) {
         return new ArrayList<>(0);
      } catch (InterruptedException interruptedexception) {
         Thread.currentThread().interrupt();
      }

      return arraylist;
   }

   public static String getFirstAnswer(String cmd2launch) {
      return getAnswerAt(cmd2launch, 0);
   }

   public static String getAnswerAt(String cmd2launch, int answerIdx) {
      List list = runNative(cmd2launch);
      return answerIdx >= 0 && answerIdx < list.size() ? (String)list.get(answerIdx) : "";
   }
}
