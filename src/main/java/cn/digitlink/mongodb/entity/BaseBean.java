package cn.digitlink.mongodb.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Date;

/**
 * @description 基础Bean
 * @company 北京数联领航网络科技有限公司 2020
 * @author yangwc
 * @date 20/12/11
 */
@Data
public class BaseBean implements Serializable {

	private static final long serialVersionUID = -1156506813413514947L;
	
	/** ID */
	@Id
	private String id;
	
	/** 乐观锁 */
	@Version
	@JsonIgnore
	@Field("locked_version")
	private Integer lockedVersion = 0;

	/** 逻辑删除标记 true数据有效、false数据无效 */
	@JsonIgnore
	@Field("is_active")
	private Boolean isActive = true;
	
	/** 数据生成时间 */
	@Field("created_at")
	@JsonProperty("created_at")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createdAt;
	
	/** 数据更新时间 */
	@Field("updated_at")
	@JsonProperty("updated_at")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date updatedAt;
	
}
