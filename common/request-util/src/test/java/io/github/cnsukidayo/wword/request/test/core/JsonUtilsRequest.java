package io.github.cnsukidayo.wword.request.test.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.github.cnsukidayo.wword.global.utils.JsonUtils;
import io.github.cnsukidayo.wword.model.entity.User;
import io.github.cnsukidayo.wword.model.enums.SexType;
import org.junit.Test;

import java.io.IOException;

/**
 * @author sukidayo
 * @date 2023/9/8 9:31
 */
public class JsonUtilsRequest {

    @Test
    public void signalJson() {
        User user = new User();
        user.setNick("cnsukidayo");
        user.setSex(SexType.MALE);
        try {
            String json = JsonUtils.objectToJson((Object) user);
            User result = JsonUtils.jsonToObject(json, User.class);
            System.out.println(result.getNick());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
