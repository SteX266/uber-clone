import { Component, OnDestroy, OnInit } from '@angular/core';
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
import { MapPoint } from '../../../models/map-point.model';
import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';
import { LocationService } from 'src/app/services/location/location.service';
import { MatDialog } from '@angular/material/dialog';
import { ReservationPreviewComponent } from '../reservation-preview/reservation-preview.component';
import { PreviewData } from 'src/app/models/preview-data.model';
import { AuthService } from 'src/app/services/auth/auth.service';
import { ReservationDTO } from 'src/app/models/reservation-dto.model';
import { PaymentDTO } from 'src/app/models/payment-dto.model';
import { PaymentModalComponent } from '../payment-modal/payment-modal.component';
import { SnackBarService } from 'src/app/services/snackbar/snackbar.service';
import { Router } from '@angular/router';

import {
  GeoJsonFeature,
  GeoJsonFeatureCollection,
} from 'src/app/models/geo-json-feature.model';
import { FavoriteRouteModalComponent } from '../favorite-route-modal/favorite-route-modal.component';
import { FavoriteRouteDTO } from 'src/app/models/favorite-route-dto.model';
@Component({
  selector: 'app-client-map',
  templateUrl: './client-map.component.html',
  styleUrls: ['./client-map.component.scss'],
})
export class ClientMapComponent implements OnInit, OnDestroy {
  constructor(
    private mapService: MapSearchService,
    private locationService: LocationService,
    public modal: MatDialog,
    private authService: AuthService,
    private snackbar: SnackBarService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.initializeMapOptions();
    this.getActiveDriverLocations();
    this.initializeWebSocketConnection();
    this.initializeReservationDTO();
  }

  ngOnDestroy(): void {
    this.closeSocket();
  }

  map!: Map;
  options!: MapOptions;
  driversLayer: FeatureGroup = new FeatureGroup();
  markerLayer: FeatureGroup = new FeatureGroup();
  routeLayer: FeatureGroup = new FeatureGroup();

  reservationDTO: ReservationDTO = new ReservationDTO();

  PRICE_COEF = 120;
  CAR_PRICE_MAP = {
    ANY: 100,
    REGULAR: 50,
    PREMIUM: 200,
    ECO: 150,
  };

  minTime: Date = this.createMinTime();
  maxTime: Date = this.createMaxTime();

  stops: Array<MapPoint> = new Array<MapPoint>();

  gotRoutes: boolean = false;
  gotFavoriteRoute: boolean = false;

  hasPet: boolean = false;
  hasBaby: boolean = false;

  customer: string = this.authService.getCurrentUserEmail();
  routes: GeoJsonFeature[][] = [];
  selectedRoutes: GeoJsonFeature[] = [];

  private stompClient: any;

  drivers: any = {};
  initializeReservationDTO() {
    this.reservationDTO.customers = [this.customer];
    this.reservationDTO.hasBaby = false;
    this.reservationDTO.hasPet = false;
    this.reservationDTO.stops = new Array<string>();
    this.reservationDTO.reservationType = 'INSTANT';
    this.reservationDTO.vehicleType = 'ANY';
    this.reservationDTO.reservationTime = new Date();
  }

  createMinTime() {
    let time = new Date();
    let minutes = time.getMinutes() + 10;
    time.setMinutes(minutes);
    return time;
  }
  createMaxTime() {
    let time = new Date();
    let hours = time.getHours() + 5;
    time.setHours(hours);
    return time;
  }

  limitTime() {
    let date = new Date(this.reservationDTO.reservationTime);
    if (date.getMilliseconds() > this.maxTime.getMilliseconds())
      this.reservationDTO.reservationTime = this.maxTime;
    if (date.getMilliseconds() < this.minTime.getMilliseconds())
      this.reservationDTO.reservationTime = this.minTime;
  }

  closeSocket() {
    this.stompClient.disconnect(() => {
      this.snackbar.openSuccessSnackBar('UGASIO SOCKET!');
    });
  }

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
      '/payment/payment-made',
      (message: { body: string }) => {
        let paymentDto: PaymentDTO = JSON.parse(message.body);
        if (
          paymentDto.customerEmail === this.authService.getCurrentUserEmail()
        ) {
          this.openPaymentDialog(paymentDto);
        }
      }
    );
    this.stompClient.subscribe(
      '/payment/all-confirmed',
      (message: { body: string }) => {
        let dto: any = JSON.parse(message.body);
        if (dto.customerEmail === this.authService.getCurrentUserEmail()) {
          if (dto.canceled) {
            this.snackbar.openFailureSnackBar('Payment canceled');
          } else {
            this.snackbar.openSuccessSnackBar('Reservation confirmed.');
            if (dto.rideId == -1) {
              this.redirect('/client');
            } else {
              this.redirect('/client/ride/' + dto.rideId);
            }
          }
        }
      }
    );
  }

  redirect(route: string) {
    this.router.navigate([route]);
  }

  openPaymentDialog(paymentDTO: PaymentDTO) {
    this.modal.open(PaymentModalComponent, {
      width: '300px',
      data: paymentDTO,
    });
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
        .subscribe((geoJsonData: GeoJsonFeatureCollection) => {
          let rts: GeoJsonFeature[] = geoJsonData.features;

          let selectedRoute: GeoJsonFeature = rts[0];
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
      this.getPriceCoeficient(this.reservationDTO.vehicleType) +
      totalDistance * 120;
    return totalPrice;
  }

  getPriceCoeficient(vehicleType: string | undefined) {
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
    this.reservationDTO.customers.push(person);
  }

  removePerson(index: number) {
    this.reservationDTO.customers.splice(index, 1);
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

  getGeoJsonStringRoutes() {
    let geoJsonData: string[] = [];
    this.selectedRoutes.forEach((route: any) => {
      geoJsonData.push(JSON.stringify(route));
    });
    return geoJsonData;
  }

  openPreview() {
    if (!this.gotFavoriteRoute) this.setReservationData();
    this.reservationDTO.estimatedCost = this.price();
    this.createModalWithData(this.reservationDTO);
  }

  setReservationData() {
    this.reservationDTO.stops = this.getStops();
    this.reservationDTO.routeGeoJson = this.getGeoJsonStringRoutes();
    this.reservationDTO.distance = this.totalDistance(this.selectedRoutes);
    this.reservationDTO.estimatedTime = this.totalEstimatedTimeInMinutes();
    this.reservationDTO.startCoordinates = this.stops[0].getCoordinates();
    this.reservationDTO.endCoordinates =
      this.stops[this.stops.length - 1].getCoordinates();
  }
  createModalWithData(reservationDTO: ReservationDTO) {
    console.log(reservationDTO);
    this.modal.open(ReservationPreviewComponent, {
      width: '500px',
      data: reservationDTO,
    });
  }

  openFavoriteRouteModal() {
    const modalRef = this.modal.open(FavoriteRouteModalComponent, {
      width: '500px',
      height: '500px',
    });
    modalRef.afterClosed().subscribe((route: FavoriteRouteDTO) => {
      this.selectedRoutes = [];
      route.routeGeoJson.forEach((geoJson: string) => {
        this.selectedRoutes.push(JSON.parse(geoJson));
      });
      this.showSelectedRoutes();
      this.map.fitBounds(this.routeLayer.getBounds());
      this.reservationDTO = new ReservationDTO(
        route.stops,
        new Date(),
        route.routeGeoJson,
        [this.customer],
        'ANY',
        'INSTANT',
        false,
        false,
        route.distance,
        route.estimatedTime,
        route.distance,
        route.startCoordinates,
        route.endCoordinates
      );
      this.gotRoutes = true;
      this.gotFavoriteRoute = true;
    });
  }
}
