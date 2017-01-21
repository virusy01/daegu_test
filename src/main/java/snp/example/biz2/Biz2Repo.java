package snp.example.biz2;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by woomun.jeong on 2016-12-05.
 */
@Mapper
public interface Biz2Repo {
    List<Map<String, String>> list(@Param(value = "user") String user);
}
