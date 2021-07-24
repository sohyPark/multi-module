package entity;

import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@RequiredArgsConstructor

@Table(name = "board")
public class BoardEntity {

    @Id
    @GeneratedValue
    private Long seq;

    private String name;

    private String description;

}
