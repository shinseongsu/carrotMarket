package com.carret.market.service.item;

import com.carret.market.domain.item.Item;
import com.carret.market.domain.item.ItemImage;
import com.carret.market.domain.item.ItemImageRepository;
import com.carret.market.domain.item.ItemRepository;
import com.carret.market.domain.item.ItemStatus;
import com.carret.market.domain.like.Likes;
import com.carret.market.domain.like.LikesRepository;
import com.carret.market.domain.member.Member;
import com.carret.market.file.FileService;
import com.carret.market.file.UploadFile;
import com.carret.market.global.exception.ItemNotFoundException;
import com.carret.market.web.item.dto.ItemInfoDto;
import com.carret.market.web.item.dto.ItemListDto;
import com.carret.market.web.item.dto.ItemRequestDto;
import com.carret.market.web.item.dto.SubscriptRequestDto;
import com.carret.market.web.item.dto.SubscriptResultDto;
import java.io.IOException;
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
    private final FileService fileService;

    private static final String EMPTY_ITEM = "상품이 존재 하지 않습니다.";

    @Transactional(readOnly = true)
    public List<ItemListDto> findByItemList() {
        return itemRepository.findByItemListPaging();
    }

    public void save(ItemRequestDto itemRequestDto, Member member) throws IOException {
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

        List<ItemImage> itemImageList = this.uploadFiles(itemRequestDto.getImageUrl(), item);

        itemRepository.save(item);
        itemImageRepository.saveAll(itemImageList);
    }

    public ItemInfoDto findByItemId(Long itemId) {
        return itemRepository.findItemInfoByItemId(itemId)
            .orElseThrow(() -> new ItemNotFoundException(EMPTY_ITEM));
    }

    private List<ItemImage> uploadFiles(List<MultipartFile> images, Item item) throws IOException {
        List<UploadFile> uploadFileList = fileService.storeFiles(images);

        List<ItemImage> itemImageList = new ArrayList<>();
        IntStream.range(0, uploadFileList.size())
            .forEach(index -> {
                    UploadFile uploadFile = uploadFileList.get(index);

                    itemImageList.add(ItemImage.builder()
                        .name(uploadFile.getStoreFileName())
                        .url(uploadFile.getStoreFileName())
                        .thumbnail(index == 0)
                        .originalName(uploadFile.getOriginalFileName())
                        .item(item)
                        .build());
                }
            );

        return itemImageList;
    }

    @Transactional
    public void viewCountIncrease(Long itemId) {
        Item item = itemRepository.findById(itemId)
            .orElseThrow(() -> new ItemNotFoundException(EMPTY_ITEM));

        item.viewCountIncrease();
    }

    @Transactional
    public SubscriptResultDto subscript(Long itemId, Member member) {
        Optional<Likes> likesOptional = itemRepository.findLikesByItemIdAndMemberId(member.getId(), itemId);

        if(!likesOptional.isPresent()) {
            Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new ItemNotFoundException("아이템을 찾을 수 없습니다."));

            Likes likes = Likes.builder()
                .member(member)
                .item(item)
                .build();
            likesRepository.save(likes);

            return SubscriptResultDto.subscript();
        }

        likesRepository.deleteById(likesOptional.get().getId());
        return SubscriptResultDto.cancel();
    }

}
