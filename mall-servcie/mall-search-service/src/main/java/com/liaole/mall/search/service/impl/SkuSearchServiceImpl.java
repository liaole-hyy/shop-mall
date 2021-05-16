package com.liaole.mall.search.service.impl;

import com.alibaba.fastjson.JSON;
import com.liaole.mall.search.mapper.SkuSearchMapper;
import com.liaole.mall.search.model.SkuEs;
import com.liaole.mall.search.service.SkuSearchService;
import com.liaole.mall.search.util.HighlightResultMapper;
import com.liaole.mall.util.PageInfo;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
public class SkuSearchServiceImpl implements SkuSearchService {

    @Autowired
    private SkuSearchMapper skuSearchMapper;

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    /**
     * 导入单个es
     * @param skuEs
     */
    @Override
    public void add(SkuEs skuEs) {
        //属性转换
        String skuAttribute = skuEs.getSkuAttribute();
        if( !StringUtils.isEmpty(skuAttribute) ){
            skuEs.setAttrMap(JSON.parseObject(skuAttribute , Map.class));
        }
        skuSearchMapper.save(skuEs);
    }

    /**
     *  根据id删除
     * @param id
     */
    @Override
    public void del(String id) {
        skuSearchMapper.deleteById(id);
    }

    /**
     *  商品搜索
     * @param searchMap
     * @return
     */
    @Override
    public Map<String, Object> search(Map<String, Object> searchMap) {
       //条件封装
        NativeSearchQueryBuilder queryBuilder = queryBuilder(searchMap);

        //分组查询实现 -品牌、分类、属性
        group(queryBuilder,searchMap);

        //1.设置高亮信息   关键词前（后）面的标签、设置高亮域
        HighlightBuilder.Field field = new HighlightBuilder
                .Field("name")  //根据指定的域进行高亮查询
                .preTags("<span style=\"color:red;\">")     //关键词高亮前缀
                .postTags("</span>")   //高亮关键词后缀
                .fragmentSize(100);     //碎片长度
        queryBuilder.withHighlightFields(field);

        //执行搜索
        //Page<SkuEs> result = skuSearchMapper.search(queryBuilder.build());
        //AggregatedPage<SkuEs> result = (AggregatedPage<SkuEs>) skuSearchMapper.search(queryBuilder.build());
        AggregatedPage<SkuEs> result = elasticsearchRestTemplate.queryForPage(queryBuilder.build(), SkuEs.class,new HighlightResultMapper());

        //获取结果集：集合列表、总记录数
        Map<String,Object> resultMap = new HashMap<String,Object>();
        //分组数据
        parseGroup(result.getAggregations(),resultMap);
        //动态属性解析
        attrParse(resultMap);
        //结果集
        List<SkuEs> list = result.getContent();
        //中记录数
        long totalElements = result.getTotalElements();

        //创建分页对象
        int currentpage = queryBuilder.build().getPageable().getPageNumber()+1;

        PageInfo pageInfo = new PageInfo(result.getTotalElements(),currentpage, 5);
        resultMap.put("list",list);
        resultMap.put("pageInfo",pageInfo);

        return resultMap;
    }

    /****
     * 将属性信息合并成Map对象
     */
    public void attrParse(Map<String,Object> searchMap){
        //先获取attrmaps
        Object attrmaps = searchMap.get("attrmaps");
        if(attrmaps!=null){
            //集合数据
            List<String> groupList= (List<String>) attrmaps;

            //定义一个集合Map<String,Set<String>>,存储所有汇总数据
            Map<String, Set<String>> allMaps = new HashMap<String,Set<String>>();

            //循环集合
            for (String attr : groupList) {
                Map<String,String> attrMap = JSON.parseObject(attr,Map.class);

                for (Map.Entry<String, String> entry : attrMap.entrySet()) {
                    //获取每条记录,将记录转成Map   就业薪资    学习费用
                    String key = entry.getKey();
                    Set<String> values = allMaps.get(key);
                    if(values==null){
                        values = new HashSet<String>();
                    }
                    values.add(entry.getValue());
                    //覆盖之前的数据
                    allMaps.put(key,values);
                }
            }
            //覆盖之前的attrmaps
            searchMap.put("attrmaps",allMaps);
        }
    }

    /**
     * 取出所有分组数据
     * @param aggregations
     * @return
     */
    public void parseGroup(Aggregations aggregations,Map<String,Object> resultMap) {
        if(aggregations!=null){
            for (Aggregation aggregation : aggregations) {
                //强转ParsedStringTerms
                ParsedStringTerms terms = (ParsedStringTerms) aggregation;

                //循环结果集对象
                List<String> values = new ArrayList<String>();
                for (Terms.Bucket bucket : terms.getBuckets()) {
                    values.add(bucket.getKeyAsString());
                }
                //名字
                String key = aggregation.getName();
                resultMap.put(key,values);
            }
        }
    }

    /**
     * 分组查询
     *
     */
    public void group(NativeSearchQueryBuilder builder,Map<String,Object> searchMap){
        //前端没有传入分类参数的时候查询分类集合作为搜索条件
        if(StringUtils.isEmpty(searchMap.get("category"))){
            //分类分组
            builder.addAggregation(AggregationBuilders.
                    terms("categoryList")
                    .field("categoryName").size(100));
        }
        //前端没有传入品牌参数的时候查询品牌集合作为搜索条件
        if(StringUtils.isEmpty(searchMap.get("brand"))){
            builder.addAggregation(
                    AggregationBuilders
                            .terms("brandList")//别名，类似Map的key
                            .field("brandName")//根据brandName域进行分组
                            .size(100)     //分组结果显示100个
            );
        }
        //属性分组查询
       builder.addAggregation(
                AggregationBuilders
                        .terms("attrmaps")//别名，类似Map的key
                        .field("skuAttribute")//根据skuAttribute域进行分组
                        .size(100000)      //分组结果显示100000个
        );
    }


    /**
     *  条件组装
     */
    public NativeSearchQueryBuilder queryBuilder(Map<String ,Object> searchMap){
        //QueryBuidler构建
        NativeSearchQueryBuilder queryBuilder =new NativeSearchQueryBuilder();

        //组合查询对象
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        //条件判断
        if( searchMap != null && searchMap.size() > 0){
            //关键词
            Object keywords = searchMap.get("keywords");
            if( !StringUtils.isEmpty(keywords)){
                //queryBuilder.withQuery(QueryBuilders.termQuery("name",keywords.toString()));
                boolQueryBuilder.must(QueryBuilders.termQuery("name",keywords.toString()));
            }

            //分类查询
            Object category = searchMap.get("category");
            if(!StringUtils.isEmpty(category)){
                boolQueryBuilder.must(QueryBuilders.termQuery("categoryName",category.toString()));
            }

            //品牌查询
            Object brand = searchMap.get("brand");
            if(!StringUtils.isEmpty(brand)){
                boolQueryBuilder.must(QueryBuilders.termQuery("brandName",brand.toString()));
            }

            //价格区间查询  price=0-500元  500-1000元  1000元以上
            Object price = searchMap.get("price");
            if(!StringUtils.isEmpty(price)){
                //价格区间
                String[] prices = price.toString().replace("元","").replace("以上","").split("-");
                //price>x
                boolQueryBuilder.must(QueryBuilders.rangeQuery("price").gt(Integer.valueOf(prices[0])));
                //price<=y
                if(prices.length==2){
                    boolQueryBuilder.must(QueryBuilders.rangeQuery("price").lte(Integer.valueOf(prices[1])));
                }
            }

            //动态属性查询
            for (Map.Entry<String, Object> entry : searchMap.entrySet()) {
                //以attr_开始，动态属性  attr_网络：移动5G
                if(entry.getKey().startsWith("attr_")){
                    String key = "attrMap."+entry.getKey().replaceFirst("attr_","")+".keyword";
                    boolQueryBuilder.must(QueryBuilders.termQuery(key,entry.getValue().toString()));
                }
            }

            //排序
            Object sfield = searchMap.get("sfield");
            Object sm = searchMap.get("sm");
            if(!StringUtils.isEmpty(sfield) && !StringUtils.isEmpty(sm)){
                queryBuilder.withSort(
                        SortBuilders.fieldSort(sfield.toString())   //指定排序域
                                .order(SortOrder.valueOf(sm.toString()))    //排序方式
                );
            }

        }
        //分页查询
        queryBuilder.withPageable(PageRequest.of(currentPage(searchMap),5));
        return  queryBuilder;
    }

    /***
     * 分页参数
     */
    public int currentPage(Map<String,Object> searchMap){
        try {
            Object page = searchMap.get("page");
            return Integer.valueOf(page.toString())-1;  //从0开始
        } catch (Exception e) {
            return  0;
        }
    }


}

