import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HeroDetailComponent } from './components/hero-detail/hero-detail/hero-detail.component';
import { HeroesComponent } from './components/heroes/heroes/heroes.component';
import { HomeComponent } from './components/home/home';
import { AddEditComponent } from './components/users/add-edit/add-edit.component';
import { AuthGuard } from './utils';

const accountModule = () => import('./components/account/account/account.module').then(x => x.AccountModule);
const usersModule = () => import('./components/users/users/users.module').then(x => x.UsersModule);

const routes: Routes = [
  { path: '', component: HomeComponent, canActivate: [AuthGuard] },
  { path: 'home', component: HomeComponent, canActivate: [AuthGuard] },
  {
    path: 'detail/:id',
    component: HeroDetailComponent,
    canActivate: [AuthGuard],
  },
  { path: 'heroes', component: HeroesComponent, canActivate: [AuthGuard] },
  { path: 'add-user', component: AddEditComponent, canActivate: [AuthGuard] },
  { path: 'users', loadChildren: usersModule, canActivate: [AuthGuard] },
  { path: 'account', loadChildren: accountModule },
  { path: '**', redirectTo: '' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
