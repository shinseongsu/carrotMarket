package com.carret.market.acceptance.item;

import static com.carret.market.acceptance.item.ItemStep.로그인_후_상품등록;
import static com.carret.market.acceptance.member.MemberStep.회원_저장;
import static org.assertj.core.api.Assertions.assertThat;

import com.carret.market.acceptance.AcceptanceTest;
import com.carret.market.acceptance.member.MemberStep;
import com.carret.market.domain.item.ItemRepository;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("상품관련")
public class ItemAcceptantceTest extends AcceptanceTest {

    @Autowired
    private ItemRepository itemRepository;

    @BeforeEach
    void init() {
        회원_저장("test@naver.com");
    }

    @Test
    void addItem() throws IOException {
        로그인_후_상품등록("test@naver.com", "컴퓨터");

        assertThat(itemRepository.findAll()).hasSize(1);
    }
    
}
