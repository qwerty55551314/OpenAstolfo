package astolfo.auth.net;

public interface ConnectionHandlerFactory {
   ConnectionHandler createHandler(NioConnection var1);
}
