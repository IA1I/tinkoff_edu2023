package edu.hw6;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Task6 {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Map<Integer, String> SERVICES = Map.ofEntries(
        Map.entry(135, "EPMAP"),
        Map.entry(137, "NETBIOS-NS"),
        Map.entry(138, "NETBIOS-DGM"),
        Map.entry(139, "NETBIOS-SSN"),
        Map.entry(445, "MICROSOFT-DS"),
        Map.entry(500, "ISAKMP"),
        Map.entry(1900, "Microsoft SSDP Enables discovery of UPnP devices"),
        Map.entry(3702, "Web Services Dynamic Discovery (WS-Discovery)"),
        Map.entry(4500, "IPsec NAT traversal"),
        Map.entry(5040, ""),
        Map.entry(5050, "Yahoo! Messenger"),
        Map.entry(5353, "Multicast DNS"),
        Map.entry(5355, "LLMNR—Link-Local Multicast Name Resolutio"),
        Map.entry(9797, ""),
        Map.entry(36283, "")
    );
    private static final String TCP = "TCP";
    private static final String UDP = "UDP";
    private static final String LINE_BREAK = "\n";
    private static final String SPACES_FOR_PROTOCOL = "       ";
    private static final String HEADER = "Протокол  Порт   Сервис\n";
    private static final String SPACE = " ";
    private static final int SPACES_FOR_PORT = 7;
    private static final int LAST_USER_OR_REGISTERED_PORT = 49151;

    private Task6() {
    }

    public static String scanPorts() {
        StringBuilder result = new StringBuilder();
        result.append(HEADER);

        for (int port = 1; port < LAST_USER_OR_REGISTERED_PORT; port++) {
            result.append(checkTCP(port));
            result.append(checkUDP(port));
        }

        return result.toString();
    }

    private static String checkTCP(int port) {
        StringBuilder result = new StringBuilder();
        try (ServerSocket socket = new ServerSocket(port)) {
            result.append("");
        } catch (IOException e) {
            result.append(TCP)
                .append(SPACES_FOR_PROTOCOL)
                .append(port)
                .append(SPACE.repeat(SPACES_FOR_PORT - String.valueOf(port).length()))
                .append(SERVICES.get(port))
                .append(LINE_BREAK);
            LOGGER.info("Busy TCP port found");
        }

        return result.toString();
    }

    private static String checkUDP(int port) {
        StringBuilder result = new StringBuilder();
        try (DatagramSocket datagramSocket = new DatagramSocket(port)) {
            result.append("");
        } catch (IOException e) {
            result.append(UDP)
                .append(SPACES_FOR_PROTOCOL)
                .append(port)
                .append(SPACE.repeat(SPACES_FOR_PORT - String.valueOf(port).length()))
                .append(SERVICES.get(port))
                .append(LINE_BREAK);
            LOGGER.info("Busy UDP port found");
        }

        return result.toString();
    }
}
