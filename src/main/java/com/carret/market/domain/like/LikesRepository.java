package com.carret.market.domain.like;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LikesRepository extends JpaRepository<Likes, Long>, LikesRepositoryCustom {

}
