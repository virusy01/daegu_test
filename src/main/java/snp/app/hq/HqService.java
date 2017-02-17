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






















	// 정산보고/비용현황
	public List<Map<String, Object>> findCosts(Map<String, Object> params){
		return repository.findCosts(params, Security.user());
	}


	// 정산보고/고용현황
	public List<Map<String, Object>> findEmployment(Map<String, Object> params){
		return repository.findEmployment(params, Security.user());
	}

	// 정산보고/ 수입 전체
	public Map<String, Object> findIncome(Map<String, Object> params){
		Map<String, Object> nestedData = new HashMap<>();

		nestedData.put("paidMeals", findIncomePaidMeals(params));
		nestedData.put("publicMeals", findIncomePublicMeals(params));
		nestedData.put("support", findIncomeSupport(params));

		return nestedData;
	}

	// 정산보고/수입-유료급식
	public List<Map<String, Object>> findIncomePaidMeals(Map<String, Object> params){
		return repository.findIncomePaidMeals(params, Security.user());
	}

	// 정산보고/수입-공공급식
	public List<Map<String, Object>> findIncomePublicMeals(Map<String, Object> params){
		return repository.findIncomePublicMeals(params, Security.user());
	}

	// 정산보고/수입-지원금
	public List<Map<String, Object>> findIncomeSupport(Map<String, Object> params){
		return repository.findIncomeSupport(params, Security.user());
	}

	// 월간실적/사회공헌활동
	public List<Map<String, Object>> findDonation(Map<String, Object> params) {
		return repository.findDonation(params, Security.user());
	}


	// 월간실적/월평균임금
	public Map<String, Object> findSalary(Map<String, Object> params) {
		return repository.findSalary(params, Security.user());
	}

	// 월간운영현황
	public List<Map<String, Object>> findMonthlyOperationStatus(Map<String, Object> params) {
		return repository.findMonthlyOperationStatus(params, Security.user());
	}

	// 월간요약
	public Map<String, Object> findMonthlySummary(Map<String, Object> params) {
		List<Map<String, Object>> summary1 = repository.findMonthlySummary1(params, Security.user());
		List<Map<String, Object>> summary2 = repository.findMonthlySummary2(params, Security.user());
		List<Map<String, Object>> summary3 = repository.findMonthlySummary3(params, Security.user());
		List<Map<String, Object>> summary4 = repository.findMonthlySummary4(params, Security.user());

		Map<String, Object> nested = new HashMap<>();
		nested.put("summary1", summary1);
		nested.put("summary2", summary2);
		nested.put("summary3", summary3);
		nested.put("summary4", summary4);

		return nested;
	}


	// 연간실적/경제적가치(재무현황)
	public List<Map<String, Object>> findFinance(Map<String, Object> params) {
		return repository.findFinance(params, Security.user());
	}

	// 연간실적/사회적공헌활동
	public List<Map<String, Object>> findSocialBiz(Map<String, Object> params) {
		return repository.findSocialBiz(params, Security.user());
	}

	// 연간실적/사업역량개발노력
	public List<Map<String, Object>> findBizStudy(Map<String, Object> params) {
		return repository.findBizStudy(params, Security.user());
	}

	// 성과지표조회
	public List<Map<String, Object>> findKpi(Map<String, Object> params) {
		return repository.findKpi(params, Security.user());
	}

	// 전체 센터 성과지표 조회, CORP_SEQ 로 서비스에서 피벗팅 함
	public Map<String, List<Map<String, Object>>> findAllCorpKpiByYear(Map<String, Object> params) {
		Map<String, List<Map<String, Object>>> result = new HashMap<>();
		List<Map<String, Object>> kpiAll = repository.findAllCorpKpiByYear(params, Security.user());

		// status(양호, 주의 , 심각) 를 가져옴
		kpiAll.forEach(kpi ->{
			String level3Code = (String)kpi.get("L3_KPI_CD");
			Map<String, Object> detailKpi = corpService.getKpiDetail(level3Code, kpi);
			if (detailKpi !=null) {
				kpi.put("STATUS", detailKpi.get("STATUS"));
			}
		});

		PivotTable pt = new PivotTable();
		pt.setKeyColumns(new String[]{"YEAR", "L1_KPI_NM", "L2_KPI_NM", "L3_KPI_NM", "L3_KPI_CD"});
		pt.setPivotColumn("CORP_SEQ");
		pt.setValueColumn("STATUS");
		//pt.setNullReplace("NA");
		pt.setSourceList(kpiAll);

		List<Map<String, Object>> pivoted = pt.composePivot();

		List<Map<String, Object>> corpInfos = repository.findAllCorpKpiByYear_corpInfo(params, Security.user());

		result.put("kpiAll", pivoted);
		result.put("corps", corpInfos);
		return result;
	}
}
