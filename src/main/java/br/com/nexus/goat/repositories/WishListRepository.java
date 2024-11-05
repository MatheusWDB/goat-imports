package br.com.nexus.goat.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.nexus.goat.models.WishList;
import br.com.nexus.goat.models.pk.WishListPK;

public interface WishListRepository extends JpaRepository<WishList, WishListPK>{
}
