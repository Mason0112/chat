package com.mason.chat.chat;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {


    @MessageMapping("/chat.sendMessage")
    ///topic開頭的前綴才會經過前面config的設定 這裡設定public是代表訂閱了public的人都能收到這則訊息
    @SendTo("/topic/public")
    //Payload用於websocket與STOMP請求資料
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
        return chatMessage;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser(
        @Payload ChatMessage chatMessage, 
        SimpMessageHeaderAccessor headerAccessor) {
            // 將chatMessage.getSender()存進Session的username,這樣可以在Websocket中辨識這個用戶 在現有的websocket中儲存用戶信息
            // 如果要使用websocket 結合JWT要建立WebSocketHandshakeInterceptor 在握手階段驗證token並將用戶attributes.put("username", username)
            // 這樣就不用在這做session的Put
            headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
            return chatMessage;
    }
}