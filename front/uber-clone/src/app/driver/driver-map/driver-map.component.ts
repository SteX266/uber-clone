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
  Location,
} from 'src/app/models/geo-json-feature.model';
import { RandomLocationService } from 'src/app/services/random-location/random-location.service';
import { RideState } from 'src/app/models/ride-state-enum.model';
import { AbortDTO } from 'src/app/models/abort-dto.model';
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
    private rideService: RideService,
    private shiftService: ShiftService,
    private randomLocationService: RandomLocationService
  ) {}

  ngOnInit(): void {
    this.startShift();
    this.initializeMapOptions();
    this.initializeWebSocketConnection();
  }

  map!: Map;
  options!: MapOptions;
  driversLayer: FeatureGroup = new FeatureGroup();
  markerLayer: FeatureGroup = new FeatureGroup();
  routeLayer: FeatureGroup = new FeatureGroup();
  routeToStartLayer: FeatureGroup = new FeatureGroup();
  available: boolean = false;

  rideId!: number;
  driverId: number = Number(this.authService.getCurrentUserId());

  driverMarker!: any;
  currentLocation: Location = this.randomLocationService.getRandomLocation();

  stops: Array<MapPoint> = new Array<MapPoint>();

  routes: any[] = [];

  routeToStart!: any;

  rideState: RideState = RideState.WAITING;

  private stompClient: any;

  drivers: any = {};
  toggleAvailable(toggledValue: boolean) {
    this.available = toggledValue;
    console.log(this.available);
  }

  initializeDriverMarker() {
    console.log(this.currentLocation);
    this.driverMarker = marker(
      [this.currentLocation.latitude, this.currentLocation.longitude],
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
    console.log(this.currentLocation);
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
          this.currentLocation.longitude,
          this.currentLocation.latitude
        )
      )
      .subscribe((data: any) => {
        console.log(data);
      });
  } // !

  endRide() {
    this.clearState();
    this.rideService.endRide(this.rideId).subscribe((data: any) => {
      console.log(data);
    });
  } // !

  clearState() {
    this.clearMap();
    this.routeToStart = [];
    this.routes = [];
    this.rideState = RideState.WAITING;
    this.stops = [];
  }

  rejectRide() {
    this.clearMap();
    this.rideService.abortRide(this.rideId, '').subscribe((data: any) => {
      console.log(data);
    });
  }

  startRide() {
    this.rideState = RideState.ONGOING;
    this.rideService.startRide(this.rideId).subscribe((data: any) => {
      console.log(data);
    });
    let total = 0;
    let totalPoints = 0;
    this.routes.forEach((feature: any) => {
      totalPoints += feature.geometry.coordinates.length - 1;
      totalPoints += 9;
    });
    this.routes.forEach((feature: any) => {
      feature.geometry.coordinates.forEach(
        (coordinate: number[], index: number) => {
          if (index == 0) total += 9;
          total += 1;
          this.updateLocation(coordinate, total, false, total >= totalPoints);
        }
      );
    });
  }

  drivingToStart() {
    this.rideState = RideState.ARRIVING;
    this.routeToStart.features.forEach((feature: any) => {
      feature.geometry.coordinates.forEach(
        (coordinate: number[], index: number) => {
          this.updateLocation(
            coordinate,
            index,
            feature.geometry.coordinates.length - 1 <= index,
            false
          );
        }
      );
    });
  }

  updateLocation(
    coordinates: number[],
    index: number,
    arriving: boolean,
    finished: boolean
  ) {
    setTimeout(() => {
      let location = new Location(coordinates[1], coordinates[0]);
      this.updateDriverMarkerLocation(location);
      if (arriving) {
        this.rideState = RideState.ARRIVED;
        // this.rideService.driverArrived(this.rideId).subscribe((data: any) => {
        //   console.log(data);
        // });
      }
      if (finished) {
        this.rideState = RideState.FINISHED;
      }
      this.locationService
        .updateLocation(
          new LocationDTO(
            Number(this.authService.getCurrentUserId()),
            coordinates[0],
            coordinates[1]
          )
        )
        .subscribe((data: any) => {
          console.log(data);
        });
    }, 1000 * index);
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

  getDirectionsToStart(routeStart: number[]) {
    let start = new MapPoint(
      '',
      this.currentLocation.latitude,
      this.currentLocation.longitude
    );
    this.mapService
      .directions(start, new MapPoint('', routeStart[1], routeStart[0]))
      .subscribe((geoJsonData: any) => {
        this.routeToStart = geoJsonData;
        console.log(geoJsonData);
        this.drivingToStart();
      });
  }

  openGlobalSocket() {
    this.stompClient.subscribe(
      '/location-updates/update-driver-location',
      (message: { body: string }) => {
        let locationDTO: LocationDTO = JSON.parse(message.body);
        if (this.driverId == locationDTO.driverId) return;
        let existingDriver = this.drivers[locationDTO.driverId];
        existingDriver.setLatLng([locationDTO.latitude, locationDTO.longitude]);
        existingDriver.update();
      }
    );
    this.stompClient.subscribe(
      '/location-updates/new-driver',
      (message: { body: string }) => {
        let locationDTO: LocationDTO = JSON.parse(message.body);
        if (this.driverId == locationDTO.driverId) return;
        let driverMarker = marker(
          [locationDTO.latitude, locationDTO.longitude],
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
          this.rideState = RideState.ARRIVING;
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

  getActiveDriverLocations() {
    this.locationService
      .getActiveDriverLocations()
      .subscribe((locationDtos: any[]) => {
        locationDtos.forEach((locationDto: any) => {
          let driverMarker = marker(
            [locationDto.latitude, locationDto.latitude],
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
