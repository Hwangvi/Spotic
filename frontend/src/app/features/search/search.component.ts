import { Component, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SpoticService } from './../../core/services/spotic.service';
import { SearchBarComponent } from './search-bar/search-bar.component';
import { ArtistBlockComponent } from './artistBlock/artistBlock.component';

@Component({
  selector: 'app-search',
  standalone: true,
  imports: [CommonModule, SearchBarComponent, ArtistBlockComponent],
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css'],
})
export class SearchComponent {
  artists = signal<any[]>([]);
  loading = signal(false);

  constructor(private spoticService: SpoticService) {}

  handleSearch(text: string) {
    if (text.length > 0) {
      this.loading.set(true);
      this.spoticService.getArtists(text).subscribe({
        next: (res: any) => {
          this.artists.set(res);
          this.loading.set(false);
        },
        error: (err) => {
          console.error('Error fetching artists:', err);
          this.loading.set(false);
        },
      });
    } else {
      this.artists.set([]);
    }
  }
}
