import { Component, signal, inject, OnInit } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { HeaderComponent } from './shared/components/header/header.component';
import { FooterComponent } from './shared/components/footer/footer.component';
import { AuthService } from './core/services/auth.service';
import { HttpClient } from '@angular/common/http';
import { environment } from '../environments/environment';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, HeaderComponent, FooterComponent],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class App implements OnInit {
  protected readonly title = signal('Spotic');

  private authService = inject(AuthService);
  private http = inject(HttpClient);

  constructor() {}

  ngOnInit(): void {
    const backendUrl = `${environment.apiUrl}/registrar-visita`;

    this.http.get(backendUrl, { responseType: 'text' })
      .subscribe({
        next: (response) => {
          console.log('Respuesta del backend:', response);
        },
        error: (err) => {
          console.error('Error al contactar con el backend:', err);
        }
      });
  }
}
