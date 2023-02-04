import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { FavoriteRouteDTO } from 'src/app/models/favorite-route-dto.model';
import { environment } from 'src/environments/environment';
import { AuthService } from '../auth/auth.service';

@Injectable({
  providedIn: 'root',
})
export class RouteService {
  constructor(private http: HttpClient, private authService: AuthService) {}

  getFavoriteRoutes() {
    let url =
      environment.apiEndpoint +
      'route/getFavoriteRoutes/' +
      this.authService.getCurrentUserId();
    return this.http.get<FavoriteRouteDTO[]>(
      url,
      this.authService.getHttpOptionsWithToken()
    );
  }
}
