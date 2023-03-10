package snp.app.hq;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import snp.infra.user.model.User;

import java.util.List;
import java.util.Map;

@Repository
public interface HqRepository
{

	List<Map<String, Object>> findCorpList(String corpType);


	Map<String, Object> findCorpInfo(@Param("p") Map<String, Object> params, @Param("u") User user);
	int insertCorpInfo(@Param("p") Map<String, Object> params, @Param("u") User user);
	int updateCorpInfo(@Param("p") Map<String, Object> params, @Param("u") User user);
	int deleteCorpInfo(@Param("p") Map<String, Object> params, @Param("u") User user);




	// 성과지표 기업유형별 현황조회 - 키워드 검색
	List<Map<String, Object>>findCorpListKeyword(@Param("p") Map<String, Object> params,@Param("u") User user);


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


	// 센터 관리
	List<Map<String, Object>> findCenterMgm(@Param("u") User user);
	int insertCenterMgm(@Param("p") Map<String, Object> params, @Param("u") User user);
	int deleteCenterMgm(@Param("p") Map<String, Object> params, @Param("u") User user);


	// *************************************** 사회적기업 이력데이터 시작 *********************************************************************** //

	// 사회적기업 이력데이터 기업리스트 - 키워드 검색
	List<Map<String, Object>>findHistorySocialCorpListKeyword(@Param("p") Map<String, Object> params,@Param("u") User user);


	// 사회적기업 이력데이터 - 기업리스트
	List<Map<String, Object>> findHistorySocialCorpList();

	// 사회적기업 이력데이터 - 기업명 조회
	Map<String, Object> findSocialOrgName(@Param("p") Map<String, Object> params, @Param("u") User user);


	// 사회적기업 이력데이터 항목추이 조회(자산)
	List<Map<String, Object>> findSocialAsset(@Param("p") Map<String, Object> params, @Param("u") User user);

	// 사회적기업 이력데이터 항목추이 조회(자본)
	List<Map<String, Object>> findSocialCapital(@Param("p") Map<String, Object> params, @Param("u") User user);

	// 사회적기업 이력데이터 항목추이 조회(부채)
	List<Map<String, Object>> findSocialDebt(@Param("p") Map<String, Object> params, @Param("u") User user);

	// 사회적기업 이력데이터 항목추이 조회(매출액)
	List<Map<String, Object>> findSocialSales(@Param("p") Map<String, Object> params, @Param("u") User user);

	// 사회적기업 이력데이터 항목추이 조회(매출총이익)
	List<Map<String, Object>> findSocialGrossProfit(@Param("p") Map<String, Object> params, @Param("u") User user);

	// 사회적기업 이력데이터 항목추이 조회(노무비)
	List<Map<String, Object>> findSocialLaborCosts(@Param("p") Map<String, Object> params, @Param("u") User user);

	// 사회적기업 이력데이터 항목추이 조회(영업이익)
	List<Map<String, Object>> findSocialOpProfit(@Param("p") Map<String, Object> params, @Param("u") User user);

	// 사회적기업 이력데이터 항목추이 조회(당기순이익)
	List<Map<String, Object>> findSocialNetIncome(@Param("p") Map<String, Object> params, @Param("u") User user);

	// 사회적기업 이력데이터 항목추이 조회(전체 종업원수)
	List<Map<String, Object>> findSocialEmpCnt(@Param("p") Map<String, Object> params, @Param("u") User user);

	// 사회적기업 이력데이터 항목추이 조회(취약계층 종업원수)
	List<Map<String, Object>> findSocialWeakEmpCnt(@Param("p") Map<String, Object> params, @Param("u") User user);

	// *************************************** 사회적기업 이력데이터 끝 *********************************************************************** //


	// *************************************** 마을기업 이력데이터 시작 *********************************************************************** //

	// 마을기업 이력데이터 기업리스트 - 키워드 검색
	List<Map<String, Object>>findHistoryVillageCorpListKeyword(@Param("p") Map<String, Object> params,@Param("u") User user);


	// 마을기업 이력데이터 - 기업리스트
	List<Map<String, Object>> findHistoryVillageCorpList();

	// 마을기업 이력데이터 - 기업명 조회
	Map<String, Object> findVillageOrgName(@Param("p") Map<String, Object> params, @Param("u") User user);

	// 마을기업 이력데이터 항목추이 조회(자산)
	List<Map<String, Object>> findVillageAsset(@Param("p") Map<String, Object> params, @Param("u") User user);

	// 마을기업 이력데이터 항목추이 조회(자본)
	List<Map<String, Object>> findVillageCapital(@Param("p") Map<String, Object> params, @Param("u") User user);

	// 마을기업 이력데이터 항목추이 조회(부채)
	List<Map<String, Object>> findVillageDebt(@Param("p") Map<String, Object> params, @Param("u") User user);

	// 마을기업 이력데이터 항목추이 조회(매출액)
	List<Map<String, Object>> findVillageSales(@Param("p") Map<String, Object> params, @Param("u") User user);

	// 마을기업 이력데이터 항목추이 조회(매출총이익)
	List<Map<String, Object>> findVillageGrossProfit(@Param("p") Map<String, Object> params, @Param("u") User user);

	// 마을기업 이력데이터 항목추이 조회(노무비)
	List<Map<String, Object>> findVillageLaborCosts(@Param("p") Map<String, Object> params, @Param("u") User user);

	// 마을기업 이력데이터 항목추이 조회(영업이익)
	List<Map<String, Object>> findVillageOpProfit(@Param("p") Map<String, Object> params, @Param("u") User user);

	// 마을기업 이력데이터 항목추이 조회(당기순이익)
	List<Map<String, Object>> findVillageNetIncome(@Param("p") Map<String, Object> params, @Param("u") User user);


	// ***************************************   마을기업 이력데이터  끝 *********************************************************************** //

}
	