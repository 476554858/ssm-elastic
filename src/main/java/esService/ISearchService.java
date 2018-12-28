package esService;

import bean.Product;
import pageResult.PageResult;

import java.util.List;

/**
 * Created by Administrator on 2018/12/7.
 */
public interface ISearchService {

    /**
     * 创建索引
     * @param goodsId
     */
    void index(Long goodsId);

    /**
     * 删除索引
     * @param goodsId
     */
    public String remove(Long goodsId);

    /**
     * 查询
     * @param content
     * @return
     */
    List<Product> searchByContent(String content);

    /**
     * 分页查询
     * @param pageNum
     * @param pageSize
     * @param standardCode
     * @param name
     * @param dosageFormName
     * @param producerName
     * @param model
     * @return
     */
    PageResult searchPage(Integer pageNum,Integer pageSize,String standardCode,String name,
                          String dosageFormName,String producerName,String model);


    public void batchSave();
}
