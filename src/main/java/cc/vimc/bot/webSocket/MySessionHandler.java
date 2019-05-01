package cc.vimc.bot.webSocket;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;

import java.lang.reflect.Type;

@Slf4j
public class MySessionHandler extends StompSessionHandlerAdapter {
    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        session.subscribe("/event/", this);
        session.send("/api/", "{\"name\":\"Client\"}".getBytes());
        log.info("New session: {}", session.getSessionId());
    }

    @Override
    public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
        exception.printStackTrace();
    }

//    @Override
//    public Type getPayloadType(StompHeaders headers) {
//        return n
//    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        Object msg =  payload;
        log.error(JSONUtil.toJsonStr(msg));
    }
}
