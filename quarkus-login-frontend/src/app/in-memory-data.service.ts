import { Injectable } from '@angular/core';
import { Hero } from './models';

@Injectable({
  providedIn: 'root'
})
export class InMemoryDataService {

  //Esta clase es solo de pruebas sin back end

  createDb() {
    const heroes = [
      { id: 11, name: 'Dr Fate' },
      { id: 12, name: 'Atom smasher' },
      { id: 13, name: 'Green lantern' },
      { id: 14, name: 'Flash' },
      { id: 15, name: 'Mr. terrific' },
      { id: 16, name: 'Star girl' },
      { id: 17, name: 'Batman' },
      { id: 18, name: 'Dr mightnight' },
      { id: 19, name: 'Shazam' },
      { id: 20, name: 'Tornado' }
    ];
    return {heroes};
  }


  genId(heroes: Hero[]): number {
    return heroes.length > 0 ? Math.max(...heroes.map(hero => hero.id)) + 1 : 11;
  }
}
