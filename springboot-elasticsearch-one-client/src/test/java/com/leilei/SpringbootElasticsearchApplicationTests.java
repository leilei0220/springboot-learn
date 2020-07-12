package com.leilei;

import com.alibaba.fastjson.JSON;
import com.leilei.entity.Student;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
class SpringbootElasticsearchApplicationTests {
    @Autowired
    RestHighLevelClient highLevelClient;

    private static final String LEI_INDEX = "lei_springboot";


    /**
     * 创建索引
     *
     * @throws IOException
     */
    @Test
    void contextLoads() throws IOException {
        CreateIndexRequest indexRequest = new CreateIndexRequest(LEI_INDEX);
        CreateIndexResponse createIndexResponse = highLevelClient.indices()
                .create(indexRequest, RequestOptions.DEFAULT);
        System.out.println(createIndexResponse);
    }

    /**
     * 判断当前索引库是否存在
     *
     * @throws IOException
     */
    @Test
    void tst() throws IOException {
        GetIndexRequest lei_springboot = new GetIndexRequest(LEI_INDEX);
        boolean exists = highLevelClient.indices().exists(lei_springboot, RequestOptions.DEFAULT);
        System.out.println(exists);
    }

    /**
     * 删除索引库
     *
     * @throws IOException
     */
    @Test
    void test() throws IOException {
        DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest(LEI_INDEX);
        AcknowledgedResponse delete = highLevelClient.indices().delete(deleteIndexRequest, RequestOptions.DEFAULT);
        System.out.println(delete.isAcknowledged());
    }

    /**
     * 判断文档是否存在
     */
    @Test
    void testdoc() throws IOException {
        GetRequest getIndexRequest = new GetRequest(LEI_INDEX, "1");
        boolean exists = highLevelClient.exists(getIndexRequest, RequestOptions.DEFAULT);
        System.out.println(exists);
    }

    /**
     * 插入文档
     */
    @Test
    void test3() throws IOException {
        IndexRequest indexRequest = new IndexRequest(LEI_INDEX);
        indexRequest.id("1");
        indexRequest.source(JSON.toJSONString(new Student("李明", "明明", 12)), XContentType.JSON);
        IndexResponse index = highLevelClient.index(indexRequest, RequestOptions.DEFAULT);
        System.out.println(index.status());
        System.out.println(index.toString());
    }


    /**
     * 修改文档
     */
    @Test
    void testUpdateDocument() throws IOException {
        UpdateRequest updateRequest = new UpdateRequest(LEI_INDEX, "1");
        updateRequest.doc(JSON.toJSONString(new Student("是个屁的李明", "明明", 13)), XContentType.JSON);
        UpdateResponse update = highLevelClient.update(updateRequest, RequestOptions.DEFAULT);
        System.out.println(update.toString());
    }

    /**
     * 查询文档
     */
    @Test
    void testGet() throws IOException {
        GetRequest getRequest = new GetRequest(LEI_INDEX, "1");
        GetResponse documentFields = highLevelClient.get(getRequest, RequestOptions.DEFAULT);
        //{"_index":"lei_springboot","_type":"_doc","_id":"1","_version":2,"_seq_no":1,"_primary_term":1,"found":true,"_source":{"age":13,"nick":"明明","username":"是个屁的李明"}}
        System.out.println(documentFields);
    }

    /**
     * 删除文档
     */
    @Test
    void testDeleteDocument() throws IOException {
        DeleteRequest deleteRequest = new DeleteRequest(LEI_INDEX, "1");
        DeleteResponse delete = highLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
        System.out.println(delete.status());
    }

    /**
     * 批量操作
     */
    @Test
    void testBeach() throws IOException {
        BulkRequest bulkRequest = new BulkRequest();
        bulkRequest.timeout("30s");
        List<Student> students = Arrays.asList(new Student("ad", "三三", 133),
                new Student("lqwead", "44", 1334),
                new Student("王五", "55", 13345),
                new Student("赵六", "66", 13456),
                new Student("马七", "77", 134567),
                new Student("陆八", "8", 1345678),
                new Student("八久", "9", 131),
                new Student("马六", "qwe", 1),
                new Student("马六", "ma", 0),
                new Student("马六", "qe", 0),
                new Student("马六", "qwe", 0),
                new Student("马六", "e", 0)
        );
        for (int i = 0; i < students.size(); i++) {
            bulkRequest.add(
                    new IndexRequest(LEI_INDEX)
                            .id(String.valueOf(i + 1))
                            .source(JSON.toJSONString(students.get(i)), XContentType.JSON)
            );
            BulkResponse bulk = highLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
            System.out.println(bulk);
        }
    }

    /**
     * 高级查询 精确匹配 高亮 分页
     */
    @Test
    void testsuperquery() throws IOException {
        SearchRequest searchRequest = new SearchRequest(LEI_INDEX);
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("age", 0);
        sourceBuilder.query(termQueryBuilder);
        sourceBuilder.from(1);
        sourceBuilder.size(3);
        // 设置高亮显示
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.requireFieldMatch(false);
        highlightBuilder.field("username");
        highlightBuilder.preTags("<span style=\"color:red\">");
        highlightBuilder.postTags("</span>");
        sourceBuilder.highlighter(highlightBuilder);
        searchRequest.source(sourceBuilder);
        SearchResponse response = highLevelClient.search(searchRequest, RequestOptions.DEFAULT);
    }


}