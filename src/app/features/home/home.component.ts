import { Component, OnInit } from '@angular/core';
import { HeroComponent } from './hero/hero.component';
import { newReleasesComponent } from './newReleases/newReleases.component';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
  imports: [HeroComponent, newReleasesComponent],
})
export class HomeComponent implements OnInit {
  constructor() {}

  ngOnInit() {}
}
