package demo.mybatis;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
//import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Join查询拦截器
 */
@Slf4j
@Intercepts({
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})
})
public class JoinQueryInterceptor implements Interceptor {

    private static final Map<String, Boolean> JOIN_QUERY_CACHE = new ConcurrentHashMap<>();

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        log.info("mybatis 拦截器");
        Object[] args = invocation.getArgs();
        MappedStatement ms = (MappedStatement) args[0];
        Object parameter = args[1];

//        // 检查是否需要处理Join查询
//        if (shouldHandleJoinQuery(ms, parameter)) {
//            return handleJoinQuery(invocation, ms, parameter);
//        }

        return invocation.proceed();
    }
//
//    private boolean shouldHandleJoinQuery(MappedStatement ms, Object parameter) {
//        String methodId = ms.getId();
//
//        // 从缓存中检查
//        if (JOIN_QUERY_CACHE.containsKey(methodId)) {
//            return JOIN_QUERY_CACHE.get(methodId);
//        }
//
//        try {
//            // 解析Mapper接口和方法
//            String className = methodId.substring(0, methodId.lastIndexOf("."));
//            String methodName = methodId.substring(methodId.lastIndexOf(".") + 1);
//
//            Class<?> mapperClass = Class.forName(className);
//            Method[] methods = mapperClass.getDeclaredMethods();
//
//            for (Method method : methods) {
//                if (method.getName().equals(methodName) && method.isAnnotationPresent(JoinQuery.class)) {
//                    JOIN_QUERY_CACHE.put(methodId, true);
//                    return true;
//                }
//            }
//
//            JOIN_QUERY_CACHE.put(methodId, false);
//            return false;
//        } catch (Exception e) {
//            return false;
//        }
//    }
//
//    private Object handleJoinQuery(Invocation invocation, MappedStatement ms, Object parameter) throws Throwable {
//        // 获取原始SQL
//        BoundSql boundSql = ms.getBoundSql(parameter);
//        String originalSql = boundSql.getSql();
//
//        // 构建Join SQL
//        String joinSql = buildJoinSql(originalSql, ms, parameter);
//
//        // 创建新的MappedStatement
//        MappedStatement newMs = createNewMappedStatement(ms, joinSql);
//
//        // 替换参数
//        Object[] newArgs = Arrays.copyOf(invocation.getArgs(), invocation.getArgs().length);
//        newArgs[0] = newMs;
//
//        // 执行查询
//        return invocation.proceed();
//    }
//
//    private String buildJoinSql(String originalSql, MappedStatement ms, Object parameter) {
//        StringBuilder sqlBuilder = new StringBuilder();
//
//        // 解析主查询表
//        String mainTable = extractMainTable(originalSql);
//
//        // 获取Join配置
//        List<JoinConfig> joinConfigs = parseJoinConfigs(ms, parameter);
//
//        // 构建SELECT字段
//        sqlBuilder.append("SELECT ");
//        sqlBuilder.append(buildSelectFields(mainTable, joinConfigs));
//        sqlBuilder.append(" FROM ");
//        sqlBuilder.append(originalSql);
//
//        // 添加JOIN语句
//        for (JoinConfig config : joinConfigs) {
//            sqlBuilder.append(" ").append(config.getJoinClause());
//        }
//
//        return sqlBuilder.toString();
//    }
//
//    private List<JoinConfig> parseJoinConfigs(MappedStatement ms, Object parameter) {
//        List<JoinConfig> configs = new ArrayList<>();
//
//        try {
//            String methodId = ms.getId();
//            String className = methodId.substring(0, methodId.lastIndexOf("."));
//            String methodName = methodId.substring(methodId.lastIndexOf(".") + 1);
//
//            Class<?> mapperClass = Class.forName(className);
//            Method method = Arrays.stream(mapperClass.getDeclaredMethods())
//                    .filter(m -> m.getName().equals(methodName))
//                    .findFirst()
//                    .orElse(null);
//
//            if (method != null && method.isAnnotationPresent(JoinQuery.class)) {
//                JoinQuery joinQuery = method.getAnnotation(JoinQuery.class);
//                Class<?> entityClass = getEntityClassFromMapper(mapperClass);
//
//                for (String fieldName : joinQuery.value()) {
//                    Field field = ReflectionUtils.findField(entityClass, fieldName);
//                    if (field != null && field.isAnnotationPresent(JoinField.class)) {
//                        JoinField joinField = field.getAnnotation(JoinField.class);
//                        configs.add(new JoinConfig(joinField, fieldName));
//                    }
//                }
//            }
//        } catch (Exception e) {
//            // 处理异常
//        }
//
//        return configs;
//    }
//
//    // 其他辅助方法...
//    private String extractMainTable(String sql) {
//        // 简化实现，实际需要更复杂的SQL解析
//        return sql.contains("from") ?
//                sql.substring(sql.indexOf("from") + 5, sql.indexOf(" ", sql.indexOf("from") + 5)) :
//                "unknown";
//    }
//
//    private String buildSelectFields(String mainTable, List<JoinConfig> joinConfigs) {
//        // 构建SELECT字段列表
//        StringBuilder fields = new StringBuilder();
//        fields.append(mainTable).append(".*");
//
//        for (JoinConfig config : joinConfigs) {
//            String alias = config.getAlias();
//            for (String field : config.getSelectFields()) {
//                fields.append(", ").append(alias).append(".").append(field)
//                        .append(" as ").append(alias).append("_").append(field);
//            }
//        }
//
//        return fields.toString();
//    }
//
//    private MappedStatement createNewMappedStatement(MappedStatement ms, String sql) {
//        // 创建新的MappedStatement
//        BoundSql boundSql = ms.getBoundSql(null);
//        SqlSource newSqlSource = new BoundSqlSqlSource(boundSql, sql);
//
//        MappedStatement.Builder builder = new MappedStatement.Builder(
//                ms.getConfiguration(), ms.getId(), newSqlSource, ms.getSqlCommandType()
//        );
//
//        // 复制其他属性
//        builder.resource(ms.getResource());
//        builder.fetchSize(ms.getFetchSize());
//        builder.statementType(ms.getStatementType());
//        builder.keyGenerator(ms.getKeyGenerator());
//        builder.keyProperty(arrayToString(ms.getKeyProperties()));
//        builder.keyColumn(arrayToString(ms.getKeyColumns()));
//        builder.databaseId(ms.getDatabaseId());
//        builder.lang(ms.getLang());
//        builder.resultOrdered(ms.isResultOrdered());
//        builder.resultSets(arrayToString(ms.getResultSets()));
//        builder.timeout(ms.getTimeout());
//        builder.parameterMap(ms.getParameterMap());
//        builder.resultMaps(ms.getResultMaps());
//        builder.flushCacheRequired(ms.isFlushCacheRequired());
//        builder.useCache(ms.isUseCache());
//        builder.cache(ms.getCache());
//
//        return builder.build();
//    }
//
//    private String arrayToString(String[] array) {
//        return array == null ? null : String.join(",", array);
//    }
//
//    // 内部配置类
//    private static class JoinConfig {
//        private final JoinField joinField;
//        private final String fieldName;
//
//        public JoinConfig(JoinField joinField, String fieldName) {
//            this.joinField = joinField;
//            this.fieldName = fieldName;
//        }
//
//        public String getJoinClause() {
//            String alias = getAlias();
//            return String.format("%s %s %s ON %s.%s = %s.%s",
//                    joinField.type().getValue(),
//                    joinField.table(),
//                    alias,
//                    alias,
//                    joinField.primaryKey(),
//                    "main", // 主表别名
//                    joinField.foreignKey());
//        }
//
//        public String getAlias() {
//            return joinField.alias().isEmpty() ?
//                    "t_" + fieldName : joinField.alias();
//        }
//
//        public String[] getSelectFields() {
//            return joinField.selectFields();
//        }
//    }
//
//    // 自定义SqlSource
//    private static class BoundSqlSqlSource implements SqlSource {
//        private final BoundSql boundSql;
//        private final String sql;
//
//        public BoundSqlSqlSource(BoundSql boundSql, String sql) {
//            this.boundSql = boundSql;
//            this.sql = sql;
//        }
//
//        @Override
//        public BoundSql getBoundSql(Object parameterObject) {
//            return new BoundSql(
//                    boundSql.getConfiguration(),
//                    sql,
//                    boundSql.getParameterMappings(),
//                    parameterObject
//            );
//        }
//    }
//
//    // 获取Mapper对应的实体类（简化实现）
//    private Class<?> getEntityClassFromMapper(Class<?> mapperClass) {
//        // 实际实现需要解析Mapper的泛型参数
//        return Object.class;
//    }
}
