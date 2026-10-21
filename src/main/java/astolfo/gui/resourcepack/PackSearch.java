package astolfo.gui.resourcepack;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;
import net.minecraft.client.resources.ResourcePackListEntry;
import net.minecraft.client.resources.ResourcePackListEntryFound;
import net.minecraft.entity.Entity;

public class PackSearch {
   public static final Comparator<ResourcePackListEntry> NAME_ASCENDING = new PackNameAscending();
   public static final Comparator<ResourcePackListEntry> NAME_DESCENDING = new PackNameDescending();
   private final List<ResourcePackListEntry> allEntries;
   private final List<ResourcePackListEntry> filteredEntries;
   private Comparator<ResourcePackListEntry> comparator;
   private Pattern searchPattern;
   private static Entity[] j;

   private static String getPackName(ResourcePackListEntry resourcePackListEntry) {
      if (resourcePackListEntry instanceof CustomPackEntry) {
         return ((CustomPackEntry)resourcePackListEntry).func_148312_b();
      } else {
         return resourcePackListEntry instanceof ResourcePackListEntryFound
            ? ((ResourcePackListEntryFound)resourcePackListEntry).func_148318_i().getResourcePackName()
            : "<INVALID>";
      }
   }

   private static String computeSortKey(ResourcePackListEntry resourcePackListEntry, boolean bl) {
      String s = !bl ? "a" : "z";
      String s1 = !bl ? "b" : "z";
      String s2 = !bl ? "z" : "a";
      boolean flag = resourcePackListEntry instanceof FolderPackEntry;
      if (flag) {
         FolderPackEntry folderpackentry = (FolderPackEntry)resourcePackListEntry;
         return folderpackentry.isBackEntry ? s + folderpackentry.displayName : s1 + folderpackentry.displayName;
      } else {
         boolean flag1 = resourcePackListEntry instanceof CustomPackEntry;
         if (flag1) {
            return s2 + ((CustomPackEntry)resourcePackListEntry).func_148312_b();
         } else {
            return resourcePackListEntry instanceof ResourcePackListEntryFound
               ? s2 + ((ResourcePackListEntryFound)resourcePackListEntry).func_148318_i().getResourcePackName()
               : s2 + "<INVALID>";
         }
      }
   }

   private static String getPackDescription(ResourcePackListEntry resourcePackListEntry) {
      if (resourcePackListEntry instanceof CustomPackEntry) {
         return ((CustomPackEntry)resourcePackListEntry).func_148311_a();
      } else {
         return resourcePackListEntry instanceof ResourcePackListEntryFound
            ? ((ResourcePackListEntryFound)resourcePackListEntry).func_148318_i().getTexturePackDescription()
            : "<INVALID>";
      }
   }

   public PackSearch(List<ResourcePackListEntry> list, List<ResourcePackListEntry> list2) {
      this.allEntries = list;
      this.filteredEntries = list2;
      this.updateFilter();
   }

   public void setComparator(Comparator<ResourcePackListEntry> comparator) {
      this.comparator = comparator;
      this.updateFilter();
   }

   public void setSearchQuery(String string) {
      if (string != null && !string.isEmpty()) {
         this.searchPattern = Pattern.compile("\\Q" + string.replace("*", "\\E.*\\Q") + "\\E", 2);
      } else {
         this.searchPattern = null;
      }

      this.updateFilter();
   }

   public void updateFilter() {
      this.filteredEntries.clear();

      for (ResourcePackListEntry resourcepacklistentry : this.allEntries) {
         if (this.matchesSearch(getPackName(resourcepacklistentry)) || this.matchesSearch(getPackDescription(resourcepacklistentry))) {
            this.filteredEntries.add(resourcepacklistentry);
         }
      }

      if (this.comparator != null) {
         Collections.sort(this.filteredEntries, this.comparator);
      }
   }

   private boolean matchesSearch(String string) {
      Pattern pattern = this.searchPattern;
      if (pattern != null) {
         boolean flag = this.searchPattern.matcher(string.toLowerCase(Locale.ENGLISH)).find();
         if (!flag) {
            return false;
         }
      }

      return true;
   }

   public static String getSortKey(ResourcePackListEntry resourcePackListEntry, boolean bl) {
      return computeSortKey(resourcePackListEntry, bl);
   }

   static {
      M((Entity[])null);
   }

   public static void M(Entity[] entityArray) {
      j = entityArray;
   }

   public static Entity[] A() {
      return j;
   }
}
