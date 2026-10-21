package astolfo.gui.resourcepack;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import java.io.File;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.client.resources.ResourcePackRepository;
import net.minecraft.client.resources.data.IMetadataSerializer;
import net.minecraft.client.settings.GameSettings;

public class FolderResourcePackRepository extends ResourcePackRepository {
   private static Constructor<ResourcePackRepository.Entry> entryConstructor;
   private List<ResourcePackRepository.Entry> allPackEntries = Lists.newArrayList();
   private List<ResourcePackRepository.Entry> activePackEntries = Lists.newArrayList();
   private boolean rescanEnabled = true;
   private static final String h = "pack.mcmeta";

   public static ResourcePackRepository.Entry createEntry(ResourcePackRepository resourcePackRepository, File file) {
      try {
         if (entryConstructor == null) {
            entryConstructor = ResourcePackRepository.Entry.class.getDeclaredConstructor(ResourcePackRepository.class, File.class);
            entryConstructor.setAccessible(true);
         }

         return entryConstructor.newInstance(resourcePackRepository, file);
      } catch (Throwable throwable) {
         throwable.printStackTrace();
         return null;
      }
   }

   public FolderResourcePackRepository(
      File file, File file2, IResourcePack iResourcePack, IMetadataSerializer iMetadataSerializer, GameSettings gameSettings, List<String> list
   ) {
      super(file, file2, iResourcePack, iMetadataSerializer, gameSettings);
      this.updateRepositoryEntriesAll();

      for (String s : list) {
         for (ResourcePackRepository.Entry resourcepackrepository$entry : this.allPackEntries) {
            if (resourcepackrepository$entry.getResourcePackName().equals(s)) {
               this.activePackEntries.add(resourcepackrepository$entry);
            }
         }
      }
   }

   private final List<File> listPackFilesRecursive(File file) {
      if (!file.isDirectory()) {
         return Collections.emptyList();
      } else {
         ArrayList arraylist = Lists.newArrayList();

         for (File file1 : file.listFiles()) {
            if (file1.isDirectory() && !new File(file1, h).isFile()) {
               arraylist.addAll(this.listPackFilesRecursive(file1));
            } else {
               arraylist.add(file1);
            }
         }

         return arraylist;
      }
   }

   @Override
   public void updateRepositoryEntriesAll() {
      if (this.rescanEnabled) {
         ArrayList arraylist = Lists.newArrayList();

         for (File file1 : this.listPackFilesRecursive(this.getDirResourcepacks())) {
            ResourcePackRepository.Entry resourcepackrepository$entry = createEntry(this, file1);
            if (!this.allPackEntries.contains(resourcepackrepository$entry)) {
               try {
                  resourcepackrepository$entry.updateResourcePack();
                  arraylist.add(resourcepackrepository$entry);
               } catch (Exception exception) {
                  arraylist.remove(resourcepackrepository$entry);
               }
            } else {
               int i = this.allPackEntries.indexOf(resourcepackrepository$entry);
               if (i > -1 && i < this.allPackEntries.size()) {
                  arraylist.add(this.allPackEntries.get(i));
               }
            }
         }

         this.allPackEntries.removeAll(arraylist);

         for (ResourcePackRepository.Entry resourcepackrepository$entry1 : this.allPackEntries) {
            resourcepackrepository$entry1.closeResourcePack();
         }

         this.allPackEntries = arraylist;
      }
   }

   @Override
   public List<ResourcePackRepository.Entry> getRepositoryEntriesAll() {
      return ImmutableList.copyOf(this.allPackEntries);
   }

   @Override
   public List<ResourcePackRepository.Entry> getRepositoryEntries() {
      return ImmutableList.copyOf(this.activePackEntries);
   }

   @Override
   public void setRepositories(List<ResourcePackRepository.Entry> repositories) {
      this.activePackEntries.clear();
      this.activePackEntries.addAll(repositories);
   }
}
