//package cn.crabapples.test.dao;
//
//import org.example.application.common.base.BaseDAO;
//import org.example.application.custom.dao.jpa.ReplayRepository;
//import org.example.application.custom.entity.*;
//import org.example.application.custom.form.ReplayForm;
//import org.springframework.data.jpa.domain.Specification;
//import org.springframework.stereotype.Component;
//import org.springframework.util.StringUtils;
//
//import javax.persistence.criteria.CriteriaBuilder;
//import javax.persistence.criteria.CriteriaQuery;
//import javax.persistence.criteria.Predicate;
//import javax.persistence.criteria.Root;
//import java.util.ArrayList;
//import java.util.List;
//
//@Component
//public class ReplayDAO extends BaseDAO<Replay, String> {
//    private final ReplayRepository repository;
//
//    public ReplayDAO(ReplayRepository repository) {
//        this.repository = repository;
//    }
//
//    public Replay getById(String id) {
//        return repository.findById(id).orElse(null);
//    }
//
//    public void removeById(String id) {
//        repository.deleteLogicById(id);
//    }
//
//    public Replay save(Replay entity) {
//        return repository.saveAndFlush(entity);
//    }
//
//    public List<Replay> getList(ReplayForm form) {
//        Specification<Replay> specification1 = new Specification<Replay>() {
//            @Override
//            public Predicate toPredicate(Root<Replay> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
//                Active active = form.getActive();
//                Resource resource = form.getResource();
//                Help help = form.getHelp();
//                Notice notice = form.getNotice();
//                LostAndFound laf = form.getLostAndFound();
//                //构造查询
//                //1.构造客户名的精准匹配查询，第一个参数，path（属性），第二个参数，属性的取值
//                Predicate p1 = builder.equal(root.get("active"), active);
//                Predicate p2 = builder.equal(root.get("resource"), resource);
//                Predicate p3 = builder.equal(root.get("help"), help);
//                Predicate p4 = builder.equal(root.get("notice"), notice);
//                Predicate p5 = builder.equal(root.get("lostAndFound"), laf);
//                //3.将多个查询条件组合到一起：组合（满足条件一并且满足条件二：与关系，满足条件一或满足条件二即可：或关系）
//                Predicate and = builder.or(p1, p2, p3, p4, p5);//以与的形式拼接多个查询条件
////                builder.or();//以或的形式拼接多个查询条件
//                return query.where(and).getRestriction();
//            }
//        };
//        Specification<Replay> specification2 = new Specification<Replay>() {
//            @Override
//            public Predicate toPredicate(Root<Replay> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
//                List<Predicate> predicateList = new ArrayList<>();
//                //构造查询
//                //1.构造客户名的精准匹配查询
//                Active active = form.getActive();
//                Resource resource = form.getResource();
//                Help help = form.getHelp();
//                Notice notice = form.getNotice();
//                LostAndFound laf = form.getLostAndFound();
//                if (!StringUtils.isEmpty(active)) {
//                    //第一个参数，path（属性），第二个参数，属性的取值
//                    predicateList.add(builder.equal(root.get("active"), active));
//                }
//                if (!StringUtils.isEmpty(resource)) {
//                    predicateList.add(builder.equal(root.get("resource"), resource));
//                }
//                if (!StringUtils.isEmpty(help)) {
//                    predicateList.add(builder.equal(root.get("help"), help));
//                }
//                if (!StringUtils.isEmpty(notice)) {
//                    predicateList.add(builder.equal(root.get("notice"), notice));
//                }
//                if (!StringUtils.isEmpty(laf)) {
//                    predicateList.add(builder.equal(root.get("lostAndFound"), laf));
//                }
//                return query.where(predicateList.toArray(new Predicate[0])).getRestriction();
//            }
//        };
//        Specification<Replay> specification3 = (root, query, cb) -> {
//            List<Predicate> predicateList = new ArrayList<>();
//            if (!StringUtils.isEmpty(form.getHelp())) {
//                Predicate predicate = cb.conjunction();
//                predicate.getExpressions().add(cb.equal(root.get("help"), form.getHelp()));
//                predicateList.add(predicate);
//            }
//            return query.where(predicateList.toArray(new Predicate[predicateList.size()])).getRestriction();
//        };
//        Specification<Replay> specification4 = new Specification<Replay>() {
//            @Override
//            public Predicate toPredicate(Root<Replay> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
//                Active active = form.getActive();
//                Resource resource = form.getResource();
//                Help help = form.getHelp();
//                Notice notice = form.getNotice();
//                LostAndFound laf = form.getLostAndFound();
//                //构造查询
//                //1.构造客户名的精准匹配查询，第一个参数，path（属性），第二个参数，属性的取值
//                Predicate p1 = builder.equal(root.get("active"), active);
//                Predicate p2 = builder.equal(root.get("resource"), resource);
//                Predicate p3 = builder.equal(root.get("help").get("id"), help.getId());
//                Predicate p4 = builder.equal(root.get("notice"), notice);
//                Predicate p5 = builder.equal(root.get("lostAndFound"), laf);
//                //3.将多个查询条件组合到一起：组合（满足条件一并且满足条件二：与关系，满足条件一或满足条件二即可：或关系）
//                Predicate and = builder.or(p1, p2, p3, p4, p5);//以与的形式拼接多个查询条件
////                builder.or();//以或的形式拼接多个查询条件
//                return query.where(and).getRestriction();
//            }
//        };
//
//        List<Replay> list1 = repository.findAll(specification1);
//        List<Replay> list2 = repository.findAll(specification2);
//        List<Replay> list3 = repository.findAll(specification3);
//        List<Replay> list4 = repository.findAll(specification4);
//
//        System.err.println(list1);
//        System.err.println(list2);
//        System.err.println(list3);
//        System.err.println(list4);
//        return list1;
//    }
//}
