package dev.queryable.filterdef.issue.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.ParamDef;

import javax.persistence.*;

@Entity
@FilterDef(name = "obj.name", parameters = @ParamDef(name = "name", type = "string"))
@Filter(name = "obj.name", condition = "name LIKE :name")

@FilterDef(name = "obj.active", parameters = @ParamDef(name = "active", type = "string"))
@Filter(name = "obj.active", condition = "active = :active")
@Table(name = "areas")
public class Area extends PanacheEntityBase {

    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "uuid", unique = true)
    @Id
    public String uuid;
    public String name;
    public String description;
    public String active;

    @Override
    public String toString() {
        return "Area{" +
                "uuid='" + uuid + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", active='" + active + '\'' +
                '}';
    }
}
