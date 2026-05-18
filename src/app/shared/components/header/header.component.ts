import { AuthService } from './../../../core/services/auth.service';
import { Component, inject, signal } from '@angular/core';
import { RouterLink, RouterLinkActive } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css'],
  imports: [RouterLink, RouterLinkActive, CommonModule],
})
export class HeaderComponent {
  public authService = inject(AuthService);

  public isMenuOpen = signal<boolean>(false);

  login(): void {
    this.closeMenu();
    this.authService.login();
  }

  logout(): void {
    this.closeMenu();
    this.authService.logout().subscribe();
  }

  toggleMenu(): void {
    this.isMenuOpen.update(value => !value);
  }

  closeMenu(): void {
    this.isMenuOpen.set(false);
  }
}
