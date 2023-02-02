import { Injectable } from '@angular/core';
import { Location } from 'src/app/models/geo-json-feature.model';

@Injectable({
  providedIn: 'root',
})
export class RandomLocationService {
  taxiStops = [
    [45.238548, 19.848225], // Stajaliste na keju
    [45.243097, 19.836284], // Stajaliste kod limanske pijace
    [45.256863, 19.844129], // Stajaliste kod trifkovicevog trga
    [45.255055, 19.810161], // Stajaliste na telepu
    [45.24654, 19.849282], // Stajaliste kod velike menze
  ];

  constructor() {}

  getRandomLocation(): Location {
    let coordinates =
      this.taxiStops[this.getRandomInt(0, this.taxiStops.length - 1)];
    return new Location(coordinates[0], coordinates[1]);
  }

  getRandomInt(min: number, max: number) {
    min = Math.ceil(min);
    max = Math.floor(max);
    return Math.floor(Math.random() * (max - min + 1)) + min;
  }
}
