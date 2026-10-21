package astolfo.loader.check;

import astolfo.util.container.ListContainerOwner;
import java.io.File;

public class VirtualMachineFileListOwner {
   private final ListContainerOwner<File> fileContainer;

   public VirtualMachineFileListOwner() {
      VirtualMachineFiles virtualmachinefiles = new VirtualMachineFiles();
      this.fileContainer = new ListContainerOwner<>(virtualmachinefiles.getFiles());
   }

   public ListContainerOwner<File> getFileContainer() {
      return this.fileContainer;
   }
}
