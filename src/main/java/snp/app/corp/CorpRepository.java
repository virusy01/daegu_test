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



	// 성과지표 등급별 분석
	List<Map<String, Object>>chartGrade1(@Param("u") User user);
	List<Map<String, Object>>chartGrade2(@Param("u") User user);
	List<Map<String, Object>>chartGrade3(@Param("u") User user);
	List<Map<String, Object>>chartGrade4(@Param("u") User user);

	// 성과지표 항목별 분석
	List<Map<String, Object>>chartKpiAll(@Param("p") Map<String, Object> params,@Param("u") User user);
	List<Map<String, Object>>chartKpiEach(@Param("p") Map<String, Object> params,@Param("u") User user);

	// 성과지표 지역별 분석
	List<Map<String, Object>>chartRegionAll(@Param("p") Map<String, Object> params,@Param("u") User user);
	List<Map<String, Object>>chartRegionEach(@Param("p") Map<String, Object> params,@Param("u") User user);


	// 성과지표 업종별 분석
	List<Map<String, Object>>chartSectorAll(@Param("p") Map<String, Object> params,@Param("u") User user);
	List<Map<String, Object>>chartSectorEach(@Param("p") Map<String, Object> params,@Param("u") User user);


	// 성과지표 유형별 분석
	List<Map<String, Object>> chartAllSocial(@Param("u") User user);
	List<Map<String, Object>> chartAllVillage(@Param("u") User user);
	List<Map<String, Object>> chartAllCooperation(@Param("u") User user);
	List<Map<String, Object>> chartAllTotal(@Param("u") User user);




}
	