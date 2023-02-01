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
  Marker,
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
import { ShiftService } from 'src/app/services/shift/shift.service';
import { LocationDTO } from 'src/app/models/location-dto.model';
import {
  GeoJsonFeature,
  GeoJsonFeatureCollection,
  Location,
} from 'src/app/models/geo-json-feature.model';
import { RandomLocationService } from 'src/app/services/random-location/random-location.service';
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
    private rideService: RideService,
    private shiftService: ShiftService,
    private randomLocationService: RandomLocationService
  ) {}

  ngOnInit(): void {
    this.initializeMapOptions();
    this.initializeWebSocketConnection();
  }

  map!: Map;
  options!: MapOptions;
  driversLayer: FeatureGroup = new FeatureGroup();
  markerLayer: FeatureGroup = new FeatureGroup();
  routeLayer: FeatureGroup = new FeatureGroup();
  routeToStartLayer: FeatureGroup = new FeatureGroup();

  rideId!: number;
  driverId: number = Number(this.authService.getCurrentUserId());

  driverMarker!: any;
  currentLocation: Location = this.randomLocationService.getRandomLocation();

  stops: Array<MapPoint> = new Array<MapPoint>();

  routes: any[] = [];

  routeToStart!: GeoJsonFeatureCollection;

  drivingToStart: boolean = false;

  arrivedAtStart: boolean = false;

  arrivedAtDestination: boolean = false;

  private stompClient: any;

  drivers: any = {};

  // trenutna lokacija vozaca nasumicna iz liste lokacija
  // ceka da mu se dodeli voznja
  // dobaviti rutu od trenutne lokacije do pocetne tacke voznje koja mu je dodeljena
  // simulirati kretanje od trenutne lokacije vozaca do pocetne tacke voznje
  // kada vozac stigne na pocetnu tacku rute obavestavaju se korisnici da je stigao na pocetnu tacku
  // lokacija ostaje ista dok vozac ne pritisne dugme start ride koje mu se pojavi kada dodje na pocetnu tacku rute
  // kada pritisne dugme start ride pocinje voznju i ide do krajnje tacke voznje
  // lokacija ostaje ista dok vozac ne pritisne dugme end ride kojom potvrdjuje da je voznja zavrsena
  // dodeljuje mu se nova lokacija ka kojoj se krece

  initializeDriverMarker() {
    this.driverMarker = marker(
      [this.currentLocation.longitude, this.currentLocation.latitude],
      {
        icon: icon({
          iconUrl: 'assets/taxi.svg',
          iconSize: [30, 30],
          iconAnchor: [10, 10],
        }),
      }
    );
    this.driverMarker.addTo(this.map);
  }

  updateDriverMarkerLocation(location: Location) {
    this.currentLocation = location;
    this.driverMarker.setLatLng([location.latitude, location.longitude]);
    this.driverMarker.update();
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

  startShift() {
    this.shiftService
      .startShift(
        new LocationDTO(
          this.driverId,
          this.currentLocation.latitude,
          this.currentLocation.longitude
        )
      )
      .subscribe((data: any) => {
        console.log(data);
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
    this.initializeDriverMarker();
  }

  openGlobalSocket() {
    this.stompClient.subscribe(
      '/location-updates/update-driver-location',
      (message: { body: string }) => {
        let locationDTO: LocationDTO = JSON.parse(message.body);
        if (this.driverId == locationDTO.driverId) return;
        let existingDriver = this.drivers[locationDTO.driverId];
        existingDriver.setLatLng([locationDTO.longitude, locationDTO.latitude]);
        existingDriver.update();
      }
    );
    this.stompClient.subscribe(
      '/location-updates/new-driver',
      (message: { body: string }) => {
        let locationDTO: LocationDTO = JSON.parse(message.body);
        if (this.driverId == locationDTO.driverId) return;
        let driverMarker = marker(
          [locationDTO.longitude, locationDTO.latitude],
          {
            icon: icon({
              iconUrl: 'assets/taxi.svg',
              iconSize: [20, 20],
              iconAnchor: [10, 10],
            }),
          }
        );
        driverMarker.addTo(this.map);
        this.drivers[locationDTO.driverId] = driverMarker;
      }
    );
    this.stompClient.subscribe(
      '/location-updates/end-drive',
      (driverId: number) => {
        delete this.drivers[driverId];
      }
    );
    this.stompClient.subscribe(
      '/ride/new-ride',
      (message: { body: string }) => {
        let dto: any = JSON.parse(message.body);
        if (dto.driverEmail === this.authService.getCurrentUserEmail()) {
          this.snackbar.openSuccessSnackBar('Got new ride.');
          this.rideId = Number(dto.rideId);
          this.rideService
            .getGeoJsonRouteById(this.rideId)
            .subscribe((routesStrings: string[]) => {
              routesStrings.forEach((routeString: string) => {
                let route: any = JSON.parse(routeString);
                this.routes.push(route);
              });
              this.showRoutes();
              this.getDirectionsToStart(this.routes[0].geometry.coordinates[0]);
            });
        }
      }
    );
  }

  getDirectionsToStart(routeStart: number[]) {
    let start = new MapPoint(
      '',
      this.currentLocation.longitude,
      this.currentLocation.latitude
    );
    let end = this.mapService
      .directions(start, new MapPoint('', routeStart[1], routeStart[0]))
      .subscribe((geoJsonData: any) => {
        this.routeToStart = geoJsonData;

        this.startDriveToStart();
      });
  }

  startDriveToStart() {
    this.drivingToStart = true;
    this.routeToStart.features.forEach((feature: any) => {
      for (
        let index = 0;
        index < feature.geometry.coordinates.length;
        index++
      ) {
        let location: number[] = feature.geometry.coordinates[index];
        setTimeout(() => {
          this.updateDriverMarkerLocation(
            new Location(location[1], location[0])
          );
        }, 1000 * index);
      }
    });
    this.routeToStart;
    this.arrivedAtStart = true;
  }

  startDriveToDestination() {
    this.arrivedAtStart = false;
    this.routes.forEach((feature: any) => {
      for (
        let index = 0;
        index < feature.geometry.coordinates.length;
        index++
      ) {
        let location: number[] = feature.geometry.coordinates[index];
        setTimeout(() => {
          this.updateDriverMarkerLocation(
            new Location(location[1], location[0])
          );
        }, 1000 * index);
      }
    });
    this.arrivedAtDestination = true;
  }

  getRouteEnd(): Location {
    return this.routes[this.routes.length - 1].geometry.coordinates[
      this.routes[this.routes.length - 1].geometry.coordinates.length - 1
    ];
  }

  Redirect(reservationId: number) {
    let route = '/client/ride/' + reservationId;
    this.router.navigate([route]);
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

  showRoutes() {
    this.routeLayer = new FeatureGroup();
    this.routes.forEach((route: GeoJsonFeature) => {
      this.createRoute(route);
    });
    this.routeLayer.addTo(this.map);
  }

  showRouteToStart() {
    this.routeToStart.features.forEach((route: GeoJsonFeature) => {
      this.createStartRoute(route);
    });
    this.routeToStartLayer.addTo(this.map);
  }

  createRoute(route: any) {
    let geoJson = geoJSON(route, { style: this.routeStyle() });
    geoJson.addTo(this.routeLayer);
  }

  createStartRoute(route: any) {
    let geoJson = geoJSON(route, { style: this.routeStyle() });
    geoJson.addTo(this.routeToStartLayer);
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

  clearMap() {
    this.clearRouteLayer();
    this.clearMarkerLayer();
    this.clearRouteToStartLayer();
  }

  clearRouteLayer() {
    if (this.map.hasLayer(this.routeLayer))
      this.map.removeLayer(this.routeLayer);
  }

  clearRouteToStartLayer() {
    if (this.map.hasLayer(this.routeToStartLayer))
      this.map.removeLayer(this.routeToStartLayer);
  }

  clearMarkerLayer() {
    if (this.map.hasLayer(this.markerLayer))
      this.map.removeLayer(this.markerLayer);
  }
}
