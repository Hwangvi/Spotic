import { Component, inject } from '@angular/core';
import { AuthService } from './../../../core/services/auth.service';
import { UserProfile } from './../../../shared/models/userProfile';
import { CommonModule } from '@angular/common';
import { LoadingComponent } from '../../../shared/components/loading/loading.component';

@Component({
  selector: 'app-profile-details',
  templateUrl: './profile-details.component.html',
  styleUrls: ['./profile-details.component.css'],
  imports: [CommonModule, LoadingComponent],
})
export class ProfileDetailsComponent {
  private authService = inject(AuthService);

  public userProfile = this.authService.currentUserProfile;

  get profile(): UserProfile | null {
    return this.userProfile();
  }
}
