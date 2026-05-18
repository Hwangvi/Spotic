import { Component, OnInit } from '@angular/core';
import { ProfileDetailsComponent } from './profile-details/profile-details.component';
import { PlaylistsComponent } from './playlists/playlists.component';
import { FavoriteSongsComponent } from './favoriteSongs/favoriteSongs.component';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css'],
  imports: [ProfileDetailsComponent, PlaylistsComponent, FavoriteSongsComponent],
})
export class ProfileComponent implements OnInit {
  constructor() {}

  ngOnInit() {}
}
