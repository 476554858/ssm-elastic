package controller;

import bean.Product;
import dao.ProductMapper;
import esService.ISearchService;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import pageResult.PageResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/12/7.
 */
@Controller
@RequestMapping("/es/")
public class ESController {

    @Autowired
    private ProductMapper goodsMapper;
    @Autowired
    private TransportClient client;
    @Autowired
    private ISearchService searchService;

    @RequestMapping("saveAll")
    @ResponseBody
    public String findOne(){
        try {
            long begin = System.currentTimeMillis();
            List<Long> list = new ArrayList<Long>();
            list.add(14l);
            list.add(15l);
            list.add(16l);
            List<Product> goodsList =  goodsMapper.selectBatchIds(list);
            for(Product goods:goodsList){
                searchService.index(goods.getId());
            }
            long end = System.currentTimeMillis();
            System.out.println("耗时："+(end-begin));
            return "成功";
        }catch (Exception e){
            e.printStackTrace();
            return "失败";
        }
    }

    @RequestMapping("delete/{id}")
    @ResponseBody
    public void deleteById(@PathVariable Long id){
        searchService.remove(id);
    }


    //查询接口
    @RequestMapping("search")
    public ResponseEntity get(@RequestParam(name = "id", defaultValue = "") String id) {

        GetResponse response = this.client.prepareGet("book", "novel", id).get();
        if (!response.isExists()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(response.getSource(), HttpStatus.OK);
    }


    @RequestMapping("getListGoods")
    @ResponseBody
    public List<Product> getListGoods(@RequestParam String content){
        return searchService.searchByContent(content);
    }

    /**
     *
     * @param page
     * @param rows
     * @param standardCode
     * @param name
     * @param dosageFormName
     * @param producerName
     * @param model
     * @return
     */
    @RequestMapping("getPageGoods")
    @ResponseBody
    public PageResult getPageGoods(@RequestParam(defaultValue = "1") Integer page,@RequestParam(defaultValue = "10")Integer rows,
    String standardCode,String name,String dosageFormName,String producerName,String model){
        return searchService.searchPage(page,rows,standardCode,name,dosageFormName,producerName,model);
    }




    @RequestMapping("batchSave")
    @ResponseBody
    public String getPageGoods(){
        long begin = System.currentTimeMillis();
        searchService.batchSave();
        long end = System.currentTimeMillis();
        System.out.println("耗时:::"+(end-begin));
        return null;
    }


    @RequestMapping("test")
    @ResponseBody
    public String test(){
        String a = "123456789";
        String b = a.replace(""," ");
        System.out.println(b);

        return null;
    }

}
