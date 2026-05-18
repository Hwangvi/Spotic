import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';


import { Artist } from '../../shared/models/artist';
import { Track } from '../../shared/models/track';
import { AlbumItem } from '../../shared/models/newReleases';
import { UserProfile } from './../../shared/models/userProfile';
import { Playlists } from '../../shared/models/playlists';


const URL_BACKEND = 'https://127.0.0.1:8443/api/spotify';

@Injectable({
  providedIn: 'root',
})
export class SpoticService {
  private http = inject(HttpClient);

  getNewReleases(): Observable<AlbumItem[]> {
    return this.http.get<AlbumItem[]>(`${URL_BACKEND}/new-releases`);
  }


  getArtist(id: string): Observable<Artist> {
    return this.http.get<Artist>(`${URL_BACKEND}/artists/${id}`);
  }


  getArtists(term: string): Observable<Artist[]> {
    return this.http.get<Artist[]>(`${URL_BACKEND}/search?term=${term}`);
  }

  getTopTracks(id: string): Observable<Track[]> {
    return this.http.get<Track[]>(`${URL_BACKEND}/artists/${id}/top-tracks`);
  }

  getCurrentUserProfile(): Observable<UserProfile> {
    return this.http.get<UserProfile>(`${URL_BACKEND}/me`);
  }


  getUserPlaylists(): Observable<Playlists[]> {
    return this.http.get<Playlists[]>(`${URL_BACKEND}/me/playlists`);
  }


  getUserTopTracks(): Observable<Track[]> {
    return this.http.get<Track[]>(`${URL_BACKEND}/me/top/tracks`);
  }
}
