package snp.infra.user;

import snp.infra.user.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <br/>개정이력<br/>
 */
@Mapper
public interface UserRepo {
    User find(@Param("userId") String userId);
    String password(@Param("userId") String userId);
}
