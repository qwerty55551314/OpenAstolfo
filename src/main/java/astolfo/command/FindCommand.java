package astolfo.command;

import astolfo.ClientAccess;
import astolfo.module.exploits.Denicker;
import astolfo.util.GameUtil;
import astolfo.util.HttpRequest;
import astolfo.util.HttpUtil;
import astolfo.util.Stopwatch;
import java.util.Arrays;

public class FindCommand extends Command {
   private Stopwatch cooldown = new Stopwatch();

   public FindCommand() {
      super("find", "<player name>");
      this.setAliases(Arrays.asList("nick"));
   }

   @Override
   public void runCommand(String[] strings) {
      boolean flag = GameUtil.isInHypixelGame();
      if (!flag) {
         ClientAccess.chat.print("This command only runs in Hypixel");
      } else {
         boolean flag1 = this.cooldown.hasElapsed(15000.0F);
         if (!flag1) {
            float f = (float)((double)this.cooldown.getElapsed() / 1000.0);
            ClientAccess.chat.print("You must wait " + (15.0F - f) + "s before doing this again");
         } else {
            switch (strings.length) {
               case 2:
                  new Thread(
                        () -> {
                           String s = HttpUtil.get(
                              new HttpRequest()
                                 .setUrl(String.format("https://api.hypixel.net/player?key=%s&name=%s", Denicker.hypixelAPI.getValue(), strings[1]))
                           );
                           if (s == null) {
                              ClientAccess.chat.printError("Wrong API Key (Check Denicker module API key)");
                           } else {
                              boolean flag2 = s.contains("\"player\":null");
                              if (flag2) {
                                 ClientAccess.chat.printError("This player is non existant");
                              } else {
                                 boolean flag3 = s.contains("Key throttle");
                                 if (flag3) {
                                    ClientAccess.chat.printError("The API key is throttled");
                                 } else if (s.contains("\"success\":false")) {
                                    ClientAccess.chat.printError("Invalid API key");
                                 } else {
                                    HttpRequest httprequest = new HttpRequest().setUrl(String.format("http://144.172.70.174:8081/%s@debug_", strings[1]));
                                    httprequest.setUserAgent("kpRNZWBTM6hut7NB");
                                    String s1 = HttpUtil.get(httprequest);
                                    if (s1 != null) {
                                       String s2 = s1.replace("\n", "");
                                       if (strings[1].contains(s2)) {
                                          ClientAccess.chat.print("This player has no nick");
                                       } else {
                                          ClientAccess.chat.print(String.format("%s nick is %s", strings[1], s2));
                                       }

                                       Denicker.nicks.put(s2, strings[1]);
                                       this.cooldown.reset();
                                    }
                                 }
                              }
                           }
                        }
                     )
                     .start();
            }
         }
      }
   }
}
