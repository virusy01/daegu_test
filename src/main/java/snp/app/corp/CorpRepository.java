package snp.app.corp;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import snp.infra.user.model.User;

import java.util.List;
import java.util.Map;

@Repository
public interface CorpRepository
{

	// 사회적기업 성과지표 조회
	List<Map<String, Object>> findSocialKpi(@Param("p") Map<String, Object> params, @Param("u") User user);

	// 협동조합 성과지표 조회
	List<Map<String, Object>> findCooperationKpi(@Param("p") Map<String, Object> params, @Param("u") User user);

	// 협동조합 성과지표 조회
	List<Map<String, Object>> findVillageKpi(@Param("p") Map<String, Object> params, @Param("u") User user);

	//공통
	Map<String, Object> findKpi_detail( @Param("LEVEL3CODE") String level3Code, @Param("p") Map<String, Object> params, @Param("u") User user);


	int deleteKpi(@Param("p") Map<String, Object> params, @Param("u") User user);
	int insertKpi(@Param("p") Map<String, Object> params, @Param("u") User user);



	// 성과지표 등급별 분석 데모용
	List<Map<String, Object>>chartDemo10(@Param("u") User user);
	List<Map<String, Object>>chartDemo11(@Param("u") User user);
	List<Map<String, Object>>chartDemo12(@Param("u") User user);
	List<Map<String, Object>>chartDemo13(@Param("u") User user);

	// 성과지표 등급별 분석 데모용
	List<Map<String, Object>>chartDemo2(@Param("p") Map<String, Object> params,@Param("u") User user);

	// 성과지표 등급별 분석 데모용
	List<Map<String, Object>>chartDemo3(@Param("p") Map<String, Object> params,@Param("u") User user);
}
	