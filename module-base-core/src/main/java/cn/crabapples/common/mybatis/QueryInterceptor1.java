package cn.crabapples.common.mybatis;//package cn.crabapples.common.mybatis;
//
//
//import lombok.extern.slf4j.Slf4j;
//import org.apache.ibatis.cache.CacheKey;
//import org.apache.ibatis.executor.Executor;
//import org.apache.ibatis.executor.statement.StatementHandler;
//import org.apache.ibatis.mapping.BoundSql;
//import org.apache.ibatis.mapping.MappedStatement;
//import org.apache.ibatis.mapping.ParameterMapping;
//import org.apache.ibatis.plugin.*;
//import org.apache.ibatis.session.ResultHandler;
//import org.apache.ibatis.session.RowBounds;
//import org.springframework.stereotype.Component;
//
//import java.sql.Connection;
//import java.util.List;
//import java.util.Map;
//import java.util.Properties;
////  Executor
////  update	    执行增删改操作
////  query	    执行查询操作
////  commit	    提交事务
////  rollback	回滚事务
//
////  StatementHandler
////  prepare	        预处理 SQL 语句
////  parameterize	设置 SQL 参数
////  batch	        执行批处理
////  update	        执行更新操作
////  query	        执行查询操作
//
////  ParameterHandler
////  getParameterObject	获取参数对象
////  setParameters	    设置参数对象
//
////  ResultSetHandler
////  handleResultSets	    处理查询结果
////  handleOutputParameters	处理存储过程的输出参数
//@Component
////@Intercepts({@Signature(type = ParameterHandler.class, method = "setParameters", args = {PreparedStatement.class})})
//@Intercepts({@Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class,
//        CacheKey.class, BoundSql.class})})
//@Slf4j
//public class QueryInterceptor1 implements Interceptor {
//    @Override
//    public Object intercept(Invocation invocation) throws Throwable {
//        Executor handler = (Executor) invocation.getTarget();
//        // 获取 MappedStatement 和参数
//        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
//        Object parameterObject = invocation.getArgs()[1];
//
//        // 修改查询条件（以添加逻辑条件为例）
//        if (parameterObject instanceof Map) {
//            @SuppressWarnings("unchecked")
//            Map<String, Object> paramMap = (Map<String, Object>) parameterObject;
//            paramMap.put("extraCondition", "AND id = -10010");
//        }
//
//        // 调用原方法
//        return invocation.proceed();
//
//    }
//
//    @Override
//    public Object plugin(Object target) {
//        return Plugin.wrap(target, this);
//    }
//
//    @Override
//    public void setProperties(Properties properties) {
//        // 可选：读取配置文件中的属性
//    }
//}
