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
import { ReservationPreviewComponent } from '../reservation-preview/reservation-preview.component';
import { PreviewData } from 'src/app/models/preview-data.model';
import { AuthService } from 'src/app/services/auth/auth.service';
@Component({
  selector: 'app-client-map',
  templateUrl: './client-map.component.html',
  styleUrls: ['./client-map.component.scss'],
})
export class ClientMapComponent implements OnInit {
  constructor(
    private mapService: MapSearchService,
    private locationService: LocationService,
    public modal: MatDialog,
    private authService: AuthService
  ) {}

  openReservationPreviewModal(
    enterAnimationDuration: string,
    exitAnimationDuration: string
  ): void {}
  ngOnInit(): void {
    this.initializeMapOptions();
    this.getActiveDriverLocations();
    this.initializeWebSocketConnection();
    this.people.push(this.authService.getCurrentUserEmail());
  }

  map!: Map;
  options!: MapOptions;
  driversLayer: FeatureGroup = new FeatureGroup();
  markerLayer: FeatureGroup = new FeatureGroup();
  routeLayer: FeatureGroup = new FeatureGroup();

  selectedVehicleType: string = 'ANY';

  PRICE_COEF = 120;
  CAR_PRICE_MAP = {
    ANY: 100,
    REGULAR: 100,
    PREMIUM: 200,
    ECO: 150,
  };

  time: Date = new Date();

  stops: Array<MapPoint> = new Array<MapPoint>();

  gotRoutes: boolean = false;

  hasPet: boolean = false;
  hasBaby: boolean = false;

  people: string[] = [];

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

  selectRoute(route: any, index: number) {
    this.selectedRoutes[index] = route;
    this.clearMap();
    this.showMarkers();
    this.showSelectedRoutes();
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

  timeInMinutesRounded(route: any) {
    var seconds = route.properties.summary.duration;
    var minutes = seconds / 60;

    return Math.round(minutes);
  }

  distanceInKm(route: any) {
    var meters = route.properties.summary.distance;
    var kilometers = meters / 1000;

    return kilometers.toFixed(2);
  }

  price() {
    let totalDistance = this.totalDistance(this.selectedRoutes);
    let totalPrice =
      this.getPriceCoeficient(this.selectedVehicleType) + totalDistance * 120;
    return totalPrice;
  }

  getPriceCoeficient(vehicleType: string) {
    if (vehicleType === 'ANY') return this.CAR_PRICE_MAP.ANY;
    if (vehicleType === 'REGULAR') return this.CAR_PRICE_MAP.REGULAR;
    if (vehicleType === 'PREMIUM') return this.CAR_PRICE_MAP.PREMIUM;
    if (vehicleType === 'ECO') return this.CAR_PRICE_MAP.ECO;
    return this.CAR_PRICE_MAP.ANY;
  }

  totalDistance(routes: any[]) {
    let totalDistance = 0;
    routes.forEach((route: any) => {
      totalDistance += route.properties.summary.distance;
    });
    let kilometers = totalDistance / 1000;

    return kilometers;
  }

  totalEstimatedTimeInMinutes() {
    let totalEstimatedTime = 0;
    this.selectedRoutes.forEach((route: any) => {
      totalEstimatedTime += route.properties.summary.duration;
    });
    var minutes = totalEstimatedTime / 60;
    return minutes;
  }

  addPerson(person: string) {
    console.log(person);
    this.people.push(person);
  }

  removePerson(index: number) {
    console.log(this.people[index]);
    this.people.splice(index, 1);
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
  openPreview() {
    this.modal.open(ReservationPreviewComponent, {
      width: '500px',
      data: new PreviewData(
        this.stops,
        this.totalDistance(this.selectedRoutes),
        this.totalEstimatedTimeInMinutes(),
        this.price(),
        this.hasBaby,
        this.hasPet,
        this.people,
        this.selectedVehicleType,
        this.selectedRoutes
      ),
    });
  }
}
