/**
 * 
 */
package ml.bootcode.profileapp.dto;

/**
 * @author sunnyb
 *
 */
public class EmpDummy {

	private Long id;
	private String name;
	private double salary;
	private int age;

	/**
	 * 
	 */
	public EmpDummy() {
	}

	/**
	 * @param name
	 * @param salary
	 * @param age
	 */
	public EmpDummy(String name, double salary, int age) {
		this.name = name;
		this.salary = salary;
		this.age = age;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the salary
	 */
	public double getSalary() {
		return salary;
	}

	/**
	 * @param salary the salary to set
	 */
	public void setSalary(double salary) {
		this.salary = salary;
	}

	/**
	 * @return the age
	 */
	public int getAge() {
		return age;
	}

	/**
	 * @param age the age to set
	 */
	public void setAge(int age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "EmpDummy [id=" + id + ", name=" + name + ", salary=" + salary + ", age=" + age + "]";
	}
}
