package com.taotao.solrj;

import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

/**
 * Created by 李壮壮 on 2018/9/3.
 */
public class TestSolrCloud {
    @Test
    public void testSolrCloudAddDocument() throws Exception{
        CloudSolrServer cloudSolrServer = new CloudSolrServer("192.168.25.130:2181,192.168.25.130:2182,192.168.25.130:2183");
        cloudSolrServer.setDefaultCollection("collection2");
        SolrInputDocument solrInputDocument = new SolrInputDocument();
        solrInputDocument.addField("id", "test001");
        solrInputDocument.addField("item_title", "测试人lzz");
        cloudSolrServer.add(solrInputDocument);
        cloudSolrServer.commit();
    }
    @Test
    public void testSolrCloudDeleteDocument() throws Exception{
        CloudSolrServer cloudSolrServer = new CloudSolrServer("192.168.25.130:2181,192.168.25.130:2182,192.168.25.130:2183");
        cloudSolrServer.setDefaultCollection("collection2");
        cloudSolrServer.deleteById("test001");
        cloudSolrServer.commit();
    }
}
