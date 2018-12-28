package pageResult;

import bean.Product;
import com.baomidou.mybatisplus.plugins.Page;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/12/8.
 */
public class PageResult {
    Integer page ;//当前页

    Long total;//总页数

    Long records;//总记录数

    List<Map<String,Object>> rows;//当前页数据

    public PageResult() {
    }

    public PageResult(Page page,List<Product> list) {
        this.page = page.getCurrent();
        this.total = page.getPages();
        this.records = page.getTotal();

        List<Map<String,Object>> listMap = new ArrayList<Map<String, Object>>();
        for(Product goods:list){
            Map<String,Object> map = new HashMap<String, Object>();
            map.put("id",goods.getId());
            map.put("cell",goods);
            listMap.add(map);
        }
        this.rows = listMap;

    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Long getRecords() {
        return records;
    }

    public void setRecords(Long records) {
        this.records = records;
    }

    public List<Map<String, Object>> getRows() {
        return rows;
    }

    public void setRows(List<Map<String, Object>> rows) {
        this.rows = rows;
    }
}
