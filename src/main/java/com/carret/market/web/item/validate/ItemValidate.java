package com.carret.market.web.item.validate;

import com.carret.market.web.item.dto.ItemRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ItemValidate implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return ItemRequestDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if(target instanceof ItemRequestDto && !errors.hasErrors()) {
            ItemRequestDto itemRequestDto = (ItemRequestDto) target;

            isExistsImage(errors, itemRequestDto);
        }
    }

    private void isExistsImage(Errors errors, ItemRequestDto itemRequestDto) {
        if(itemRequestDto.getImageUrl().get(0).isEmpty()) {
            errors.rejectValue("imageUrl", "", "하나 이상의 이미지가 필요합니다.");
        }
    }

}
