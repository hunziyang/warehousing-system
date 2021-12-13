package com.yang.security.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.logging.stdout.StdOutImpl;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.time.LocalDateTime;

@Configuration
@MapperScan(basePackages = "com.yang.security.mapper", sqlSessionTemplateRef = "securitySqlSessionTemplate")
public class SecurityDataSourcesConfig {

    @Bean(name = "securityDataSource")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.security")
    public DataSource dataSource() {
        return new DruidDataSource();
    }

    @Bean(name = "securitySqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("securityDataSource") DataSource dataSource) throws Exception {
        MybatisSqlSessionFactoryBean bean = new MybatisSqlSessionFactoryBean();
        // 添加 Mybatis-plus 全局配置
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setMetaObjectHandler(new MyMetaObjectHandler());
        bean.setGlobalConfig(globalConfig);
        // 设置 Mybatis-plus 基础配置
        MybatisConfiguration configuration = new MybatisConfiguration();
        configuration.setLogImpl(StdOutImpl.class);
        // 设置插件
        bean.setPlugins(mybatisPlusInterceptor());
        bean.setConfiguration(configuration);
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/security/*.xml"));
        return bean.getObject();
    }

    @Bean(name = "securityTransactionManager")
    public DataSourceTransactionManager transactionManager(@Qualifier("securityDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "securitySqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("securitySqlSessionFactory") SqlSessionFactory sqlSessionFactory)
            throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }


    /**
     * 开启mybatis-plus 插件
     * @return
     */
    public MybatisPlusInterceptor mybatisPlusInterceptor(){
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 添加乐观锁插件
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        return interceptor;
    }

    /**
     * 自动插入相应数据
     */
    private class MyMetaObjectHandler implements MetaObjectHandler {
        @Override
        public void insertFill(MetaObject metaObject) {
            this.strictInsertFill(metaObject, "createdTime", LocalDateTime.class, LocalDateTime.now());
            this.strictInsertFill(metaObject, "updatedTime", LocalDateTime.class, LocalDateTime.now());
        }
        @Override
        public void updateFill(MetaObject metaObject) {
            this.strictUpdateFill(metaObject, "updatedTime", LocalDateTime.class, LocalDateTime.now());
        }
    }

}
