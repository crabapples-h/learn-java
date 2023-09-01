package cn.crabapples.mybatis.flex;

import cn.crabapples.system.dao.MenusDAO;
import cn.crabapples.system.entity.SysMenu;
import cn.crabapples.system.entity.SysRole;
import com.mybatisflex.core.query.QueryChain;
import com.mybatisflex.core.query.QueryColumn;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * TODO jpa测试类
 *
 * @author Mr.He
 * 2020/1/27 14:32
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name 29404
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("dev")
@Slf4j
public class MybatisFlexTest {
    @Autowired
    MenusDAO menusDAO;

    @Test
    public void case1() {
        List<SysMenu> menusTree = menusDAO.findMenusTree();
        System.out.println(menusTree);
    }

    /**
     * 关联查询需要使用APT功能或QueryColumn
     */
    @Test
    public void case2() {
        String sql = QueryChain.create()
                .select(
                        new QueryColumn("sys_role", "id").as("id"),
                        new QueryColumn("sys_role", "name").as("name"),
                        new QueryColumn("sys_menu", "*")
                ).from(SysRole.class)
                .leftJoin("sys_role_menus")
                .on(new QueryColumn("sys_role", "id").eq("role_id"))
                .toSQL();
        System.err.println(sql);
    }

}

