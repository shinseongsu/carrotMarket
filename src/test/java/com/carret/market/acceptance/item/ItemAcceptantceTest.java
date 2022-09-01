package com.carret.market.acceptance.item;

import static com.carret.market.acceptance.item.ItemStep.로그인_후_상품_관심_주기;
import static com.carret.market.acceptance.item.ItemStep.로그인_후_상품등록;
import static com.carret.market.acceptance.member.MemberStep.회원_저장;
import static org.assertj.core.api.Assertions.assertThat;

import com.carret.market.acceptance.AcceptanceTest;
import com.carret.market.acceptance.member.MemberStep;
import com.carret.market.domain.item.Item;
import com.carret.market.domain.item.ItemRepository;
import com.carret.market.domain.like.LikesRepository;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("상품관련")
public class ItemAcceptantceTest extends AcceptanceTest {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private LikesRepository likesRepository;

    @BeforeEach
    void init() {
        회원_저장("test@naver.com");
    }

    @Test
    void addItem() throws IOException {
        로그인_후_상품등록("test@naver.com", "컴퓨터");

        assertThat(itemRepository.findAll()).hasSize(1);
    }

    @Test
    void subscriptTest() throws IOException {
        로그인_후_상품등록("test@naver.com", "컴퓨터");

        Item item = itemRepository.findAll().get(0);
        로그인_후_상품_관심_주기("test@naver.com", item.getId());

        assertThat(likesRepository.findAll()).hasSize(1);
    }


}
