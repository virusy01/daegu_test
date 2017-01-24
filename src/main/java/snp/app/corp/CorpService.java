package snp.app.corp;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import snp.infra.security.Security;
import snp.infra.user.model.User;
import snp.util.PivotTable;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class CorpService {
	@Autowired
	private CorpRepository repository;

	public Map<String, Object> findCorpDetail(int corpSeq) {
		Map<String, Object> detail = new HashMap<String, Object>();
		detail.put("basic", findCorpInfo(corpSeq));
		detail.put("seInfo", findCorpEmployee(corpSeq));
		return detail;
	}

	public Map<String, Object> findCorpInfo(int corpSeq) {
		return repository.findCorpInfo(corpSeq, Security.user());
	}

	public int saveCorpInfos(List<Map<String, Object>> params) {
		int r = 0;
		for (Map<String, Object> p : params) {
			r += saveCorpInfo(p);
		}
		return r;
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

	public int deleteCorpInfo(int corpSeq) {
		return repository.deleteCorpInfo(corpSeq, Security.user());
	}

	public Map<String, Object> findCorpEmployee(int corpSeq) {
		return repository.findCorpEmployee(corpSeq, Security.user());
	}

	public int saveCorpEmployees(List<Map<String, Object>> params) {
		int r = 0;
		for (Map<String, Object> p : params) {
			r += saveCorpEmployee(p);
		}
		return r;
	}

	public int saveCorpEmployee(Map<String, Object> params) {
		int r;

		int corpSeq = (int) params.get("CORP_SEQ");
		repository.deleteCorpEmployee(corpSeq, Security.user());
		r = repository.insertCorpEmployee(params, Security.user());
		return r;
	}

	public int deleteCorpEmployee(Integer corpSeq) {
		return repository.deleteCorpEmployee(corpSeq, Security.user());
	}


	// 월간실적/비용현황
	public List<Map<String, Object>> findCosts(Map<String, Object> params) {
		return repository.findCosts(params, Security.user());
	}

	public int saveCosts(List<Map<String, Object>> costs) {
		if (costs.size() < 1) {
			return -1;
		}
		repository.deleteCosts(costs.get(0), Security.user());

		costs.forEach(param -> {
			repository.insertCosts(param, Security.user());
		});
		return 1;
	}

	// 월간실적/고용현황
	public List<Map<String, Object>> findEmployment(Map<String, Object> params) {
		return repository.findEmployment(params, Security.user());
	}

	public int saveEmployment(List<Map<String, Object>> employments) {
		if (employments.size() < 1) {
			return -1;
		}
		repository.deleteEmployment(employments.get(0), Security.user());

		employments.forEach(param -> {
			repository.insertEmployment(param, Security.user());
		});
		return 1;
	}

	// 월간실적/전체
	public Map<String, Object> findIncome(Map<String, Object> params) {
		Map<String, Object> nestedData = new HashMap<>();

		nestedData.put("paidMeals", findIncomePaidMeals(params));
		nestedData.put("publicMeals", findIncomePublicMeals(params));
		nestedData.put("support", findIncomeSupport(params));

		return nestedData;
	}

	public int saveIncome(Map<String, List<Map<String, Object>>> incomes) {

		saveIncomePaidMeals(incomes.get("paidMeals"));
		saveIncomePublicMeals(incomes.get("publicMeals"));
		saveIncomeSupport(incomes.get("support"));

		return 1;
	}

	// 월간실적/수입-유료급식
	public List<Map<String, Object>> findIncomePaidMeals(Map<String, Object> params) {
		return repository.findIncomePaidMeals(params, Security.user());
	}


	public int saveIncomePaidMeals(List<Map<String, Object>> incomes) {
		if (incomes.size() < 1) {
			return -1;
		}
		repository.deleteIncomePaidMeals(incomes.get(0), Security.user());

		incomes.forEach(param -> {
			repository.insertIncomePaidMeals(param, Security.user());
		});
		return 1;
	}

	// 월간실적/수입-공공급식
	public List<Map<String, Object>> findIncomePublicMeals(Map<String, Object> params) {
		return repository.findIncomePublicMeals(params, Security.user());
	}

	public int saveIncomePublicMeals(List<Map<String, Object>> incomes) {
		if (incomes.size() < 1) {
			return -1;
		}
		repository.deleteIncomePublicMeals(incomes.get(0), Security.user());

		incomes.forEach(param -> {
			repository.insertIncomePublicMeals(param, Security.user());
		});
		return 1;
	}

	// 월간실적/수입-지원금
	public List<Map<String, Object>> findIncomeSupport(Map<String, Object> params) {
		return repository.findIncomeSupport(params, Security.user());
	}

	public int saveIncomeSupport(List<Map<String, Object>> incomes) {
		if (incomes.size() < 1) {
			return -1;
		}
		repository.deleteIncomeSupport(incomes.get(0), Security.user());

		incomes.forEach(param -> {
			repository.insertIncomeSupport(param, Security.user());
		});
		return 1;
	}

	// 월간실적/사회공헌활동
	public List<Map<String, Object>> findDonation(Map<String, Object> params) {
		return repository.findDonation(params, Security.user());
	}

	public int saveDonation(List<Map<String, Object>> donations) {
		if (donations.size() < 1) {
			return -1;
		}
		repository.deleteDonation(donations.get(0), Security.user());

		donations.forEach(param -> {
			repository.insertDonation(param, Security.user());
		});
		return 1;
	}

	// 월간실적/월평균임금
	public Map<String, Object> findSalary(Map<String, Object> params) {
		return repository.findSalary(params, Security.user());
	}

	public int saveSalary(Map<String, Object> salary) {
		repository.deleteSalary(salary, Security.user());
		return repository.insertSalary(salary, Security.user());
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

	public int saveFinance(List<Map<String, Object>> finances) {
		finances.forEach(param -> {
			repository.deleteFinance(param, Security.user());
		});

		finances.forEach(param -> {
			repository.insertFinance(param, Security.user());
		});
		return 1;
	}

	// 연간실적/사회적공헌활동
	public List<Map<String, Object>> findSocialBiz(Map<String, Object> params) {
		return repository.findSocialBiz(params, Security.user());
	}

	public int saveSocialBiz(List<Map<String, Object>> socialBizes) {
		socialBizes.forEach(param -> {
			repository.deleteSocialBiz(param, Security.user());
		});

		socialBizes.forEach(param -> {
			repository.insertSocialBiz(param, Security.user());
		});
		return 1;
	}

	// 연간실적/사업역량개발노력
	public List<Map<String, Object>> findBizStudy(Map<String, Object> params) {
		return repository.findBizStudy(params, Security.user());
	}

	public int saveBizStudy(List<Map<String, Object>> bizStudies) {
		bizStudies.forEach(param -> {
			repository.deleteBizStudy(param, Security.user());
		});

		bizStudies.forEach(param -> {
			repository.insertBizStudy(param, Security.user());
		});
		return 1;
	}

	// 성과지표조회
	public List<Map<String, Object>> findKpi(Map<String, Object> params) {
		List<Map<String, Object>> result = repository.findKpi(params, Security.user());

		result.forEach(kpiItem -> {
			String level3Code = (String) kpiItem.get("L3_KPI_CD");
			Map<String, Object> detailResult = getKpiDetail(level3Code, params);
			if (detailResult != null) {
				//kpiItem.putAll(detailResult);
			}
		});

		return result;
	}

	public Map<String, Object> getKpiDetail(String level3Code, Map<String, Object> params) {
		Map<String, Object> detailResult = null;
		switch (level3Code) {

			case "010101":
				detailResult = repository.findKpi_010101(params, Security.user());
				break;
			case "010102":
				//detailResult = repository.findKpi_010102(params, Security.user());
				break;
			case "010201":
				//detailResult = repository.findKpi_010201(params, Security.user());
				break;
			case "010301":
				//detailResult = repository.findKpi_010301(params, Security.user());
				break;
			case "010401":
				//detailResult = repository.findKpi_010401(params, Security.user());
				break;
			case "010402":
				//detailResult = repository.findKpi_010402(params, Security.user());
				break;
			case "020101":
				break;
			// 경제적 가치 창출 - 매출액 -  • 년간 매출액 평균
			case "030101":
				//detailResult = repository.findKpi_030101(params, Security.user());
				break;
			case "030201":
				break;
			case "030301":
				break;
			case "030401":
				break;
			case "030501":
				break;
			case "030601":
				break;
			case "040101":
				break;
			case "040201":
				break;
			case "050101":
				break;
			case "050201":
				break;
			case "060101":
				break;
			case "060201":
				break;
			case "070101":
				break;
			case "070102":
				break;
			case "070103":
				break;
			case "070104":
				break;
			case "070201":
				break;
			case "070202":
				break;
			case "070203":
				break;
			case "070204":
				break;
			case "070205":
				break;
			case "070206":

		}
		return detailResult;
	}

	// 성과지표조회
	public List<Map<String, Object>> findKpiCompare(Map<String, Object> params, String level3Code) {
		List<Map<String, Object>> result = null;
		switch (level3Code) {
			// 사회적 가치 창출 - 공공급식 품질개선 가치 -  • 자부담 공공급식 가치=급식수×6,400원*1 (품질개선 상수)
			case "010101":
				//result = repository.findKpi_010101_compare(params, Security.user());
				break;
			case "010102":
				//detailResult = repository.findKpi_010102(params, Security.user());
				//kpiItem.putAll(detailResult);
				break;
			case "010201":
				break;
			case "010301":
				break;
			case "010401":
				break;
			case "010402":
				break;
			case "020101":
				break;
			// 경제적 가치 창출 - 매출액 -  • 년간 매출액 평균
			case "030101":
				//result = repository.findKpi_030101_compare(params, Security.user());
				break;
			case "030201":
				break;
			case "030301":
				break;
			case "030401":
				break;
			case "030501":
				break;
			case "030601":
				break;
			case "040101":
				break;
			case "040201":
				break;
			case "050101":
				break;
			case "050201":
				break;
			case "060101":
				break;
			case "060201":
				break;
			case "070101":
				break;
			case "070102":
				break;
			case "070103":
				break;
			case "070104":
				break;
			case "070201":
				break;
			case "070202":
				break;
			case "070203":
				break;
			case "070204":
				break;
			case "070205":
				break;
			case "070206":

		}
		return result;
	}

	// 매뉴얼 성과지표 조회(등록용 템플릿)
	public List<Map<String, Object>> findManualKpi(Map<String, Object> params) {
		List<Map<String, Object>> manualKpis = repository.findManualKpi(params, Security.user());
		PivotTable pt = new PivotTable();
		pt.setKeyColumns(new String[]{"CORP_SEQ", "CENTER_NM", "YEAR"});
		pt.setPivotColumn("KPI_CD");
		pt.setValueColumn("RESULT");
		//pt.setNullReplace("0");
		pt.setSourceList(manualKpis);

		List<Map<String, Object>> pivoted = pt.composePivot();
		return pivoted;
	}

	public int saveManualKpi(List<Map<String, Object>> manualKpis) {
		// 피벗된 데이터를 원래 테이블 구조에 맞게 풀어냄.
		manualKpis.forEach(param -> {
			Map<String, Object> newParam = new HashMap<>();
			Iterator<String> keyIterator = param.keySet().iterator();
			// HashMap은 key가 정렬되지 않았으므로 pivot 데이터 이외의 key값을 먼저 분리해낸다.
			while (keyIterator.hasNext()) {
				String key = keyIterator.next();
				// 피벗 데이터(x축)는 key-value값 분리
				if ("YEAR".equals(key) || "CORP_SEQ".equals(key) || "CENTER_NM".equals(key)) {
					newParam.put(key, param.get(key));
				}
			}

			// 피벗데이터를 만날때마다 데이터를 등록한다(CORP_SEQ, KPI_CD, RESULT)
			keyIterator = param.keySet().iterator();
			while (keyIterator.hasNext()) {
				String key = keyIterator.next();
				// 피벗 데이터(x축)는 key-value값 분리
				if (!"YEAR".equals(key) && !"CORP_SEQ".equals(key) && !"CENTER_NM".equals(key)) {
					newParam.put("KPI_CD", key);
					newParam.put("RESULT", param.get(key));
					repository.deleteManualKpi(newParam, Security.user());
				}
			}
		});

		manualKpis.forEach(param -> {
			Map<String, Object> newParam = new HashMap<>();
			Iterator<String> keyIterator = param.keySet().iterator();

			// HashMap은 key가 정렬되지 않았으므로 pivot 데이터 이외의 key값을 먼저 분리해낸다.
			while (keyIterator.hasNext()) {
				String key = keyIterator.next();
				// 피벗 데이터(x축)는 key-value값 분리
				if ("YEAR".equals(key) || "CORP_SEQ".equals(key) || "CENTER_NM".equals(key)) {
					newParam.put(key, param.get(key));
				}
			}

			// 피벗데이터를 만날때마다 데이터를 등록한다(CORP_SEQ, KPI_CD, RESULT)
			keyIterator = param.keySet().iterator();
			while (keyIterator.hasNext()) {
				String key = keyIterator.next();
				// 피벗 데이터(x축)는 key-value값 분리
				if (!"YEAR".equals(key) && !"CORP_SEQ".equals(key) && !"CENTER_NM".equals(key)) {
					newParam.put("KPI_CD", key);
					newParam.put("RESULT", param.get(key));
					repository.insertManualKpi(newParam, Security.user());
				}
			}
		});
		return 1;
	}

	// 월간운영실적 추이
	public List<Map<String, Object>> findOpTrend(Map<String, Object> params, String kpiKind) {
		List<Map<String, Object>> result;
		switch (kpiKind) {
			case "0":
				result = repository.findOpSalesAmount(params, Security.user());
				break;
			case "1":
				result = repository.findOpSalesPower(params, Security.user());
				break;
			case "2":
				result = repository.findOpSocialBizCount(params, Security.user());
				break;
			case "3":
				result = repository.findOpSocialBizExpense(params, Security.user());
				break;
			case "4":
				result = repository.findOpBizStudyCount(params, Security.user());
				break;
			default: // "5"
				result = repository.findOpBizStudyTime(params, Security.user());
				break;
		}

		return result;
	}


	// 성과지표 등급별 분석 데모용
	public List<Map<String, Object>>chartDemo1(String corpKind) {
		List<Map<String, Object>> result;
		switch (corpKind) {
			case "0":
				result = repository.chartDemo10(Security.user()); //전체
				break;
			case "1":
				result = repository.chartDemo11(Security.user()); //사회적기업
				break;
			case "2":
				result = repository.chartDemo12(Security.user()); //협동조합
				break;
			default:
				result = repository.chartDemo13(Security.user()); //마을기업
				break;
		}

		return result;
	}
	// 성과지표 항목별 분석 데모용
	public List<Map<String, Object>>chartDemo2(Map<String, Object> params,String corpKind) {
		return repository.chartDemo2(params,Security.user());
	}

	// 성과지표 지역별 분석 데모용
	public List<Map<String, Object>>chartDemo3(Map<String, Object> params,String corpKind) {
		return repository.chartDemo3(params,Security.user());
	}
}