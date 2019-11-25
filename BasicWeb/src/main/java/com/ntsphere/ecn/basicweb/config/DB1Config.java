package com.ntsphere.ecn.basicweb.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
@MapperScan("com.ntsphere.ecn.basicweb.repository1")
@EnableTransactionManagement
public class DB1Config {
	@Value("${server.datasource.db1.driverClassName}") private String driverClassName;
	@Value("${server.datasource.db1.url}") private String url;
	@Value("${server.datasource.db1.username}") private String username;
	@Value("${server.datasource.db1.password}") private String password;
	@Value("${server.datasource.db1.mapper-locations}") private String mapperLocations;
	
	
	
	
	
	@Bean(name = "db1DataSource")
    @Primary
    public DataSource db1DataSource() {
		HikariDataSource dataSource = new HikariDataSource();
		dataSource.setDriverClassName(driverClassName);
		dataSource.setJdbcUrl(url);
		dataSource.setUsername(username);
		dataSource.setPassword(password);
		
		return dataSource;
    }

	
    @Bean(name = "db1SqlSessionFactory")
    @Primary
    public SqlSessionFactory db1SqlSessionFactory(@Qualifier("db1DataSource") DataSource db1DataSource, ApplicationContext applicationContext) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(db1DataSource);
        sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources(mapperLocations));
        sqlSessionFactoryBean.setConfigLocation(applicationContext.getResource("classpath:/mybatis/mybatis-config.xml"));
        sqlSessionFactoryBean.setVfs(SpringBootVFS.class);
        
        
        Properties properties = new Properties();
        properties.put("mapUnderscoreToCamelCase", true);
        properties.put("jdbcTypeForNull", "NULL");
        sqlSessionFactoryBean.setConfigurationProperties(properties);
        
        return sqlSessionFactoryBean.getObject();
    }
    
    
    @Bean(name = "db1SqlSessionTemplate")
    @Primary
    public SqlSessionTemplate db1SqlSessionTemplate(SqlSessionFactory db1SqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(db1SqlSessionFactory);
    }
}
