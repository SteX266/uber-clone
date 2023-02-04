import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { RouteService } from 'src/app/services/route/route.service';
import { FavoriteRouteDTO } from '../../../models/favorite-route-dto.model';

@Component({
  selector: 'app-favorite-route-modal',
  templateUrl: './favorite-route-modal.component.html',
  styleUrls: ['./favorite-route-modal.component.scss'],
})
export class FavoriteRouteModalComponent implements OnInit {
  constructor(
    private routeService: RouteService,
    public modalRef: MatDialogRef<FavoriteRouteModalComponent>
  ) {}
  ngOnInit(): void {
    this.initializeRoutes();
  }
  routes: FavoriteRouteDTO[] = [];
  selectedRoute?: FavoriteRouteDTO;

  initializeRoutes() {
    this.routeService
      .getFavoriteRoutes()
      .subscribe((routes: FavoriteRouteDTO[]) => {
        this.routes = routes;
      });
  }

  selectRoute(route: FavoriteRouteDTO) {
    this.selectedRoute = route;
  }

  showRoute() {
    this.modalRef.close(this.selectedRoute);
  }
  cancel() {
    this.modalRef.close();
  }
}
