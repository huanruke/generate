package cn.digitlink;

import com.spring4all.mongodb.EnableMongoPlus;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @description 启动类
 * @company 北京数联领航网络科技有限公司 2020
 * @author yangwc
 * @date 20/12/10
 */
@EnableMongoPlus
@MapperScan("cn.digitlink.generate.dao")
@SpringBootApplication
public class GenerateApplication {

    public static void main(String[] args) {
        SpringApplication.run(GenerateApplication.class, args);
    }

}
