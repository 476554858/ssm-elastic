package bean;

import java.math.BigDecimal;


/** 商品共性 */

public class ProductCommon extends BaseEntity{

	/**
	 * 编码
	 */
	private String code;
	/**
	 * 类别(Attribute)
	 */
	private String type;
	/**
	 * 产品名称
	 */
	private String name;
	/**
	 * 商品名
	 */
	private String tradeName;
	/**
	 * 拼音简称
	 */
	private String pinyin;
	/**
	 * 通用名
	 */
	private String genericName;
	/**
	 * 英文名称
	 */
	private String englishName;
	/**
	 * 剂型编码
	 */
	private String dosageFormCode;
	/**
	 * 剂型名称
	 */
	private String dosageFormName;
	/**
	 * 生产企业(company).
	 */
	private Long producerId;
	/**
	 * 生产企业名称
	 */
	private String producerName;
	/**
	 * 产地
	 */
	private String originPlace;
	/**
	 * 规格
	 */
	private String model;
	/**
	 * 图片路径
	 */
	private String imagePath;
	/**
	 * 单位转换比
	 */
	private Integer convertRatio;
	/**
	 * 最小制剂单位(Attribute)
	 */
	private String minunit;
	/**
	 * 单位(Attribute)
	 */
	private String unit;
	/**
	 * 中包装数量
	 */
	private Integer mediumQty;
	/**
	 * 大包装数量
	 */
	private Integer largeQty;
	/**
	 * 包装规格
	 */
	private String packDesc;
	/**
	 * 本位码
	 */
	private String standardCode;
    /**
     * 商品条码
     */
	private String barCode;
	/**
	 * 批准文号
	 */
	private String authorizeNo;
	/**
	 * 批准文号有效起始日期
	 */
	private String authorizeBeginDate;
	/**
	 * 批准文号有效截止日期
	 */
	private String authorizeOutDate;
	/**
	 * 处方药(Attribute)
	 */
	private String prescription;
	/**
	 * 存储类型(Attribute)
	 */
	private String storageType;
	/**
	 * 质量标准类型(Attribute)
	 */
	private String qualityType;
	/**
	 * 用法
	 */
	private String useage;
	/**
	 * 用量
	 */
	private String dosage;
	/**
	 * 是否进口药品
	 */
	private Boolean isImport = false;
	/**
	 * 是否医保药品
	 */
	private Boolean isInsurance = false;
	/**
	 * 剂量
	 **/
	private BigDecimal dose;
	/**
	 * 剂量单位
	 */
	private String doseUnitName;
	/**
	 * 保质期（月）
	 */
	private Integer valiMonth;
	/**
	 * 效期预警天数
	 */
	private Integer valiWarnDay;
	/**
	 * 拆零系数
	 */
	private Integer splitRatio;
	/**
	 * 备 注
	 */
	private String notes;
	/**
	 * 销售单位
	 */
	private String saleUnit;
	/**
	 * 销售转换比
	 */
	private BigDecimal saleConvertRatio = new BigDecimal(1);
	/**
	 * 是否含麻黄碱
	 */
	private Boolean isEphedrine = false;
	/**
	 * 是否网上限售
	 */
	private Boolean isNetLimitSale = false;
	/**
	 * 是否按处方销售
	 */
	private Boolean isPrescriptSale = false;
	/**
	 * 是否限售
	 */
	private Boolean isLimitSale = false;
	/**
	 * 限售数量
	 */
	private BigDecimal limitQty = new BigDecimal(0);
	/**
	 * 是否禁用(1是，0否)
	 */
	private Boolean isDisabled = false;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTradeName() {
		return tradeName;
	}

	public void setTradeName(String tradeName) {
		this.tradeName = tradeName;
	}

	public String getPinyin() {
		return pinyin;
	}

	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}

	public String getGenericName() {
		return genericName;
	}

	public void setGenericName(String genericName) {
		this.genericName = genericName;
	}

	public String getEnglishName() {
		return englishName;
	}

	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}

	public String getDosageFormCode() {
		return dosageFormCode;
	}

	public void setDosageFormCode(String dosageFormCode) {
		this.dosageFormCode = dosageFormCode;
	}

	public String getDosageFormName() {
		return dosageFormName;
	}

	public void setDosageFormName(String dosageFormName) {
		this.dosageFormName = dosageFormName;
	}

	public Long getProducerId() {
		return producerId;
	}

	public void setProducerId(Long producerId) {
		this.producerId = producerId;
	}

	public String getProducerName() {
		return producerName;
	}

	public void setProducerName(String producerName) {
		this.producerName = producerName;
	}

	public String getOriginPlace() {
		return originPlace;
	}

	public void setOriginPlace(String originPlace) {
		this.originPlace = originPlace;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public Integer getConvertRatio() {
		return convertRatio;
	}

	public void setConvertRatio(Integer convertRatio) {
		this.convertRatio = convertRatio;
	}

	public String getMinunit() {
		return minunit;
	}

	public void setMinunit(String minunit) {
		this.minunit = minunit;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Integer getMediumQty() {
		return mediumQty;
	}

	public void setMediumQty(Integer mediumQty) {
		this.mediumQty = mediumQty;
	}

	public Integer getLargeQty() {
		return largeQty;
	}

	public void setLargeQty(Integer largeQty) {
		this.largeQty = largeQty;
	}

	public String getPackDesc() {
		return packDesc;
	}

	public void setPackDesc(String packDesc) {
		this.packDesc = packDesc;
	}

	public String getStandardCode() {
		return standardCode;
	}

	public void setStandardCode(String standardCode) {
		this.standardCode = standardCode;
	}

	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	public String getAuthorizeNo() {
		return authorizeNo;
	}

	public void setAuthorizeNo(String authorizeNo) {
		this.authorizeNo = authorizeNo;
	}

	public String getAuthorizeBeginDate() {
		return authorizeBeginDate;
	}

	public void setAuthorizeBeginDate(String authorizeBeginDate) {
		this.authorizeBeginDate = authorizeBeginDate;
	}

	public String getAuthorizeOutDate() {
		return authorizeOutDate;
	}

	public void setAuthorizeOutDate(String authorizeOutDate) {
		this.authorizeOutDate = authorizeOutDate;
	}

	public String getPrescription() {
		return prescription;
	}

	public void setPrescription(String prescription) {
		this.prescription = prescription;
	}

	public String getStorageType() {
		return storageType;
	}

	public void setStorageType(String storageType) {
		this.storageType = storageType;
	}

	public String getQualityType() {
		return qualityType;
	}

	public void setQualityType(String qualityType) {
		this.qualityType = qualityType;
	}

	public String getUseage() {
		return useage;
	}

	public void setUseage(String useage) {
		this.useage = useage;
	}

	public String getDosage() {
		return dosage;
	}

	public void setDosage(String dosage) {
		this.dosage = dosage;
	}

	public Boolean getIsImport() {
		return isImport;
	}

	public void setIsImport(Boolean isImport) {
		this.isImport = isImport;
	}

	public Boolean getIsInsurance() {
		return isInsurance;
	}

	public void setIsInsurance(Boolean isInsurance) {
		this.isInsurance = isInsurance;
	}

	public BigDecimal getDose() {
		return dose;
	}

	public void setDose(BigDecimal dose) {
		this.dose = dose;
	}

	public String getDoseUnitName() {
		return doseUnitName;
	}

	public void setDoseUnitName(String doseUnitName) {
		this.doseUnitName = doseUnitName;
	}

	public Integer getValiMonth() {
		return valiMonth;
	}

	public void setValiMonth(Integer valiMonth) {
		this.valiMonth = valiMonth;
	}

	public Integer getValiWarnDay() {
		return valiWarnDay;
	}

	public void setValiWarnDay(Integer valiWarnDay) {
		this.valiWarnDay = valiWarnDay;
	}

	public Integer getSplitRatio() {
		return splitRatio;
	}

	public void setSplitRatio(Integer splitRatio) {
		this.splitRatio = splitRatio;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getSaleUnit() {
		return saleUnit;
	}

	public void setSaleUnit(String saleUnit) {
		this.saleUnit = saleUnit;
	}

	public BigDecimal getSaleConvertRatio() {
		return saleConvertRatio;
	}

	public void setSaleConvertRatio(BigDecimal saleConvertRatio) {
		this.saleConvertRatio = saleConvertRatio;
	}

	public Boolean getIsEphedrine() {
		return isEphedrine;
	}

	public void setIsEphedrine(Boolean isEphedrine) {
		this.isEphedrine = isEphedrine;
	}

	public Boolean getIsNetLimitSale() {
		return isNetLimitSale;
	}

	public void setIsNetLimitSale(Boolean isNetLimitSale) {
		this.isNetLimitSale = isNetLimitSale;
	}

	public Boolean getIsPrescriptSale() {
		return isPrescriptSale;
	}

	public void setIsPrescriptSale(Boolean isPrescriptSale) {
		this.isPrescriptSale = isPrescriptSale;
	}

	public Boolean getIsLimitSale() {
		return isLimitSale;
	}

	public void setIsLimitSale(Boolean isLimitSale) {
		this.isLimitSale = isLimitSale;
	}

	public BigDecimal getLimitQty() {
		return limitQty;
	}

	public void setLimitQty(BigDecimal limitQty) {
		this.limitQty = limitQty;
	}
}