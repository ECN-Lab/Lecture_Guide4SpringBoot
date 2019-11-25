package com.ntsphere.ecn.basicweb.config;

import java.util.Properties;

import javax.naming.NamingException;
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
import org.springframework.jndi.JndiObjectFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@MapperScan(value="com.ntsphere.ecn.basicweb.repository2", sqlSessionFactoryRef="db2SqlSessionFactory")
@EnableTransactionManagement
public class DB2Config {
	@Value("${server.datasource.db2.jndi-name}") private String jndiName;
	@Value("${server.datasource.db2.mapper-locations}") private String mapperLocations;
	
	
	
	
	
	@Bean(name = "db2DataSource")
    public DataSource db2DataSource() throws IllegalArgumentException, NamingException {
	    JndiObjectFactoryBean bean = new JndiObjectFactoryBean();
	    bean.setJndiName(jndiName);
	    bean.setProxyInterface(DataSource.class);
	    bean.setLookupOnStartup(false);
	    bean.afterPropertiesSet();
	    return (DataSource)bean.getObject();
    }

	
    @Bean(name = "db2SqlSessionFactory")
    public SqlSessionFactory db2SqlSessionFactory(@Qualifier("db2DataSource") DataSource db2DataSource, ApplicationContext applicationContext) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(db2DataSource);
        sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources(mapperLocations));
        sqlSessionFactoryBean.setConfigLocation(applicationContext.getResource("classpath:/mybatis/mybatis-config.xml"));
        sqlSessionFactoryBean.setVfs(SpringBootVFS.class);
        
        
        Properties properties = new Properties();
        properties.put("mapUnderscoreToCamelCase", true);
        properties.put("jdbcTypeForNull", "NULL");
        sqlSessionFactoryBean.setConfigurationProperties(properties);
        
        return sqlSessionFactoryBean.getObject();
    }
    
    
    @Bean(name = "db2SqlSessionTemplate")
    public SqlSessionTemplate db2SqlSessionTemplate(SqlSessionFactory db2SqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(db2SqlSessionFactory);
    }
}
