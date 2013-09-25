package com.wcj.dao.test;
/**  */
public class Customer{
	/**  */
	public int customer_id=0;
	/**  */
	public Integer store_id=0;
	/**  */
	public String first_name="";
	/**  */
	public String last_name="";
	/**  */
	public String email;
	/**  */
	public int address_id=0;
	/**  */
	public Integer active=0;
	/**  */
	public java.sql.Timestamp create_date=new java.sql.Timestamp(System.currentTimeMillis());
	/**  */
	public java.sql.Timestamp last_update=new java.sql.Timestamp(System.currentTimeMillis());
	public int getCustomer_id(){
		return this.customer_id;
	}
	public Integer getStore_id(){
		return this.store_id;
	}
	public String getFirst_name(){
		return this.first_name;
	}
	public String getLast_name(){
		return this.last_name;
	}
	public String getEmail(){
		return this.email;
	}
	public int getAddress_id(){
		return this.address_id;
	}
	public Integer getActive(){
		return this.active;
	}
	public java.sql.Timestamp getCreate_date(){
		return this.create_date;
	}
	public java.sql.Timestamp getLast_update(){
		return this.last_update;
	}
	public void setCustomer_id(int customer_id){
		this.customer_id = customer_id;
	}
	public void setStore_id(Integer store_id){
		this.store_id = store_id;
	}
	public void setFirst_name(String first_name){
		this.first_name = first_name;
	}
	public void setLast_name(String last_name){
		this.last_name = last_name;
	}
	public void setEmail(String email){
		this.email = email;
	}
	public void setAddress_id(int address_id){
		this.address_id = address_id;
	}
	public void setActive(Integer active){
		this.active = active;
	}
	public void setCreate_date(java.sql.Timestamp create_date){
		this.create_date = create_date;
	}
	public void setLast_update(java.sql.Timestamp last_update){
		this.last_update = last_update;
	}
}

