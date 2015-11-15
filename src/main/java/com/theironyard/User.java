package com.theironyard;

import javax.persistence.*;
import java.util.List;

/**
 * Created by macbookair on 11/12/15.
 */
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue
    Integer id;

    String name;
    String gamerTag;
    String password;

    @OneToMany (mappedBy = "user")
    List<Game> games;
}
