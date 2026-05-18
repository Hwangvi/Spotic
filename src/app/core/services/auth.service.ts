import { Injectable, signal, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';
import { Router } from '@angular/router';
import { UserProfile } from '../../shared/models/userProfile';
import { SpoticService } from './spotic.service';

const URL_BACKEND_AUTH = 'https://127.0.0.1:8443/api/auth';

@Injectable({
  providedIn: 'root',
})
export class AuthService {

  private http = inject(HttpClient);
  private spoticService = inject(SpoticService);
  private router = inject(Router);

  public currentUserProfile = signal<UserProfile | null>(null);
  public isAuthenticated = signal<boolean>(false);

  login(): void {
    window.location.href = `${URL_BACKEND_AUTH}/login`;
  }

  logout(): Observable<any> {

      return this.http.post(`${URL_BACKEND_AUTH}/logout`, {}, { withCredentials: true }).pipe(
      tap(() => {
        this.isAuthenticated.set(false);
        this.currentUserProfile.set(null);

        localStorage.clear();

        this.router.navigate(['/']);
      })
    );
  }

  checkAuthStatus(): Observable<boolean> {
    if (this.currentUserProfile()) {
      return of(true);
    }

    return this.spoticService.getCurrentUserProfile().pipe(
      tap(profile => {
        this.currentUserProfile.set(profile);
        this.isAuthenticated.set(true);
      }),
      map(() => true),
      catchError(() => {
        this.isAuthenticated.set(false);
        this.currentUserProfile.set(null);
        return of(false);
      })
    );
  }
}
