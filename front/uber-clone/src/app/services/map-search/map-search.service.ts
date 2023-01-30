import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { map, Observable } from 'rxjs';
import { MapPoint } from '../../models/map-point.model';
@Injectable({
  providedIn: 'root',
})
export class MapSearchService {
  API_KEY = '5b3ce3597851110001cf6248fadb036acdb548c5977c319c02c25c24';
  constructor(private httpClient: HttpClient) {}
  autocomplete(text: string): Observable<Array<MapPoint>> {
    let url = `https://nominatim.openstreetmap.org/search?q=${text}&city=Novi Sad&limit=3&format=json`;
    return this.httpClient
      .get<Array<MapPoint>>(url)
      .pipe(
        map((data: any[]) =>
          data.map(
            (item) => new MapPoint(item.display_name, item.lat, item.lon)
          )
        )
      );
  }

  directions(start: MapPoint, end: MapPoint) {
    let header = new HttpHeaders({
      Authorization: '5b3ce3597851110001cf6248fadb036acdb548c5977c319c02c25c24',
    });
    let url = `https://api.openrouteservice.org/v2/directions/driving-car?api_key=${this.API_KEY}&start=${start.longitude},${start.latitude}&end=${end.longitude},${end.latitude}`;
    return this.httpClient.get(url);
  }

  directionsWithAlternatives(start: MapPoint, end: MapPoint) {
    let headers = new HttpHeaders({
      Authorization: this.API_KEY,
    });
    headers.append('Content-Type', 'application/json');
    headers.append(
      'Accept',
      'application/json, application/geo+json, application/gpx+xml, img/png; charset=utf-8'
    );
    let body = {
      coordinates: [start.getCoordinates(), end.getCoordinates()],
      alternative_routes: { target_count: 3 },
    };
    let requestOptions = { headers: headers };
    let url =
      'https://api.openrouteservice.org/v2/directions/driving-car/geojson';
    return this.httpClient.post(url, body, requestOptions);
  }

  directionsWithAlternativesForMorePoints(points: Array<MapPoint>) {
    let coordinates: any[] = [];
    points.forEach((point: MapPoint) => {
      coordinates.push(point.getCoordinates());
    });
    let headers = new HttpHeaders({
      Authorization: this.API_KEY,
    });
    headers.append('Content-Type', 'application/json');
    headers.append(
      'Accept',
      'application/json, application/geo+json, application/gpx+xml, img/png; charset=utf-8'
    );
    let body = {
      coordinates: coordinates,
    };
    let requestOptions = { headers: headers };
    let url =
      'https://api.openrouteservice.org/v2/directions/driving-car/geojson';
    return this.httpClient.post(url, body, requestOptions);
  }
}
