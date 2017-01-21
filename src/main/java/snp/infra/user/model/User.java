package snp.infra.user.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import snp.util.V;

/**
 * <br/>개정이력<br/>
 */
public class User {
    /**
     * 기본 언어 코드.
     */

    public User(String userId,
                String userName,
                Integer corpSeq,
                String email) {
        super();

        V.noEmpty(userId, "userId");
        V.noEmpty(userName, "userName");
        V.noEmpty(corpSeq, "corpSeq");

        this.userId = userId;
        this.userName = userName;
        this.corpSeq = corpSeq;
        this.email = email;

    }

    final private String userId;
    final private String userName;
    final private Integer corpSeq;
    final private String email;

    @JsonGetter
    public String userId() {
        return userId;
    }

    @JsonGetter
    public String userName() {
        return userName;
    }

    @JsonGetter
    public Integer corpSeq() {
        return corpSeq;
    }

    @JsonGetter
    public String email() {
        return email;
    }
}
