package hello;

import java.util.List;

/**
 * Created by : faiez.elleuch
 * Date: 03/03/14
 * Time: 17:51
 */
public interface CustomerRepositoryCustom {
	public List<Customer> findInactiveCustomers();
	public List<Customer> findMediumCustomer(long age);
}
