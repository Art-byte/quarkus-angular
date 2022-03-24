package com.artbyte.service;


import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import com.artbyte.model.Hero;
import com.artbyte.repository.HeroRepository;

@Transactional
@ApplicationScoped
public class HeroeService {

    @Inject
    HeroRepository heroRepository;

    public HeroeService() {
    };

    public List<Hero> findAllHeroes() {
        return heroRepository.findAll().list();
    }

    public List<Hero> findHeroByMatchingName(String term) {
        return heroRepository.findByNameLike(term);
    }

    public Hero findHeroById(Long id) {
        return heroRepository.findById(id);
    }

    public Hero addHero(Hero hero) {
        heroRepository.persist(hero);
        return hero;
    }

    public Hero deleteHeroById(Long id) {
        Hero heroToDelete = findHeroById(id);
        heroRepository.deleteById(id);
        return heroToDelete;
    }

    public Hero updateHero(Hero hero) {
        Hero heroToUpdate = findHeroById(hero.id);
        heroToUpdate.setName(hero.getName());
        heroRepository.persist(heroToUpdate);

        return heroToUpdate;
    }
}
