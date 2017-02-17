package snp.app.hq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import snp.app.corp.CorpService;
import snp.infra.helper.RequestParams;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/hq")
public class HqController
{
	@Autowired
	private HqService service;

	
	/**
	 * 사회적기업정보 기본정보 목록
	 * @return
	 */
	@RequestMapping(value="/socialList")
	public List<Map<String, Object>> findSocialList(){
		return service.findSocialList();
	}

	/**
	 * 사회적기업정보 상세정보
	 * @return
	 */
	@RequestMapping(value="/socialInfo/{CORP_SEQ}")
	public Map<String, Object> findSocialInfo(@PathVariable("CORP_SEQ") Integer corpSeq){
		return service.findSocialInfo(corpSeq);
	}

	/**
	 * 사회적기업 정보 저장
	 * corp_info_save.php
	 * @param params
	 * @return
	 */
	@RequestMapping(value="/socialInfo", method= RequestMethod.POST)
	public int saveSocialInfo(@RequestBody Map<String, Object> params)
	{
		return service.saveSocialInfo(params);
	}

	/**
	 * 사회적기업 정보 삭제
	 * corp_info_del.php
	 * @param corpSeq
	 * @return
	 */
	@RequestMapping(value="/info/{CORP_SEQ}", method= RequestMethod.DELETE)
	public int deleteSocialInfo(@PathVariable("CORP_SEQ") Integer corpSeq)
	{
		return service.deleteSocialInfo(corpSeq);
	}



	
	// 정산보고/비용현황
	@RequestMapping(value="/monthly-report/costs", method= RequestMethod.GET)
	public List<Map<String, Object>> findCosts(@RequestParam(name = "YEAR") int year){
		return service.findCosts(RequestParams.map());
	}

	// 정산보고/고용현황
	@RequestMapping(value="/monthly-report/employment", method= RequestMethod.GET)
	public List<Map<String, Object>> findEmployment(@RequestParam(name = "YEAR") int year){
		return service.findEmployment(RequestParams.map());
	}

	// 정산보고/ 수입 전체 - 수입현황 입력, 수정을 각각 세개의 서비스로 되 있던걸 각각 하나로 통합
	@RequestMapping(value="/monthly-report/income", method= RequestMethod.GET)
	public Map<String, Object> findIncome(@RequestParam(name = "YEAR") int year){
		return service.findIncome(RequestParams.map());
	}

	// 정산보고/수입-유료급식
	@RequestMapping(value="/monthly-report/income/paidMeals", method= RequestMethod.GET)
	public List<Map<String, Object>> findIncomePaidMeals(@RequestParam(name = "YEAR") int year){
		return service.findIncomePaidMeals(RequestParams.map());
	}

	// 정산보고/수입-공공급식
	@RequestMapping(value="/monthly-report/income/publicMeals", method= RequestMethod.GET)
	public List<Map<String, Object>> findIncomePublicMeals(@RequestParam(name = "YEAR") int year){
		return service.findIncomePublicMeals(RequestParams.map());
	}

	// 정산보고/수입-지원금
	@RequestMapping(value="/monthly-report/income/support", method= RequestMethod.GET)
	public List<Map<String, Object>> findIncomeSupport(@RequestParam(name = "YEAR") int year){
		return service.findIncomeSupport(RequestParams.map());
	}

	// 월간실적/사회공헌활동
	@RequestMapping(value="/monthly-report/donation", method= RequestMethod.GET)
	public List<Map<String, Object>> findDonation(@RequestParam(name = "YEAR") int year){
		return service.findDonation(RequestParams.map());
	}
	// 월간실적/월평균임금
	@RequestMapping(value="/monthly-report/salary", method= RequestMethod.GET)
	public Map<String, Object> findSalary(@RequestParam(name = "CORP_SEQ") int corpSeq,
										  @RequestParam(name = "YEAR") int year){
		return service.findSalary(RequestParams.map());
	}

	// 월간운영현황
	@RequestMapping(value="/monthly-report/operation", method= RequestMethod.GET)
	public List<Map<String, Object>> findMonthlyOperationStatus(@RequestParam(name = "YEAR") int year){
		return service.findMonthlyOperationStatus(RequestParams.map());
	}

	// 월간요약
	@RequestMapping(value="/monthly-report/summary", method= RequestMethod.GET)
	public Map<String, Object> findMonthlySummary(@RequestParam(name = "YEAR") int year){
		return service.findMonthlySummary(RequestParams.map());
	}

	/*// 연간실적/경제적가치(재무현황)
	@RequestMapping(value="/yearly-report/finance", method= RequestMethod.GET)
	public List<Map<String, Object>> findFinance(@RequestParam(name = "YEAR") int year){
		return service.findFinance(RequestParams.map());
	}

	// 연간실적/사회혁신노력
	@RequestMapping(value="/yearly-report/socialBiz", method= RequestMethod.GET)
	public List<Map<String, Object>> findSocialBiz(@RequestParam(name = "YEAR") int year){
		return service.findSocialBiz(RequestParams.map());
	}

	// 연간실적/사업역량개발노력
	@RequestMapping(value="/yearly-report/bizStudy", method= RequestMethod.GET)
	public List<Map<String, Object>> findBizStudy(@RequestParam(name = "YEAR") int year){
		return service.findBizStudy(RequestParams.map());
	}*/

	// 성과지표 조회
	@RequestMapping(value="/yearly-report/kpi", method= RequestMethod.GET)
	public List<Map<String, Object>> findKpi(@RequestParam(name = "YEAR") int year){
		return service.findKpi(RequestParams.map());
	}

	// 전체 센터 성과지표 조회, CORP_SEQ 로 서비스에서 피벗팅 함
	@RequestMapping(value="/yearly-report/kpiAll", method= RequestMethod.GET)
	public Map<String, List<Map<String, Object>>> findAllCorpKpiByYear(@RequestParam(name = "YEAR") int year){
		return service.findAllCorpKpiByYear(RequestParams.map());
	}
}
