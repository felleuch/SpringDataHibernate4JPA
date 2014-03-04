package hello;

import javax.persistence.*;

@Entity
@NamedQuery(
		name = "Customer.findYoung",
		query = "from Customer c where c.age = ?"
)
public class Customer {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
    private String firstName;

	private String lastName;

	private long age;

	public long getAge() {
		return age;
	}

	public void setAge(long age) {
		this.age = age;
	}



    protected Customer() {}

    public Customer(String firstName, String lastName,long age) {
        this.firstName = firstName;
        this.lastName = lastName;
		this.age=age;
    }

    @Override
    public String toString() {
        return String.format(
                "Customer[id=%d, firstName='%s', lastName='%s']",
                id, firstName, lastName);
    }

}
