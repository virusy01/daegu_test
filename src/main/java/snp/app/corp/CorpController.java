package snp.app.corp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import snp.infra.helper.RequestParams;
import snp.infra.security.Security;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/corp")
public class CorpController
{
	@Autowired
	private CorpService service;


	/**
	 * 기업정보 조회
	 * corp_detail_info.php
	 * @param corpSeq
	 * @return
	 */
	@RequestMapping(value="/info/{CORP_SEQ}")
	public Map<String, Object> findCorpInfo(@PathVariable("CORP_SEQ") Integer corpSeq){
		return service.findCorpInfo(corpSeq);
	}

	/**
	 * 기업 정보 저장
	 * corp_info_save.php
	 * @param params
	 * @return
	 */
	@RequestMapping(value="/info", method= RequestMethod.POST)
	public int saveCorpInfo(@RequestBody Map<String, Object> params)
	{
		return service.saveCorpInfo(params);
	}

	/**
	 * 기업 정보 삭제
	 * corp_info_del.php
	 * @param corpSeq
	 * @return
	 */
	@RequestMapping(value="/info/{CORP_SEQ}", method= RequestMethod.DELETE)
	public int deleteCorpInfo(@PathVariable("CORP_SEQ") Integer corpSeq)
	{
		return service.deleteCorpInfo(corpSeq);
	}

	/**
	 * 기업별 현재년도 고용현황
	 * @param corpSeq
	 * @return
	 */
	@RequestMapping(value="/employee/{CORP_SEQ}")
	public Map<String, Object> findCorpEmployee(@PathVariable("CORP_SEQ") Integer corpSeq){
		return service.findCorpEmployee(corpSeq);
	}

	/**
	 * 고용현황 저장
	 * corp_employee_save.php
	 * @param param
	 * @return
	 */
	@RequestMapping(value="/employee", method= RequestMethod.POST)
	public int saveCorpEmployee(@RequestBody Map<String, Object> param)
	{
		return service.saveCorpEmployee(param);
	}

	/**
	 * 고용현황 항목 삭제
	 * corp_employee_del.php
	 * @param employeeSeq
	 * @return
	 */
	@RequestMapping(value="/employee/{CORP_EMP_SEQ}", method= RequestMethod.DELETE)
	public int deleteCorpEmployee(@PathVariable("CORP_EMP_SEQ") Integer employeeSeq){
		return service.deleteCorpEmployee(employeeSeq);
	}

	// 월간실적/비용현황
	@RequestMapping(value="/monthly-report/costs", method= RequestMethod.GET)
	public List<Map<String, Object>> findCosts(@RequestParam(name = "CORP_SEQ") int corpSeq,
											   @RequestParam(name = "YEAR") int year){
		return service.findCosts(RequestParams.map());
	}
	@RequestMapping(value="/monthly-report/costs", method= RequestMethod.POST)
	public int saveCosts(@RequestBody List<Map<String, Object>> costs){
		return service.saveCosts(costs);
	}

	// 월간실적/고용현황
	@RequestMapping(value="/monthly-report/employment", method= RequestMethod.GET)
	public List<Map<String, Object>> findEmployment(@RequestParam(name = "CORP_SEQ") int corpSeq,
													@RequestParam(name = "YEAR") int year){
		return service.findEmployment(RequestParams.map());
	}
	@RequestMapping(value="/monthly-report/employment", method= RequestMethod.POST)
	public int saveEmployee(@RequestBody List<Map<String, Object>> employments){
		return service.saveEmployment(employments);
	}

	// 월간실적/전체 - 수입현황 입력, 수정을 각각 세개의 서비스로 되 있던걸 각각 하나로 통합
	@RequestMapping(value="/monthly-report/income", method= RequestMethod.GET)
	public Map<String, Object> findIncome(@RequestParam(name = "CORP_SEQ") int corpSeq,
														 @RequestParam(name = "YEAR") int year){
		return service.findIncome(RequestParams.map());
	}

    @RequestMapping(value="/monthly-report/income", method= RequestMethod.POST)
    public int saveIncomeList(@RequestBody Map<String, List<Map<String, Object>>> incomes){
        return service.saveIncome(incomes);
    }

	// 월간실적/수입-유료급식
	@RequestMapping(value="/monthly-report/income/paidMeals", method= RequestMethod.GET)
	public List<Map<String, Object>> findIncomePaidMeals(@RequestParam(name = "CORP_SEQ") int corpSeq,
														 @RequestParam(name = "YEAR") int year){
		return service.findIncomePaidMeals(RequestParams.map());
	}
	@RequestMapping(value="/monthly-report/income/paidMeals", method= RequestMethod.POST)
	public int saveIncomePaidMeals(@RequestBody List<Map<String, Object>> incomes){
		return service.saveIncomePaidMeals(incomes);
	}

	// 월간실적/수입-공공급식
	@RequestMapping(value="/monthly-report/income/publicMeals", method= RequestMethod.GET)
	public List<Map<String, Object>> findIncomePublicMeals(@RequestParam(name = "CORP_SEQ") int corpSeq,
														   @RequestParam(name = "YEAR") int year){
		return service.findIncomePublicMeals(RequestParams.map());
	}

	@RequestMapping(value="/monthly-report/income/publicMeals", method= RequestMethod.POST)
	public int saveIncomePublicMeals(@RequestBody  List<Map<String, Object>> incomes){
		return service.saveIncomePublicMeals(incomes);
	}

	// 월간실적/수입-지원금
	@RequestMapping(value="/monthly-report/income/support", method= RequestMethod.GET)
	public List<Map<String, Object>> findIncomeSupport(@RequestParam(name = "CORP_SEQ") int corpSeq,
													   @RequestParam(name = "YEAR") int year){
		return service.findIncomeSupport(RequestParams.map());
	}

	@RequestMapping(value="/monthly-report/income/support", method= RequestMethod.POST)
	public int saveIncomeSupport(@RequestBody List<Map<String, Object>> incomes){
		return service.saveIncomeSupport(incomes);
	}

	// 월간실적/사회공헌활동
	@RequestMapping(value="/monthly-report/donation", method= RequestMethod.GET)
	public List<Map<String, Object>> findDonation(@RequestParam(name = "CORP_SEQ") int corpSeq,
												   @RequestParam(name = "YEAR") int year){
		return service.findDonation(RequestParams.map());
	}

	@RequestMapping(value="/monthly-report/donation", method= RequestMethod.POST)
	public int saveDonation(@RequestBody List<Map<String, Object>> donations){
		return service.saveDonation(donations);
	}

	// 월간실적/월평균임금
	@RequestMapping(value="/monthly-report/salary", method= RequestMethod.GET)
	public Map<String, Object> findSalary(@RequestParam(name = "CORP_SEQ") int corpSeq,
										  @RequestParam(name = "YEAR") int year){
		return service.findSalary(RequestParams.map());
	}

	// 월간운영현황
	@RequestMapping(value="/monthly-report/operation", method= RequestMethod.GET)
	public List<Map<String, Object>> findMonthlyOperationStatus(@RequestParam(name = "CORP_SEQ") int corpSeq,
																@RequestParam(name = "YEAR") int year){
		return service.findMonthlyOperationStatus(RequestParams.map());
	}

	// 월간요약
	@RequestMapping(value="/monthly-report/summary", method= RequestMethod.GET)
	public Map<String, Object> findMonthlySummary(@RequestParam(name = "CORP_SEQ") int corpSeq,
												  @RequestParam(name = "YEAR") int year){
		return service.findMonthlySummary(RequestParams.map());
	}

	// 연간실적/경제적가치(재무현황)
	@RequestMapping(value="/yearly-report/finance", method= RequestMethod.GET)
	public List<Map<String, Object>> findFinance(@RequestParam(name = "CORP_SEQ") int corpSeq,
												  @RequestParam(name = "YEAR") int year){
		return service.findFinance(RequestParams.map());
	}

	@RequestMapping(value="/yearly-report/finance", method= RequestMethod.POST)
	public int saveFinance(@RequestBody List<Map<String, Object>> finances){
		return service.saveFinance(finances);
	}

	// 연간실적/사회혁신노력
	@RequestMapping(value="/yearly-report/socialBiz", method= RequestMethod.GET)
	public List<Map<String, Object>> findSocialBiz(@RequestParam(name = "CORP_SEQ") int corpSeq,
												  @RequestParam(name = "YEAR") int year){
		return service.findSocialBiz(RequestParams.map());
	}

	@RequestMapping(value="/yearly-report/socialBiz", method= RequestMethod.POST)
	public int saveSocialBiz(@RequestBody List<Map<String, Object>> socialBizes){
		return service.saveSocialBiz(socialBizes);
	}

	// 연간실적/사업역량개발노력
	@RequestMapping(value="/yearly-report/bizStudy", method= RequestMethod.GET)
	public List<Map<String, Object>> findBizStudy(@RequestParam(name = "CORP_SEQ") int corpSeq,
												  @RequestParam(name = "YEAR") int year){
		return service.findBizStudy(RequestParams.map());
	}

	@RequestMapping(value="/yearly-report/bizStudy", method= RequestMethod.POST)
	public int saveBizStudy(@RequestBody List<Map<String, Object>> bizStudies){
		return service.saveBizStudy(bizStudies);
	}

    // 성과지표 조회
    @RequestMapping(value="/yearly-report/kpi", method= RequestMethod.GET)
    public List<Map<String, Object>> findKpi(@RequestParam(name = "CORP_SEQ") int corpSeq,
                                                   @RequestParam(name = "YEAR") int year){
        return service.findKpi(RequestParams.map());
    }
    // 성과지표 세부 차트 팝업
	@RequestMapping(value="/yearly-report/kpiCompare", method= RequestMethod.GET)
	public List<Map<String, Object>> findKpiCompare(@RequestParam(name = "CORP_SEQ") int corpSeq,
											 		@RequestParam(name = "YEAR") int year,
													@RequestParam(name = "L3_KPI_CD") String level3Code){
		return service.findKpiCompare(RequestParams.map(), level3Code);
	}

    // 성과지표 조회
    @RequestMapping(value="/yearly-report/manualKpi", method= RequestMethod.GET)
    public List<Map<String, Object>> findManualKpi(@RequestParam(name = "YEAR") int year){
        return service.findManualKpi(RequestParams.map());
    }

    @RequestMapping(value="/yearly-report/manualKpi", method= RequestMethod.POST)
    public int saveManualKpi(@RequestBody List<Map<String, Object>> bizStudies){
        return service.saveManualKpi(bizStudies);
    }

    // 성과지표 조회
    @RequestMapping(value="/yearly-report/operationTrend", method= RequestMethod.GET)
    public List<Map<String, Object>> findOpTrend(@RequestParam(name = "CORP_SEQ") int corpSeq,
                                                 @RequestParam(name = "FROM_YEAR") int fromYear,
                                                 @RequestParam(name = "TO_YEAR") int toYear,
                                                 @RequestParam(name = "KPI_KIND") String kpiKind){
        return service.findOpTrend(RequestParams.map(), kpiKind);
    }

	// 성과지표 등급별 분석 데모용
	@RequestMapping(value="/analytics/chartDemo1", method= RequestMethod.GET)
	public List<Map<String, Object>>chartDemo1(@RequestParam(name = "CORP_KIND") String corpKind){
		return service.chartDemo1(corpKind);
	}

	// 성과지표 항목별 분석 데모용
	@RequestMapping(value="/analytics/chartDemo2", method= RequestMethod.GET)
	public List<Map<String, Object>>chartDemo2(@RequestParam(name = "CORP_KIND") String corpKind){
		return service.chartDemo2(RequestParams.map(),corpKind);
	}

	// 성과지표 지역별 분석 데모용
	@RequestMapping(value="/analytics/chartDemo3", method= RequestMethod.GET)
	public List<Map<String, Object>>chartDemo3(@RequestParam(name = "CORP_KIND") String corpKind){
		return service.chartDemo3(RequestParams.map(),corpKind);
	}
}
