package cn.crabapples.common.mybatis;


import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.*;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Properties;
//  Executor
//  update	    执行增删改操作
//  query	    执行查询操作
//  commit	    提交事务
//  rollback	回滚事务

//  StatementHandler
//  prepare	        预处理 SQL 语句
//  parameterize	设置 SQL 参数
//  batch	        执行批处理
//  update	        执行更新操作
//  query	        执行查询操作

//  ParameterHandler
//  getParameterObject	获取参数对象
//  setParameters	    设置参数对象

//  ResultSetHandler
//  handleResultSets	    处理查询结果
//  handleOutputParameters	处理存储过程的输出参数
@Component
//@Intercepts({@Signature(type = ParameterHandler.class, method = "setParameters", args = {PreparedStatement.class})})
@Intercepts({@Signature(type = ParameterHandler.class, method = "setParameters", args = {PreparedStatement.class})})
@Slf4j
public class QueryInterceptor implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        ParameterHandler handler = (ParameterHandler) invocation.getTarget();

        System.err.println(handler);
        PreparedStatement ps = (PreparedStatement) invocation.getArgs()[0];
        System.err.println(ps);
//        handler.setParameters(ps);
//        Object[] args = invocation.getArgs();
//        MappedStatement mappedStatement = (MappedStatement) args[0];
//        Object object = args[1];
//        RowBounds rowBounds = (RowBounds) args[2];
//        ResultHandler resultHandler = (ResultHandler)args[3];
//        Transaction transaction = executor.getTransaction();
//        Object parameterObject = parameterHandler.getParameterObject();
//        System.err.println(handler);
//        Object parameterObject = handler.getBoundSql().getParameterObject();
//        System.err.println(parameterObject);
//
//        BoundSql boundSql = handler.getBoundSql();
//        String sql = boundSql.getSql() + " and id = -10010";
//
//        Field sqlField = boundSql.getClass().getDeclaredField("sql");
//        sqlField.setAccessible(true);
//        sqlField.set(boundSql, sql);
//
//        // 使用反射设置修改后的 SQL
//        Field field = handler.getClass().getDeclaredField("boundSql");
//        field.setAccessible(true);
//        field.set(handler, boundSql);

//        System.err.println(args);
//        System.err.println(transaction);
        //第一种，性能高
        // if(parameterObject instanceof BaseModel){
        //  BaseModel baseModel = (BaseModel) parameterObject;
        //  baseModel.setLastUpdateBy(LocalUserUtil.getLocalUser().getNickName());
        // }
        //第二种使用反射处理，扒光撕开
//        Field lastUpdateBy = ReflectUtil.getField(parameterObject.getClass(), "lastUpdateBy");
//        if (lastUpdateBy != null) {
//            ReflectUtil.setFieldValue(parameterObject, lastUpdateBy, LocalUserUtil.getLocalUser().getNickName());
//        }
        return invocation.proceed();

    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        // 可选：读取配置文件中的属性
    }


//    /**
//     * 自定义 BoundSql，替换 SQL 内容
//     */
//    private static class CustomBoundSql extends BoundSql {
//        private final String newSql;
//
//        public CustomBoundSql(BoundSql boundSql, String newSql) {
//            super(boundSql.getConfiguration(), newSql, boundSql.getParameterMappings(), boundSql.getParameterObject());
//            this.newSql = newSql;
//        }
//
//        @Override
//        public String getSql() {
//            return newSql;
//        }
//    }
}