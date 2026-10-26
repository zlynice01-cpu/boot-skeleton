package com.zly.skeleton.mybatisplus.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.zly.skeleton.mybatisplus.handler.AuditUserProvider;
import com.zly.skeleton.mybatisplus.handler.DataObjectHandler;
import com.zly.skeleton.mybatisplus.inteceptor.SqlBeautyInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.zly.skeleton.**.mapper")
public class DatasourceConfiguration
{

    @Bean
    @ConditionalOnProperty(name = {"sql.beauty.show"}, havingValue = "true", matchIfMissing = true)
    public SqlBeautyInterceptor sqlBeautyInterceptor() {
        return new SqlBeautyInterceptor();
    }

    /**
     * 自动填充处理器；当前操作人由上层模块通过 {@link AuditUserProvider} 提供，
     * 无实现时注入 null，单元测试或非 Web 场景不会启动失败
     */
    @Bean
    @ConditionalOnMissingBean
    public DataObjectHandler myMetaObjectHandler(ObjectProvider<AuditUserProvider> auditUserProvider)
    {
        return new DataObjectHandler(auditUserProvider.getIfAvailable());
    }

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor()
    {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        //乐观锁插件
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        //防全表更新与删除插件
        interceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());
        //分页插件
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }

}
