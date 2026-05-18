import { Component, OnInit, inject } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';
import { take } from 'rxjs';

@Component({
  selector: 'app-hero',
  templateUrl: './hero.component.html',
  styleUrls: ['./hero.component.css']
})
export class HeroComponent implements OnInit {

  private authService = inject(AuthService);
  private router = inject(Router);

  ngOnInit() {

    this.authService.checkAuthStatus().pipe(take(1)).subscribe(isAuthenticated => {
      if (isAuthenticated) {
        this.router.navigate(['/home']);
      }
    });
  }
}
