import { Component, OnInit, inject } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { switchMap } from 'rxjs/operators';
import { CommonModule } from '@angular/common';
import { environment } from '../../../../environments/environment';

@Component({
  selector: 'app-callback',
  standalone: true,
  imports: [CommonModule],
  template: '<p>Finalizando autenticación...</p>',
})
export class CallbackComponent implements OnInit {
  private route = inject(ActivatedRoute);
  private router = inject(Router);
  private http = inject(HttpClient);

  ngOnInit(): void {
    this.route.queryParamMap.pipe(
      switchMap(params => {
        const code = params.get('code');
        if (!code) throw new Error('No hay código');

        const backendCallbackUrl = `${environment.apiUrl}/api/auth/callback`;
        return this.http.get(`${backendCallbackUrl}?code=${code}`, { withCredentials: true });
      })
    ).subscribe({
      next: () => {
        this.router.navigate(['/home']);
      },
      error: (err) => {
        console.error('Error finalizando auth', err);
        this.router.navigate(['/']);
      }
    });
  }
}
