import { CommonModule } from '@angular/common';
import { Component, OnInit, ViewChild, ElementRef, signal } from '@angular/core';
import { SpoticService } from '../../../core/services/spotic.service';
import { Router } from '@angular/router';
import { NoImgPipe } from '../../../shared/pipes/noImg.pipe';

import { AlbumItem } from '../../../shared/models/newReleases';

@Component({
  selector: 'app-newReleases',
  templateUrl: './newReleases.component.html',
  styleUrls: ['./newReleases.component.css'],
  imports: [CommonModule, NoImgPipe],
})
export class newReleasesComponent implements OnInit {
  @ViewChild('carousel', { static: true }) carousel!: ElementRef<HTMLDivElement>;

  newSongs = signal<AlbumItem[]>([]);

  constructor(private spoticService: SpoticService, private router: Router) {
    this.spoticService.getNewReleases().subscribe((responseItems) => {
      this.newSongs.set(responseItems);
    });
  }

  seeArtists(item: AlbumItem) {
    const artistID = item.artistId;

    if (artistID) {
      this.router.navigate(['/artist', artistID]);
    }
  }

  scrollCarousel(direction: number) {
    const carouselEl = this.carousel.nativeElement;
    const card = carouselEl.querySelector('.song-card') as HTMLElement;
    if (!card) return;

    const cardWidth = card.offsetWidth + 16;
    carouselEl.scrollBy({
      left: cardWidth * direction,
      behavior: 'smooth',
    });
  }

  ngOnInit() {}
}
