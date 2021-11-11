package part.domain;

import brand.domain.Brand;
import value.Designation;

import javax.persistence.*;

@Entity
public class Part {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Version
    private long version;

    private Designation name;

    private PartNumber partNumber;

    @ManyToOne
    private Brand brand;


}
