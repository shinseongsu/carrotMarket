package com.carret.market.application.item;

import static com.carret.market.global.exception.ErrorCode.NOT_FOUND_ITEM;

import com.carret.market.domain.item.Item;
import com.carret.market.domain.item.ItemImage;
import com.carret.market.domain.item.ItemImageRepository;
import com.carret.market.domain.item.ItemRepository;
import com.carret.market.domain.item.ItemStatus;
import com.carret.market.domain.like.Likes;
import com.carret.market.domain.like.LikesRepository;
import com.carret.market.domain.member.Member;
import com.carret.market.global.exception.ItemNotFoundException;
import com.carret.market.infrastructure.file.S3UploadUtils;
import com.carret.market.infrastructure.file.UploadFile;
import com.carret.market.application.item.dto.ItemInfo;
import com.carret.market.application.item.dto.ItemList;
import com.carret.market.application.item.dto.ItemPagingRequest;
import com.carret.market.application.item.dto.ItemRequest;
import com.carret.market.application.item.dto.SubscriptResult;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemImageRepository itemImageRepository;
    private final LikesRepository likesRepository;
    private final S3UploadUtils s3UploadUtils;

    @Transactional(readOnly = true)
    public List<ItemList> findByItemList(ItemPagingRequest itemRequest) {
        return itemRepository.findByItemListPaging(itemRequest);
    }

    public void save(ItemRequest itemRequestDto, Member member) {
        Item item = Item.builder()
            .title(itemRequestDto.getTitle())
            .detail(itemRequestDto.getDescription())
            .price(itemRequestDto.getPrice())
            .status(ItemStatus.SELL)
            .category(itemRequestDto.getCategory())
            .location(member.getGeolocation())
            .member(member)
            .viewCount(0)
            .build();

        List<ItemImage> itemImageList = this.uploadFiles(itemRequestDto.getImageUrl(), item, 0);

        itemRepository.save(item);
        itemImageRepository.saveAll(itemImageList);
    }

    private List<ItemImage> uploadFiles(List<MultipartFile> images, Item item, int itemImageSize) {
        List<UploadFile> uploadFileList = s3UploadUtils.uploadFiles(images);

        List<ItemImage> itemImageList = new ArrayList<>();
        IntStream.range(0, uploadFileList.size())
            .forEach(index -> {
                    UploadFile uploadFile = uploadFileList.get(index);

                    itemImageList.add(ItemImage.builder()
                        .name(uploadFile.getStoreFileName())
                        .url(uploadFile.getFileUploadUrl())
                        .thumbnail(index == 0 & itemImageSize == 0)
                        .originalName(uploadFile.getOriginalFileName())
                        .item(item)
                        .build());
                }
            );

        return itemImageList;
    }

    @Transactional(readOnly = true)
    public ItemInfo findByItemId(Long itemId, Long memberId) {
        return itemRepository.findItemInfoByItemId(itemId, memberId)
            .orElseThrow(() -> new ItemNotFoundException(NOT_FOUND_ITEM));
    }

    @Transactional(readOnly = true)
    public ItemRequest selectEditItem(Long itemId) {
        return itemRepository.findEditItemByItemId(itemId)
            .orElseThrow(() -> new ItemNotFoundException(NOT_FOUND_ITEM));
    }

    public void updateEditItem(ItemRequest itemRequestDto) {
        Item item = itemRepository.findById(itemRequestDto.getItemId())
            .orElseThrow(() -> new ItemNotFoundException(NOT_FOUND_ITEM));

        item.updateItem(itemRequestDto.getTitle(), itemRequestDto.getDescription(), itemRequestDto.getPrice(),
            itemRequestDto.getCategory());
        itemImageRepository.deleteItemImageByUrl(itemRequestDto.getDeleteImageIds());

        List<ItemImage> itemImageList = itemImageRepository.findByItem(item);

        if (!itemRequestDto.getImageUrl().get(0).isEmpty()) {
            itemImageRepository.saveAll(this.uploadFiles(itemRequestDto.getImageUrl(), item, itemImageList.size()));
        }
    }

    public void viewCountIncrease(Long itemId) {
        Item item = itemRepository.findById(itemId)
            .orElseThrow(() -> new ItemNotFoundException(NOT_FOUND_ITEM));

        item.viewCountIncrease();
    }

    public SubscriptResult subscript(Long itemId, Member member) {
        Optional<Likes> likesOptional = itemRepository.findLikesByItemIdAndMemberId(member.getId(), itemId);

        if (!likesOptional.isPresent()) {
            Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new ItemNotFoundException(NOT_FOUND_ITEM));

            likesRepository.save(new Likes(member, item));
            return SubscriptResult.subscript();
        }

        likesRepository.deleteById(likesOptional.get().getId());
        return SubscriptResult.cancel();
    }

}
