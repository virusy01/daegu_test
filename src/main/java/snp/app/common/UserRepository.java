package snp.app.common;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import snp.infra.user.model.User;

import java.util.List;
import java.util.Map;

@Repository
public interface UserRepository {
	int countUsers(String userNm);
	List<Map<String, Object>> findUsers(@Param("p") Map<String, Object> params, @Param("u") User user);
	Map<String, Object> findUser(@Param("p") Map<String, Object> params, @Param("u") User user);
	Map<String, Object> findUserByUsername(@Param("p") Map<String, Object> params, @Param("u") User user);
	int insertUser(@Param("p") Map<String, Object> params, @Param("u") User user);
	int updateUser(@Param("p") Map<String, Object> params, @Param("u") User user);
	int deleteUser(@Param("p") Map<String, Object> params, @Param("u") User user);
}
