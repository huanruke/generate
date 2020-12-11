package cn.digitlink;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.event.EventListener;
import org.springframework.data.mapping.context.MappingContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.data.mongodb.core.index.IndexOperations;
import org.springframework.data.mongodb.core.index.IndexResolver;
import org.springframework.data.mongodb.core.index.MongoPersistentEntityIndexResolver;
import org.springframework.data.mongodb.core.mapping.BasicMongoPersistentEntity;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

/**
 * @description mongodb配置
 * @company 北京数联领航网络科技有限公司 2020
 * @author: yangwc
 * @date: 2020/08/17
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class MongoConfiguration implements InitializingBean {

    private final MongoTemplate mongoTemplate;

    private final MongoConverter mongoConverter;

    @Lazy
    private final MappingMongoConverter mappingMongoConverter;

    @Override
    public void afterPropertiesSet() {
        // 去除表中_class字段
        mappingMongoConverter.setTypeMapper(new DefaultMongoTypeMapper(null));
    }

    @EventListener(ApplicationReadyEvent.class)
    public void initIndicesAfterStartup() {
        // 手动创建mongodb索引（启动时自动创建表和相关索引）
        MappingContext mappingContext = this.mongoConverter.getMappingContext();
        if (mappingContext instanceof MongoMappingContext) {
            MongoMappingContext mongoMappingContext = (MongoMappingContext) mappingContext;
            for (BasicMongoPersistentEntity<?> persistentEntity : mongoMappingContext.getPersistentEntities()) {
                Class clazz = persistentEntity.getType();
                if (clazz.isAnnotationPresent(Document.class)) {
                    IndexResolver resolver = new MongoPersistentEntityIndexResolver(mongoMappingContext);
                    IndexOperations indexOps = mongoTemplate.indexOps(clazz);
                    resolver.resolveIndexFor(clazz).forEach(indexOps::ensureIndex);
                }
            }
            log.debug("Completed init mongodb index...");
        }
    }

}
