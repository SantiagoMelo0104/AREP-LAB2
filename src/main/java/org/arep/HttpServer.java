package org.arep;

import java.net.*;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

/**
 * Class to start the server
 * @author Santiago Naranjo
 * @author Daniel Benavides
 */
public class HttpServer {
    /**
     * The main method that creates a `ServerSocket` and listens for incoming connections.
     * When a client connects, it reads the request, sends an HTTP response with the
     * requested movie information (if applicable), and then closes the connection.
     *
     * @param args command line arguments (not used)
     * @throws IOException if there is an error creating the ServerSocket or handling a client connection
     * @throws URISyntaxException if there is an error parsing the URI of the client's request
     */
    public static void main(String[] args) throws IOException, URISyntaxException {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(35000);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 35001.");
            System.exit(1);
        }

        boolean running = true;
        while (running) {
            Socket clientSocket = null;
            try {
                System.out.println("Listo para recibir ...");
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                System.err.println("Accept failed.");
                System.exit(1);
            }

            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            clientSocket.getInputStream()));
            String inputLine, outputLine = null;

            boolean firtsLine = true;
            String uriStr = "";

            while ((inputLine = in.readLine()) != null) {
                if (firtsLine) {
                    uriStr = inputLine.split(" ")[1];
                    firtsLine = false;
                }
                System.out.println("Received: " + inputLine);
                if (!in.ready()) {
                    break;
                }
            }

            URI requestUri = new URI(uriStr);
            if (requestUri.getPath().equals("/movie")) {
                String searchTerm = requestUri.getQuery().split("=")[1];
                try {
                    String responseBody = ApiConnection.httpClientAPI(searchTerm);
                    out.write("HTTP/1.1 200 OK\r\n");
                    out.write("Content-Type: text/html\r\n");
                    out.write("\r\n");
                    out.write(responseBody);
                    System.out.println( "----------------------------------");
                    System.out.println( responseBody);
                } catch (Exception e) {
                    out.write("HTTP/1.1 500 Internal Server Error\r\n");
                    out.write("Content-Type: text/plain\r\n");
                    out.write("\r\n");
                    out.write("Error al procesar la solicitud.");
                    out.flush();
                }
            }else{
                httpResponse(requestUri, clientSocket.getOutputStream());
            }
            //out.println(outputLine);

            out.close();
            in.close();
            clientSocket.close();
        }
        serverSocket.close();
    }

    /**
     * Returns an HTTP error message as an HTML document indicating a 404 Not Found status.
     * @return A string containing an HTTP error message in the format of an HTTP response.
     */
    public static String httpError(){
        String outputLine ="HTTP/1.1 404 Not Found \r\n"
                + "Content-Type:text/html\r\n"
                + "\r\n"
                + "<!DOCTYPE html>\n"
                + "<html>\n"
                + "    <head>\n"
                + "        <title>Form Example</title>\n"
                + "        <meta charset=\"UTF-8\">\n"
                + "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                + "    </head>\n"
                + "    <body>\n"
                + "     <h1>Error</h1>\n"
                + "    <body>"
                + "</html>";

        return outputLine;

    }
    /**
     * This method handles the HTTP response for a given requested URI. It checks if the requested file exists in the "public" directory.
     * If the file exists, it sends an HTTP 200 OK response with the appropriate content type.
     * If the file does not exist, it sends an HTTP 404 Not Found error response.
     *
     * @param requestedUri The requested URI.
     * @param outputStream The output stream to write the HTTP response to.
     * @throws IOException If an I/O error occurs while reading the file or writing the HTTP response.
     */
    public static void httpResponse(URI requestedUri, OutputStream outputStream) throws IOException {
        Path file = Paths.get("target/classes/public" + requestedUri.getPath());
        if (Files.exists(file)) {
            String contentType = Files.probeContentType(file);
            if (contentType != null) {
                outputStream.write("HTTP/1.1 200 OK\r\n".getBytes());
                outputStream.write(("Content-Type: " + contentType + "\r\n").getBytes());
                outputStream.write("\r\n".getBytes());
                if (Files.isRegularFile(file) && contentType.startsWith("image/")) {
                    byte[] bytes = Files.readAllBytes(file);
                    outputStream.write(bytes);
                } else {
                    String content = new String(Files.readAllBytes(file), StandardCharsets.UTF_8);
                    outputStream.write(content.getBytes());
                }
            } else {
                String outputLine = httpError();
                outputStream.write(outputLine.getBytes());
            }
        } else {
            String outputLine = httpError();
            outputStream.write(outputLine.getBytes());
        }
    }

}
