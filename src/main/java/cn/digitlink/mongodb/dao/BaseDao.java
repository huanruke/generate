package cn.digitlink.mongodb.dao;

import cn.digitlink.mongodb.entity.BaseBean;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * @description 基础Dao
 * @company 北京数联领航网络科技有限公司 2020
 * @author: yangwc
 * @date: 20/12/11
 */
@NoRepositoryBean
public interface BaseDao<T extends BaseBean> extends MongoRepository<T, String> {

}
