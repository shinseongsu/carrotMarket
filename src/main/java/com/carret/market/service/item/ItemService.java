package com.carret.market.service.item;

import com.carret.market.domain.item.Item;
import com.carret.market.domain.item.ItemImage;
import com.carret.market.domain.item.ItemImageRepository;
import com.carret.market.domain.item.ItemRepository;
import com.carret.market.domain.item.ItemStatus;
import com.carret.market.domain.member.Member;
import com.carret.market.file.FileService;
import com.carret.market.file.UploadFile;
import com.carret.market.web.item.dto.ItemListDto;
import com.carret.market.web.item.dto.ItemRequestDto;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
    private final FileService fileService;

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
            .location("서울시 강남구") // TODO - 지리 API 사용 전 하드 코딩
            .member(member)
            .build();

        List<ItemImage> itemImageList = this.uploadFiles(itemRequestDto.getImageUrl(), item);

        itemRepository.save(item);
        itemImageRepository.saveAll(itemImageList);
    }

    private List<ItemImage> uploadFiles(List<MultipartFile> images, Item item) throws IOException {
        List<UploadFile> uploadFileList = fileService.storeFiles(images);

        List<ItemImage> itemImageList = new ArrayList<>();
        IntStream.range(0, uploadFileList.size())
            .forEach(index -> {
                    UploadFile uploadFile = uploadFileList.get(index);

                    itemImageList.add(ItemImage.builder()
                        .name(uploadFile.getStoreFileName())
                        .url(uploadFile.getFileUploadUrl())
                        .thumbnail(index == 0)
                        .originalName(uploadFile.getOriginalFileName())
                        .item(item)
                        .build());
                }
            );

        return itemImageList;
    }

}
