package softuni.exam.models.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "countries")
public class Country extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String countryName;
    @Column(nullable = false)
    private String currency;

    public String getName() {
        return countryName;
    }

    public void setName(String name) {
        this.countryName = name;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
