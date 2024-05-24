package com.deenn.mkoboassessment.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@Table(name="staff")
public class Staff implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, insertable = false)
    private Long id;

    @Column(nullable=false)
    private String uuid;

    @Column(nullable=false)
    private String name;

    @Column(name = "registration_date",nullable = false)
    private Instant registrationDate;

    @CreationTimestamp
    @Column(name = "created_at",nullable = false,updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @JsonFormat(pattern = "dd-MM-yyyy hh:mm a")
    @Column(name = "updated_at",nullable = false)
    private Instant updatedAt;
}
