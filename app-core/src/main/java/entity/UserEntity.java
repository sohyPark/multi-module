package entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Table(name = "user")
public class UserEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue
    private long seq;

    private String id;

    private String password;

    private String name;

    private int count;

    private int rank;
}
