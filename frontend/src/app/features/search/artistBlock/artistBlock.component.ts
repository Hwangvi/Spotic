import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
import { Router } from '@angular/router';
import { NoImgPipe } from '../../../shared/pipes/noImg.pipe';

@Component({
  selector: 'app-artist-block',
  standalone: true,
  templateUrl: './artistBlock.component.html',
  styleUrls: ['./artistBlock.component.css'],
  imports: [CommonModule, NoImgPipe],
})
export class ArtistBlockComponent {
  @Input() artists: any[] = [];

  constructor(private router: Router) {}

  viewArtist(id: string) {
    this.router.navigate(['/artist', id]);
  }
}
