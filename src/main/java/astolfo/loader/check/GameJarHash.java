package astolfo.loader.check;

import astolfo.config.ConfigFiles;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.xml.bind.DatatypeConverter;

public class GameJarHash {
   private List<String> hashes;
   private static final String b = "MD5";

   public GameJarHash() {
      try {
         this.hashes = new ArrayList<>(Collections.singleton(this.computeHash()));
      } catch (URISyntaxException | NoSuchAlgorithmException | IOException ioexception) {
      }
   }

   public List<String> getHashes() {
      return this.hashes;
   }

   private String computeHash() throws IOException, NoSuchAlgorithmException, URISyntaxException {
      byte[] abyte = Files.readAllBytes(Paths.get(ConfigFiles.getGameJarPath()));
      byte[] abyte1 = MessageDigest.getInstance(b).digest(abyte);
      return DatatypeConverter.printHexBinary(abyte1).toLowerCase();
   }
}
