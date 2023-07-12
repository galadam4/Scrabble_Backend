package Part3;


import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

/*
 * BookScrabbleHandler class implements the ClientHandler interface,
 * indicating that it provides an implementation for handling client connection
 */
public class BookScrabbleHandler implements ClientHandler {
    String in;
    PrintWriter serverOutput;

    /*
     *  reads the input line from the client, extracts the books,
     *  and performs either a query or challenge operation using the DictionaryManager
     *  it writes the result back to the client using the serverOutput PrintWriter.
     */
    @Override
    public void handleClient(InputStream inFromClient, OutputStream outToClient) {
        Scanner in = new Scanner(inFromClient);
        String line = in.nextLine();
        serverOutput = new PrintWriter(outToClient);
        String[] books = line.substring(1).split(",");

        if (line.charAt(0) == 'Q') {
            if (DictionaryManager.get().query(books)) {
                serverOutput.println("true\n");
                serverOutput.flush();
            } else {
                serverOutput.println("false\n");
                serverOutput.flush();
            }
        }
        else{
            if (DictionaryManager.get().challenge(books)) {
                serverOutput.println("true\n");
                serverOutput.flush();
            } else {
                serverOutput.println("false\n");
                serverOutput.flush();
            }
        }
    }

    @Override
    public void close() {
        serverOutput.close();
    }
}
