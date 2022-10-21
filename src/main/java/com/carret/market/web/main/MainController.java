package com.carret.market.web.main;

import com.carret.market.application.item.ItemService;
import com.carret.market.application.item.dto.ItemPagingRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final ItemService itemService;

    @GetMapping("/")
    public String main(Model model,
        @RequestParam(required = false) String search) {
        model.addAttribute("itemList", itemService.findByItemList(new ItemPagingRequest(search)));

        return "main/main";
    }

}
