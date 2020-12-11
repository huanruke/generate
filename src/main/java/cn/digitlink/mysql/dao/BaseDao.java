package cn.digitlink.mysql.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @description 基础Dao
 * @company 北京数联领航网络科技有限公司 2020
 * @author: yangwc
 * @date: 2020/12/10
 */
public interface BaseDao<T> {

    /**
     * 新增
     * @param entity	对象
     * @return int  新增条数
     * @author yangwc 20/12/10 11:46
     */
    int insert(T entity);

    /**
     * 批量新增
     * @param entities	对象集合
     * @return int  新增条数
     */
    int insertBatch(List<T> entities);

    /**
     * 更新
     * @param entity    对象
     * @return int  更新条数
     */
    int update(T entity);

    /**
     * ID删除
     * @param id	ID
     * @return int  删除条数
     */
    int deleteById(Integer id);

    /**
     * ID查询
     * @param id    ID
     * @return T
     */
    T queryById(Integer id);

    /**
     * 条件查询
     * @param entity	条件
     * @return java.util.List<T>
     */
    List<T> queryAll(T entity);

    /**
     * 条件查询数量
     * @param entity	条件
     * @return long
     */
    long queryCount(T entity);

    /**
     * 条件分页查询
     * @param entity    条件
     * @param offset	偏移
     * @param limit	    条数
     * @return java.util.List<T>
     */
    List<T> queryPage(@Param("entity") T entity, @Param("offset") Integer offset, @Param("limit") Integer limit);

}