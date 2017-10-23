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
	List<Map<String, Object>> findHistoryCorpList();

	Map<String, Object> findCorpInfo(@Param("p") Map<String, Object> params, @Param("u") User user);
	int insertCorpInfo(@Param("p") Map<String, Object> params, @Param("u") User user);
	int updateCorpInfo(@Param("p") Map<String, Object> params, @Param("u") User user);
	int deleteCorpInfo(@Param("p") Map<String, Object> params, @Param("u") User user);

	Map<String, Object> findOrgName(@Param("p") Map<String, Object> params, @Param("u") User user);


	// 성과지표 기업유형별 현황조회 - 키워드 검색
	List<Map<String, Object>>findCorpListKeyword(@Param("p") Map<String, Object> params,@Param("u") User user);

	// 사회적기업 이력데이터 기업리스트 - 키워드 검색
	List<Map<String, Object>>findHistoryCorpListKeyword(@Param("p") Map<String, Object> params,@Param("u") User user);

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

	// 사회적기업 이력데이터 항목추이 조회(자산)
	List<Map<String, Object>> findAsset(@Param("p") Map<String, Object> params, @Param("u") User user);

	// 사회적기업 이력데이터 항목추이 조회(자본)
	List<Map<String, Object>> findCapital(@Param("p") Map<String, Object> params, @Param("u") User user);

	// 사회적기업 이력데이터 항목추이 조회(부채)
	List<Map<String, Object>> findDebt(@Param("p") Map<String, Object> params, @Param("u") User user);

	// 사회적기업 이력데이터 항목추이 조회(매출액)
	List<Map<String, Object>> findSales(@Param("p") Map<String, Object> params, @Param("u") User user);

	// 사회적기업 이력데이터 항목추이 조회(매출총이익)
	List<Map<String, Object>> findGrossProfit(@Param("p") Map<String, Object> params, @Param("u") User user);

	// 사회적기업 이력데이터 항목추이 조회(노무비)
	List<Map<String, Object>> findLaborCosts(@Param("p") Map<String, Object> params, @Param("u") User user);

	// 사회적기업 이력데이터 항목추이 조회(영업이익)
	List<Map<String, Object>> findOpProfit(@Param("p") Map<String, Object> params, @Param("u") User user);

	// 사회적기업 이력데이터 항목추이 조회(당기순이익)
	List<Map<String, Object>> findNetIncome(@Param("p") Map<String, Object> params, @Param("u") User user);

	// 사회적기업 이력데이터 항목추이 조회(전체 종업원수)
	List<Map<String, Object>> findEmpCnt(@Param("p") Map<String, Object> params, @Param("u") User user);

	// 사회적기업 이력데이터 항목추이 조회(취약계층 종업원수)
	List<Map<String, Object>> findWeakEmpCnt(@Param("p") Map<String, Object> params, @Param("u") User user);



}
	