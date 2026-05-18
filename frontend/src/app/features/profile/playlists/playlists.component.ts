import { Component, OnInit, inject } from '@angular/core';
import { SpoticService } from '../../../core/services/spotic.service';
import { Playlists } from '../../../shared/models/playlists';
import { Observable } from 'rxjs';
import { CommonModule } from '@angular/common';
import { LoadingComponent } from '../../../shared/components/loading/loading.component';
import { NoImgPipe } from '../../../shared/pipes/noImg.pipe';

@Component({
  selector: 'app-playlists',
  templateUrl: './playlists.component.html',
  styleUrls: ['./playlists.component.css'],
  imports: [CommonModule, LoadingComponent, NoImgPipe],
})
export class PlaylistsComponent implements OnInit {
  private spoticService = inject(SpoticService);

  public playlists$!: Observable<Playlists[]>;

  ngOnInit(): void {
    this.playlists$ = this.spoticService.getUserPlaylists();
  }
}
