package com.carret.market.web.item.validate;

import static com.carret.market.global.exception.ErrorCode.AT_LEAST_ONE_IMAGE;

import com.carret.market.application.item.dto.ItemRequest;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ItemValidate implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return ItemRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if(target instanceof ItemRequest && !errors.hasErrors()) {
            ItemRequest itemRequestDto = (ItemRequest) target;

            isExistsImage(errors, itemRequestDto);
        }
    }

    private void isExistsImage(Errors errors, ItemRequest itemRequestDto) {
        if(itemRequestDto.getImageUrl().get(0).isEmpty()) {
            errors.rejectValue("imageUrl", "", AT_LEAST_ONE_IMAGE.getMessage());
        }
    }

}
