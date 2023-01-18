import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
@Injectable({
  providedIn: 'root',
})
export class MapSearchService {
  constructor(private httpClient: HttpClient) {}
  search(req: any): Observable<any> {
    let url = `https://nominatim.openstreetmap.org/search?street=${req}&city=Novi Sad&country=Serbia&limit=10&format=json`;
    return this.httpClient.get<any>(url);
  }
}
