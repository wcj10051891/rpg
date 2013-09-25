package com.wcj.dao.test;
import com.wcj.dao.annotation.Dao;
import com.wcj.dao.annotation.Sql;
import com.wcj.dao.annotation.Arg;
import java.util.List;
import com.wcj.dao.test.Customer;
@Dao
public interface CustomerDao {
	@Sql(value="insert into customer(	`customer_id`,	`store_id`,	`first_name`,	`last_name`,	`email`,	`address_id`,	`active`,	`create_date`,	`last_update`	) values(	:customer.customer_id,	:customer.store_id,	:customer.first_name,	:customer.last_name,	:customer.email,	:customer.address_id,	:customer.active,	:customer.create_date,	:customer.last_update	)")
	Integer insert(@Arg(value="customer") Customer o);
	
	@Sql(value="delete from customer where id=:id")
	void delete(@Arg(value="id")Integer id);
	
	@Sql(value="update customer set 	`customer_id`=:customer.customer_id,	`store_id`=:customer.store_id,	`first_name`=:customer.first_name,	`last_name`=:customer.last_name,	`email`=:customer.email,	`address_id`=:customer.address_id,	`active`=:customer.active,	`create_date`=:customer.create_date,	`last_update`=:customer.last_update	 where id=:customer.id")
	void update(@Arg(value="customer") Customer o);

	@Sql(value="select * from customer where customer_id=:id")
	Customer get(@Arg(value="id") Integer id);
	
	@Sql(value="select * from customer")
	List<Customer> getAll();
}

