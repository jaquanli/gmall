package com.atguigu.gmall.list.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.gmall.bean.SkuLsInfo;
import com.atguigu.gmall.bean.SkuLsParam;
import com.atguigu.gmall.service.ListService;
import io.searchbox.client.JestClient;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.highlight.HighlightBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ListServiceImpl implements ListService {

    @Autowired
    private JestClient jestClient;

    @Override
    public List<SkuLsInfo> searchSkuLsInfo(SkuLsParam skuLsParam) {

        //定义List用于封装对象
        List<SkuLsInfo> skuLsInfos = new ArrayList<>();

        //定义El库
        String elDB = "gmall";
        //定义El表
        String elTable = "SkuLsInfo";

        //获取查询语句
        String dsl = getDSLByFilterAndMust(skuLsParam);
        System.err.println(dsl);
        if (StringUtils.isNotBlank(dsl)){
            //生成查询对象
            Search search = new Search.Builder(dsl).addIndex(elDB).addType(elTable).build();
            if (search != null) {
                try {
                    //执行操作，获得结果对象
                    SearchResult searchResult = jestClient.execute(search);
                    if (searchResult != null) {
                        //获得结果的List对象
                        List<SearchResult.Hit<SkuLsInfo, Void>> hits = searchResult.getHits(SkuLsInfo.class);
                        if (hits != null) {
                            //遍历单个对象
                            for (SearchResult.Hit<SkuLsInfo, Void> hit : hits) {
                                if (hit != null) {
                                    //获取真正的结果值
                                    SkuLsInfo skuLsInfo = hit.source;

                                    Map<String, List<String>> highlight = hit.highlight;
                                    if (highlight != null) {
                                        List<String> skuNames = highlight.get("skuName");
                                        if (skuNames != null) {
                                            for (String skuName : skuNames) {
                                                skuLsInfo.setSkuName(skuName);
                                            }
                                        }
                                    }
                                    //封装
                                    skuLsInfos.add(skuLsInfo);
                                }
                            }
                            //关闭连接,这里不能关闭连接，因为再次查询时会报无法获取连接的错误
                            //jestClient.close();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }



        //返回
        return skuLsInfos;
    }

    /**
     * 生成查询语句，传入参数并每页20个进行获得结果
     *
     * @return 返回DSL语句
     */
    private String getDSLByFilterAndMust(SkuLsParam skuLsParam) {
        //创建组成search语句的查询对象
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //创建包裹搜索和过滤语句的对象
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        //获取传入的值
        Long catalog3Id = skuLsParam.getCatalog3Id();
        String keyword = skuLsParam.getKeyword();
        String[] valueIds = skuLsParam.getValueId();
        //当catalog3Id不为空时，说明是过滤传入的，拼装过滤属性
        if (catalog3Id != null) {
            TermQueryBuilder termQuery = new TermQueryBuilder("catalog3Id", catalog3Id);
            boolQueryBuilder.filter(termQuery);
        }
        if (StringUtils.isNotBlank(keyword)) {
            MatchQueryBuilder matchQuery = new MatchQueryBuilder("skuName", keyword);
            //设置分词并列查询
            matchQuery.operator(MatchQueryBuilder.Operator.AND);
            boolQueryBuilder.must(matchQuery);
        }
        if (valueIds != null) {
            for (String attrValueId : valueIds) {
                TermQueryBuilder termQuery = new TermQueryBuilder("skuAttrValueList.valueId", attrValueId);
                boolQueryBuilder.filter(termQuery);
            }
        }

        //最后拼装dsl语句
        //拼装bool中的语句,设置查询参数
        searchSourceBuilder.query(boolQueryBuilder);

        //拼装分页的size,设置分页的数量
        searchSourceBuilder.size(skuLsParam.getPageSize());
        //拼装分页的form,设置分页从哪开始
        searchSourceBuilder.from(skuLsParam.getPageNo());

        //设置高亮属性
        HighlightBuilder highlight = new HighlightBuilder();
        //设置要高亮的字段
        highlight.field("skuName");
        //设置高亮的字段的加入css修饰前缀
        highlight.preTags("<span style='color:red;font-weight:900'>");
        // //设置高亮的字段的加入css修饰后缀
        highlight.postTags("</span>");
        //拼装highlight
        searchSourceBuilder.highlight(highlight);
        //返回语句
        return searchSourceBuilder.toString();

    }
}
