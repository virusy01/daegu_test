package snp.app.hq;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import snp.infra.user.model.User;

import java.util.List;
import java.util.Map;

@Repository
public interface HqRepository
{
	// 사회적기업 기업 리스트
	List<Map<String, Object>> findSocialList();

	// 사회적기업 상세정보
	Map<String, Object> findSocialInfo(@Param("CORP_SEQ") int corpSeq, @Param("u") User user);
	int insertSocialInfo(@Param("p") Map<String, Object> params, @Param("u") User user);
	int updateSocialInfo(@Param("p") Map<String, Object> params, @Param("u") User user);
	int deleteSocialInfo(@Param("CORP_SEQ") int corpSeq, @Param("u") User user);

	// 협동조합 리스트
	List<Map<String, Object>> findCooperationList();

	// 협동조합 상세정보
	Map<String, Object> findCooperationInfo(@Param("CORP_SEQ") int corpSeq, @Param("u") User user);
	int insertCooperationInfo(@Param("p") Map<String, Object> params, @Param("u") User user);
	int updateCooperationInfo(@Param("p") Map<String, Object> params, @Param("u") User user);
	int deleteCooperationInfo(@Param("CORP_SEQ") int corpSeq, @Param("u") User user);

	// 마을기업 기업 리스트
	List<Map<String, Object>> findVillageList();

	// 마을기업 상세정보
	Map<String, Object> findVillageInfo(@Param("CORP_SEQ") int corpSeq, @Param("u") User user);
	int insertVillageInfo(@Param("p") Map<String, Object> params, @Param("u") User user);
	int updateVillageInfo(@Param("p") Map<String, Object> params, @Param("u") User user);
	int deleteVillageInfo(@Param("CORP_SEQ") int corpSeq, @Param("u") User user);

}
	