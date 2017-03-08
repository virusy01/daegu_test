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

	// 사회적기업 성과지표조회
	public List<Map<String, Object>> findSocialKpi(Map<String, Object> params) {
		List<Map<String, Object>> result = repository.findSocialKpi(params, Security.user());


		result.forEach(kpiItem -> {
			String level3Code = (String) kpiItem.get("L3_KPI_CD");
			Map<String, Object> detailResult = getKpiSocial(level3Code, params);
			if (detailResult != null) {
				kpiItem.putAll(detailResult);
			}
		});

		return result;
	}

	public Map<String, Object> getKpiSocial(String level3Code, Map<String, Object> params) {
		Map<String, Object> detailResult = null;

		detailResult = repository.findKpi_detail(level3Code, params, Security.user());
		return detailResult;
	}


	// 협동조합 성과지표조회
	public List<Map<String, Object>> findCooperationKpi(Map<String, Object> params) {
		List<Map<String, Object>> result = repository.findCooperationKpi(params, Security.user());


		result.forEach(kpiItem -> {
			String level3Code = (String) kpiItem.get("L3_KPI_CD");
			Map<String, Object> detailResult = getKpiCooperation(level3Code, params);
			if (detailResult != null) {
				kpiItem.putAll(detailResult);
			}
		});

		return result;
	}

	public Map<String, Object> getKpiCooperation(String level3Code, Map<String, Object> params) {
		Map<String, Object> detailResult = null;

		detailResult = repository.findKpi_detail(level3Code, params, Security.user());
		return detailResult;
	}



	// 마을기업 성과지표조회
	public List<Map<String, Object>> findVillageKpi(Map<String, Object> params) {
		List<Map<String, Object>> result = repository.findVillageKpi(params, Security.user());


		result.forEach(kpiItem -> {
			String level3Code = (String) kpiItem.get("L3_KPI_CD");
			Map<String, Object> detailResult = getKpiVillage(level3Code, params);
			if (detailResult != null) {
				kpiItem.putAll(detailResult);
			}
		});

		return result;
	}

	public Map<String, Object> getKpiVillage(String level3Code, Map<String, Object> params) {
		Map<String, Object> detailResult = null;

		detailResult = repository.findKpi_detail(level3Code, params, Security.user());
		return detailResult;
	}


	public int saveKpi(List<Map<String, Object>> social) {

		if (social.size() < 1) {
			return -1;
		}
		repository.deleteKpi(social.get(0), Security.user());

		social.forEach(param -> {
			repository.insertKpi(param, Security.user());
		});
		return 1;
	}








	// 성과지표 등급별 분석 데모용
	public List<Map<String, Object>>chartGrade(String corpKind) {
		List<Map<String, Object>> result;
		switch (corpKind) {
			case "0":
				result = repository.chartGrade1(Security.user()); //전체
				break;
			case "1":
				result = repository.chartGrade2(Security.user()); //사회적기업
				break;
			case "2":
				result = repository.chartGrade3(Security.user()); //협동조합
				break;
			default:
				result = repository.chartGrade4(Security.user()); //마을기업
				break;
		}

		return result;
	}
	// 성과지표 항목별 분석 데모용
	public List<Map<String, Object>>chartKpi(Map<String, Object> params,String corpKind) {
		List<Map<String, Object>> result;
		switch (corpKind) {
			case "0":
				result = repository.chartKpiAll(params, Security.user()); //전체
				break;
			default:
				result = repository.chartKpiEach(params, Security.user()); //사회적기업,마을기업, 협동조합
				break;
		}
		return result;
	}


	// 성과지표 지역별 분석
	public List<Map<String, Object>>chartRegion(Map<String, Object> params,String corpKind) {
		List<Map<String, Object>> result;
		switch (corpKind) {
			case "0":
				result = repository.chartRegionAll(params, Security.user()); //전체
				break;
			default:
				result = repository.chartRegionEach(params, Security.user()); //사회적기업,마을기업, 협동조합
				break;
		}
		return result;
	}

	// 성과지표 업종별 분석 - 등급항목
	public List<Map<String, Object>>chartSectorGrade(Map<String, Object> params,String corpKind) {
		List<Map<String, Object>> result;
		switch (corpKind) {
			case "0":
				result = repository.chartSectorAllGrade(params, Security.user()); //전체
				break;
			default:
				result = repository.chartSectorEachGrade(params, Security.user()); //사회적기업,마을기업, 협동조합
				break;
		}
		return result;
	}

	// 성과지표 업종별 분석 - 비율항목
	public List<Map<String, Object>>chartSectorRate(Map<String, Object> params,String corpKind) {
		List<Map<String, Object>> result;
		switch (corpKind) {
			case "0":
				result = repository.chartSectorAllRate(params, Security.user()); //전체
				break;
			default:
				result = repository.chartSectorEachRate(params, Security.user()); //사회적기업,마을기업, 협동조합
				break;
		}
		return result;
	}

	 // 성과지표 사회적경제조직 유형별 분석 - 등급항목 통계
	public Map<String, Object> chartAllGrade() {
		Map<String, Object> nestedData = new HashMap<>();

		nestedData.put("socialData", chartAllGradeSocial());
		nestedData.put("villageData", chartAllGradeVillage());
		nestedData.put("cooperationData", chartAllGradeCooperation());
		nestedData.put("totalData", chartAllGradeTotal());

		return nestedData;
	}

	// 성과지표 사회적경제조직 유형별 분석 - 등급항목 통계 - 사회적 기업
	public List<Map<String, Object>> chartAllGradeSocial() {
		return repository.chartAllGradeSocial(Security.user());
	}
	// 성과지표 사회적경제조직 유형별 분석 - 등급항목 통계 - 마을기업
	public List<Map<String, Object>> chartAllGradeVillage() {
		return repository.chartAllGradeVillage(Security.user());
	}
	// 성과지표 사회적경제조직 유형별 분석 - 등급항목 통계 - 협동조합
	public List<Map<String, Object>> chartAllGradeCooperation() {return repository.chartAllGradeCooperation(Security.user());
	}
	// 성과지표 사회적경제조직 유형별 분석 - 등급항목 통계 - 합계
	public List<Map<String, Object>> chartAllGradeTotal() {
		return repository.chartAllGradeTotal(Security.user());
	}


	// 성과지표 사회적경제조직 유형별 분석 - 비율항목 통계
	public Map<String, Object> chartAllRate() {
		Map<String, Object> nestedData = new HashMap<>();

		nestedData.put("socialData", chartAllRateSocial());
		nestedData.put("villageData", chartAllRateVillage());
		nestedData.put("cooperationData", chartAllRateCooperation());
		nestedData.put("totalData", chartAllRateTotal());

		return nestedData;
	}

	// 성과지표 사회적경제조직 유형별 분석 - 비율항목 통계 - 사회적 기업
	public List<Map<String, Object>> chartAllRateSocial() {
		return repository.chartAllRateSocial(Security.user());
	}
	// 성과지표 사회적경제조직 유형별 분석 - 비율항목 통계 - 마을기업
	public List<Map<String, Object>> chartAllRateVillage() {
		return repository.chartAllRateVillage(Security.user());
	}
	// 성과지표 사회적경제조직 유형별 분석 - 비율항목 통계 - 협동조합
	public List<Map<String, Object>> chartAllRateCooperation() {return repository.chartAllRateCooperation(Security.user());
	}
	// 성과지표 사회적경제조직 유형별 분석 - 비율항목 통계 - 합계
	public List<Map<String, Object>> chartAllRateTotal() {
		return repository.chartAllRateTotal(Security.user());
	}



	// 성과지표 업종별 분석
	public List<Map<String, Object>>gridCorp(Map<String, Object> params,String corpKind) {
		List<Map<String, Object>> result;
		switch (corpKind) {
			case "0":
				result = repository.gridCorpAll(params, Security.user()); //전체
				break;
			default:
				result = repository.gridCorpEach(params, Security.user()); //사회적기업,마을기업, 협동조합
				break;
		}
		return result;
	}

	// 성과지표 사회적경제조직 유형별 분석 - 등급항목 통계
	public List<Map<String, Object>> chartTypeGrade() {
		return repository.chartTypeGrade(Security.user());
	}

	// 성과지표 사회적경제조직 유형별 분석 - 비율항목 통계
	public List<Map<String, Object>> chartTypeRate() {
		return repository.chartTypeRate(Security.user());
	}





}