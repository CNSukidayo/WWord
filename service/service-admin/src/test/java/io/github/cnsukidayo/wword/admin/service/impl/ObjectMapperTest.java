package io.github.cnsukidayo.wword.admin.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.cnsukidayo.wword.global.utils.JsonUtils;
import io.github.cnsukidayo.wword.model.bo.JsonWordBO;
import org.junit.Test;

/**
 * @author sukidayo
 * @date 2023/8/1 19:10
 */
public class ObjectMapperTest {

    @Test
    public void test() throws JsonProcessingException {
        ObjectMapper objectMapper = JsonUtils.DEFAULT_JSON_MAPPER;
        String json = "{\"wordRank\":1,\"headWord\":\"flutter\",\"content\":{\"word\":{\"wordHead\":\"flutter\",\"wordId\":\"Level4luan_2_1\",\"content\":{\"sentence\":{\"sentences\":[{\"sContent\":\"Dead leaves fluttered slowly to the ground.\",\"sCn\":\"枯叶缓缓飘落到地上。\"},{\"sContent\":\"The flag fluttered in the light breeze.\",\"sCn\":\"旗帜在微风中飘动。\"}],\"desc\":\"例句\"},\"usphone\":\"'flʌtɚ\",\"syno\":{\"synos\":[{\"pos\":\"vi\",\"tran\":\"飘动；鼓翼；烦扰\",\"hwds\":[{\"w\":\"fan\"},{\"w\":\"float\"}]},{\"pos\":\"vt\",\"tran\":\"拍；使焦急；使飘动\",\"hwds\":[{\"w\":\"stream\"}]},{\"pos\":\"n\",\"tran\":\"摆动；鼓翼；烦扰\",\"hwds\":[{\"w\":\"swing\"},{\"w\":\"bob\"}]}],\"desc\":\"同近\"},\"ukphone\":\"'flʌtə\",\"ukspeech\":\"flutter&type=1\",\"star\":0,\"phrase\":{\"phrases\":[{\"pContent\":\"atrial flutter\",\"pCn\":\"心房扑动\"},{\"pContent\":\"flutter kick\",\"pCn\":\"浅打水\"},{\"pContent\":\"have a flutter\",\"pCn\":\"[口语](在证券交易市场或赛马场)参加小赌\"}],\"desc\":\"短语\"},\"phone\":\"'flʌtə\",\"speech\":\"flutter\",\"relWord\":{\"rels\":[{\"pos\":\"adj\",\"words\":[{\"hwd\":\"fluttering\",\"tran\":\" 颤动的；翅膀拍打的\"}]},{\"pos\":\"n\",\"words\":[{\"hwd\":\"fluttering\",\"tran\":\" 振抖；测谎\"}]},{\"pos\":\"v\",\"words\":[{\"hwd\":\"fluttering\",\"tran\":\" 摆动；鼓翼；忙乱（flutter的ing形式）\"}]}],\"desc\":\"同根\"},\"usspeech\":\"flutter&type=2\",\"trans\":[{\"tranCn\":\"飘动；鼓翼；烦扰\",\"descOther\":\"英释\",\"descCn\":\"中释\",\"pos\":\"v\",\"tranOther\":\"to make small gentle movements in the air\"},{\"tranCn\":\"摆动；鼓翼；烦扰\",\"descCn\":\"中释\",\"pos\":\"n\"}]}}},\"bookId\":\"Level4luan_2\"}\n";
        JsonWordBO jsonWordBO = objectMapper.readValue(json, JsonWordBO.class);
    }

}
