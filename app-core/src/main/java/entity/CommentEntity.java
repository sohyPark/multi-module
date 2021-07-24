package entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Table(name = "comment")
public class CommentEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue
    private Long seq;

    private String comment;

    @OneToOne
    private UserEntity user_id;
}
