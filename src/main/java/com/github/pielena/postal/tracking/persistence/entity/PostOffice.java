package com.github.pielena.postal.tracking.persistence.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "post_offices")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class PostOffice {

    @Id
    @Column(name = "indexx")
    private int index;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "postOffice", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Address> addresses;

    @OneToMany(mappedBy = "postOffice", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Operation> operationHistories;

    @Override
    public String toString() {
        return "PostOffice{" +
                "index=" + index +
                ", name='" + name + '\'' +
                '}';
    }
}
