package com.carret.market.web.item;

import com.carret.market.support.authorization.AuthenticationPrincipal;
import com.carret.market.support.user.UserDetail;
import com.carret.market.domain.item.Category;
import com.carret.market.domain.member.Member;
import com.carret.market.application.item.ItemService;
import com.carret.market.web.item.dto.ItemInfoDto;
import com.carret.market.web.item.dto.ItemRequestDto;
import com.carret.market.web.item.dto.SubscriptRequestDto;
import com.carret.market.web.item.dto.SubscriptResultDto;
import com.carret.market.web.item.validate.ItemValidate;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/item")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;
    private final ItemValidate itemValidate;

    @ModelAttribute("categories")
    public List<Map.Entry<String, String>> getCategories() {
        return Category.getCategories();
    }

    @GetMapping
    public String articleView(Model model) {
        model.addAttribute("itemRequestDto", new ItemRequestDto());
        model.addAttribute("categories", Category.getCategories());

        return "/item/item";
    }

    @PostMapping
    public String addItem(@Valid @ModelAttribute ItemRequestDto itemRequestDto,
        BindingResult errors,
        @AuthenticationPrincipal UserDetail userDetail) throws IOException {

        itemValidate.validate(itemRequestDto, errors);
        if (errors.hasErrors()) {
            return "/item/item";
        }

        Member member = (Member) userDetail.getMemberDetail();
        itemService.save(itemRequestDto, member);

        return "redirect:/";
    }

    @GetMapping("/info")
    public String pageForm(@RequestParam Long itemId, Model model) {
        ItemInfoDto itemInfoDto = itemService.findByItemId(itemId);
        model.addAttribute("itemInfoDto", itemInfoDto);
        
        return "/item/itemInfo";
    }

    @PostMapping("/subscript")
    public ResponseEntity<SubscriptResultDto> subscript(@RequestBody SubscriptRequestDto subscriptRequestDto,
        @AuthenticationPrincipal UserDetail userDetail) {

        Member member = (Member) userDetail.getMemberDetail();
        return ResponseEntity.ok(itemService.subscript(subscriptRequestDto.getItemId(), member));
    }

}
