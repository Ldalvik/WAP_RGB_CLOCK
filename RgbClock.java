package plotter.clock;

import fi.iki.elonen.NanoHTTPD;
import org.java_websocket.server.WebSocketServer;
import plotter.Port;
import plotter.Utils;
import java.io.IOException;
import java.net.InetSocketAddress;

class RgbClock extends NanoHTTPD {
    private String WEBSERVER_FILE;
    private static RGB rgb;

    public RgbClock(int SERVER_PORT, String comPort, int socketTimeout, String WEBSERVER_FILE) throws IOException {
        super(SERVER_PORT);
        this.WEBSERVER_FILE = WEBSERVER_FILE;
        Port port = new Port(comPort);
        print(Utils.getServerUrl(SERVER_PORT));
        rgb = new RGB(port);
        start(socketTimeout, false);
    }

    @Override
    public NanoHTTPD.Response serve(NanoHTTPD.IHTTPSession session) {
        return newFixedLengthResponse(Utils.readFile(WEBSERVER_FILE));
    }

    static void print(String text) {
        System.out.println(text);
    }

    public static void main(String[] args) {

        try {
            new RgbClock(
                    8080,
                    "COM31",
                    1000,
                    "webapp.html"
            );
        } catch (IOException e) {
            System.err.println("Couldn't start server:\n" + e);
        }

        String host = "localhost";
        int port = 8887;
        WebSocketServer server = new Server(new InetSocketAddress(host, port), rgb);
        server.run();
    }
}
