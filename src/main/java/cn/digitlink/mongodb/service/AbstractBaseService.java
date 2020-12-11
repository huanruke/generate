package cn.digitlink.mongodb.service;

import cn.digitlink.mongodb.entity.BaseBean;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @description 基础业务抽象类
 * @company 北京数联领航网络科技有限公司 2020
 * @author yangwc
 * @date 20/12/11
 */
public abstract class AbstractBaseService<T extends BaseBean, E extends MongoRepository<T, String>>
		implements IBaseService<T> {

	@Autowired(required = false)
	private MongoRepository< T, String> mongoRepository;
	
	@Autowired
	private MongoTemplate mongoTemplate;

	public static final int DEFAULT_FROM = 0;
	public static final int DEFAULT_LIMIT = 10;
	public static final int DEFAULT_PAGE_SIZE = 10;
	public static final String ORDER_BY_KEY = "key";
	public static final String ORDER_BY_RULE = "rule";
	
	@Override
	public void save(T entity) {
		entity.setCreatedAt(new Date());
		entity.setUpdatedAt(new Date());
		mongoRepository.insert(entity);
	}
	
	@Override
	public void saveBatch(List<T> list) {
		for (T entity : list) {
			entity.setCreatedAt(new Date());
			entity.setUpdatedAt(new Date());
		}
		mongoRepository.insert(list);
	}

	@Override
	public void remove(String id) {
		Assert.hasText(id, "ID is null");
		T entity = findById(id);
		if (entity != null) {
			entity.setIsActive(false);
			entity.setUpdatedAt(new Date());
			mongoRepository.save(entity);
		}
	}
	
	@Override
	public void realRemove(String id) {
		Assert.hasText(id, "ID is null");
		mongoRepository.deleteById(id);
	}

	@Override
	public void modify(T entity) {
		Assert.hasText(entity.getId(), "ID is null");
		Assert.notNull(entity.getLockedVersion(), "Locked version is null");
		entity.setUpdatedAt(new Date());
		mongoRepository.save(entity);
	}
	
	@Override
	public void modify(List<T> list) {
		for (T entity : list) {
			Assert.hasText(entity.getId(), "ID is null");
			Assert.notNull(entity.getLockedVersion(), "Locked version is null");
			entity.setUpdatedAt(new Date());
		}
		mongoRepository.saveAll(list);
	}

	@Override
	public T findById(String id) {
		Assert.hasText(id, "ID is null");
		Optional<T> result = mongoRepository.findById(id);
		T res = (T) result.orElse(null);
		if (res != null && res.getIsActive()) {
			return res;
		}
		return null;
	}

	@Override
	public T findOne(T entity) {
		Example<T> example = Example.of(entity);
		Optional<T> result = mongoRepository.findOne(example);
		T res = (T) result.orElse(null);
		if (res != null && res.getIsActive()) {
			return res;
		}
		return null;
	}

	@Override
	public List<T> findAll(T entity) {
		// 匹配规则
		ExampleMatcher matcher = getMatcher();
		// 条件
		Example<T> example = Example.of(entity, matcher);
		// 查询
		return mongoRepository.findAll(example);
	}

	@Override
	public Long findCount(T entity){
		// 匹配规则
		ExampleMatcher matcher = getMatcher();
		// 查询条件
		Query query = getQuery(entity, matcher);
		// 查询
		return mongoTemplate.count(query, entity.getClass());
	}

	@Override
	public List<T> findList(T entity, Integer from, Integer limit, List<JSONObject> orderBy){
		if (from == null || from < 0) {
			from = DEFAULT_FROM;
		}
		if (limit == null || limit < 0) {
			limit = DEFAULT_LIMIT;
		}
		// 匹配规则
		ExampleMatcher matcher = getMatcher();
		// 查询条件
		Query query = getQuery(entity, matcher);
		// 排序
		List<Order> orderList = orderList(orderBy);
		// 查询数量、排序
        query.skip(from).limit(limit).with(Sort.by(orderList));
        // 查询
        return (List<T>) mongoTemplate.find(query, entity.getClass());
	}

	@Override
	public Page<T> findPage(T entity, Integer pageNum, Integer pageSize, List<JSONObject> orderBy) {
		if (pageNum == null || pageNum <= 0) {
			pageNum = 1;
		}
		if (pageSize == null || pageSize <= 0) {
			pageSize = DEFAULT_PAGE_SIZE;
		}
		// 匹配规则
		ExampleMatcher matcher = getMatcher();
		// 条件
		Example<T> example = Example.of(entity, matcher);
		// 排序
		List<Order> orderList = orderList(orderBy);
		// 分页
		Sort sort = Sort.by(orderList);
		PageRequest pageable = PageRequest.of(pageNum - 1, pageSize, sort);
		// 查询
		return mongoRepository.findAll(example, pageable);
	}

	/**
	 * 获取排序规则列表
	 * @param orderBy	排序规则JSON  eg:[{"key":"name", "rule":"desc"}]
	 * @return java.util.List<org.springframework.data.domain.Sort.Order>
	 */
	public List<Order> orderList(List<JSONObject> orderBy){
		List<Order> orderList = new ArrayList<Order>();
		if (orderBy != null && orderBy.size() > 0) {
			for (JSONObject order : orderBy) {
				String property = order.getString(ORDER_BY_KEY);
				String direction = order.getString(ORDER_BY_RULE);
				direction = direction.startsWith("des") ? "desc" : direction;
				orderList.add(new Order(Direction.fromString(direction), property));
			}
		}
		return orderList;
	}

	/**
	 * 默认简单条件（复杂条件需重写此方法)
	 * @param entity	条件模板
	 * @param matcher	匹配规则
	 * @return Query	查询条件
	 */
	public Query getQuery(T entity, ExampleMatcher matcher) {
		Query query = new Query();
		query.addCriteria(new Criteria().alike(Example.of(entity, matcher)));
		return query;
	}
	
	/**
	 * 获取查询匹配规则（默认模糊匹配，基本数据有初始值问题，可重写此方法）
	 * @return ExampleMatcher	匹配规则
	 */
	public ExampleMatcher getMatcher() {
		return ExampleMatcher.matching()
				.withIgnoreCase(true)
				.withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
	}

}
