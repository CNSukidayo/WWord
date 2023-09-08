package io.github.cnsukidayo.wword.request.test.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.github.cnsukidayo.wword.global.utils.JsonUtils;
import io.github.cnsukidayo.wword.model.entity.User;
import org.junit.Test;

/**
 * @author sukidayo
 * @date 2023/9/8 9:31
 */
public class JsonUtilsRequest {

    @Test
    public void signalJson() {
        User user = new User();
        user.setNick("cnsukidayo");
        try {
            String json = JsonUtils.objectToJson(user);
            System.out.println(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
