import { Component, OnInit, inject } from '@angular/core';
import { SpoticService } from '../../../core/services/spotic.service';
import { Track } from '../../../shared/models/track';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { CommonModule } from '@angular/common';
import { SecureDomPipe } from '../../../shared/pipes/secure-dom.pipe';
import { LoadingComponent } from '../../../shared/components/loading/loading.component';

@Component({
  selector: 'app-favoriteSongs',
  templateUrl: './favoriteSongs.component.html',
  styleUrls: ['./favoriteSongs.component.css'],
  imports: [CommonModule, SecureDomPipe, LoadingComponent],
})
export class FavoriteSongsComponent implements OnInit {
  private spoticService = inject(SpoticService);
  public topTracks$!: Observable<Track[]>;

  ngOnInit(): void {
    this.topTracks$ = this.spoticService.getUserTopTracks().pipe(
      map(tracks => tracks.slice(0, 9))
    );
  }

}
