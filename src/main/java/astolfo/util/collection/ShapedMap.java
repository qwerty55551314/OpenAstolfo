package astolfo.util.collection;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public class ShapedMap<K, V> implements Map<K, V>, Serializable {
   private static final long serialVersionUID = -7720507706954394566L;
   public MapShape<K, V> shape = MapShape.EMPTY;
   public Object slotValues;
   public Object secondValue;
   public Object firstValue;

   @Override
   public int size() {
      return this.shape.getSize(this);
   }

   @Override
   public boolean isEmpty() {
      return this.size() == 0;
   }

   @Override
   public boolean containsKey(Object object) {
      return this.shape.hasKey(this, object);
   }

   @Override
   public boolean containsValue(Object object) {
      return this.values().contains(object);
   }

   @Override
   public V get(Object object) {
      return this.shape.getValue(this, (K)object);
   }

   @Override
   public V put(K object, V object2) {
      return this.shape.putValue(this, (K)object, object2);
   }

   public V putRaw(K object, Object object2) {
      return this.shape.putValue(this, (K)object, object2);
   }

   @Override
   public V remove(Object object) {
      return this.shape.putValue(this, (K)object, MapShape.REMOVED);
   }

   @Override
   public void putAll(Map<? extends K, ? extends V> map) {
      for (Entry entry : map.entrySet()) {
         this.put((K)entry.getKey(), (V)entry.getValue());
      }
   }

   @Override
   public void clear() {
      this.shape = MapShape.EMPTY;
      this.slotValues = this.secondValue = this.firstValue = null;
   }

   @Override
   public Set<K> keySet() {
      return this.shape.createKeySet(this);
   }

   @Override
   public Collection<V> values() {
      return this.shape.createValues(this);
   }

   @Override
   public Set<Entry<K, V>> entrySet() {
      return this.shape.createEntrySet(this);
   }

   @Override
   public boolean equals(Object object) {
      if (object == this) {
         return true;
      } else if (!(object instanceof Map)) {
         return false;
      } else {
         Map map = (Map)object;
         int i = map.size();
         if (i != this.size()) {
            return false;
         } else {
            for (Entry entry : this.entrySet()) {
               Object object1 = entry.getKey();
               Object object1x = entry.getValue();
               if (object1x == null) {
                  if (map.get(object1) != null || !map.containsKey(object1)) {
                     return false;
                  }
               } else if (!object1x.equals(map.get(object1))) {
                  return false;
               }
            }

            return true;
         }
      }
   }

   @Override
   public String toString() {
      Iterator iterator = this.entrySet().iterator();
      if (!iterator.hasNext()) {
         return "{}";
      } else {
         StringBuilder stringbuilder = new StringBuilder();
         stringbuilder.append('{');

         while (iterator.hasNext()) {
            Entry entry = (Entry)iterator.next();
            Object object = entry.getKey();
            Object object1 = entry.getValue();
            stringbuilder.append(object).append('=').append(object1);
            stringbuilder.append(',').append(' ');
         }

         stringbuilder.setLength(stringbuilder.length() - 2);
         return stringbuilder.append('}').toString();
      }
   }

   @Override
   public int hashCode() {
      int i = 0;

      for (Entry entry : this.entrySet()) {
         i += entry.hashCode();
      }

      return i;
   }

   private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
      this.shape.serialize(this, objectOutputStream);
   }

   private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
      MapShape.deserialize(this, objectInputStream);
   }
}
