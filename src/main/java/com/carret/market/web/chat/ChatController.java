package com.carret.market.web.chat;

import com.carret.market.application.chat.ChatService;
import com.carret.market.support.authorization.AuthenticationPrincipal;
import com.carret.market.support.user.UserDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @GetMapping("/chat/{productId}")
    public String chatForm(@PathVariable Long productId,
                            @AuthenticationPrincipal UserDetail userDetail,
                            Model model) {

        model.addAttribute("chatResponse", chatService.enterRoom(productId, userDetail.getMemberDetail().getEmail()));

        return "chat/chat";
    }

    @GetMapping("/chat/room/{roomId}")
    public String chatRoomForm(@PathVariable Long roomId,
                                @AuthenticationPrincipal UserDetail userDetail,
                                Model model) {

        model.addAttribute("chatResponse",
            chatService.findByChattingList(roomId, userDetail.getMemberDetail().getId()));

        return "chat/chat";
    }

    @GetMapping("/chatmenu")
    public String chatmenuForm(@AuthenticationPrincipal UserDetail userDetail,
                                Model model) {

        model.addAttribute("roomList", chatService.findByRoomList(userDetail.getMemberDetail().getId()));

        return "member/roomList";
    }

}
