package com.emt.card.db.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "sys_worker_node")
public class WorkerNode {

    @Id
    @GeneratedValue
    private Long id;
    @Column
    private String hostName;
    @Column
    private String port;
    @Column
    private Integer type;
    @Column
    private LocalDateTime launchDate;
    @Column
    private LocalDateTime modified;
    @Column
    private LocalDateTime created;
}
