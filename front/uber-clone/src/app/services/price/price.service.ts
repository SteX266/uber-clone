import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class PriceService {
  constructor(private http: HttpClient) {}

  getPriceRange(distance: number) {
    let url = environment.apiEndpoint + 'price/calculate-price-range';
    return this.http.post(url, distance);
  }
}
