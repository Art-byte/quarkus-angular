import { Component, OnInit } from '@angular/core';
import { Hero, User } from 'src/app/models';
import { AccountService } from 'src/app/service/account.service';
import { HeroService } from 'src/app/service/hero.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
})
export class HomeComponent implements OnInit {
  user: User;
  heroes: Hero[] = [];

  constructor(
    private accountService: AccountService,
    private heroService: HeroService
  ) {
    this.user = this.accountService.userValue;
  }

  ngOnInit() {
    this.getHeroes();
  }

  getHeroes(): void {
    this.heroService
      .getHeroes()
      .subscribe((heroes) => (this.heroes = heroes.slice(1, 5)));
  }
}
