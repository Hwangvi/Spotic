import { Routes } from '@angular/router';
import { HomeComponent } from '../app/features/home/home.component';
import { SearchComponent } from './features/search/search.component';
import { ArtistComponent } from './features/artist/artist.component';
import { authGuard } from './core/guards/auth.guard';
import { ProfileComponent } from './features/profile/profile.component';
import { SavesComponent } from './features/saves/saves.component';
import { HeroComponent } from './features/home/hero/hero.component';


export const routes: Routes = [
  {path: '', component: HeroComponent},
  { path: 'home', component: HomeComponent, canActivate: [authGuard] },
  { path: 'search', component: SearchComponent, canActivate: [authGuard] },
  { path: 'artist/:id', component: ArtistComponent, canActivate: [authGuard] },
  { path: 'profile', component: ProfileComponent, canActivate: [authGuard] },
  { path: 'saves', component: SavesComponent, canActivate: [authGuard] },
  { path: '', pathMatch: 'full', redirectTo: 'home' },
  // Abajo siempre
  { path: '**', redirectTo: '' },
];
