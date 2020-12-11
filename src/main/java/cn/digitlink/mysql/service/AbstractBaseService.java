package cn.digitlink.mysql.service;

import cn.digitlink.mysql.dao.BaseDao;
import cn.digitlink.mysql.entity.BaseBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;

/**
 * @description 基础业务抽象类
 * @company 北京数联领航网络科技有限公司 2020
 * @author: yangwc
 * @date: 2020/11/11
 */
public class AbstractBaseService<T extends BaseBean, E extends BaseDao<T>> implements IBaseService<T> {

    @Autowired(required = false)
    private E baseDao;

    @Override
    public void save(T entity) {
        entity.setCreatedAt(new Date());
        entity.setUpdatedAt(new Date());
        baseDao.insert(entity);
    }

    @Override
    public void saveBatch(List<T> entities) {
        for (T entity : entities) {
            entity.setCreatedAt(new Date());
            entity.setUpdatedAt(new Date());
        }
        baseDao.insertBatch(entities);
    }

    @Override
    public void remove(Integer id) {
        Assert.notNull(id, "ID is null");
        baseDao.deleteById(id);
    }

    @Override
    public void modify(T entity) {
        Assert.notNull(entity, "Entity is null");
        Assert.notNull(entity.getId(), "ID is null");
        entity.setUpdatedAt(new Date());
        baseDao.update(entity);
    }

    @Override
    public T findById(Integer id) {
        Assert.notNull(id, "ID is null");
        return baseDao.queryById(id);
    }

    @Override
    public List<T> findAll(T entity) {
        Assert.notNull(entity, "Example entity is null");
        return baseDao.queryAll(entity);
    }

    @Override
    public long findCount(T entity) {
        Assert.notNull(entity, "Example entity is null");
        return baseDao.queryCount(entity);
    }

    @Override
    public List<T> findPage(T entity, Integer pageNum, Integer pageSize) {
        Assert.notNull(entity, "Example entity is null");
        pageNum = pageNum <= 0 ? 1 : pageNum;
        Integer offset = (pageNum - 1) * pageSize;
        Integer limit = pageSize;
        return baseDao.queryPage(entity, offset, limit);
    }

}
