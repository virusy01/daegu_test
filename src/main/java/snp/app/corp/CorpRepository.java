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



	// 성과지표 등급별 분석 - 등급항목별
	List<Map<String, Object>>chartGrade1Grade(@Param("u") User user);
	List<Map<String, Object>>chartGrade2Grade(@Param("u") User user);
	List<Map<String, Object>>chartGrade3Grade(@Param("u") User user);
	List<Map<String, Object>>chartGrade4Grade(@Param("u") User user);

	// 성과지표 등급별 분석 - 비율항목별
	List<Map<String, Object>>chartGrade1Rate(@Param("u") User user);
	List<Map<String, Object>>chartGrade2Rate(@Param("u") User user);
	List<Map<String, Object>>chartGrade3Rate(@Param("u") User user);
	List<Map<String, Object>>chartGrade4Rate(@Param("u") User user);

	// 성과지표 1단계 항목별 분석 - 등급항목별
	List<Map<String, Object>>chartKpiAllGrade(@Param("p") Map<String, Object> params,@Param("u") User user);
	List<Map<String, Object>>chartKpiEachGrade(@Param("p") Map<String, Object> params,@Param("u") User user);

	// 성과지표 1단계 항목별 분석 - 비율항목별
	List<Map<String, Object>>chartKpiAllRate(@Param("p") Map<String, Object> params,@Param("u") User user);
	List<Map<String, Object>>chartKpiEachRate(@Param("p") Map<String, Object> params,@Param("u") User user);

	// 성과지표 2단계 항목별 분석 - 등급항목별
	List<Map<String, Object>>chartKpiDetailAllGrade(@Param("p") Map<String, Object> params,@Param("u") User user);
	List<Map<String, Object>>chartKpiDetailEachGrade(@Param("p") Map<String, Object> params,@Param("u") User user);

	// 성과지표 2단계 항목별 분석 - 비율항목별
	List<Map<String, Object>>chartKpiDetailAllRate(@Param("p") Map<String, Object> params,@Param("u") User user);
	List<Map<String, Object>>chartKpiDetailEachRate(@Param("p") Map<String, Object> params,@Param("u") User user);


	// 성과지표 지역별 분석 - 등급항목별
	List<Map<String, Object>>chartRegionAllGrade(@Param("p") Map<String, Object> params,@Param("u") User user);
	List<Map<String, Object>>chartRegionEachGrade(@Param("p") Map<String, Object> params,@Param("u") User user);

	// 성과지표 지역별 분석 - 비율항목별
	List<Map<String, Object>>chartRegionAllRate(@Param("p") Map<String, Object> params,@Param("u") User user);
	List<Map<String, Object>>chartRegionEachRate(@Param("p") Map<String, Object> params,@Param("u") User user);


	// 성과지표 업종별 분석 - 등급항목별
	List<Map<String, Object>>chartSectorAllGrade(@Param("p") Map<String, Object> params,@Param("u") User user);
	List<Map<String, Object>>chartSectorEachGrade(@Param("p") Map<String, Object> params,@Param("u") User user);

	// 성과지표 업종별 분석 비율항목별
	List<Map<String, Object>>chartSectorAllRate(@Param("p") Map<String, Object> params,@Param("u") User user);
	List<Map<String, Object>>chartSectorEachRate(@Param("p") Map<String, Object> params,@Param("u") User user);

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

	// 성과지표 사회적경제조직 유형별 분석 - 등급항목 통계 - 노동통합형, 문제해결형, 지역사회혁신형, 고용창출형
	List<Map<String, Object>>chartTypeGrade(@Param("u") User user);

	// 성과지표 사회적경제조직 유형별 분석 - 비율항목 통계 - 노동통합형, 문제해결형, 지역사회혁신형, 고용창출형
	List<Map<String, Object>>chartTypeRate(@Param("u") User user);




}
	