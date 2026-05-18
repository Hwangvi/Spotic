import { FavoritesService } from './../../core/services/favorites.service';
import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { Track } from '../../shared/models/track';
import { SecureDomPipe } from '../../shared/pipes/secure-dom.pipe';
import { NoImgPipe } from '../../shared/pipes/noImg.pipe';
import Swal from 'sweetalert2'

@Component({
  selector: 'app-saves',
  templateUrl: './saves.component.html',
  styleUrls: ['./saves.component.css'],
  imports: [CommonModule, RouterModule, SecureDomPipe, NoImgPipe]
})
export class SavesComponent {
  favoritesService = inject(FavoritesService);

  confirmRemoveFavorite(track: Track): void {
    Swal.fire({
      title: '¿Estás seguro?',
      text: `¿Quieres quitar "${track.name}" de tus favoritos?`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#1DB954',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Sí, ¡quítala!',
      cancelButtonText: 'Cancelar'
    }).then((result) => {
      if (result.isConfirmed) {
        this.favoritesService.removeFavorite(track.id);
        Swal.fire(
          '¡Eliminada!',
          `"${track.name}" ha sido eliminada de tus favoritos.`,
          'success'
        );
      }
    });
  }
}
