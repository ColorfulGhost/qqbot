package cc.vimc.bot.webSocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint("/event/")
@Component
public class WebSocketServer {
    private static final Logger logger = LoggerFactory.getLogger(WebSocketServer.class);


    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;
    /**
     * 这里顺便看了一眼opyOnWrite
     * 容器即写时复制的容器。通俗的理解是当我们往一个容器添加元素的时候，
     * 不直接往当前容器添加，而是先将当前容器进行Copy，复制出一个新的容器，
     * 然后新的容器里添加元素，添加完元素之后，再将原容器的引用指向新的容器。
     * 这样做的好处是我们可以对CopyOnWrite容器进行并发的读，而不需要加锁，因为当前容器不会添加任何元素。
     * 所以CopyOnWrite容器也是一种读写分离的思想，读和写不同的容器。
     *
     * 这里可以以后做对多个端进行握手 暂时CoolQ只有一个端 点对点 只需要创建一个String消息内容就可以了
     */
    private CopyOnWriteArraySet<WebSocketServer> webSocketSet = new CopyOnWriteArraySet();

    String message;
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        webSocketSet.add(this);
        try {
            sendMessage("链接成功！");
        } catch (IOException e) {
            logger.error("webSocket 链接失败异常:{}", e);
        }
    }

    /**
     * 收到客户端消息后调用的方法
     */

    @OnMessage
    public void onMessage(String message,Session session) throws IOException {
        logger.info("收到消息：{}",message);
        this.message = message;
//        for (WebSocketServer webSocketServer : webSocketSet) {
//            webSocketServer.sendMessage(message);
//        }
    }
    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        webSocketSet.remove(this);
    }

    @OnError
    public void onError(Session session, Throwable error) {
        logger.error("发生错误");
        error.printStackTrace();
    }

    /**
     * 实现服务器主动推送
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }
}
