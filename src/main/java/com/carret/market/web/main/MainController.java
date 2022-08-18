package com.carret.market.web.main;

import com.carret.market.service.item.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final ItemService itemService;

    @GetMapping("/")
    public String main(Model model) {
        model.addAttribute("itemList", itemService.findByItemList());

        return "/main/main";
    }

}
