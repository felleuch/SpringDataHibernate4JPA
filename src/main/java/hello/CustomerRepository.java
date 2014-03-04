package hello;

import java.util.List;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Long>,CustomerRepositoryCustom {

    List<Customer> findByLastName(String lastName);


	@Query("select c from Customer c where c.age = ?")
	List<Customer> findAllOldCustomers(long age);

	List<Customer> findYoung(long age);

}