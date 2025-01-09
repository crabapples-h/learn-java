package cn.crabapples.common.mybatisflex.mybatisplus;

import cn.crabapples.common.jwt.JwtTokenUtils;
import com.mybatisflex.core.tenant.TenantFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * TODO 多租户插件
 *
 * @author 张三
 * 2024-12-26 10:33
 * e-mail 123456789@qq.com
 * qq 123456789
 * pc-name 张三
 */
@Component
public class TenantHandler implements TenantFactory {
    @Resource
    private JwtTokenUtils jwtTokenUtils;


    @Override
    public Object[] getTenantIds() {
//        String tenantId = jwtTokenUtils.getDepartId();
        return null;
//        if (tenantId == null) {
//            return null;
//        }
//        return new Object[]{tenantId};
    }

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

}