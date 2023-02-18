package cn.crabapples.common.elasticsearch;//package cn.crabapples.common.utils.elasticsearch;
//
//import org.elasticsearch.action.index.IndexRequest;
//import org.elasticsearch.action.index.IndexResponse;
//import org.elasticsearch.action.search.SearchRequest;
//import org.elasticsearch.client.RequestOptions;
//import org.elasticsearch.client.RestHighLevelClient;
//import org.elasticsearch.client.indices.CreateIndexRequest;
//import org.elasticsearch.client.indices.CreateIndexResponse;
//import org.elasticsearch.client.indices.GetIndexRequest;
//import org.elasticsearch.search.SearchHits;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//import java.util.Map;
//
///**
// * TODO
// *
// * @author Mr.He
// * 2021/4/10 15:33
// * e-mail crabapples.cn@gmail.com
// * qq 294046317
// * pc-name mrhe
// */
//@Component
//public class ElasticSearchUtils {
//    private final RestHighLevelClient client;
//
//    public ElasticSearchUtils(ElasticSearchClientFactory elasticSearchClientFactory) {
//        client = elasticSearchClientFactory.getEsClient();
//    }
//
//    public SearchHits findAll(String indexName) throws IOException {
//        SearchRequest request = new SearchRequest(indexName);
//        return client.search(request, RequestOptions.DEFAULT).getHits();
//    }
//
//    public IndexResponse insert(String indexName, Map<String, Object> dataMap) throws IOException {
//        IndexRequest request = new IndexRequest(indexName).source(indexName, dataMap);
//        return client.index(request, RequestOptions.DEFAULT);
//    }
//
//    public void createIndex(String indexName) throws IOException {
//        GetIndexRequest request = new GetIndexRequest(indexName);
//        boolean exists = client.indices().exists(request, RequestOptions.DEFAULT);
//        if (!exists) {
//            CreateIndexRequest createRequest = new CreateIndexRequest(indexName);
//            CreateIndexResponse response = client.indices().create(createRequest, RequestOptions.DEFAULT);
//        }
//    }
//}
