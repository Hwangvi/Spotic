import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Artist } from '../../shared/models/artist';
import { Track } from '../../shared/models/track';
import { AlbumItem } from '../../shared/models/newReleases';
import { UserProfile } from './../../shared/models/userProfile';
import { Playlists } from '../../shared/models/playlists';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class SpoticService {
  private http = inject(HttpClient);
  private readonly URL_BACKEND = `${environment.apiUrl}/api/spotify`;

  private getHeaders(): { headers: HttpHeaders } {
    const token = localStorage.getItem('spotify_token');
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return { headers };
  }

  getNewReleases(): Observable<AlbumItem[]> {
    return this.http.get<AlbumItem[]>(`${this.URL_BACKEND}/new-releases`, this.getHeaders());
  }

  getArtist(id: string): Observable<Artist> {
    return this.http.get<Artist>(`${this.URL_BACKEND}/artists/${id}`, this.getHeaders());
  }

  getArtists(term: string): Observable<Artist[]> {
    return this.http.get<Artist[]>(`${this.URL_BACKEND}/search?term=${term}`, this.getHeaders());
  }

  getTopTracks(id: string): Observable<Track[]> {
    return this.http.get<Track[]>(`${this.URL_BACKEND}/artists/${id}/top-tracks`, this.getHeaders());
  }

  getCurrentUserProfile(): Observable<UserProfile> {
    return this.http.get<UserProfile>(`${this.URL_BACKEND}/me`, this.getHeaders());
  }

  getUserPlaylists(): Observable<Playlists[]> {
    return this.http.get<Playlists[]>(`${this.URL_BACKEND}/me/playlists`, this.getHeaders());
  }

  getUserTopTracks(): Observable<Track[]> {
    return this.http.get<Track[]>(`${this.URL_BACKEND}/me/top/tracks`, this.getHeaders());
  }
}
