package modi.modurang.domain.stomp.controller;

import modi.modurang.domain.stomp.dto.ChatMessageDto;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class StompChatController {

    private final SimpMessagingTemplate template;

    public StompChatController(SimpMessagingTemplate template) {
        this.template = template;
    }

    @MessageMapping("/chat/message")
    public void message(ChatMessageDto message) {
        template.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
    }
}
