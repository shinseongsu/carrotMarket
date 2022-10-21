package com.carret.market.web.item;

import com.carret.market.support.authorization.AuthenticationPrincipal;
import com.carret.market.support.user.UserDetail;
import com.carret.market.domain.item.Category;
import com.carret.market.domain.member.Member;
import com.carret.market.application.item.ItemService;
import com.carret.market.web.item.dto.ItemInfoDto;
import com.carret.market.web.item.dto.ItemListDto;
import com.carret.market.web.item.dto.ItemRequestDto;
import com.carret.market.web.item.dto.ItemRequest;
import com.carret.market.web.item.dto.SubscriptRequestDto;
import com.carret.market.web.item.dto.SubscriptResultDto;
import com.carret.market.web.item.validate.ItemValidate;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/item")
@RequiredArgsConstructor
@Slf4j
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

        return "item/item";
    }

    @GetMapping("/edit/{itemId}")
    public String editForm(@PathVariable Long itemId,
        Model model) {

        model.addAttribute("itemRequestDto", itemService.selectEditItem(itemId));
        model.addAttribute("categories", Category.getCategories());

        return "item/item";
    }

    @PostMapping("/edit")
    public String edit(@ModelAttribute ItemRequestDto itemRequestDto) {
        itemService.updateEditItem(itemRequestDto);

        return "redirect:/item/edit/" + itemRequestDto.getItemId();
    }


    @PostMapping
    public String addItem(@Valid @ModelAttribute ItemRequestDto itemRequestDto,
        BindingResult errors,
        @AuthenticationPrincipal UserDetail userDetail) throws IOException {

        itemValidate.validate(itemRequestDto, errors);
        if (errors.hasErrors()) {
            return "item/item";
        }

        Member member = (Member) userDetail.getMemberDetail();
        itemService.save(itemRequestDto, member);

        return "redirect:/";
    }

    @GetMapping("/info")
    public String pageForm(@RequestParam Long itemId,
        @AuthenticationPrincipal UserDetail userDetail,
        Model model) {
        ItemInfoDto itemInfoDto = itemService.findByItemId(itemId, userDetail.getMemberDetail().getId());
        model.addAttribute("itemInfoDto", itemInfoDto);
        
        return "item/itemInfo";
    }

    @PostMapping("/subscript")
    public ResponseEntity<SubscriptResultDto> subscript(@RequestBody SubscriptRequestDto subscriptRequestDto,
        @AuthenticationPrincipal UserDetail userDetail) {

        Member member = (Member) userDetail.getMemberDetail();
        return ResponseEntity.ok(itemService.subscript(subscriptRequestDto.getItemId(), member));
    }

    @PostMapping("/list")
    public ResponseEntity<List<ItemListDto>> searchItemList(@RequestBody ItemRequest itemRequest) {
        return ResponseEntity.ok(itemService.findByItemList(itemRequest));
    }

}
