package com.carret.market.acceptance;

import static com.carret.market.acceptance.ItemStep.로그인_후_상품_관심_주기;
import static com.carret.market.acceptance.ItemStep.로그인_후_상품등록;
import static com.carret.market.acceptance.MemberStep.회원_저장;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.carret.market.domain.item.Item;
import com.carret.market.domain.item.ItemRepository;
import com.carret.market.domain.like.LikesRepository;
import com.carret.market.infrastructure.file.S3UploadUtils;
import com.carret.market.infrastructure.file.UploadFile;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

@ExtendWith(MockitoExtension.class)
public class ItemAcceptantceTest extends AcceptanceTest {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private LikesRepository likesRepository;

    @MockBean
    private S3UploadUtils s3UploadUtils;

    @BeforeEach
    void init() {
        회원_저장("test@naver.com");
    }

    @DisplayName("상품을 등록한다.")
    @Test
    void addItem() throws IOException {
        when(s3UploadUtils.uploadFile(any())).thenReturn(new UploadFile("원본이름", "변경된 이름", "저장된 URL"));

        로그인_후_상품등록("test@naver.com", "컴퓨터");

        assertThat(itemRepository.findAll()).hasSize(1);
    }

    @DisplayName("관심목록에 추가한다.")
    @Test
    void subscriptTest() throws IOException {
        when(s3UploadUtils.uploadFile(any())).thenReturn(new UploadFile("원본이름", "변경된 이름", "저장된 URL"));

        로그인_후_상품등록("test@naver.com", "컴퓨터");

        Item item = itemRepository.findAll().get(0);
        로그인_후_상품_관심_주기("test@naver.com", item.getId());

        assertThat(likesRepository.findAll()).hasSize(1);
    }


}
