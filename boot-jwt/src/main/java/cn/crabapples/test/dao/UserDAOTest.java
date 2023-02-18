package cn.crabapples.test.dao;

import cn.crabapples.common.base.BaseDAO;
import cn.crabapples.system.entity.SysMenus;
import cn.crabapples.system.entity.SysUser;
import cn.crabapples.test.dao.jpa.UserRepositoryTest;
import cn.crabapples.test.dao.mybatis.UserMapperTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * TODO 用户相关服务测试DAO
 *
 * @author Mr.He
 * 2022/11/27 0:20
 * pc-name mrhe
 */
@Component
public class UserDAOTest extends BaseDAO<SysUser, String> {
    private final UserRepositoryTest repository;
    private final UserMapperTest mapper;

    public UserDAOTest(UserRepositoryTest repository, UserMapperTest mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<SysUser> findByHQL(String name) {
        return repository.findByHQL(name);
    }

    public List<SysUser> findBySQL(String name) {
        return repository.findBySQL(name);
    }

    public List<SysUser> findAll() {
        return super.findAll(repository);
    }

    public List<SysUser> findAll(Example<SysUser> example) {
        return super.findAll(repository, example);
    }

    public List<SysUser> findAll(Specification<SysUser> specification) {
        return super.findAll(repository, specification);
    }

    public List<SysUser> findAllForExample() {
        SysUser user = new SysUser();
        ExampleMatcher matcher = ExampleMatcher.matching()
                //忽略大小写
                .withIgnoreCase()
                //忽略id和age字段
                .withIgnorePaths("id", "age")
                //忽略为 null 的的字段
                .withIgnoreNullValues()
                //包含为 null 的的字段
                .withIncludeNullValues()
                //对 null 字段的处理方式
                .withNullHandler(ExampleMatcher.NullHandler.IGNORE)
                //设置属性转换器 lambda 中 的 value 就是需要匹配的参数的值包裹了一层Optional
                //可以在 lambda 表达式中替换所传入的参数
                .withTransformer("username", value -> {
                    if (value.isPresent()) {
                        String username = value.get().toString();
                        return Optional.of(username);
                    }
                    return Optional.empty();
                })
                //字符串匹配方式 (默认为精确匹配)
                .withStringMatcher(ExampleMatcher.StringMatcher.DEFAULT)
                //对 username 字段模糊匹配  %{str}%
                .withMatcher("username", ExampleMatcher.GenericPropertyMatchers.contains())
                //对 username 字段模糊匹配(区分大小写)
                .withMatcher("username", ExampleMatcher.GenericPropertyMatchers.caseSensitive())
                //对 username 字段模糊匹配(以xxx开头) {str}%
                .withMatcher("username", ExampleMatcher.GenericPropertyMatchers.startsWith())
                //对 username 字段模糊匹配(以xxx结尾) %{str}
                .withMatcher("username", ExampleMatcher.GenericPropertyMatchers.endsWith())
                //对 username 字段模糊匹配(忽略大小写)
                .withMatcher("username", ExampleMatcher.GenericPropertyMatchers.ignoreCase())
                //对 username 字段精确匹配
                .withMatcher("username", ExampleMatcher.GenericPropertyMatchers.exact())
                //对 username 字段正则匹配
                .withMatcher("username", ExampleMatcher.GenericPropertyMatchers.regex())
                //对 username 字段默认匹配
                .withMatcher("username", ExampleMatcher.GenericPropertyMatchers.storeDefaultMatching());
        Example<SysUser> example = Example.of(user, matcher);
        return repository.findAll(example);
    }
}
