package com.carret.market.Integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static reactor.core.publisher.Mono.when;

import com.carret.market.application.item.ItemService;
import com.carret.market.domain.item.Category;
import com.carret.market.domain.item.Item;
import com.carret.market.domain.item.ItemImage;
import com.carret.market.domain.item.ItemImageRepository;
import com.carret.market.domain.item.ItemRepository;
import com.carret.market.domain.item.ItemStatus;
import com.carret.market.domain.like.LikesRepository;
import com.carret.market.domain.member.Member;
import com.carret.market.domain.member.MemberRepository;
import com.carret.market.domain.member.Roletype;
import com.carret.market.infrastructure.file.S3UploadUtils;
import com.carret.market.infrastructure.file.UploadFile;
import com.carret.market.application.item.dto.ItemInfo;
import com.carret.market.application.item.dto.ItemList;
import com.carret.market.application.item.dto.ItemPagingRequest;
import com.carret.market.application.item.dto.ItemRequest;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

public class ItemServiceTest extends IntegrationTest {

    @Autowired
    ItemService itemService;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    ItemImageRepository itemImageRepository;

    @Autowired
    LikesRepository likesRepository;

    @MockBean
    private S3UploadUtils s3UploadUtils;

    Member 회원;

    Item 상품1;
    Item 상품2;

    @BeforeEach
    void setup() {
        회원 = 회원_추가("test@naver.com");

        상품1 = 상품_추가("test1", 회원);
        상품2 = 상품_추가("test2", 회원);
    }

    @DisplayName("이미지 전체 리스트 보여주기")
    @Test
    void findByItemList() {
        List<ItemList> itemList = itemService.findByItemList(new ItemPagingRequest());

        List<String> itemNameList = itemList.stream()
            .map(ItemList::getTitle)
            .collect(Collectors.toList());

        assertThat(itemNameList).containsExactly("test1", "test2");
    }

    @DisplayName("상품을 저장합니다.")
    @Test
    void save() throws IOException {
        Mockito.when(s3UploadUtils.uploadFile(any())).thenReturn(new UploadFile("원본이름", "변경된 이름", "저장된 URL"));

        ItemRequest itemRequestDto = ItemRequest.builder()
            .title("테스트 상품")
            .category(Category.BEAUTY)
            .price(1000)
            .description("네고 안됨 ㄴㄴ")
            .build();

        itemService.save(itemRequestDto, 회원);

        assertThat(itemRepository.findAll()).hasSize(3);
    }

    @DisplayName("상품 상세정보를 조회합니다.")
    @Test
    void findByItemId() {
        ItemInfo itemInfoDto = itemService.findByItemId(상품1.getId(), 회원.getId());

        assertThat(itemInfoDto.getTitle()).isEqualTo(상품1.getTitle());
    }

    @DisplayName("조회수를 증가합니다.")
    @Test
    void viewCountIncrease() {
        itemService.viewCountIncrease(상품1.getId());

        int viewCount = itemService.findByItemId(상품1.getId(), 회원.getId()).getViewCount();

        assertThat(viewCount).isEqualTo(1);
    }

    @DisplayName("관심수를 증가시킵니다.")
    @Test
    void subscriptTest() {
        itemService.subscript(상품1.getId(), 회원);

        assertThat(likesRepository.findAll()).hasSize(1);
    }

    private Item 상품_추가(String title, Member member) {
        Item item = itemRepository.save(Item.builder()
            .title(title)
            .detail("테스트내용")
            .price(1000)
            .status(ItemStatus.SELL)
            .category(Category.BOOK)
            .location("서울시 강남구")
            .member(member)
            .viewCount(0)
            .build());
        상품_이미지_추가("test", item);

        return item;
    }

    private ItemImage 상품_이미지_추가(String name, Item item) {
        return itemImageRepository.save(ItemImage.builder()
            .name(name)
            .url("/test")
            .thumbnail(true)
            .originalName("originalName")
            .item(item)
            .build());
    }

    private Member 회원_추가(String email) {
        return memberRepository.save(Member.builder()
            .email(email)
            .password("password")
            .name("테스트")
            .nickname("닉네임")
            .geolocation("서울특별시 서초구")
            .role(Roletype.ROLE_MEMBER)
            .build());
    }

}
