package com.lyf.basfile.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.lang.String;
import java.lang.Double;
import java.lang.Integer;
import java.math.BigDecimal;
import javax.xml.soap.Text;
import java.sql.Blob;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.SequenceGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

/**   
 * @Title: Entity
 * @Description: 用户模版信息
 * @author onlineGenerator
 * @date 2019-03-25 22:36:54
 * @version V1.0   
 *
 */
@Entity
@Table(name = "vw_bas_file_detail", schema = "")
@SuppressWarnings("serial")
public class VwBasFileDetailEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.String id;
	/**模版类型id*/
	@Excel(name="模版类型id",width=15)
	private java.lang.String basFileOid;
	/**模版类型*/
	@Excel(name="模版类型",width=15)
	private java.lang.String bfName;
	/**模版内容*/
	@Excel(name="模版内容",width=15)
	private java.lang.String bfdContent;
	@Excel(name="文件路径",width=15)
	private java.lang.String bfPath;
	/**是否收藏*/
	@Excel(name="是否收藏",width=15,dicCode="sf_yn")
	private java.lang.String bfdStore;
	/**创建人名称*/
	private java.lang.String createName;
	/**创建人登录名称*/
	private java.lang.String createBy;
	/**创建日期*/
	private java.util.Date createDate;
	/**更新人名称*/
	private java.lang.String updateName;
	/**更新人登录名称*/
	private java.lang.String updateBy;
	/**更新日期*/
	private java.util.Date updateDate;
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  主键
	 */
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")

	@Column(name ="ID",nullable=false,length=36)
	public java.lang.String getId(){
		return this.id;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  主键
	 */
	public void setId(java.lang.String id){
		this.id = id;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  模版类型id
	 */

	@Column(name ="BAS_FILE_OID",nullable=true,length=32)
	public java.lang.String getBasFileOid(){
		return this.basFileOid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  模版类型id
	 */
	public void setBasFileOid(java.lang.String basFileOid){
		this.basFileOid = basFileOid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  模版类型
	 */

	@Column(name ="BF_NAME",nullable=true,length=32)
	public java.lang.String getBfName(){
		return this.bfName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  模版类型
	 */
	public void setBfName(java.lang.String bfName){
		this.bfName = bfName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  模版内容
	 */

	@Column(name ="BFD_CONTENT",nullable=true,length=3000)
	public java.lang.String getBfdContent(){
		return this.bfdContent;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  模版内容
	 */
	public void setBfdContent(java.lang.String bfdContent){
		this.bfdContent = bfdContent;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  文件路径
	 */

	@Column(name ="BF_PATH",nullable=true,length=50)
	public java.lang.String getBfPath(){
		return this.bfPath;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  文件路径
	 */
	public void setBfPath(java.lang.String bfPath){
		this.bfPath = bfPath;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  是否收藏
	 */

	@Column(name ="BFD_STORE",nullable=true,length=2)
	public java.lang.String getBfdStore(){
		return this.bfdStore;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  是否收藏
	 */
	public void setBfdStore(java.lang.String bfdStore){
		this.bfdStore = bfdStore;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  创建人名称
	 */

	@Column(name ="CREATE_NAME",nullable=true,length=50)
	public java.lang.String getCreateName(){
		return this.createName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  创建人名称
	 */
	public void setCreateName(java.lang.String createName){
		this.createName = createName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  创建人登录名称
	 */

	@Column(name ="CREATE_BY",nullable=true,length=50)
	public java.lang.String getCreateBy(){
		return this.createBy;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  创建人登录名称
	 */
	public void setCreateBy(java.lang.String createBy){
		this.createBy = createBy;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  创建日期
	 */

	@Column(name ="CREATE_DATE",nullable=true,length=20)
	public java.util.Date getCreateDate(){
		return this.createDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  创建日期
	 */
	public void setCreateDate(java.util.Date createDate){
		this.createDate = createDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  更新人名称
	 */

	@Column(name ="UPDATE_NAME",nullable=true,length=50)
	public java.lang.String getUpdateName(){
		return this.updateName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  更新人名称
	 */
	public void setUpdateName(java.lang.String updateName){
		this.updateName = updateName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  更新人登录名称
	 */

	@Column(name ="UPDATE_BY",nullable=true,length=50)
	public java.lang.String getUpdateBy(){
		return this.updateBy;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  更新人登录名称
	 */
	public void setUpdateBy(java.lang.String updateBy){
		this.updateBy = updateBy;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  更新日期
	 */

	@Column(name ="UPDATE_DATE",nullable=true,length=20)
	public java.util.Date getUpdateDate(){
		return this.updateDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  更新日期
	 */
	public void setUpdateDate(java.util.Date updateDate){
		this.updateDate = updateDate;
	}
}
