package astolfo.auth.handler;

import astolfo.auth.net.PacketOutput;
import astolfo.auth.packet.PacketId;
import astolfo.module.exploits.Disabler;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class DisablerScriptHandler extends PacketHandler<String> {
   public DisablerScriptHandler() {
      super(PacketId.DISABLER_SCRIPT);
   }

   public void handle(String string, PacketOutput packetOutput) {
      ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
      ScriptEngine scriptEngine = scriptEngineManager.getEngineByName("nashorn");
      scriptEngine.put("disabler", new DisablerScriptSender(this, packetOutput));

      try {
         Disabler.jsHandler = scriptEngine.eval(string);
      } catch (ScriptException scriptException) {
         scriptException.printStackTrace();
      }
   }
}
