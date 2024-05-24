package com.deenn.mkoboassessment.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@Table(name="staffs_log")
public class StaffLog implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, insertable = false)
    private Long id;

    @Column(nullable=false)
    private String status;

    private String reason;

    @Column(nullable=false)
    private String payload;

    @CreationTimestamp
    @Column(name = "created_at",nullable = false,updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @JsonFormat(pattern = "dd-MM-yyyy hh:mm a")
    @Column(name = "updated_at",nullable = false)
    private Instant updatedAt;
}
