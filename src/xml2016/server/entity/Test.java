package xml2016.server.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "test")
public class Test implements Serializable {

	private static final long serialVersionUID = 7033232793607903812L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "test_id", unique = true, nullable = false)
	private Integer id;

	




	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	

	public Test() {
		super();
	}

}
