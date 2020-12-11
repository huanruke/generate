package cn.digitlink.mysql.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @description 基础Bean
 * @company 北京数联领航网络科技有限公司 2020
 * @author yangwc
 * @date 20/12/10
 */
@Data
public class BaseBean implements Serializable {

	private static final long serialVersionUID = -1156506813413514947L;
	
	/** ID */
	private Integer id;

	/** 数据生成时间 */
	private Date createdAt;

	/** 数据生成时间 */
	private Date updatedAt;

}
