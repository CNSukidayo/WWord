package io.github.cnsukidayo.wword.pojo;

import com.baomidou.mybatisplus.annotation.*;
import io.github.cnsukidayo.wword.pojo.base.BaseEntity;

import java.io.Serial;

/**
 * @author sukidayo
 * @date 2023/5/17 19:30
 */
@TableName(value = "user", autoResultMap = true)
public class User extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "uuid", type = IdType.AUTO)
    private Long UUID;

    @TableField(value = "nick",select = true,fill = FieldFill.INSERT_UPDATE)
    private String nick;

    @TableField("password")
    private String password;

    public User() {
    }

    public Long getUUID() {
        return UUID;
    }

    public void setUUID(Long UUID) {
        this.UUID = UUID;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
