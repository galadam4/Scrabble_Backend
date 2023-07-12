package Part3;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface ClientHandler {
	void handleClient(InputStream inFromClient, OutputStream outToClient) throws IOException;
	void close()throws IOException;
}
