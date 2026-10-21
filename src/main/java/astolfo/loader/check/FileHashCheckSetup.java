package astolfo.loader.check;

import astolfo.ClientAccess;

public class FileHashCheckSetup implements ClientAccess {
   private final PathListHolder pathListHolder;
   private final FileHashCheck fileHashCheck;

   public FileHashCheckSetup() {
      HashedFilePaths hashedfilepaths = new HashedFilePaths();
      this.pathListHolder = new PathListHolder(hashedfilepaths.getPathContainer());
      this.fileHashCheck = new FileHashCheck(this.pathListHolder);
   }

   public FileHashCheck getFileHashCheck() {
      return this.fileHashCheck;
   }
}
