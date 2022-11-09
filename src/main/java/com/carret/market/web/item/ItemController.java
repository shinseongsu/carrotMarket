package com.carret.market.web.item;

import com.carret.market.application.item.ItemService;
import com.carret.market.application.item.dto.ItemInfo;
import com.carret.market.application.item.dto.ItemList;
import com.carret.market.application.item.dto.ItemPagingRequest;
import com.carret.market.application.item.dto.ItemRequest;
import com.carret.market.application.item.dto.SubscriptRequest;
import com.carret.market.application.item.dto.SubscriptResult;
import com.carret.market.domain.item.Category;
import com.carret.market.domain.member.Member;
import com.carret.market.support.authorization.AuthenticationPrincipal;
import com.carret.market.support.user.UserDetail;
import com.carret.market.web.item.validate.ItemValidate;
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
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ItemController {

    private final ItemService itemService;
    private final ItemValidate itemValidate;

    @ModelAttribute("/item/categories")
    public List<Map.Entry<String, String>> getCategories() {
        return Category.getCategories();
    }

    @GetMapping
    public String articleView(Model model) {
        model.addAttribute("itemRequest", new ItemRequest());
        model.addAttribute("categories", Category.getCategories());

        return "item/item";
    }

    @GetMapping("/item/edit/{itemId}")
    public String editForm(@PathVariable Long itemId,
                            Model model) {

        model.addAttribute("itemRequest", itemService.selectEditItem(itemId));
        model.addAttribute("categories", Category.getCategories());

        return "item/item";
    }

    @PostMapping("/item/edit")
    public String edit(@ModelAttribute ItemRequest itemRequestDto) {
        itemService.updateEditItem(itemRequestDto);

        return "redirect:/item/edit/" + itemRequestDto.getItemId();
    }


    @PostMapping("/item")
    public String addItem(@Valid @ModelAttribute ItemRequest itemRequestDto,
                            BindingResult errors,
                            @AuthenticationPrincipal UserDetail userDetail) {

        itemValidate.validate(itemRequestDto, errors);
        if (errors.hasErrors()) {
            return "item/item";
        }

        Member member = (Member) userDetail.getMemberDetail();
        itemService.save(itemRequestDto, member);

        return "redirect:/";
    }

    @GetMapping("/item/info")
    public String pageForm(@RequestParam Long itemId,
                            @AuthenticationPrincipal UserDetail userDetail,
                            Model model) {

        ItemInfo itemInfoDto = itemService.findByItemId(itemId, userDetail.getMemberDetail().getId());
        model.addAttribute("itemInfo", itemInfoDto);

        return "item/itemInfo";
    }

    @PostMapping("/item/subscript")
    public ResponseEntity<SubscriptResult> subscript(@RequestBody SubscriptRequest subscriptRequestDto,
                                                        @AuthenticationPrincipal UserDetail userDetail) {

        Member member = (Member) userDetail.getMemberDetail();
        return ResponseEntity.ok(itemService.subscript(subscriptRequestDto.getItemId(), member));
    }

    @PostMapping("/item/list")
    public ResponseEntity<List<ItemList>> searchItemList(@RequestBody ItemPagingRequest itemRequest) {
        return ResponseEntity.ok(itemService.findByItemList(itemRequest));
    }

}
