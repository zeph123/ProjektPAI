package com.example.projekt.daos;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "task")
public class TaskDao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @JoinColumn(name="status_id")
    @ManyToOne(cascade = CascadeType.ALL)
    private TaskStatusDao status;

    @Column
    private ZonedDateTime creationDate;

    @Column
    private ZonedDateTime completionDate;

    @JoinColumn(name="user_id")
    @ManyToOne(cascade = CascadeType.ALL)
    private UserDao user;

}
