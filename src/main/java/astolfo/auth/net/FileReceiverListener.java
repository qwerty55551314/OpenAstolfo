package astolfo.auth.net;

public interface FileReceiverListener {
   void onReceiverError(FileReceiver var1, String var2, Exception var3);

   void onSessionClosed(FileReceiver var1, String var2, FileReceiveSession var3);

   void onSessionError(FileReceiver var1, String var2, Exception var3, FileReceiveSession var4);
}
