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
 * @Description: 律师文件类型
 * @author onlineGenerator
 * @date 2019-03-24 21:20:45
 * @version V1.0   
 *
 */
@Entity
@Table(name = "bas_file", schema = "")
@SuppressWarnings("serial")
public class BasFileEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.String id;
	/**编码*/
	@Excel(name="编码",width=15)
	private java.lang.String bfCode;
	/**类型*/
	@Excel(name="类型",width=15)
	private java.lang.String bfName;
	/**文件路径*/
	@Excel(name="文件路径",width=15)
	private java.lang.String bfPath;
	/**父ID*/
	@Excel(name="父ID",width=15)
	private java.lang.String bfParentid;
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
	 *@return: java.lang.String  编码
	 */

	@Column(name ="BF_CODE",nullable=true,length=32)
	public java.lang.String getBfCode(){
		return this.bfCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  编码
	 */
	public void setBfCode(java.lang.String bfCode){
		this.bfCode = bfCode;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  类型
	 */

	@Column(name ="BF_NAME",nullable=true,length=32)
	public java.lang.String getBfName(){
		return this.bfName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  类型
	 */
	public void setBfName(java.lang.String bfName){
		this.bfName = bfName;
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
	 *@return: java.lang.String  父ID
	 */

	@Column(name ="BF_PARENTID",nullable=true,length=32)
	public java.lang.String getBfParentid(){
		return this.bfParentid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  父ID
	 */
	public void setBfParentid(java.lang.String bfParentid){
		this.bfParentid = bfParentid;
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
