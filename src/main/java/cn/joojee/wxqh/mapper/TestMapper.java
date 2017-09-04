package cn.joojee.wxqh.mapper;

import cn.joojee.wxqh.model.OpenidBean;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by Administrator on 2017/8/24.
 */

@Mapper
public interface TestMapper {

    /**
     * 获取所有opendi信息
     * @return
     */
    public List<OpenidBean> getOpenids();
}
