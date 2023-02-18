package cn.crabapples.datasource;

import cn.crabapples.system.dao.MenusDAO;
import cn.crabapples.system.entity.SysUser;
import cn.crabapples.system.service.SystemUserService;
import cn.crabapples.test.service.UserServiceTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.criteria.*;
import java.util.ArrayList;
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
public class JpaTest {
    @Autowired
    UserServiceTest userServiceTest;
    @Autowired
    SystemUserService userService;

    @Autowired
    MenusDAO menusDAO;

    @Test
    public void jpaTest1() {
        List<SysUser> sql = userServiceTest.findBySQL("kitty");
        List<SysUser> hql = userServiceTest.findByHQL("kitty");
        log.info("sql查询结果:[{}]", sql);
        log.info("hql查询结果:[{}]", hql);
    }

    @Test
    public void jpaTest2() {
        List<SysUser> list = userServiceTest.findAll();
        log.info("查询结果:[{}]", list);
    }

    /**
     * 使用Example类查询
     */
    @Test
    public void jpaTest3() {
        SysUser sysUser = new SysUser();
        sysUser.setName("11111");
        // 两种创建方式都可以
        ExampleMatcher matcher1 = ExampleMatcher.matching()
                .withMatcher("name",
                        ExampleMatcher.GenericPropertyMatcher.of(ExampleMatcher.StringMatcher.CONTAINING));

        ExampleMatcher matcher2 = ExampleMatcher.matching()
                .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains());
        Example<SysUser> example1 = Example.of(sysUser, matcher1);
        Example<SysUser> example2 = Example.of(sysUser, matcher2);
        List<SysUser> list1 = userServiceTest.findAll(example1);
        List<SysUser> list2 = userServiceTest.findAll(example2);
        log.info("查询结果1:[{}]", list1);
        log.info("查询结果2:[{}]", list2);
    }

    @Test
    public void jpaTest4() {
        SysUser sysUser = new SysUser();
        sysUser.setName("11111");
//        Sort sort = new Sort(Sort.Direction.DESC,"createTime");
//        PageRequest pageRequest = new PageRequest(queryDTO.getPageIndex(), queryDTO.getPageSize(), sort);
//        Page<Environment> page = environmentRepository.findAll((root, query, cb) -> {
//            List<Predicate> predicateList = new ArrayList<>();
//            if(StringUtils.isNotBlank(queryDTO.getName())){
//                Predicate predicate = cb.conjunction();
//                predicate.getExpressions().add(cb.like(root.get("name"), "%"+queryDTO.getName().trim()+"%"));
//                predicateList.add(predicate);
//            }
//            return query.where(predicateList.toArray(new Predicate[predicateList.size()])).getRestriction();
//        }, pageRequest);
//        return page;

        Specification<SysUser> specification = new Specification<SysUser>() {

            @Override
            public Predicate toPredicate(Root<SysUser> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

                List<Predicate> predicateList = new ArrayList<>();
                Predicate predicate = builder.conjunction();
                predicate.getExpressions().add(builder.like(root.get("name"), "%" + sysUser.getName().trim() + "%"));
                predicateList.add(predicate);


                Path<Object> custName = root.get("custName");//客户名
                Path<Object> custAddress = root.get("custAddress");//地址
                //构造查询
                //1.构造客户名的精准匹配查询
                Predicate p1 = builder.equal(custName, "黑马程序员");//第一个参数，path（属性），第二个参数，属性的取值
                //2..构造所属行业的精准匹配查询
                Predicate p2 = builder.equal(custAddress, "hangz");
                //3.将多个查询条件组合到一起：组合（满足条件一并且满足条件二：与关系，满足条件一或满足条件二即可：或关系）
                Predicate and = builder.and(p1, p2);//以与的形式拼接多个查询条件
                // cb.or();//以或的形式拼接多个查询条件
                System.err.println(root);
                System.err.println(query);
                System.err.println(builder);

                return query.where(predicateList.toArray(new Predicate[predicateList.size()])).getRestriction();
            }
        };
        List<SysUser> list = userServiceTest.findAll(specification);
        log.info("查询结果:[{}]", list);
}


//    @Test
//    public void getAllUserTest() {
//        userService.findAll().forEach(e -> {
//            List<SysRoles> sysRoles = e.getRolesList();
//            sysRoles.forEach(t -> {
//                List<SysMenus> sysMenus = t.getSysMenus();
//                sysMenus.forEach(System.err::println);
//            });
//        });
//    }

}

