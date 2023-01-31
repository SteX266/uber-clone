import { Component, OnInit } from '@angular/core';
import {
  latLng,
  tileLayer,
  Map,
  icon,
  marker,
  MapOptions,
  geoJSON,
  FeatureGroup,
} from 'leaflet';
import { MapSearchService } from 'src/app/services/map-search/map-search.service';
import { MapPoint } from '../../models/map-point.model';
import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';
import { LocationService } from 'src/app/services/location/location.service';
import { MatDialog } from '@angular/material/dialog';
import { AuthService } from 'src/app/services/auth/auth.service';
import { SnackBarService } from 'src/app/services/snackbar/snackbar.service';
import { Router } from '@angular/router';
import { RideService } from 'src/app/services/ride/ride.service';
@Component({
  selector: 'app-driver-map',
  templateUrl: './driver-map.component.html',
  styleUrls: ['./driver-map.component.scss'],
})
export class DriverMapComponent implements OnInit {
  constructor(
    private mapService: MapSearchService,
    private locationService: LocationService,
    public modal: MatDialog,
    private authService: AuthService,
    private snackbar: SnackBarService,
    private router: Router,
    private rideService: RideService
  ) {}

  ngOnInit(): void {
    this.initializeMapOptions();
    this.getActiveDriverLocations();
    this.initializeWebSocketConnection();
    this.customers.push(this.authService.getCurrentUserEmail());
  }

  map!: Map;
  options!: MapOptions;
  driversLayer: FeatureGroup = new FeatureGroup();
  markerLayer: FeatureGroup = new FeatureGroup();
  routeLayer: FeatureGroup = new FeatureGroup();

  rideId!: number;

  stops: Array<MapPoint> = new Array<MapPoint>();

  gotRoutes: boolean = false;

  customers: string[] = [];

  routes: any[] = [];
  selectedRoutes: any[] = [];

  private stompClient: any;

  drivers: any = {};

  initializeWebSocketConnection() {
    let ws = new SockJS('http://localhost:8080/socket');
    this.stompClient = Stomp.over(ws);
    this.stompClient.debug = null;
    let that = this;
    this.stompClient.connect({}, function () {
      that.openGlobalSocket();
    });
  }

  initializeMapOptions() {
    this.options = {
      zoom: 14,
      layers: [
        tileLayer(
          'https://tiles.stadiamaps.com/tiles/alidade_smooth_dark/{z}/{x}/{y}{r}.png',
          {
            maxZoom: 18,
            attribution:
              '&copy; <a href="https://stadiamaps.com/">Stadia Maps</a>, &copy; <a href="https://openmaptiles.org/">OpenMapTiles</a> &copy; <a href="http://openstreetmap.org">OpenStreetMap</a> contributors',
          }
        ),
      ],
      center: latLng(45.253434, 19.831323),
    };
  }

  initializeMap(map: Map) {
    this.map = map;
  }

  openGlobalSocket() {
    this.stompClient.subscribe(
      '/location-updates/update-driver-location',
      (message: { body: string }) => {
        let locationDto = JSON.parse(message.body);
        let existingDriver = this.drivers[locationDto.driverId];
        existingDriver.setLatLng([locationDto.longitude, locationDto.latitude]);
        existingDriver.update();
      }
    );
    this.stompClient.subscribe(
      '/location-updates/new-driver',
      (message: { body: string }) => {
        let locationDto = JSON.parse(message.body);
        let driverMarker = marker(
          [locationDto.longitude, locationDto.latitude],
          {
            icon: icon({
              iconUrl: 'assets/taxi.svg',
              iconSize: [20, 20],
              iconAnchor: [10, 10],
            }),
          }
        );
        driverMarker.addTo(this.map);
        this.drivers[locationDto.driverId] = driverMarker;
      }
    );
    this.stompClient.subscribe(
      '/location-updates/end-drive',
      (message: { body: string }) => {
        let driverId = JSON.parse(message.body);
        delete this.drivers[driverId];
      }
    );
    this.stompClient.subscribe(
      '/ride/new-confirmed',
      (message: { body: string }) => {
        let dto: any = JSON.parse(message.body);

        if (dto.driverEmail === this.authService.getCurrentUserEmail()) {
          this.snackbar.openSuccessSnackBar('Got new ride.');
          this.rideId = Number(dto.rideId);
          this.rideService
            .getGeoJsonRouteById(this.rideId)
            .subscribe((route: any) => {
              this.selectedRoutes = route;
              this.showSelectedRoutes();
            });
        }
      }
    );
  }

  Redirect(reservationId: number) {
    let route = '/client/ride/' + reservationId;
    this.router.navigate([route]);
    console.log(route);
  }

  getActiveDriverLocations() {
    this.locationService
      .getActiveDriverLocations()
      .subscribe((locationDtos: any[]) => {
        locationDtos.forEach((locationDto: any) => {
          let driverMarker = marker(
            [locationDto.longitude, locationDto.latitude],
            {
              icon: icon({
                iconUrl: 'assets/taxi.svg',
                iconSize: [20, 20],
                iconAnchor: [10, 10],
              }),
            }
          );
          driverMarker.addTo(this.map);
          this.drivers[locationDto.driverId] = driverMarker;
        });
      });
  }

  routeStyle() {
    return {
      color: 'black',
      weight: 6,
    };
  }

  pinIcon() {
    return {
      icon: icon({
        iconUrl: 'assets/location_pin.svg',
        iconSize: [30, 30],
        iconAnchor: [10, 10],
      }),
    };
  }

  refreshDirections(stops: Array<MapPoint>) {
    this.stops = stops;
    this.clearMap();
    this.routeLayer = new FeatureGroup();
    this.routeLayer.addTo(this.map);
    this.showMarkers();
    this.routes = [];
    this.selectedRoutes = [];
    for (let i = 0; i < stops.length - 1; i++) {
      let start = stops[i];
      let end = stops[i + 1];
      this.mapService
        .directionsWithAlternatives(start, end)
        .subscribe((geoJsonData: any) => {
          let rts: any[] = geoJsonData.features;
          let selectedRoute = rts.at(0);
          this.selectedRoutes.push(selectedRoute);
          this.routes.push(rts);
          this.createRoute(selectedRoute);
        });
    }
    this.gotRoutes = true;
  }

  showSelectedRoutes() {
    this.routeLayer = new FeatureGroup();
    this.selectedRoutes.forEach((route: any) => {
      this.createRoute(route);
    });
    this.routeLayer.addTo(this.map);
  }

  createRoute(route: any) {
    let geoJson = geoJSON(route, { style: this.routeStyle() });
    geoJson.addTo(this.routeLayer);
  }

  createMarker(stop: MapPoint) {
    let coordinates = latLng([stop.latitude, stop.longitude]);
    let m = marker(coordinates, this.pinIcon());
    m.addTo(this.markerLayer);
  }

  showMarkers() {
    this.markerLayer = new FeatureGroup();
    this.stops.forEach((stop) => {
      this.createMarker(stop);
    });
    this.markerLayer.addTo(this.map);
    this.map.fitBounds(this.markerLayer.getBounds());
  }

  totalDistance(routes: any[]) {
    let totalDistance = 0;
    routes.forEach((route: any) => {
      totalDistance += route.properties.summary.distance;
    });
    let kilometers = totalDistance / 1000;

    return kilometers;
  }

  clearMap() {
    this.clearRouteLayer();
    this.clearMarkerLayer();
  }

  clearRouteLayer() {
    if (this.map.hasLayer(this.routeLayer))
      this.map.removeLayer(this.routeLayer);
  }

  clearMarkerLayer() {
    if (this.map.hasLayer(this.markerLayer))
      this.map.removeLayer(this.markerLayer);
  }

  getStops() {
    let strings: string[] = [];
    this.stops.forEach((stop: MapPoint) => {
      let arr = stop.name.split(', ');
      arr.splice(-7, 8);
      let stopName = arr.join(', ');
      strings.push(stopName);
    });
    return strings;
  }
}
