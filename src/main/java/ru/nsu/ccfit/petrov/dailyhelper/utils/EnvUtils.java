package ru.nsu.ccfit.petrov.dailyhelper.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EnvUtils {

    private final Environment environment;

    private Integer port;
    private String hostName;

    public Integer getPort() {
        if (port == null) {
            port = Integer.parseInt(
                Objects.requireNonNull(environment.getProperty("local.server.port")));
        }
        return port;
    }

    @SneakyThrows
    public String getHostName() {
        if (hostName == null) {
            hostName = InetAddress.getLocalHost()
                                  .getHostName();
        }
        return hostName;
    }

    public String getHostUrl() {
        return "http://" + getHostName() + ":" + getPort();
    }
}
