package com.example.clothdonationsystem.model;

import java.util.Date;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
@FieldDefaults(level= AccessLevel.PRIVATE)
public class BaseEntity {

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

    @Column (name="created_on")
//    @Temporal (TemporalType.TIMESTAMP)
    Date createdOn;

    @Column (name="updated_on")
    //  @Temporal(TemporalType.TIMESTAMP)    //by default temporal type= timestamp
    Date updatedOn;

    @Column (name="deleted")
    @ColumnDefault(value = "0")
    Boolean deleted = false;

    @Column (name="active")
    @ColumnDefault(value = "1")
    Boolean active = true;

//    @CreatedBy
    @Column(name = "created_by", updatable = false)
    Long createdBy;

//    @LastModifiedBy
    @Column(name="updated_by")
    Long updatedBy;

    @PrePersist
    public void prePersist() {
        Date now = new Date();
        createdOn = now;
        updatedOn = now;
    }
    @PreUpdate
    public void preUpdate() {
        updatedOn = new Date();
    }
}
