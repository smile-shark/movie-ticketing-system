package com.movie.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.github.pagehelper.PageInterceptor;
import org.apache.ibatis.logging.stdout.StdOutImpl;
import org.apache.ibatis.session.AutoMappingBehavior;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

@Configuration
@MapperScan("com.movie.mapper")
@PropertySource("classpath:mybatis-config.properties")
public class MybatisConfig {
    @Value("${druid.driverClassName}")
    private String driverClassName;
    @Value("${druid.url}")
    private String url;
    @Value("${druid.username}")
    private String username;
    @Value("${druid.password}")
    private String password;

    @Bean
    public DataSource dataSource(){
        DruidDataSource druidDataSource = new DruidDataSource() {{
            setDriverClassName(driverClassName);
            setUrl(url);
        }};
        druidDataSource.setUsername(username);
        druidDataSource.setPassword(password);
        return druidDataSource;
    }
    @Bean
    public SqlSessionFactoryBean sqlSessionFactoryBean(DataSource dataSource){
        return new SqlSessionFactoryBean(){{
           setTypeAliasesPackage("com.movie.entity");
           setDataSource(dataSource);
           setConfiguration(new org.apache.ibatis.session.Configuration(){{
               setMapUnderscoreToCamelCase(true);
               setAutoMappingBehavior(AutoMappingBehavior.FULL);
               setPlugins(
                       new PageInterceptor()
               );
//               setLogImpl(StdOutImpl.class);
           }});
        }};
    }

}
