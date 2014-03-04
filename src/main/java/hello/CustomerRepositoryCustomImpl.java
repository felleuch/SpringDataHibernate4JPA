package hello;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Created by : faiez.elleuch
 * Date: 03/03/14
 * Time: 17:52
 */
public class CustomerRepositoryCustomImpl implements CustomerRepositoryCustom{
	@PersistenceContext
	private EntityManager em;


	public List<Customer> findInactiveCustomers() {
		return em.createQuery("SELECT c FROM Customer c").getResultList();
	}

	public List<Customer> findMediumCustomer(long age){

		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Customer> query = builder.createQuery(Customer.class);
		Root<Customer> root = query.from(Customer.class);

		Predicate hasBirthday = builder.equal(root.<Long>get("age"), 20);
		//Predicate isLongTermCustomer = builder.lessThan(root.get(Customer_.createdAt), today.minusYears(2);
		query.where(builder.and(hasBirthday));


		return em.createQuery(query.select(root)).getResultList();
	}


}
