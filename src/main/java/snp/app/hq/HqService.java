package snp.app.hq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import snp.app.corp.CorpRepository;
import snp.app.corp.CorpService;
import snp.infra.security.Security;
import snp.util.PivotTable;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class HqService
{
	@Autowired
	private HqRepository repository;

	@Autowired
	private CorpService corpService;

	public List<Map<String, Object>> findCorpList(String corpType)
	{
		return repository.findCorpList(corpType);
	}

	public Map<String, Object> findCorpInfo(Map<String, Object> params)
	{
		return repository.findCorpInfo(params, Security.user());
	}



	public int saveCorpInfo(Map<String, Object> params) {
		int r;
		Integer corpSeq = (Integer) params.get("CORP_SEQ");
		if (corpSeq != null) {
			//repository.deleteCorpInfo(corpSeq, Security.user());
			r = repository.updateCorpInfo(params, Security.user());
		} else {
			r = repository.insertCorpInfo(params, Security.user());
		}
		return r;
	}

	// // 성과지표 기업유형별 현황조회 - 키워드 검색
	public List<Map<String, Object>> findCorpListKeyword(Map<String, Object> params) {
		return repository.findCorpListKeyword(params,Security.user());
	}



	// 사회적기업 리스트
	public List<Map<String, Object>> findSocialList()
	{
		return repository.findSocialList();
	}

	// 사회적기업 상세 정보
	public Map<String, Object> findSocialInfo(int corpSeq) {
		return repository.findSocialInfo(corpSeq, Security.user());
	}

	public int saveSocialInfo(Map<String, Object> params) {
		int r;
		Integer corpSeq = (Integer) params.get("CORP_SEQ");
		if (corpSeq != null) {
			//repository.deleteCorpInfo(corpSeq, Security.user());
			r = repository.updateSocialInfo(params, Security.user());
		} else {
			r = repository.insertSocialInfo(params, Security.user());
		}
		return r;
	}

	public int deleteSocialInfo(int corpSeq) {
		return repository.deleteSocialInfo(corpSeq, Security.user());
	}


	// 협동조합 리스트
	public List<Map<String, Object>> findCooperationList()
	{
		return repository.findCooperationList();
	}

	// 협동조합 상세 정보
	public Map<String, Object> findCooperationInfo(int corpSeq) {
		return repository.findCooperationInfo(corpSeq, Security.user());
	}

	public int saveCooperationInfo(Map<String, Object> params) {
		int r;
		Integer corpSeq = (Integer) params.get("CORP_SEQ");
		if (corpSeq != null) {
			//repository.deleteCorpInfo(corpSeq, Security.user());
			r = repository.updateCooperationInfo(params, Security.user());
		} else {
			r = repository.insertCooperationInfo(params, Security.user());
		}
		return r;
	}

	public int deleteCooperationInfo(int corpSeq) {
		return repository.deleteVillageInfo(corpSeq, Security.user());
	}


	// 마을기업 리스트
	public List<Map<String, Object>> findVillageList()
	{
		return repository.findVillageList();
	}

	// 마을기업 상세 정보
	public Map<String, Object> findVillageInfo(int corpSeq) {
		return repository.findVillageInfo(corpSeq, Security.user());
	}

	public int saveVillageInfo(Map<String, Object> params) {
		int r;
		Integer corpSeq = (Integer) params.get("CORP_SEQ");
		if (corpSeq != null) {
			//repository.deleteCorpInfo(corpSeq, Security.user());
			r = repository.updateVillageInfo(params, Security.user());
		} else {
			r = repository.insertVillageInfo(params, Security.user());
		}
		return r;
	}

	public int deleteVillageInfo(int corpSeq) {
		return repository.deleteVillageInfo(corpSeq, Security.user());
	}


	// 센터 관리
	public List<Map<String, Object>> findCenterMgm() {
		return repository.findCenterMgm(Security.user());
	}

	public int saveCenterMgm(List<Map<String, Object>> centerMgm) {
		centerMgm.forEach(param -> {
			repository.deleteCenterMgm(param, Security.user());
		});

		centerMgm.forEach(param -> {
			repository.insertCenterMgm(param, Security.user());
		});
		return 1;
	}



	// *************************************** 사회적기업 이력데이터 시작 *********************************************************************** //

	// 사회적기업 이력데이터 기업리스트
	public List<Map<String, Object>> findHistorySocialCorpList()
	{
		return repository.findHistorySocialCorpList();
	}

	// 사회적기업 이력데이터 기업리스트 - 키워드 검색
	public List<Map<String, Object>> findHistorySocialCorpListKeyword(Map<String, Object> params) {
		return repository.findHistorySocialCorpListKeyword(params,Security.user());
	}

	// 사회적기업 이력데이터 기업리스트 - 키워드 검색
	public Map<String, Object> findSocialOrgName(Map<String, Object> params)
	{
		return repository.findSocialOrgName(params, Security.user());
	}

	// 사회적기업 이력데이터 항목추이 조회
	public List<Map<String, Object>> findSocialTrend(Map<String, Object> params, String kpiKind) {
		List<Map<String, Object>> result;
		switch (kpiKind) {
			case "0":
				result = repository.findSocialAsset(params, Security.user());
				break;
			case "1":
				result = repository.findSocialCapital(params, Security.user());
				break;
			case "2":
				result = repository.findSocialDebt(params, Security.user());
				break;
			case "3":
				result = repository.findSocialSales(params, Security.user());
				break;
			case "4":
				result = repository.findSocialGrossProfit(params, Security.user());
				break;
			case "5":
				result = repository.findSocialLaborCosts(params, Security.user());
				break;
			case "6":
				result = repository.findSocialOpProfit(params, Security.user());
				break;
			case "7":
				result = repository.findSocialNetIncome(params, Security.user());
				break;
			case "8":
				result = repository.findSocialEmpCnt(params, Security.user());
				break;
			default: // 9
				result = repository.findSocialWeakEmpCnt(params, Security.user());
				break;
		}

		return result;
	}

	// ***************************************   사회적기업 이력데이터  끝 *********************************************************************** //


	// *************************************** 마을기업 이력데이터 시작 *********************************************************************** //

	// 마을기업 이력데이터 기업리스트
	public List<Map<String, Object>> findHistoryVillageCorpList()
	{
		return repository.findHistoryVillageCorpList();
	}

	// 마을기업 이력데이터 기업리스트 - 키워드 검색
	public List<Map<String, Object>> findHistoryVillageCorpListKeyword(Map<String, Object> params) {
		return repository.findHistoryVillageCorpListKeyword(params,Security.user());
	}

	// 마을기업 이력데이터 기업리스트 - 키워드 검색
	public Map<String, Object> findVillageOrgName(Map<String, Object> params)
	{
		return repository.findVillageOrgName(params, Security.user());
	}

	// 마을기업 이력데이터 항목추이 조회
	public List<Map<String, Object>> findVillageTrend(Map<String, Object> params, String kpiKind) {
		List<Map<String, Object>> result;
		switch (kpiKind) {
			case "0":
				result = repository.findVillageAsset(params, Security.user());
				break;
			case "1":
				result = repository.findVillageCapital(params, Security.user());
				break;
			case "2":
				result = repository.findVillageDebt(params, Security.user());
				break;
			case "3":
				result = repository.findVillageSales(params, Security.user());
				break;
			case "4":
				result = repository.findVillageGrossProfit(params, Security.user());
				break;
			case "5":
				result = repository.findVillageLaborCosts(params, Security.user());
				break;
			case "6":
				result = repository.findVillageOpProfit(params, Security.user());
				break;
			default: //9
				result = repository.findVillageNetIncome(params, Security.user());
				break;
		}

		return result;
	}


	// ***************************************   마을기업 이력데이터  끝 *********************************************************************** //

}
