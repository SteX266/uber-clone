import { Component } from '@angular/core';

@Component({
  selector: 'app-form-map-container',
  templateUrl: './form-map-container.component.html',
  styleUrls: ['./form-map-container.component.scss'],
})
export class FormMapContainerComponent {
  activeCard: string = 'drive';
  drivePath: string = 'assets/drive-active.svg';
  ridePath: string = 'assets/ride-inactive.svg';
  mapPath: string = 'assets/map-inactive.svg';
  mapClass: string = '';

  start: string = '';
  destination: string = '';

  changeActiveCard(card: string) {
    this.activeCard = card;
    switch (card) {
      case 'drive':
        this.drivePath = 'assets/drive-active.svg';
        this.ridePath = 'assets/ride-inactive.svg';
        this.mapPath = 'assets/map-inactive.svg';
        this.mapClass = '';
        break;
      case 'ride':
        this.drivePath = 'assets/drive-inactive.svg';
        this.ridePath = 'assets/ride-active.svg';
        this.mapPath = 'assets/map-inactive.svg';
        this.mapClass = '';
        break;
      case 'map':
        this.drivePath = 'assets/drive-inactive.svg';
        this.ridePath = 'assets/ride-inactive.svg';
        this.mapPath = 'assets/map-active.svg';
        this.mapClass = 'map-container';
        break;

      default:
        break;
    }
  }

  calculateCost() {
    alert(this.start + this.destination);
  }
}