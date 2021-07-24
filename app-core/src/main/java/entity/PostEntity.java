package entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "post")
public class PostEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue
    private Long seq;

    private String title;

    private String contents;

}
