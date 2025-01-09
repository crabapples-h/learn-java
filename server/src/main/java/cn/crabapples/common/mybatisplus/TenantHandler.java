package cn.crabapples.common.mybatisplus;
//
//import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
//import net.sf.jsqlparser.expression.Expression;
//import net.sf.jsqlparser.expression.LongValue;
//import org.example.common.jwt.JwtTokenUtils;
//import org.springframework.stereotype.Component;
//
//import java.util.LinkedList;
//
/// **
// * TODO 多租户插件
// *
// * @author 张三
// * 2024-12-26 10:33
// * e-mail 123456789@qq.com
// * qq 123456789
// * pc-name 张三
// */
//@Component
//public class TenantHandler implements TenantLineHandler {
//    private final JwtTokenUtils jwtTokenUtils;
//
//    public TenantHandler(JwtTokenUtils jwtTokenUtils) {
//        this.jwtTokenUtils = jwtTokenUtils;
//    }
//
//    @Override
//    public Expression getTenantId() {
//        String tenantId = jwtTokenUtils.getExpand();
//        if (tenantId == null) {
//            tenantId = "-1";
//        }
//        // 返回租户ID的表达式，LongValue 是 JSQLParser 中表示 bigint 类型的 class
//        return new LongValue(tenantId);
//    }
//
//    @Override
//    public String getTenantIdColumn() {
//        return "depart_id";
//    }
//
//    @Override
//    public boolean ignoreTable(String tableName) {
//        LinkedList<String> ignoreTables = new LinkedList<>();
//        ignoreTables.add("file_info"); // 文件上传表
//        ignoreTables.add("sys_user"); // 系统用户表
////        ignoreTables.add("sys_role"); // 系统角色表
////        ignoreTables.add("sys_menu"); // 系统菜单表
//        ignoreTables.add("sys_user_roles"); // 系统用户角色表
//        ignoreTables.add("sys_role_menus"); // 系统角色菜单表
//        ignoreTables.add("sys_dict"); // 系统字典表
//        ignoreTables.add("sys_dict_item"); // 系统字典项表
//        ignoreTables.add("sys_depart"); // 系统部门(租户)表
//        return ignoreTables.contains(tableName);
//    }
//
//}