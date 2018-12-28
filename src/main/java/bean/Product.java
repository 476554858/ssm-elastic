package bean;

import com.baomidou.mybatisplus.annotations.TableName;


/** 商品 */
@TableName("b_product")
public class Product extends ProductCommon {

	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}