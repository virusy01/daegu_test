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





	// 정산보고/비용현황
	List<Map<String, Object>> findCosts(@Param("p") Map<String, Object> params, @Param("u") User user);

	// 정산보고/고용현황
	List<Map<String, Object>> findEmployment(@Param("p") Map<String, Object> params, @Param("u") User user);

	// 정산보고/수입-유료급식
	List<Map<String, Object>> findIncomePaidMeals(@Param("p") Map<String, Object> params, @Param("u") User user);

	// 정산보고/수입-공공급식
	List<Map<String, Object>> findIncomePublicMeals(@Param("p") Map<String, Object> params, @Param("u") User user);

	// 정산보고/수입-지원금
	List<Map<String, Object>> findIncomeSupport(@Param("p") Map<String, Object> params, @Param("u") User user);

	// 월간실적/사회공헌활동
	List<Map<String, Object>> findDonation(@Param("p") Map<String, Object> params, @Param("u") User user);

	// 월간실적/월평균임금
	Map<String, Object> findSalary(@Param("p") Map<String, Object> params, @Param("u") User user);

	// 월간 운영실적
	List<Map<String, Object>> findMonthlyOperationStatus(@Param("p") Map<String, Object> params, @Param("u") User user);

	// 월간 요약
	List<Map<String, Object>> findMonthlySummary1(@Param("p") Map<String, Object> params, @Param("u") User user);
	List<Map<String, Object>> findMonthlySummary2(@Param("p") Map<String, Object> params, @Param("u") User user);
	List<Map<String, Object>> findMonthlySummary3(@Param("p") Map<String, Object> params, @Param("u") User user);
	List<Map<String, Object>> findMonthlySummary4(@Param("p") Map<String, Object> params, @Param("u") User user);

	// 연간실적/경제적가치(재무현황)
	List<Map<String, Object>> findFinance(@Param("p") Map<String, Object> params, @Param("u") User user);

	// 연간실적/사회혁신활동
	List<Map<String, Object>> findSocialBiz(@Param("p") Map<String, Object> params, @Param("u") User user);

	// 연간실적/사업역량개발노력
	List<Map<String, Object>> findBizStudy(@Param("p") Map<String, Object> params, @Param("u") User user);

	// 성과지표 조회
	List<Map<String, Object>> findKpi(@Param("p") Map<String, Object> params, @Param("u") User user);

	// 전체 센터 성과지표 조회, CORP_SEQ 로 서비스에서 피벗팅 함
	List<Map<String, Object>> findAllCorpKpiByYear(@Param("p") Map<String, Object> params, @Param("u") User user);
	List<Map<String, Object>> findAllCorpKpiByYear_corpInfo(@Param("p") Map<String, Object> params, @Param("u") User user);

}
	