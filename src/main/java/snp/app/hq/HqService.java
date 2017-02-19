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

}
