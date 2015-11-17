package com.theironyard;

import org.springframework.data.domain.Page;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.domain.Pageable;

/**
 * Created by macbookair on 11/12/15.
 */
public interface GamesRepository extends PagingAndSortingRepository<Game,Integer> {
    Page <Game> findAll(Pageable pageable);
}
