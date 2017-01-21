package snp.infra.helper;

/**
 * Front End에서 입력, 수정, 삭제 표기된 List가 한꺼번에 올라올때, 입력,수정,삭제로 분기하기 위한 상태 값
 *
 * <br/>개정이력<br/>
 * 2016-11-22 woomun.jung@gmail.com 관리자 : System Unit, 부서정보, Group Code, Detail Code<br/>
 * 2016-11-24 lempel@gmail.com code review<br/>
 * 2016-11-28 woomun.jung@gmail.com 상수값 추가하기 위해 class안에 enum 구조로 변경<br/>
 *
 * @author woomun.jung@gmail.com
 * @since 2016-11-22.
 */
public class EditStatus {
    final public static String FLAG = "flag";
    final public static String ITEM = "item";
    public enum ItemAs {
        // DB Insert
        NEW,
        // DB Update
        UPDATE,
        // DB Delete
        DELETE
    }
}
