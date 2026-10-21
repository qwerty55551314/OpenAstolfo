package astolfo.util;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class FileUtil {
   public static final ExecutorService executor = Executors.newSingleThreadExecutor();

   public static JsonElement readJson(File file) {
      JsonElement jsonelement = null;
      FileReader filereader = null;

      try {
         filereader = new FileReader(file);
         jsonelement = new JsonParser().parse(filereader);
      } catch (JsonIOException | JsonSyntaxException | FileNotFoundException filenotfoundexception) {
         filenotfoundexception.printStackTrace();
      } finally {
         if (filereader != null) {
            try {
               filereader.close();
            } catch (IOException ioexception) {
               ioexception.printStackTrace();
            }
         }
      }

      return jsonelement;
   }

   public static void writeJson(File file, JsonElement jsonElement) {
      FileWriter filewriter = null;

      try {
         filewriter = new FileWriter(file);
         filewriter.write(new GsonBuilder().setPrettyPrinting().create().toJson(jsonElement));
      } catch (IOException ioexception1) {
         ioexception1.printStackTrace();
      } finally {
         if (filewriter != null) {
            try {
               filewriter.close();
            } catch (IOException ioexception) {
               ioexception.printStackTrace();
            }
         }
      }
   }

   public static void writeLines(File file, Collection<String> collection) {
      BufferedReader bufferedreader = null;

      try {
         bufferedreader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));

         String s;
         while ((s = bufferedreader.readLine()) != null) {
            collection.add(s);
         }
      } catch (IOException ioexception1) {
         ioexception1.printStackTrace();
      } finally {
         try {
            if (bufferedreader != null) {
               bufferedreader.close();
            }
         } catch (IOException ioexception) {
            ioexception.printStackTrace();
         }
      }
   }

   public static File chooseFile(File file) {
      File file1 = null;
      TopFileChooser topfilechooser = new TopFileChooser(file);
      if (topfilechooser.showOpenDialog(topfilechooser) == 0) {
         file1 = topfilechooser.getSelectedFile();
      }

      return file1;
   }
}
