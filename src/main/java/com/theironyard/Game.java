package com.theironyard;

import jdk.internal.org.objectweb.asm.tree.analysis.Value;

import javax.persistence.*;

/**
 * Created by macbookair on 11/12/15.
 */
@Entity
public class Game {
    @Id
    @GeneratedValue
    Integer id;

    @Column(nullable = false)
    String system;

    @Column(nullable = false)
    String title;

  public Game(){

  }
    public Game (Integer id, String system, String title) {
        this.id = id;
        this.system = system;
        this.title = title;
    }
    @ManyToOne
    User user;
}
