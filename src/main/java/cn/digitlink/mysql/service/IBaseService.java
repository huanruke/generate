package cn.digitlink.mysql.service;

import java.util.List;

/**
 * @description 基础业务接口
 * @company 北京数联领航网络科技有限公司 2020
 * @author: yangwc
 * @date: 2020/11/11
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
     * 删除
     * @param id	ID
     * @return void
     */
    void remove(Integer id);

    /**
     * 修改
     * @param entity    对象
     * @return void
     */
    void modify(T entity);

    /**
     * ID查询
     * @param id	ID
     * @return T
     */
    T findById(Integer id);

    /**
     * 条件查询
     * @param entity	条件对象
     * @return java.util.List<T>
     */
    List<T> findAll(T entity);

    /**
     * 查询数量
     * @param entity	条件对象
     * @return long
     */
    long findCount(T entity);

    /**
     * 分页查询
     * @param entity	条件对象
     * @param pageNum	第几页
     * @param pageSize	每页数量
     * @return java.util.List<T>
     */
    List<T> findPage(T entity, Integer pageNum, Integer pageSize);

}
