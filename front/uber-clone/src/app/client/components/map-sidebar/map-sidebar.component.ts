import { Component, EventEmitter, Output } from '@angular/core';

import { MapPoint } from 'src/app/models/map-point.model';
import { MapSearchService } from 'src/app/services/map-search/map-search.service';

@Component({
  selector: 'app-map-sidebar',
  templateUrl: './map-sidebar.component.html',
  styleUrls: ['./map-sidebar.component.scss'],
})
export class MapSidebarComponent {
  constructor(private mapSearchService: MapSearchService) {}

  start: string = '';
  destination: string = '';

  stops: string[] = [];

  timerId: any = undefined;

  @Output() onShowResults = new EventEmitter();

  resultsForStart: Array<MapPoint> = new Array<MapPoint>();
  resultsForDestination: Array<MapPoint> = new Array<MapPoint>();
  resultsForStops: Array<Array<MapPoint>> = new Array<Array<MapPoint>>();

  @Output() onSearch = new EventEmitter();

  customTrackBy(index: number, obj: any): any {
    return index;
  }

  autocomplete(text: string, type: string, index: number) {
    if (text.length < 3) return;
    if (type === 'start')
      this.mapSearchService
        .autocomplete(text)
        .subscribe((points: MapPoint[]) => {
          this.resultsForStart = points;
        });
    else if (type === 'destination')
      this.mapSearchService
        .autocomplete(text)
        .subscribe((points: MapPoint[]) => {
          this.resultsForDestination = points;
        });
    else
      this.mapSearchService
        .autocomplete(text)
        .subscribe((points: MapPoint[]) => {
          this.resultsForStops[index] = points;
        });
  }

  trottleAutocomplete(
    text: string,
    type: string,
    index: number,
    timeDelay: number
  ) {
    if (this.timerId) {
      return;
    }
    this.timerId = setTimeout(() => {
      this.autocomplete(text, type, index);
      this.timerId = undefined;
    }, timeDelay);
  }

  showResults() {
    let all_results = Array<MapPoint>();
    all_results.push(this.resultsForStart[0]);
    this.resultsForStops.forEach((results: Array<MapPoint>) => {
      all_results.push(results[0]);
    });
    all_results.push(this.resultsForDestination[0]);
    console.log(all_results);
    this.onShowResults.emit(all_results);
  }

  addStop() {
    this.stops.push('');
    this.resultsForStops.push(new Array<MapPoint>());
  }

  removeStop(i: number) {
    this.stops.splice(i, 1);
    this.resultsForStops.splice(i, 1);
    console.log(this.resultsForStops);
  }
}
