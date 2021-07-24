package entity;

import lombok.Generated;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
public class UserEntity {

    @Id
    @GeneratedValue
    private long seq;

    private String id;

    private String name;

    private String password;

    private int count;

}
