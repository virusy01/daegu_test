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


	// 성과지표 사회적경제조직 유형별 분석 - 등급항목 통계
	List<Map<String, Object>> chartAllGradeSocial(@Param("u") User user);
	List<Map<String, Object>> chartAllGradeVillage(@Param("u") User user);
	List<Map<String, Object>> chartAllGradeCooperation(@Param("u") User user);
	List<Map<String, Object>> chartAllGradeTotal(@Param("u") User user);

	// 성과지표 사회적경제조직 유형별 분석 - 등급항목 통계
	List<Map<String, Object>> chartAllRateSocial(@Param("u") User user);
	List<Map<String, Object>> chartAllRateVillage(@Param("u") User user);
	List<Map<String, Object>> chartAllRateCooperation(@Param("u") User user);
	List<Map<String, Object>> chartAllRateTotal(@Param("u") User user);




	// 성과지표 기업별 분석
	List<Map<String, Object>>gridCorpAll(@Param("p") Map<String, Object> params,@Param("u") User user);
	List<Map<String, Object>>gridCorpEach(@Param("p") Map<String, Object> params,@Param("u") User user);

	// 성과지표 유형별 분석 - 노동통합형, 문제해결형, 지역사회혁신형, 고용창출형
	List<Map<String, Object>>chartType(@Param("u") User user);

}
	