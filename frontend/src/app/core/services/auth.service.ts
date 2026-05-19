import { Injectable, signal, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';
import { Router } from '@angular/router';
import { UserProfile } from '../../shared/models/userProfile';
import { SpoticService } from './spotic.service';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private http = inject(HttpClient);
  private spoticService = inject(SpoticService);
  private router = inject(Router);

  private readonly URL_BACKEND_AUTH = `${environment.apiUrl}/api/auth`;

  public currentUserProfile = signal<UserProfile | null>(null);

  public isAuthenticated = signal<boolean>(false);

  login(): void {
    window.location.href = `${this.URL_BACKEND_AUTH}/login`;
  }

  logout(): Observable<any> {
    return this.http.post(`${this.URL_BACKEND_AUTH}/logout`, {}).pipe(
      tap(() => {
        this.isAuthenticated.set(false);
        this.currentUserProfile.set(null);
        localStorage.removeItem('spotify_token');
        this.router.navigate(['/']);
      }),
    );
  }

  checkAuthStatus(): Observable<boolean> {
    if (this.currentUserProfile()) {
      return of(true);
    }

    const urlParams = new URLSearchParams(window.location.search);
    let tokenFromUrl = urlParams.get('token');

    if (!tokenFromUrl && window.location.hash) {
      const hashParams = new URLSearchParams(window.location.hash.substring(1));
      tokenFromUrl = hashParams.get('token');
    }
    if (tokenFromUrl) {
      localStorage.setItem('spotify_token', tokenFromUrl);

      window.history.replaceState({}, document.title, window.location.pathname);
    }

    if (!localStorage.getItem('spotify_token')) {
      return of(false);
    }

    return this.spoticService.getCurrentUserProfile().pipe(
      tap((profile) => {
        this.currentUserProfile.set(profile);
        this.isAuthenticated.set(true);
      }),
      map(() => true),
      catchError(() => {
        this.isAuthenticated.set(false);
        this.currentUserProfile.set(null);
        localStorage.removeItem('spotify_token');
        return of(false);
      }),
    );
  }
}
