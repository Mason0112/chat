package com.mason.chat.config;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import com.mason.chat.chat.ChatMessage;
import com.mason.chat.chat.MessageType;

@Component
@Slf4j
@RequiredArgsConstructor
//加入跟傳訊息時就會在聊天室顯示 但想顯示出有人離開就要使用listener
public class WebSocketEventListener {

    //Spring 中的一個介面 主要是STOMP中傳遞訊息 最常用convertAndSend(destination, payload): 將給定的對象轉換為消息並發送到指定目的地。
    //和convertAndSendToUser(user, destination, payload): 發送消息給特定用戶。
    private final SimpMessageSendingOperations messagingTemplate;
    @EventListener
    //SessionDisconnectEvent 在Websocket斷開連結時會觸發 裡用它來觸發下面的邏輯
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        //透過StompHeaderAccessor來訪問離開事件(SessionDisconnectEvent)的header利用.getMessage 之後拿到是誰離開
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String username = (String) headerAccessor.getSessionAttributes().get("username");
        if (username != null) {
            log.info("user disconnected: {}", username);
            var chatMessage = ChatMessage.builder()
                    .type(MessageType.LEAVE)
                    .sender(username)
                    .build();
            //透過離開websocket所產生的東西打包成一個物件 再透過SimpMessageSendingOperations做公告
            messagingTemplate.convertAndSend("/topic/public", chatMessage);
        }
    }

}