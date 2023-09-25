package com.example.db.vo.user;

import com.example.db.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.elasticsearch.common.io.stream.Writeable;
import org.elasticsearch.search.SearchHits;

@Data
@AllArgsConstructor
public class UserVO {
    private Integer id;
    private String name;
    private Integer role;
    private String avatar;
    public UserVO(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.role = user.getRole();
        this.avatar = user.getAvatar();
    }
}
