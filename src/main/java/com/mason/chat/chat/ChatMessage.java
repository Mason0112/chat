package com.mason.chat.chat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
可以將這些程式碼作對應SQL的寫法 
@Entity
@Table(name = "chat_messages") 等等
就可以將資料存進SQL中
再來新增JPAreposirory 在controller中增加save將對話紀錄存進SQL中 就能夠獲得對話紀錄
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatMessage {


    private String content;

    private String sender;
    //建立一個枚舉 限制這個種類會有的型態 也能夠透過.呼叫 ex:MessageType.LEAVE
    private MessageType type;
}
