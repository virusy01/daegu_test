package snp.app.corp;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import snp.infra.user.model.User;

import java.util.List;
import java.util.Map;

@Repository
public interface CorpRepository
{
	// 센터 기본정보
	Map<String, Object> findCorpInfo(@Param("CORP_SEQ") int corpSeq, @Param("u") User user);
	int insertCorpInfo(@Param("p") Map<String, Object> params, @Param("u") User user);
	int updateCorpInfo(@Param("p") Map<String, Object> params, @Param("u") User user);
	int deleteCorpInfo(@Param("CORP_SEQ") int corpSeq, @Param("u") User user);

	// 센터 고용현황
	Map<String, Object> findCorpEmployee(@Param("CORP_SEQ") int corpSeq, @Param("u") User user);
	int insertCorpEmployee(@Param("p") Map<String, Object> params, @Param("u") User user);
	int updateCorpEmployee(@Param("p") Map<String, Object> params, @Param("u") User user);
	int deleteCorpEmployee(@Param("CORP_SEQ") int corpSeq, @Param("u") User user);

	// 월간실적/비용현황
	List<Map<String, Object>> findCosts(@Param("p") Map<String, Object> params, @Param("u") User user);
	int insertCosts(@Param("p") Map<String, Object> params, @Param("u") User user);
	int deleteCosts(@Param("p") Map<String, Object> params, @Param("u") User user);

	// 월간실적/고용현황
	List<Map<String, Object>> findEmployment(@Param("p") Map<String, Object> params, @Param("u") User user);
	int insertEmployment(@Param("p") Map<String, Object> params, @Param("u") User user);
	int deleteEmployment(@Param("p") Map<String, Object> params, @Param("u") User user);

	// 월간실적/수입-유료급식
	List<Map<String, Object>> findIncomePaidMeals(@Param("p") Map<String, Object> params, @Param("u") User user);
	int insertIncomePaidMeals(@Param("p") Map<String, Object> params, @Param("u") User user);
	int deleteIncomePaidMeals(@Param("p") Map<String, Object> params, @Param("u") User user);

	// 월간실적/수입-공공급식
	List<Map<String, Object>> findIncomePublicMeals(@Param("p") Map<String, Object> params, @Param("u") User user);
	int insertIncomePublicMeals(@Param("p") Map<String, Object> params, @Param("u") User user);
	int deleteIncomePublicMeals(@Param("p") Map<String, Object> params, @Param("u") User user);

	// 월간실적/수입-지원금
	List<Map<String, Object>> findIncomeSupport(@Param("p") Map<String, Object> params, @Param("u") User user);
	int insertIncomeSupport(@Param("p") Map<String, Object> params, @Param("u") User user);
	int deleteIncomeSupport(@Param("p") Map<String, Object> params, @Param("u") User user);

	// 월간실적/사회공헌활동
	List<Map<String, Object>> findDonation(@Param("p") Map<String, Object> params, @Param("u") User user);
	int insertDonation(@Param("p") Map<String, Object> params, @Param("u") User user);
	int deleteDonation(@Param("p") Map<String, Object> params, @Param("u") User user);

	// 월간실적/월평균임금
	Map<String, Object> findSalary(@Param("p") Map<String, Object> params, @Param("u") User user);
	int insertSalary(@Param("p") Map<String, Object> params, @Param("u") User user);
	int deleteSalary(@Param("p") Map<String, Object> params, @Param("u") User user);

	// 월간 운영실적
	List<Map<String, Object>> findMonthlyOperationStatus(@Param("p") Map<String, Object> params, @Param("u") User user);

	// 월간 요약
	List<Map<String, Object>> findMonthlySummary1(@Param("p") Map<String, Object> params, @Param("u") User user);
	List<Map<String, Object>> findMonthlySummary2(@Param("p") Map<String, Object> params, @Param("u") User user);
	List<Map<String, Object>> findMonthlySummary3(@Param("p") Map<String, Object> params, @Param("u") User user);
	List<Map<String, Object>> findMonthlySummary4(@Param("p") Map<String, Object> params, @Param("u") User user);

	// 연간실적/경제적가치(재무현황)
	List<Map<String, Object>> findFinance(@Param("p") Map<String, Object> params, @Param("u") User user);
	int insertFinance(@Param("p") Map<String, Object> params, @Param("u") User user);
	int deleteFinance(@Param("p") Map<String, Object> params, @Param("u") User user);

	// 연간실적/사회혁신활동
	List<Map<String, Object>> findSocialBiz(@Param("p") Map<String, Object> params, @Param("u") User user);
	int insertSocialBiz(@Param("p") Map<String, Object> params, @Param("u") User user);
	int deleteSocialBiz(@Param("p") Map<String, Object> params, @Param("u") User user);

	// 연간실적/사업역량개발노력
	List<Map<String, Object>> findBizStudy(@Param("p") Map<String, Object> params, @Param("u") User user);
	int insertBizStudy(@Param("p") Map<String, Object> params, @Param("u") User user);
	int deleteBizStudy(@Param("p") Map<String, Object> params, @Param("u") User user);

	// 성과지표 조회
	List<Map<String, Object>> findKpi(@Param("p") Map<String, Object> params, @Param("u") User user);

	Map<String, Object> findKpi_010101(@Param("p") Map<String, Object> params, @Param("u") User user);
	//Map<String, Object> findKpi_010102(@Param("p") Map<String, Object> params, @Param("u") User user);
	//Map<String, Object> findKpi_010201(@Param("p") Map<String, Object> params, @Param("u") User user);
	//Map<String, Object> findKpi_010301(@Param("p") Map<String, Object> params, @Param("u") User user);
	//Map<String, Object> findKpi_010401(@Param("p") Map<String, Object> params, @Param("u") User user);
	//Map<String, Object> findKpi_010402(@Param("p") Map<String, Object> params, @Param("u") User user);


	/* • 3년간 매출액 평균  */
	//Map<String, Object> findKpi_030101(@Param("p") Map<String, Object> params, @Param("u") User user);

	// 성과지표 조회 중 아이콘 클릭시 상세조회
    List<Map<String, Object>> findKpi_010101_compare(@Param("p") Map<String, Object> params, @Param("u") User user);
	/* • 3년간 매출액 평균 등급별 건수 */
	List<Map<String, Object>> findKpi_030101_compare(@Param("p") Map<String, Object> params, @Param("u") User user);

	// 매뉴얼 성과지표 조회(등록용 템플릿)
	List<Map<String, Object>> findManualKpi(@Param("p") Map<String, Object> params, @Param("u") User user);
	int insertManualKpi(@Param("p") Map<String, Object> params, @Param("u") User user);
	int deleteManualKpi(@Param("p") Map<String, Object> params, @Param("u") User user);

	Integer findCurrentYear();
	Integer findMaxYear();


	// 운영실적 추이(매출액, 인당생산성, 사업개발/자문활용 횟수, 사업개발/자문활용 금액, 교육/학습 횟수, 교육/학습 시간 - 6가지)
	List<Map<String, Object>> findOpSalesAmount(@Param("p") Map<String, Object> params, @Param("u") User user);

	// 운영실적 추이(매출액, 인당생산성, 사업개발/자문활용 횟수, 사업개발/자문활용 금액, 교육/학습 횟수, 교육/학습 시간 - 6가지)
	List<Map<String, Object>> findOpSalesPower(@Param("p") Map<String, Object> params, @Param("u") User user);

	// 운영실적 추이(매출액, 인당생산성, 사업개발/자문활용 횟수, 사업개발/자문활용 금액, 교육/학습 횟수, 교육/학습 시간 - 6가지)
	List<Map<String, Object>> findOpSocialBizCount(@Param("p") Map<String, Object> params, @Param("u") User user);

	// 운영실적 추이(매출액, 인당생산성, 사업개발/자문활용 횟수, 사업개발/자문활용 금액, 교육/학습 횟수, 교육/학습 시간 - 6가지)
	List<Map<String, Object>> findOpSocialBizExpense(@Param("p") Map<String, Object> params, @Param("u") User user);

	// 운영실적 추이(매출액, 인당생산성, 사업개발/자문활용 횟수, 사업개발/자문활용 금액, 교육/학습 횟수, 교육/학습 시간 - 6가지)
	List<Map<String, Object>> findOpBizStudyCount(@Param("p") Map<String, Object> params, @Param("u") User user);

	// 운영실적 추이(매출액, 인당생산성, 사업개발/자문활용 횟수, 사업개발/자문활용 금액, 교육/학습 횟수, 교육/학습 시간 - 6가지)
	List<Map<String, Object>> findOpBizStudyTime(@Param("p") Map<String, Object> params, @Param("u") User user);


	// pie chart demo1
	List<Map<String, Object>>chartDemo0(@Param("u") User user);
	List<Map<String, Object>>chartDemo1(@Param("u") User user);
	List<Map<String, Object>>chartDemo2(@Param("u") User user);
	List<Map<String, Object>>chartDemo3(@Param("u") User user);
}
	