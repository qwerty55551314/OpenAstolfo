package astolfo.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

public final class ProxyListReader {
   // $VF: Could not verify finally blocks. A semaphore variable has been added to preserve control flow.
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public static Set<HostAndPort> readProxiesWithSeparator(Path path, String string) throws IOException {
      boolean flag1 = false;
      HashSet hashset = new HashSet();
      BufferedReader bufferedreader = Files.newBufferedReader(path);
      Throwable throwable = null;

      while (true) {
         boolean flag = false /* VF: Semaphore variable */;

         try {
            flag = true;
            String s;
            if ((s = bufferedreader.readLine()) == null) {
               flag = false;
               break;
            }

            String[] astring = s.split(string);
            hashset.add(new HostAndPort(astring[0], Integer.parseInt(astring[1])));
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

      return hashset;
   }

   public static Set<HostAndPort> readProxies(Path path) throws IOException {
      return readProxiesWithSeparator(path, ":");
   }
}
