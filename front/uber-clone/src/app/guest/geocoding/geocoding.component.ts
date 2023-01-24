import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { delay } from 'rxjs';
import { MapPoint } from 'src/app/models/map-point.model';
import { MapSearchService } from 'src/app/services/map-search/map-search.service';

@Component({
  selector: 'app-geocoding',
  templateUrl: './geocoding.component.html',
  styleUrls: ['./geocoding.component.scss'],
})
export class GeocodingComponent {
  constructor(private mapSearchService: MapSearchService) {}

  start: string = '';
  destination: string = '';
  timerId: any = undefined;

  @Output() onShowResults = new EventEmitter();

  resultsForStart: Array<MapPoint> = new Array<MapPoint>();
  resultsForDestination: Array<MapPoint> = new Array<MapPoint>();

  @Output() onSearch = new EventEmitter();

  autocomplete(text: string, type: string) {
    if (text.length < 3) return;
    if (type === 'start')
      this.mapSearchService
        .autocomplete(text)
        .subscribe((points: MapPoint[]) => {
          this.resultsForStart = points;
        });
    else
      this.mapSearchService
        .autocomplete(text)
        .subscribe((points: MapPoint[]) => {
          this.resultsForDestination = points;
        });
  }

  trottleAutocomplete(text: string, type: string, timeDelay: number) {
    if (this.timerId) {
      return;
    }
    this.timerId = setTimeout(() => {
      this.autocomplete(text, type);
      this.timerId = undefined;
    }, timeDelay);
  }

  showResults() {
    this.onShowResults.emit(
      this.resultsForStart.concat(this.resultsForDestination)
    );
  }
}
