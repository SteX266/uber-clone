import { Component, OnInit } from '@angular/core';
import { MapPoint } from 'src/app/models/map-point.model';
import { MapSearchService } from 'src/app/services/map-search.service';

@Component({
  selector: 'app-geocoding',
  templateUrl: './geocoding.component.html',
  styleUrls: ['./geocoding.component.scss'],
})
export class GeocodingComponent implements OnInit {
  constructor(private mapSearchService: MapSearchService) {}
  ngOnInit(): void {
    this.resultsStart = new Array<MapPoint>();
    this.resultsDest = new Array<MapPoint>();
  }

  start!: string;
  destination!: string;
  resultsStart!: Array<MapPoint>;
  resultsDest!: Array<MapPoint>;

  resultStart!: MapPoint;
  resultDest!: MapPoint;

  searchStart(text: string) {
    let results: Array<MapPoint> = new Array<MapPoint>();
    if (text.length > 3) {
      this.mapSearchService.search(text).subscribe((data: any) => {
        console.log(data);
        data.forEach((element: any) => {
          let address = element.display_name.split(',');
          results.push(
            new MapPoint(
              this.prettyDisplayAddress(address),
              element.lat,
              element.lon
            )
          );
        });
        console.log(results);
      });
    }
    this.resultsStart = results;
  }

  prettyDisplayAddress(address: Array<string>): string {
    return address[1] + ' ' + address[0];
  }

  searchDestination(text: string) {
    let results: Array<MapPoint> = new Array<MapPoint>();
    if (text.length > 5) {
      this.mapSearchService.search(text).subscribe((data: any) => {
        console.log(data);
        data.forEach((element: any) => {
          results.push(
            new MapPoint(element.display_name, element.lat, element.lon)
          );
        });
        console.log(results);
      });
    }
    this.resultsDest = results;
  }

  calculate() {
    console.log(this.resultsStart);
  }
}
