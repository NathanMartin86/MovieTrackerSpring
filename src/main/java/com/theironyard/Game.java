package com.theironyard;

import jdk.internal.org.objectweb.asm.tree.analysis.Value;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * Created by macbookair on 11/12/15.
 */
@Entity
public class Game {
    @Id
    @GeneratedValue
    Integer id;

    String system;
    String title;
    String genre;

    @ManyToOne
    User user;
}
