package cn.digitlink.mongodb.service;

import com.alibaba.fastjson.JSONObject;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @description 基础业务接口
 * @company 北京数联领航网络科技有限公司 2020
 * @author yangwc
 * @date 20/12/11
 */
public interface IBaseService<T> {
	
	/**
	 * 新增
	 * @param entity	对象
	 * @return void
	 */
	void save(T entity);
	
	/**
	 * 批量新增
	 * @param entities	对象集合
	 * @return void
	 */
	void saveBatch(List<T> entities);
	
	/**
	 * 逻辑删除
	 * @param id	ID
	 * @return void
	 */
	void remove(String id);
	
	/**
	 * 物理删除
	 * @param id	ID
	 * @return void
	 */
	void realRemove(String id);
	
	/**
	 * 修改
	 * @param entity	对象
	 * @return void
	 */
	void modify(T entity);
	
	/**
	 * 批量修改
	 * @param entities	对象集合
	 * @return void
	 */
	void modify(List<T> entities);
	
	/**
	 * ID查询
	 * @param id	ID
	 * @return T
	 */
	T findById(String id);
	
	/**
	 * 条件查询
	 * @param entity	条件对象
	 * @return T
	 */
	T findOne(T entity);

	/**
	 * 条件查询所有
	 * @param entity	条件对象
	 * @return java.util.List<T>
	 */
	List<T> findAll(T entity);

	/**
	 * 条件查询数量
	 * @param entity	条件对象
	 * @return java.lang.Long
	 */
	Long findCount(T entity);

	/**
	 * 分页条件查询
	 * @param entity	条件对象
	 * @param from		起始偏移
	 * @param limit		记录条数
	 * @param orderBy	排序规则 [{"key":"name", "rule": "desc"}]
	 * @return java.util.List<T>
	 */
	List<T> findList(T entity, Integer from, Integer limit, List<JSONObject> orderBy);

	/**
	 * 分页条件查询
	 * @param entity	条件对象
	 * @param pageNum	第几页
	 * @param pageSize	记录条数
	 * @param orderBy	排序规则
	 * @return org.springframework.data.domain.Page<T>
	 */
	Page<T> findPage(T entity, Integer pageNum, Integer pageSize, List<JSONObject> orderBy);

}
