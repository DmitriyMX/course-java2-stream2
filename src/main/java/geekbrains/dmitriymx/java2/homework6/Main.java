package geekbrains.dmitriymx.java2.homework6;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class Main {
    private final String host;
    private final int port;

    private static void printUsage() {
        log.info("arg.1 : listen ip (string) (opt.) (default: 0.0.0.0)");
        log.info("arg.2 : listen port (int, 0-65535) (opt.) (default: 5566)");
    }

    public static void main(String[] args) throws IOException {
        int port = -1;
        String host = null;

        // Парсим аргументы
        if (args.length == 0) {
            printUsage();
            return;
        } else if (args.length == 1) {
            try {
                port = Integer.valueOf(args[0]);
            } catch (NumberFormatException e) {
                // значит у нас там не число
                host = args[0]; // TODO здесь надо производить проверку по маске "[0-255].[0-255].[0-255].[0-255]", но мне лень
            }
        } else {
            host = args[0]; // TODO здесь надо производить проверку по маске "[0-255].[0-255].[0-255].[0-255]", но мне лень
            try {
                port = Integer.valueOf(args[1]);
            } catch (NumberFormatException e) {
                log.error("PORT: incorrect int argument \"{}\"", args[1]);
                return;
            }
        }

        Server server = new Server();
        server.setHost(host);
        server.setPort(port);
        server.start();
    }
}
