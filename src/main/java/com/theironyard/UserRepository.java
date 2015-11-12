package com.theironyard;

import org.springframework.data.repository.CrudRepository;

/**
 * Created by macbookair on 11/12/15.
 */
public interface UserRepository extends CrudRepository <User, Integer>{
    User findOneByName (String username);
}
