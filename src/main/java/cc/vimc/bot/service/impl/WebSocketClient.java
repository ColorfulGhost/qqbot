package cc.vimc.bot.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.asynchttpclient.Dsl;
import org.asynchttpclient.ws.WebSocket;
import org.asynchttpclient.ws.WebSocketListener;
import org.asynchttpclient.ws.WebSocketUpgradeHandler;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

/**
 * @author Ghost
 * @Description 处理WebSocket消息
 * @return
 * @date 2019/12/3
 */
@Slf4j
@Service
public class WebSocketClient {

    public WebSocket getWebSocket() {
        return webSocket;
    }

    public void setWebSocket(WebSocket webSocket) {
        this.webSocket = webSocket;
    }

    private WebSocket webSocket;

    public WebSocketClient() {
        WebSocketUpgradeHandler.Builder upgradeHandlerBuilder
                = new WebSocketUpgradeHandler.Builder();
        WebSocketUpgradeHandler wsHandler = upgradeHandlerBuilder
                .addWebSocketListener(new WebSocketListener() {
                    @Override
                    public void onOpen(WebSocket websocket) {
                        // WebSocket connection opened

                    }

                    @Override
                    public void onClose(WebSocket websocket, int code, String reason) {
                        // WebSocket connection closed
                    }

                    @Override
                    public void onError(Throwable t) {
                        // WebSocket connection error
                    }
                }).build();
        WebSocket webSocketClient = null;
        try {
            webSocketClient = Dsl.asyncHttpClient()
                    .prepareGet("ws://home.vimc.cc:6700/")
                    .setRequestTimeout(5000)
                    .execute(wsHandler)
                    .get();
        } catch (InterruptedException | ExecutionException e) {
            log.error(e.getMessage(), e);
        }
        this.webSocket = webSocketClient;
    }

    public void sendMessage(){

    }

}
