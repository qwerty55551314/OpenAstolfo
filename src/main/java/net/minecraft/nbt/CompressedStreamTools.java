package net.minecraft.nbt;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.util.ReportedException;

public class CompressedStreamTools {
   public static NBTTagCompound readCompressed(InputStream is) throws IOException {
      DataInputStream datainputstream = new DataInputStream(new BufferedInputStream(new GZIPInputStream(is)));

      NBTTagCompound nbttagcompound;
      try {
         nbttagcompound = read(datainputstream, NBTSizeTracker.INFINITE);
      } finally {
         datainputstream.close();
      }

      return nbttagcompound;
   }

   public static void writeCompressed(NBTTagCompound nbtTagCompound, OutputStream outputStream) throws IOException {
      DataOutputStream dataoutputstream = new DataOutputStream(new BufferedOutputStream(new GZIPOutputStream(outputStream)));

      try {
         write(nbtTagCompound, dataoutputstream);
      } finally {
         dataoutputstream.close();
      }
   }

   public static void safeWrite(NBTTagCompound nbtTagCompound, File file) throws IOException {
      boolean flag3 = true;
      File file1 = new File(file.getAbsolutePath() + "_tmp");
      boolean flag = file1.exists();
      if (flag) {
         file1.delete();
      }

      write(nbtTagCompound, file1);
      boolean flag1 = file.exists();
      if (flag1) {
         file.delete();
      }

      boolean flag2 = file.exists();
      if (flag2) {
         throw new IOException("Failed to delete " + file);
      } else {
         file1.renameTo(file);
      }
   }

   public static void write(NBTTagCompound nbtTagCompound, File file) throws IOException {
      DataOutputStream dataoutputstream = new DataOutputStream(new FileOutputStream(file));

      try {
         write(nbtTagCompound, dataoutputstream);
      } finally {
         dataoutputstream.close();
      }
   }

   public static NBTTagCompound read(File file) throws IOException {
      if (!file.exists()) {
         return null;
      } else {
         DataInputStream datainputstream = new DataInputStream(new FileInputStream(file));

         NBTTagCompound nbttagcompound;
         try {
            nbttagcompound = read(datainputstream, NBTSizeTracker.INFINITE);
         } finally {
            datainputstream.close();
         }

         return nbttagcompound;
      }
   }

   public static NBTTagCompound read(DataInputStream inputStream) throws IOException {
      return read(inputStream, NBTSizeTracker.INFINITE);
   }

   public static NBTTagCompound read(DataInput dataInput, NBTSizeTracker nbtSizeTracker) throws IOException {
      NBTBase nbtbase = func_152455_a(dataInput, 0, nbtSizeTracker);
      if (nbtbase instanceof NBTTagCompound) {
         return (NBTTagCompound)nbtbase;
      } else {
         throw new IOException("Root tag must be a named compound tag");
      }
   }

   public static void write(NBTTagCompound nbtTagCompound, DataOutput dataOutput) throws IOException {
      writeTag(nbtTagCompound, dataOutput);
   }

   private static void writeTag(NBTBase nbtBase, DataOutput dataOutput) throws IOException {
      dataOutput.writeByte(nbtBase.getId());
      if (nbtBase.getId() != 0) {
         dataOutput.writeUTF("");
         nbtBase.write(dataOutput);
      }
   }

   private static NBTBase func_152455_a(DataInput dataInput, int i, NBTSizeTracker nbtSizeTracker) throws IOException {
      byte b0 = dataInput.readByte();
      if (b0 == 0) {
         return new NBTTagEnd();
      } else {
         dataInput.readUTF();
         NBTBase nbtbase = NBTBase.createNewByType(b0);

         try {
            nbtbase.read(dataInput, i, nbtSizeTracker);
            return nbtbase;
         } catch (IOException ioexception) {
            CrashReport crashreport = CrashReport.makeCrashReport(ioexception, "Loading NBT data");
            CrashReportCategory crashreportcategory = crashreport.makeCategory("NBT Tag");
            crashreportcategory.addCrashSection("Tag name", "[UNNAMED TAG]");
            crashreportcategory.addCrashSection("Tag type", b0);
            throw new ReportedException(crashreport);
         }
      }
   }
}
