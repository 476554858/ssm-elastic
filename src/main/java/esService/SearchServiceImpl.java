package esService;

import bean.Product;
import bean.ProductIndexKey;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dao.ProductMapper;
import indexBean.ProductIndexTemplate;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pageResult.PageResult;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/12/7.
 */
@Service
public class SearchServiceImpl implements ISearchService{

    private static final String INDEX_NAME= "product";

    private static final String INDEX_TYPE = "product";

    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private TransportClient client;

    @Override
    public void index(Long productId) {
        Product product = productMapper.selectById(productId);

        ProductIndexTemplate productIndexTemplate = new ProductIndexTemplate();
        modelMapper.map(product,productIndexTemplate);

        GetResponse response = this.client.prepareGet(INDEX_NAME,INDEX_TYPE,productId+"").get();
        boolean success;
        if(response.isExists()){
            success = update(productIndexTemplate);
        }else {
            success = create(productIndexTemplate);
        }

        if(success){
            System.out.println(productId);
        }
    }

    @Override
    public String remove(Long productId) {
        DeleteResponse response = this.client.prepareDelete(INDEX_NAME,INDEX_TYPE,productId+"").get();
         return response.getResult().toString();
    }

    @Override
    public List<Product> searchByContent(String content) {
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();

        boolQuery.should(
                QueryBuilders.multiMatchQuery(content,
                        ProductIndexKey.STANDARDCODE,
                        ProductIndexKey.NAME,
                        ProductIndexKey.DOSAGEFORMNAME,
                        ProductIndexKey.PRODUCERNAME,
                        ProductIndexKey.MODEL
                ));

        SearchRequestBuilder requestBuilder = this.client.prepareSearch(INDEX_NAME)
                .setTypes(INDEX_TYPE)
                .setQuery(boolQuery);
        List<Long> productIds = new ArrayList<Long>();
        SearchResponse response = requestBuilder.get();
        if(response.status() != RestStatus.OK){
            return new ArrayList<Product>();
        }

        for(SearchHit hit:response.getHits()){
            productIds.add(Long.valueOf(String.valueOf(hit.getSource().get("id"))));
        }
        if(productIds.isEmpty()){
            return new ArrayList<Product>();
        }
        return getproductByIds(productIds);
    }

    @Override
    public PageResult searchPage(Integer pageNum,Integer pageSize,String standardCode,String name,
                                 String dosageFormName,String producerName,String model) {
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        //设置权重和精度精度
        if(StringUtils.isNotBlank(standardCode)){
            boolQuery.must(
                    QueryBuilders.matchQuery(ProductIndexKey.STANDARDCODE, standardCode.replace("", " ")).boost(0.25f).minimumShouldMatch("85%")
            );
        }
        if(StringUtils.isNotBlank(name)){
            boolQuery.must(
                    QueryBuilders.matchQuery(ProductIndexKey.NAME, name).boost(0.25f).minimumShouldMatch("55%")
            );
        }
        if(StringUtils.isNotBlank(dosageFormName)){
            boolQuery.must(
                    QueryBuilders.matchQuery(ProductIndexKey.DOSAGEFORMNAME, dosageFormName).boost(0.15f).minimumShouldMatch("50%")
            );
        }
        if(StringUtils.isNotBlank(producerName)){
            boolQuery.must(
                    QueryBuilders.matchQuery(ProductIndexKey.PRODUCERNAME, producerName).boost(0.15f).minimumShouldMatch("47%")
            );
        }
        if(StringUtils.isNotBlank(model)){
            boolQuery.must(
                    QueryBuilders.matchQuery(ProductIndexKey.MODEL, model).boost(0.2f).minimumShouldMatch("100%")
            );
        }

        SearchRequestBuilder requestBuilder = this.client.prepareSearch(INDEX_NAME)
                .setTypes(INDEX_TYPE)
                .setQuery(boolQuery)
                .setSize(pageSize)//一页多少条
                .setFrom((pageNum - 1) * pageSize)
                .setFetchSource(ProductIndexKey.PRODUCTID,null);
        List<Long> productIds = new ArrayList<Long>();
        SearchResponse response = requestBuilder.get();

        if(response.status() != RestStatus.OK){
            return emptyResult();
        }

        for(SearchHit hit:response.getHits()){
            productIds.add(Long.valueOf(String.valueOf(hit.getSource().get(ProductIndexKey.PRODUCTID))));
        }
        if(productIds.isEmpty()){
            return emptyResult();
        }
        List<Product> productList = getproductByIds(productIds);
        return parseResult(productList, response, pageNum, pageSize);
    }

    private PageResult emptyResult(){
        PageResult pr = new PageResult();
        pr.setPage(1);
        pr.setTotal(1l);
        pr.setRecords(0l);
        pr.setRows(new ArrayList<Map<String, Object>>());
        return pr;
    }

    private PageResult parseResult(List<Product> productList,SearchResponse response,Integer pageNum,Integer pageSize){
        PageResult pr = new PageResult();
        pr.setPage(pageNum);
        Long totalHits = response.getHits().getTotalHits();
        pr.setTotal(new BigDecimal(Math.floor(totalHits/pageSize)).longValue()+1);

        pr.setRecords(totalHits);

        List<Map<String,Object>> listMap = new ArrayList<Map<String, Object>>();
        for(Product product:productList){
            Map<String,Object> map = new HashMap<String, Object>();
            map.put("id",product.getId());
            map.put("cell",product);
            listMap.add(map);
        }
        pr.setRows(listMap);
        return pr;
    }

    private List<Product> getproductByIds(List<Long> productIds){
        List<Product> productList = productMapper.selectBatchIds(productIds);
        //为了调成es查出来的顺序
        Map<Long, Product> map = new HashMap<Long, Product>();
        for(Product product:productList){
            map.put(product.getId(),product);
        }
        List<Product> productList2 = new ArrayList<Product>();
        for(Long id:productIds){
            productList2.add(map.get(id));
        }
        return productList2;
    }


    public PageResult getPageResult(List<Long> idList){
        Page<Product> page = new Page<Product>();
        List<Product> listproduct = productMapper.selectPage(page, new EntityWrapper<Product>()
                .in("id", idList));

        return new PageResult(page,listproduct);
    }


    private boolean create(ProductIndexTemplate productIndexTemplate){
            Map<String,Object> map = new HashMap<String, Object>();
            map.put(ProductIndexKey.STANDARDCODE,productIndexTemplate.getStandardCode());
            map.put(ProductIndexKey.NAME,productIndexTemplate.getName());
            map.put(ProductIndexKey.DOSAGEFORMNAME,productIndexTemplate.getDosageFormName());
            map.put(ProductIndexKey.PRODUCTID,productIndexTemplate.getId());
            map.put(ProductIndexKey.MODEL,productIndexTemplate.getModel());
            IndexResponse response =  this.client.prepareIndex(INDEX_NAME, INDEX_TYPE, productIndexTemplate.getId() + "")
                    .setSource(map).get();
            if(response.status() == RestStatus.CREATED){
                return true;
            }else{
                return false;
            }
    }
    private boolean update( ProductIndexTemplate productIndexTemplate){
        try {
            UpdateResponse response =  this.client.prepareUpdate(INDEX_NAME, INDEX_TYPE,
                    productIndexTemplate.getId()+"")
                    .setDoc(objectMapper.writeValueAsBytes(productIndexTemplate),
                            XContentType.JSON).get();
            if(response.status() == RestStatus.OK){
                return true;
            }else{
                return false;
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void batchSave(){
        List<Product> productList = productMapper.selectList(null);
        //每10万条插入一次，避免内存溢出
        int k = 100000;
        int j = 0;
        List<Product> tempList = new ArrayList<Product>();

        for(Product product:productList){
            tempList.add(product);
            j++;
            if(j%k == 0){
                esBulkSave(tempList);
                tempList.clear();
            }
        }

        if(!tempList.isEmpty()){
            esBulkSave(tempList);
        }
    }

    //批量插入es数据
    public void esBulkSave(List<Product> list){
        BulkRequestBuilder bulkRequestBuilder = client.prepareBulk();
        for(Product product:list){
            Map<String,Object> map = new HashMap<String, Object>();
            map.put(ProductIndexKey.PRODUCTID,product.getId());
            map.put(ProductIndexKey.STANDARDCODE,product.getStandardCode()==null?null:product.getStandardCode().replace(""," "));
            map.put(ProductIndexKey.NAME,product.getName());
            map.put(ProductIndexKey.DOSAGEFORMNAME,product.getDosageFormName());
            map.put(ProductIndexKey.PRODUCERNAME,product.getProducerName());
            map.put(ProductIndexKey.MODEL,product.getModel());

            bulkRequestBuilder.add(client.prepareIndex(INDEX_NAME,INDEX_TYPE,product.getId()+"").setSource(map));
        }
        bulkRequestBuilder.execute().actionGet();
    }
}
