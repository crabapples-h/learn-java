//package cn.crabapples.common.mybatis.plugins.join;
//
//
//import cn.crabapples.system.sysUser.entity.SysUser;
//import com.baomidou.mybatisplus.annotation.TableName;
//import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
//import com.baomidou.mybatisplus.core.metadata.TableInfo;
//import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.ibatis.executor.statement.StatementHandler;
//import org.apache.ibatis.mapping.*;
//import org.apache.ibatis.plugin.*;
//import org.apache.ibatis.reflection.MetaObject;
//import org.apache.ibatis.reflection.SystemMetaObject;
//import org.apache.ibatis.session.Configuration;
//import org.apache.ibatis.session.ResultHandler;
//import org.apache.ibatis.type.ArrayTypeHandler;
//import org.springframework.stereotype.Component;
//
//import java.lang.reflect.Field;
//import java.lang.reflect.Method;
//import java.sql.Connection;
//import java.sql.Statement;
//import java.util.*;
//import java.util.stream.Collectors;
//
//import static org.apache.ibatis.type.JdbcType.ARRAY;
//
//
///**
// * StatementHandler SQL语句处理器
// */
//@Component
//@Intercepts({
//        @Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class}),
//        @Signature(type = StatementHandler.class, method = "query", args = {Statement.class, ResultHandler.class}),
//        @Signature(type = StatementHandler.class, method = "getBoundSql", args = {}),
//        @Signature(type = StatementHandler.class, method = "getParameterHandler", args = {}),
//})
//@Slf4j
//public class JoinInterceptor implements Interceptor {
//    public static void main(String[] args) {
//        Field[] fields = SysUser.class.getDeclaredFields();
//        List<String> collect = Arrays.stream(fields).map(Field::getName).collect(Collectors.toList());
//        System.err.println(collect);
//        String sql = "SELECT  id,username,password,name,mail,phone,avatar,age,gender,role_list,status, del_flag,tenant_id,create_time,update_time,create_by,update_by  FROM sys_user WHERE  del_flag=0";
//        String sb = "LEFT JOIN sys_user_roles  AS ur ON  sys_user.id =  ur.user_id ";
//        StringBuffer newSql = new StringBuffer(sql).insert(sql.indexOf("WHERE"), sb).append(" ").append("group by id");
//        System.err.println(newSql);
//
//    }
//
//    @Override
//    public Object intercept(Invocation invocation) throws Throwable {
//        log.info("\nStatement拦截器拦截开始");
//        StatementHandler target = (StatementHandler) invocation.getTarget();
//        Method method = invocation.getMethod();
//        Object[] args = invocation.getArgs();
//
//        String className = target.getClass().getName();
//        String methodName = method.getName();
//
//        if (methodName.equals("prepare")) {
//            log.info("\n拦截器类:[{}]\n方法:[{}]\n参数:[{}]", className, methodName, Arrays.toString(args));
//
//            MetaObject metaObject = SystemMetaObject.forObject(target);
//            // 获取Mapper信息
//            MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("delegate.mappedStatement");
//            String fullMapperId = mappedStatement.getId();
//            String mapperClass = fullMapperId.substring(0, fullMapperId.lastIndexOf("."));
//            String mapperMethod = fullMapperId.substring(fullMapperId.lastIndexOf(".") + 1);
//            log.info("\nMapperId: [{}]\nMapper类: [{}]\nMapper方法: [{}]", fullMapperId, mapperClass, mapperMethod);
//            List<ResultMap> resultMaps = mappedStatement.getResultMaps();
//            if (!resultMaps.isEmpty()) {
//                // 获取表信息
//                ResultMap resultMap = resultMaps.get(0);
//                log.info("\n返回结果集: [{}]", resultMap);
//                Class<?> tableClass = resultMap.getType();
//                TableInfo tableInfo = TableInfoHelper.getTableInfo(tableClass);
//                // 获取需要查询的字段
//                List<TableFieldInfo> fieldInfoList = tableInfo.getFieldList();
//                List<String> fieldNameList = fieldInfoList.stream().map(TableFieldInfo::getEl).collect(Collectors.toList());
//                Field[] fields = tableClass.getDeclaredFields();
//                TableName tableNameAnno = tableClass.getAnnotation(TableName.class);
//                String tableName = tableNameAnno.value();
//                List<String> selectFields = Arrays.stream(fields).map(Field::getName).collect(Collectors.toList());
//                log.info("\n查询主表名: [{}],返回结果集对象: [{}]\n返回结果集对象字段: [{}]\n", tableName, tableClass, selectFields);
//                BoundSql boundSql = target.getBoundSql();
//                String sql = boundSql.getSql();
//                log.info("\nSQL语句: [{}]", sql);
//                StringBuilder sb = new StringBuilder();
//                for (Field field : fields) {
//                    if (field.isAnnotationPresent(JoinField.class)) {
//                        JoinField annotation = field.getAnnotation(JoinField.class);
//                        if (fieldNameList.contains(field.getName())) {
//                            log.info("\n处理join字段: [{}]", field.getName());
//                            JoinType joinType = annotation.type();
//                            String joinTable = annotation.table();
//                            String tableAlias = annotation.alias().isEmpty() ? joinTable : annotation.alias();
//                            String selfKey = annotation.selfKey();
//                            String targetKey = annotation.targetKey();
//                            // 生成join语句
//                            sb.append(joinType.getValue()).append(" ")
//                                    .append(joinTable).append(" ")
//                                    .append(" AS ").append(tableAlias)
//                                    .append(" ON ").append(" ")
//                                    .append(tableName).append(".").append(selfKey)
//                                    .append(" = ").append(" ")
//                                    .append(tableAlias).append(".").append(targetKey)
//                                    .append(" ");
//                            // 替换原sql中join查询的字段
//                            String column = fieldInfoList.stream()
//                                    .filter(e -> e.getEl().equals(field.getName()))
//                                    .collect(Collectors.toList())
//                                    .get(0).getColumn();
//                            StringBuilder newColumn = new StringBuilder()
//                                    .append("GROUP_CONCAT(")
//                                    .append(tableAlias).append(".").append(annotation.selectFields())
//                                    .append(")").append(" AS ").append(column);
//                            sql = sql.replace(column, newColumn);
//                        }
//                    }
//                }
//                StringBuffer newSql = new StringBuffer(sql)
//                        .insert(sql.indexOf("WHERE"), sb)
//                        .append(" ").append("GROUP BY id");
//                log.info("\n新的SQL语句: [{}]", newSql);
//                // 替换原有SQL语句
//                Field sqlField = BoundSql.class.getDeclaredField("sql");
//                sqlField.setAccessible(true);
//                sqlField.set(boundSql, newSql.toString());
//                log.info("\nStatement拦截器拦截结束");
//                log.info("\n----------------------------\n");
//            }
//        }
//        return invocation.proceed();
//    }
//}
