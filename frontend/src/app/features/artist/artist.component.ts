import { FavoritesService } from '../../core/services/favorites.service';
import { Component, OnInit, inject } from '@angular/core';
import { ActivatedRoute, RouterLink, RouterModule } from '@angular/router';
import { SpoticService } from '../../core/services/spotic.service';
import { LoadingComponent } from '../../shared/components/loading/loading.component';
import { SecureDomPipe } from '../../shared/pipes/secure-dom.pipe';
import { NoImgPipe } from '../../shared/pipes/noImg.pipe';
import { CommonModule } from '@angular/common';

import { Artist } from '../../shared/models/artist';
import { Track } from '../../shared/models/track';

import Swal from 'sweetalert2';

@Component({
  selector: 'app-artist',
  imports: [LoadingComponent, SecureDomPipe, NoImgPipe, CommonModule, RouterLink, RouterModule],
  templateUrl: './artist.component.html',
  styleUrl: './artist.component.css',
})
export class ArtistComponent implements OnInit {
  private route = inject(ActivatedRoute);
  private spoticService = inject(SpoticService);
  public favoritesService = inject(FavoritesService);

  artist?: Artist;
  topTracks: Track[] = [];
  loading: boolean;

  constructor() {
    this.loading = true;
  }

  ngOnInit(): void {
    this.route.params.subscribe((params) => {
      const artistId = params['id'];
      this.getArtistData(artistId);
      this.getArtistTopTracks(artistId);
    });
  }

  getArtistData(id: string) {
    this.loading = true;
    this.spoticService.getArtist(id).subscribe((artistFromApi) => {
      console.log(artistFromApi);
      this.artist = artistFromApi;
      this.loading = false;
    });
  }

  getArtistTopTracks(id: string) {
    this.spoticService.getTopTracks(id).subscribe((tracksFromApi) => {
      this.topTracks = tracksFromApi;
      console.log('Top tracks recibidos:', this.topTracks);
    });
  }

  toggleFavorite(track: Track): void {
    if (this.favoritesService.isFavorite(track.id)) {
      Swal.fire({
        title: 'Quitar de favoritos',
        text: `¿Seguro que quieres quitar "${track.name}" de tus favoritos?`,
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#1DB954',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Sí, quitar',
        cancelButtonText: 'Cancelar'
      }).then((result) => {
        if (result.isConfirmed) {
          this.favoritesService.removeFavorite(track.id);
        }
      });
    } else {
      Swal.fire({
        title: 'Añadir a favoritos',
        text: `¿Quieres añadir "${track.name}" a tus favoritos?`,
        icon: 'question',
        showCancelButton: true,
        confirmButtonColor: '#1DB954',
        cancelButtonColor: '#aaa',
        confirmButtonText: 'Sí, añadir',
        cancelButtonText: 'Cancelar'
      }).then((result) => {
        if (result.isConfirmed) {
          this.favoritesService.addFavorite(track);
        }
      });
    }
  }

}
