package com.yx.test.solr;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * User: LiWenC
 * Date: 16-8-30
 */
public class SolrTest {
    private SolrServer server;
    private HttpSolrServer httpServer;
    private static final String DEFAULT_URL = "http://192.168.2.81:8983/solr/";

    @Before
    public void init() {
        server = new HttpSolrServer(DEFAULT_URL);
        httpServer = new HttpSolrServer(DEFAULT_URL);

    }

    @After
    public void destroy() {
        server = null;
        httpServer = null;
        System.runFinalization();
        System.gc();
    }

    public final void fail(Object o) {
        System.out.println(o);
    }

    @Test
    public void server() {
        fail(server);
        fail(httpServer);
    }


    @Test
    public void query() {
        try {
            SolrQuery params = new SolrQuery("中国");//通配符
            QueryResponse response = server.query(params);
            SolrDocumentList list = response.getResults();
            for (int i = 0; i < list.size(); i++) {
                SolrDocument document = list.get(i);
                System.out.println(document.getFieldValue("id") + "--------------------" + document.getFieldValue("name"));
            }
        } catch (SolrServerException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void queryBeans() {
        try {
            SolrQuery params = new SolrQuery("name_*");//通配符
            params.addSort("name", SolrQuery.ORDER.desc);
            QueryResponse response = server.query(params);
            List<Animal> animals = response.getBeans(Animal.class);
            for (Animal animal : animals) {
                System.out.println(animal);
            }
        } catch (SolrServerException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void delete() {
        try {
            server.deleteById("3");
            server.commit();
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void addBean() {
        try {
            Animal bean = new Animal("333", "中国");
            server.addBean(bean);
            UpdateResponse response = server.commit();
            System.out.println(response.getStatus());
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void addBeans() {
        try {
            List<Animal> beanList = new ArrayList<Animal>();
            for (int i = 0; i < 10; i++) {
                beanList.add(new Animal(String.valueOf(i), "name_" + i));
            }
            server.addBeans(beanList);
            server.commit();
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}