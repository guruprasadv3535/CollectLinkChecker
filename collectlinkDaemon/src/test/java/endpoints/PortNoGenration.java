package endpoints;

import java.io.IOException;
import java.net.ServerSocket;

public class PortNoGenration {
	
	public static int callPort() {
        int port = findAvailablePort();
        if (port != -1) {
        	return port;
        } else {
        	return 0;
        }
    }

    public static int findAvailablePort() {
        for (int port = 1024; port <= 65535; port++) {
            if (isPortAvailable(port)) {
                return port;
            }
        }
        return -1; // No available port found.
    }

    public static boolean isPortAvailable(int port) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            // Port is available, no exception thrown.
            return true;
        } catch (IOException e) {
            // Port is already in use.
            return false;
        }
    }

}
