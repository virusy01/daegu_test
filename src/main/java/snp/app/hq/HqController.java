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
	@RequestMapping(value="/socialinfo/{CORP_SEQ}", method= RequestMethod.DELETE)
	public int deleteSocialInfo(@PathVariable("CORP_SEQ") Integer corpSeq)
	{
		return service.deleteSocialInfo(corpSeq);
	}


	/**
	 * 협동조합 기본정보 목록
	 * @return
	 */
	@RequestMapping(value="/cooperationList")
	public List<Map<String, Object>> findCooperationList(){
		return service.findCooperationList();
	}

	/**
	 * 사회적기업정보 상세정보
	 * @return
	 */
	@RequestMapping(value="/cooperationInfo/{CORP_SEQ}")
	public Map<String, Object> findCooperationnfo(@PathVariable("CORP_SEQ") Integer corpSeq){
		return service.findCooperationInfo(corpSeq);
	}

	/**
	 * 협동조합 정보 저장
	 * @param params
	 * @return
	 */
	@RequestMapping(value="/cooperationInfo", method= RequestMethod.POST)
	public int saveCooperationInfo(@RequestBody Map<String, Object> params)
	{
		return service.saveCooperationInfo(params);
	}

	/**
	 * 협동조합 정보 삭제
	 * @param corpSeq
	 * @return
	 */
	@RequestMapping(value="/cooperationinfo/{CORP_SEQ}", method= RequestMethod.DELETE)
	public int deleteCooperationInfo(@PathVariable("CORP_SEQ") Integer corpSeq)
	{
		return service.deleteCooperationInfo(corpSeq);
	}

}
