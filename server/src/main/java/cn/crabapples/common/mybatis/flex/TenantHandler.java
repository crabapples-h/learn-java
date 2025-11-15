//package cn.crabapples.common.mybatis.flex;
//
//import cn.crabapples.system.sysUser.entity.SysUser;
//import cn.crabapples.system.sysUser.service.SystemUserService;
//import com.mybatisflex.core.tenant.TenantFactory;
//import org.apache.commons.lang.StringUtils;
//import org.springframework.context.annotation.Lazy;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.Resource;
//
///**
// * TODO 多租户插件
// *
// * @author Ms.He
// * 2025-01-10 09:45
// * e-mail crabapples.cn@gmail.com
// * qq 294046317
// * pc-name mshe
// */
//@Component
//public class TenantHandler implements TenantFactory {
////    @Resource
////    private JwtTokenUtils jwtTokenUtils;
//    @Resource
//    @Lazy
//    private SystemUserService userService;
//
//
//    @Override
//    public Object[] getTenantIds() {
////        String userId = jwtTokenUtils.getUserId();
//        SysUser userInfo = userService.getUserInfo();
//        String tenantId = userInfo.getTenantId();
//        if (StringUtils.isEmpty(tenantId))
//            return null;
//        else
//            return tenantId.split(",");
//    }
//
//
////    @Override
////    public boolean ignoreTable(String tableName) {
////        LinkedList<String> ignoreTables = new LinkedList<>();
////        ignoreTables.add("file_info"); // 文件上传表
////        ignoreTables.add("sys_user"); // 系统用户表
//////        ignoreTables.add("sys_role"); // 系统角色表
//////        ignoreTables.add("sys_menu"); // 系统菜单表
////        ignoreTables.add("sys_user_roles"); // 系统用户角色表
////        ignoreTables.add("sys_role_menus"); // 系统角色菜单表
////        ignoreTables.add("sys_dict"); // 系统字典表
////        ignoreTables.add("sys_dict_item"); // 系统字典项表
////        ignoreTables.add("sys_depart"); // 系统部门(租户)表
////        return ignoreTables.contains(tableName);
////    }
//
//}
