package com.github.pielena.postal.tracking.persistence.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "items")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Item {

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "id")
    private UUID id;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private ItemType type;

    @ManyToOne
    @JoinColumn(name = "destination_post_office_index")
    private PostOffice destinationPostOffice;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private Person sender;

    @ManyToOne
    @JoinColumn(name = "recipient_id")
    private Person recipient;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Operation> operationHistory;

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                '}';
    }
}
