package com.yx.test.solr;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

import java.io.IOException;

/**
 * User: NMY
 * Date: 16-8-30
 */

/**
 * 操作Solr服务端的索引库
 * <p>Title: SolrDao</p>
 * <p>Description: </p>
 * <p>Company:</p>
 *
 * @author 夏 杰
 * @version 1.0
 * @date 2016年1月25日下午3:44:43
 */
public class SolrServerTest {

    private static final String DEFAULT_URL = "http://115.28.100.160:8983/solr/";

    /**
     * 往索引库添加文档
     *
     * @throws IOException
     * @throws SolrServerException
     */
    @Test
    public void addDoc() throws SolrServerException, IOException {
        //这里可以从文件里读或数据库里读

        //构造一篇文档
        SolrInputDocument document = new SolrInputDocument();
        //往doc中添加字段,在客户端这边添加的字段必须在服务端中有过定义
        document.addField("id", "number001");
//        document.addField("title_ik", "浙大恒久远");
//        document.addField("content_ik", "软院永流传");

        //获得一个solr服务端的请求，去提交
        HttpSolrServer solrClient = new HttpSolrServer(DEFAULT_URL);
        solrClient.add(document);
        solrClient.commit();
    }

    /**
     * 根据id删除文档
     * 从索引库删除文档
     */
    @Test
    public void deleteDocumentById() throws Exception {
        HttpSolrServer server = new HttpSolrServer(DEFAULT_URL);
        //删除文档
        server.deleteById("num001");
        //提交修改
        server.commit();
    }

    //根据查询删除文档
    @Test
    public void deleteDocumentByQuery() throws Exception {
        HttpSolrServer server = new HttpSolrServer(DEFAULT_URL);
        //根据查询条件删除
        //server.deleteByQuery("*:*");
        //提交修改
        server.commit();
    }

    /**
     * 对索引库中的文档进行更新
     *
     * @throws SolrServerException
     */
    @Test
    public void query() throws SolrServerException {
        HttpSolrServer server = new HttpSolrServer(DEFAULT_URL);
        SolrQuery query = new SolrQuery();
        //给query设置一个主查询条件
        //query.set("q", "*:*");//查询所有

        query.set("q", "台灯");

        //给query增加过滤查询条件
        query.addFilterQuery("product_price:[0 TO 200]");

        //给query增加布尔过滤条件
        //query.addFilterQuery("-product_name:台灯");

        //给query设置默认搜索域
        query.set("df", "product_keywords");

        //设置返回结果的排序规则
        query.setSort("product_price", ORDER.desc);

        //设置分页参数
        query.setStart(0);
        query.setRows(20);//每一页多少值

        //设置高亮
        query.setHighlight(true);
        //设置高亮的字段
        query.addHighlightField("product_name");
        //设置高亮的样式
        query.setHighlightSimplePre("<em>");
        query.setHighlightSimplePost("</em>");
        QueryResponse response = server.query(query);
        //查询得到文档的集合
        SolrDocumentList solrDocumentList = response.getResults();
        System.out.println("查询结果的总数量：" + solrDocumentList.getNumFound());

        //遍历列表
        for (SolrDocument solrDocument : solrDocumentList) {
            System.out.println(solrDocument.get("id"));
            System.out.println(solrDocument.get("product_name"));
            System.out.println(solrDocument.get("product_price"));
            System.out.println(solrDocument.get("product_catalog_name"));
            System.out.println(solrDocument.get("product_picture"));
        }
    }

}