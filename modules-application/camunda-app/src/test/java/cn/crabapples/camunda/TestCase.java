package cn.crabapples.camunda;


import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngineConfiguration;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.impl.history.HistoryLevel;
import org.junit.Test;

public class TestCase {
    @Test
    public void case_01() {
        // 1. 创建配置对象
        ProcessEngineConfiguration configuration = ProcessEngineConfiguration
                .createStandaloneProcessEngineConfiguration()
                .setJdbcUrl("jdbc:mysql://localhost:3306/camunda")
                .setJdbcUsername("root")
                .setJdbcPassword("root")
                .setJdbcDriver("com.mysql.cj.jdbc.Driver")
                .setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE)
                .setHistory(HistoryLevel.HISTORY_LEVEL_FULL.getName());
        // 2. 根据配置构建引擎
        ProcessEngine engine = configuration.buildProcessEngine();

        RepositoryService repositoryService = engine.getRepositoryService();

        System.out.println(engine);
        // 3. 测试完成后关闭引擎，释放资源
        engine.close();
    }
}
