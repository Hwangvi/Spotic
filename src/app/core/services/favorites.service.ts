import { Track } from '../../shared/models/track';
import { Injectable, signal } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class FavoritesService {
  private readonly favoriteKey = 'spoticFavorites';
  public favoriteTracks = signal<Track[]>(this.getFavorites());

  constructor(){}


  private getFavorites(): Track[] {
    const favorites = localStorage.getItem(this.favoriteKey);
    return favorites ? JSON.parse(favorites) : [];
  }

  private saveFavorites(tracks: Track[]): void{
    localStorage.setItem(this.favoriteKey, JSON.stringify(tracks));
    this.favoriteTracks.set(tracks);
  }

     addFavorite(track: Track): void {
    const favorites = this.getFavorites();
    if (!favorites.some(fav => fav.id === track.id)){
      favorites.push(track);
      this.saveFavorites(favorites)
    }
  }

    removeFavorite(trackId: string): void {
      let favorites = this.getFavorites();
      favorites = favorites.filter(fav => fav.id !== trackId);
      this.saveFavorites(favorites);
    }

    isFavorite(trackId: string): boolean{
      return this.getFavorites().some(fav => fav.id === trackId);
    }
}
