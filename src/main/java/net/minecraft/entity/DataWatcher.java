package net.minecraft.entity;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ReportedException;
import net.minecraft.util.Rotations;
import org.apache.commons.lang3.ObjectUtils;

public class DataWatcher {
   private final Entity owner;
   private boolean isBlank = true;
   private static final Map<Class<?>, Integer> dataTypes = Maps.newHashMap();
   private final Map<Integer, DataWatcher.WatchableObject> watchedObjects = Maps.newHashMap();
   private boolean objectChanged;
   private ReadWriteLock lock = new ReentrantReadWriteLock();

   public DataWatcher(Entity owner) {
      this.owner = owner;
   }

   public <T> void addObject(int id, T object) {
      Integer integer = dataTypes.get(object.getClass());
      if (integer == null) {
         throw new IllegalArgumentException("Unknown data type: " + object.getClass());
      } else if (id > 31) {
         throw new IllegalArgumentException("Data value id is too big with " + id + "! (Max is " + 31 + ")");
      } else if (this.watchedObjects.containsKey(id)) {
         throw new IllegalArgumentException("Duplicate id value for " + id + "!");
      } else {
         DataWatcher.WatchableObject datawatcher$watchableobject = new DataWatcher.WatchableObject(integer, id, object);
         this.lock.writeLock().lock();
         this.watchedObjects.put(id, datawatcher$watchableobject);
         this.lock.writeLock().unlock();
         this.isBlank = false;
      }
   }

   public void addObjectByDataType(int id, int typeIn) {
      DataWatcher.WatchableObject datawatcher$watchableobject = new DataWatcher.WatchableObject(typeIn, id, null);
      this.lock.writeLock().lock();
      this.watchedObjects.put(id, datawatcher$watchableobject);
      this.lock.writeLock().unlock();
      this.isBlank = false;
   }

   public byte getWatchableObjectByte(int id) {
      return (Byte)this.getWatchedObject(id).getObject();
   }

   public short getWatchableObjectShort(int id) {
      return (Short)this.getWatchedObject(id).getObject();
   }

   public int getWatchableObjectInt(int id) {
      return (Integer)this.getWatchedObject(id).getObject();
   }

   public float getWatchableObjectFloat(int id) {
      return (Float)this.getWatchedObject(id).getObject();
   }

   public String getWatchableObjectString(int id) {
      return (String)this.getWatchedObject(id).getObject();
   }

   public ItemStack getWatchableObjectItemStack(int id) {
      return (ItemStack)this.getWatchedObject(id).getObject();
   }

   private DataWatcher.WatchableObject getWatchedObject(int id) {
      this.lock.readLock().lock();

      DataWatcher.WatchableObject datawatcher$watchableobject;
      try {
         datawatcher$watchableobject = this.watchedObjects.get(id);
      } catch (Throwable throwable) {
         CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Getting synched entity data");
         CrashReportCategory crashreportcategory = crashreport.makeCategory("Synched entity data");
         crashreportcategory.addCrashSection("Data ID", id);
         throw new ReportedException(crashreport);
      }

      this.lock.readLock().unlock();
      return datawatcher$watchableobject;
   }

   public Rotations getWatchableObjectRotations(int id) {
      return (Rotations)this.getWatchedObject(id).getObject();
   }

   public <T> void updateObject(int id, T newData) {
      DataWatcher.WatchableObject datawatcher$watchableobject = this.getWatchedObject(id);
      if (ObjectUtils.notEqual(newData, datawatcher$watchableobject.getObject())) {
         datawatcher$watchableobject.setObject(newData);
         this.owner.onDataWatcherUpdate(id);
         datawatcher$watchableobject.setWatched(true);
         this.objectChanged = true;
      }
   }

   public void setObjectWatched(int id) {
      this.getWatchedObject(id).watched = true;
      this.objectChanged = true;
   }

   public boolean hasObjectChanged() {
      return this.objectChanged;
   }

   public static void writeWatchedListToPacketBuffer(List<DataWatcher.WatchableObject> objectsList, PacketBuffer buffer) throws IOException {
      if (objectsList != null) {
         for (DataWatcher.WatchableObject datawatcher$watchableobject : objectsList) {
            writeWatchableObjectToPacketBuffer(buffer, datawatcher$watchableobject);
         }
      }

      buffer.writeByte(127);
   }

   public List<DataWatcher.WatchableObject> getChanged() {
      ArrayList arraylist = null;
      if (this.objectChanged) {
         this.lock.readLock().lock();

         for (DataWatcher.WatchableObject datawatcher$watchableobject : this.watchedObjects.values()) {
            if (datawatcher$watchableobject.isWatched()) {
               datawatcher$watchableobject.setWatched(false);
               if (arraylist == null) {
                  arraylist = Lists.newArrayList();
               }

               arraylist.add(datawatcher$watchableobject);
            }
         }

         this.lock.readLock().unlock();
      }

      this.objectChanged = false;
      return arraylist;
   }

   public void writeTo(PacketBuffer buffer) throws IOException {
      this.lock.readLock().lock();

      for (DataWatcher.WatchableObject datawatcher$watchableobject : this.watchedObjects.values()) {
         writeWatchableObjectToPacketBuffer(buffer, datawatcher$watchableobject);
      }

      this.lock.readLock().unlock();
      buffer.writeByte(127);
   }

   public List<DataWatcher.WatchableObject> getAllWatched() {
      ArrayList arraylist = null;
      this.lock.readLock().lock();

      for (DataWatcher.WatchableObject datawatcher$watchableobject : this.watchedObjects.values()) {
         if (arraylist == null) {
            arraylist = Lists.newArrayList();
         }

         arraylist.add(datawatcher$watchableobject);
      }

      this.lock.readLock().unlock();
      return arraylist;
   }

   private static void writeWatchableObjectToPacketBuffer(PacketBuffer buffer, DataWatcher.WatchableObject object) throws IOException {
      int i = (object.getObjectType() << 5 | object.getDataValueId() & 31) & 0xFF;
      buffer.writeByte(i);
      switch (object.getObjectType()) {
         case 0:
            buffer.writeByte((Byte)object.getObject());
            break;
         case 1:
            buffer.writeShort((Short)object.getObject());
            break;
         case 2:
            buffer.writeInt((Integer)object.getObject());
            break;
         case 3:
            buffer.writeFloat((Float)object.getObject());
            break;
         case 4:
            buffer.writeString((String)object.getObject());
            break;
         case 5:
            ItemStack itemstack = (ItemStack)object.getObject();
            buffer.writeItemStackToBuffer(itemstack);
            break;
         case 6:
            BlockPos blockpos = (BlockPos)object.getObject();
            buffer.writeInt(blockpos.getX());
            buffer.writeInt(blockpos.getY());
            buffer.writeInt(blockpos.getZ());
            break;
         case 7:
            Rotations rotations = (Rotations)object.getObject();
            buffer.writeFloat(rotations.getX());
            buffer.writeFloat(rotations.getY());
            buffer.writeFloat(rotations.getZ());
      }
   }

   public static List<DataWatcher.WatchableObject> readWatchedListFromPacketBuffer(PacketBuffer buffer) throws IOException {
      ArrayList arraylist = null;

      for (byte b0 = buffer.readByte(); b0 != 127; b0 = buffer.readByte()) {
         if (arraylist == null) {
            arraylist = Lists.newArrayList();
         }

         int i = (b0 & 224) >> 5;
         int j = b0 & 31;
         DataWatcher.WatchableObject datawatcher$watchableobject = null;
         switch (i) {
            case 0:
               datawatcher$watchableobject = new DataWatcher.WatchableObject(i, j, buffer.readByte());
               break;
            case 1:
               datawatcher$watchableobject = new DataWatcher.WatchableObject(i, j, buffer.readShort());
               break;
            case 2:
               datawatcher$watchableobject = new DataWatcher.WatchableObject(i, j, buffer.readInt());
               break;
            case 3:
               datawatcher$watchableobject = new DataWatcher.WatchableObject(i, j, buffer.readFloat());
               break;
            case 4:
               datawatcher$watchableobject = new DataWatcher.WatchableObject(i, j, buffer.readStringFromBuffer(32767));
               break;
            case 5:
               datawatcher$watchableobject = new DataWatcher.WatchableObject(i, j, buffer.readItemStackFromBuffer());
               break;
            case 6:
               int k = buffer.readInt();
               int l = buffer.readInt();
               int i1 = buffer.readInt();
               datawatcher$watchableobject = new DataWatcher.WatchableObject(i, j, new BlockPos(k, l, i1));
               break;
            case 7:
               float f = buffer.readFloat();
               float f1 = buffer.readFloat();
               float f2 = buffer.readFloat();
               datawatcher$watchableobject = new DataWatcher.WatchableObject(i, j, new Rotations(f, f1, f2));
         }

         arraylist.add(datawatcher$watchableobject);
      }

      return arraylist;
   }

   public void updateWatchedObjectsFromList(List<DataWatcher.WatchableObject> list) {
      this.lock.writeLock().lock();

      for (DataWatcher.WatchableObject datawatcher$watchableobject : list) {
         DataWatcher.WatchableObject datawatcher$watchableobject1 = this.watchedObjects.get(datawatcher$watchableobject.getDataValueId());
         if (datawatcher$watchableobject1 != null) {
            datawatcher$watchableobject1.setObject(datawatcher$watchableobject.getObject());
            this.owner.onDataWatcherUpdate(datawatcher$watchableobject.getDataValueId());
         }
      }

      this.lock.writeLock().unlock();
      this.objectChanged = true;
   }

   public boolean getIsBlank() {
      return this.isBlank;
   }

   public void func_111144_e() {
      this.objectChanged = false;
   }

   static {
      dataTypes.put(Byte.class, 0);
      dataTypes.put(Short.class, 1);
      dataTypes.put(Integer.class, 2);
      dataTypes.put(Float.class, 3);
      dataTypes.put(String.class, 4);
      dataTypes.put(ItemStack.class, 5);
      dataTypes.put(BlockPos.class, 6);
      dataTypes.put(Rotations.class, 7);
   }

   public static class WatchableObject {
      private final int objectType;
      private final int dataValueId;
      private Object watchedObject;
      private boolean watched;

      public WatchableObject(int typeIn, int id, Object object) {
         this.dataValueId = id;
         this.watchedObject = object;
         this.objectType = typeIn;
         this.watched = true;
      }

      public int getDataValueId() {
         return this.dataValueId;
      }

      public void setObject(Object object) {
         this.watchedObject = object;
      }

      public Object getObject() {
         return this.watchedObject;
      }

      public int getObjectType() {
         return this.objectType;
      }

      public boolean isWatched() {
         return this.watched;
      }

      public void setWatched(boolean watched) {
         this.watched = watched;
      }
   }
}
