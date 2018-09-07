package geekbrains.dmitriymx.java2.homework6;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;

@Slf4j
@NoArgsConstructor
public class Server implements Runnable {
    @Getter
    private int port;
    @Getter
    private InetAddress host;
    private Thread listenThread;
    private ServerSocket serverSocket;

    public void setPort(int port) {
        if (port < 0 || port > 65535) {
            log.warn("PORT: argument \"{}\" out of range [0-65535]", port);
            log.warn("PORT: using default value - {}", this.port);
        } else {
            this.port = port;
        }
    }

    public void setHost(String host) throws UnknownHostException {
        if (host != null && !host.isEmpty()) {
            try {
                this.host = InetAddress.getByName(host);
            } catch (UnknownHostException e) {
                log.error("HOST: incorrect host \"{}\"", host);
                log.error("HOST: using default value - 0.0.0.0");
                this.host = InetAddress.getByName("0.0.0.0");
            }
        } else {
            this.host = InetAddress.getByName("0.0.0.0");
        }
    }

    public void start() throws IOException {
        listenThread = new Thread(this, "SERVER Listener");
        listenThread.start();
    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(port, 0, host);
            log.info("Сервер запущен ({}:{})", getHost().getHostAddress(), getPort());
        } catch (IOException e) {
            log.error("Server start", e);
            return;
        }

        try {
            log.debug("Ожидаем подключения клиента...");
            serverSocket.accept();
        } catch (IOException e) {
            log.error("Server waiting client", e);
            return;
        }
    }
}
