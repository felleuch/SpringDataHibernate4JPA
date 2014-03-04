package hello;

import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.H2;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableJpaRepositories(basePackages ="hello", repositoryImplementationPostfix = "CustomImpl")
@ComponentScan("hello")
public class Application {

    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder().setType(H2).build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource, JpaVendorAdapter jpaVendorAdapter) {
        LocalContainerEntityManagerFactoryBean lef = new LocalContainerEntityManagerFactoryBean();
        lef.setDataSource(dataSource);
        lef.setJpaVendorAdapter(jpaVendorAdapter);
        lef.setPackagesToScan("hello");
        return lef;
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
        hibernateJpaVendorAdapter.setShowSql(false);
        hibernateJpaVendorAdapter.setGenerateDdl(true);
        hibernateJpaVendorAdapter.setDatabase(Database.H2);
        return hibernateJpaVendorAdapter;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new JpaTransactionManager();
    }


    public static void main(String[] args) {
        AbstractApplicationContext context = new AnnotationConfigApplicationContext(Application.class);
        CustomerRepository repository = context.getBean(CustomerRepository.class);

        // save a couple of customers
        repository.save(new Customer("Jack", "Bauer",10));
        repository.save(new Customer("Chloe", "O'Brian",20));
        repository.save(new Customer("Kim", "Bauer",30));
        repository.save(new Customer("David", "Palmer",40));
        repository.save(new Customer("Michelle", "Dessler",50));

        // fetch all customers
        Iterable<Customer> customers = repository.findAll();
        System.out.println("Customers found with findAll():");
        System.out.println("-------------------------------");
        for (Customer customer : customers) {
            System.out.println(customer);
        }
        System.out.println();

		// fetch all old customers
		Iterable<Customer> customersOld = repository.findAllOldCustomers(50);
		System.out.println("Customers found with findAllOldCustomers():");
		System.out.println("-------------------------------");
		for (Customer customer : customersOld) {
			System.out.println("Old customers");
			System.out.println(customer);
		}
		System.out.println();

		// fetch all young customers
		Iterable<Customer> customersYoung = repository.findYoung(10);
		System.out.println("Customers found with findYoung():");
		System.out.println("-------------------------------");
		for (Customer customer : customersYoung) {
			System.out.println("Young customers");
			System.out.println(customer);
		}
		System.out.println();

		// fetch all Inactive customers
		Iterable<Customer> customersInactive = repository.findInactiveCustomers();
		System.out.println("Customers found with findInactiveCustomers():");
		System.out.println("-------------------------------");
		for (Customer customer : customersInactive) {
			System.out.println("Inactive customers");
			System.out.println(customer);
		}
		System.out.println();




        // fetch an individual customer by ID
        Customer customer = repository.findOne(1L);
        System.out.println("Customer found with findOne(1L):");
        System.out.println("--------------------------------");
        System.out.println(customer);
        System.out.println();

        // fetch customers by last name
        List<Customer> bauers = repository.findByLastName("Bauer");
        System.out.println("Customer found with findByLastName('Bauer'):");
        System.out.println("--------------------------------------------");
        for (Customer bauer : bauers) {
            System.out.println(bauer);
        }

		// fetch customers medium
		List<Customer> medium = repository.findMediumCustomer(20)  ;
		System.out.println("Customer found with findMediumCustomer(20):");
		System.out.println("--------------------------------------------");
		for (Customer m : medium) {
			System.out.println(m);
		}


        context.close();
    }

}
