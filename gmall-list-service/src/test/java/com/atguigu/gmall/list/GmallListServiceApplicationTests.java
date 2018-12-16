package com.atguigu.gmall.list;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.gmall.bean.SkuInfo;
import com.atguigu.gmall.bean.SkuLsInfo;
import com.atguigu.gmall.service.SkuService;
import io.searchbox.client.JestClient;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import org.apache.commons.beanutils.BeanUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GmallListServiceApplicationTests {

    @Autowired
    JestClient jestClient;

    @Reference
    private SkuService skuService;

    @Test
    public void contextLoads() throws IOException {
        //定义一个查询语句
        String query = "{\n" +
                "  \"query\": {\n" +
                "    \"match\": {\n" +
                "      \"name\": \"红海行动\"\n" +
                "    }\n" +
                "  }\n" +
                "}\n";
        //将查询语句封装到查询对象，其中添加好实例名（indexName）和表名（indexType）
        Search searchObject = new Search.Builder(query).addIndex("movie_chn").addType("movie").build();
        //通过获得了连接的jestClient查询数据，将查询对象封装到查询客户端，返回的是一个查询到的结果对象
        SearchResult searchResult = jestClient.execute(searchObject);
        //获得查询结果
        List<SearchResult.Hit<HashMap, Void>> hits = searchResult.getHits(HashMap.class);
        //将查询结果遍历
        for (SearchResult.Hit<HashMap, Void> hit : hits) {
            //每一个hit就是其中一个结果对象，source是其结果值
            HashMap source = hit.source;
            //打印结果值
            System.err.println(source);
        }
        //查询完毕之后关闭连接
        jestClient.close();
    }

    @Test
    public void importElasticSearch() throws InvocationTargetException, IllegalAccessException, IOException {
        //1****.先去数据库中查出带有SkuInfo和SkuAttrValue的数据，并且是根据三级id查询
        Long catalog3Id = Long.parseLong("61");
        //查数据库
        List<SkuInfo> skuInfos = skuService.queryskuInfoAndSkuAttrValueByCatalog3Id(catalog3Id);
        //创建skuLsInfoList方便封装SkuInfo查询回来的多个值
        List<SkuLsInfo> skuLsInfos = new ArrayList<>();
        //遍历数据库查询返回的值
        //2****.将查到的结果封装到elasticSearch对应的结构的对象中，用BeanUtils进行封装
        for (SkuInfo skuInfo : skuInfos) {
            //创建SkuLsInfo对象方便转换
            SkuLsInfo skuLsInfo = new SkuLsInfo();
            //将SkuInfo数据库查询来值，封装到skyLsInfo
            BeanUtils.copyProperties(skuLsInfo,skuInfo);
            //将每一个对象封装到skuLSInfoList
            skuLsInfos.add(skuLsInfo);
        }
        //3****.进行导入到elasticSearch中
        //定义要操作的EL数据库是哪个
        String elDB = "gmall";
        //定义要操作的El数据库中的表是哪个
        String elTable = "SkuLsInfo";

        //遍历封装好的skuLSInfoList
        for (SkuLsInfo skuLsInfo : skuLsInfos) {
            Long skuLsInfoId = skuLsInfo.getId();
            Index indexBuild = new Index.Builder(skuLsInfo).index(elDB).type(elTable).id(String.valueOf(skuLsInfoId)).build();
            jestClient.execute  (indexBuild);
        }
    }

}
