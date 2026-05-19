import { Component, inject, OnInit } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { HeaderComponent } from './shared/components/header/header.component';
import { FooterComponent } from './shared/components/footer/footer.component';
import { AuthService } from './core/services/auth.service';
import { HttpClient } from '@angular/common/http';
import { environment } from '../environments/environment';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, HeaderComponent, FooterComponent],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class App implements OnInit {
  private authService = inject(AuthService);
  private http = inject(HttpClient);

  constructor() {}

  ngOnInit(): void {
    this.mostrarAlertaContingencia();
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

  private mostrarAlertaContingencia(): void {
    Swal.fire({
      title: 'Entorno de Demostración Activo',
      html: `
        <div style="text-align: left; font-family: system-ui, sans-serif; color: #b3b3b3; font-size: 0.95rem; line-height: 1.5;">
          <p style="margin-bottom: 15px;">
            La API de Desarrollo de Spotify restringe el acceso <strong>exclusivamente</strong> a usuarios autorizados en su panel (Error 403 Forbidden) y limita la reproducción a cuentas Premium.
          </p>
          <p style="margin-bottom: 20px;">
            Para que puedas evaluar este portfolio sin bloqueos, el Backend (Spring Boot) ha activado un <strong>Modo de Contingencia</strong> inyectando datos simulados perfectamente estructurados.
          </p>

          <!-- Bloque de la Traza de Error estilo Consola -->
          <div style="background-color: #07090e; border: 1px solid #1c1e24; border-radius: 6px; padding: 10px; font-family: monospace; font-size: 0.75rem;">
            <div style="color: #888; text-transform: uppercase; margin-bottom: 5px; font-weight: bold;">
              ⚠️ Traza del Servidor interceptada:
            </div>
            <div style="color: #ff5555; white-space: pre-wrap; word-break: break-all;">
              org.springframework.web.client.HttpClientErrorException$Forbidden: 403 Forbidden on GET request for "https://api.spotify.com/v1/me"
            </div>
          </div>
        </div>
      `,
      icon: 'warning',
      iconColor: '#ffb020',
      background: '#121212',
      color: '#ffffff',
      confirmButtonText: 'Entendido, evaluar demo',
      confirmButtonColor: '#1db954',
      allowOutsideClick: true,
      allowEscapeKey: true,
      customClass: {
        popup: 'border-spotify-modal'
      }
    });
  }
}
