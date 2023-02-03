import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import {
  FeatureGroup,
  geoJSON,
  icon,
  latLng,
  Map,
  MapOptions,
  Marker,
  marker,
  tileLayer,
} from 'leaflet';
import * as SockJS from 'sockjs-client';
import { LocationService } from 'src/app/services/location/location.service';
import { RideService } from 'src/app/services/ride/ride.service';
import { SnackBarService } from 'src/app/services/snackbar/snackbar.service';
import * as Stomp from 'stompjs';
import { ReviewRideModalComponent } from '../../components/review-ride-modal/review-ride-modal.component';
@Component({
  selector: 'app-ride-map',
  templateUrl: './ride-map.component.html',
  styleUrls: ['./ride-map.component.scss'],
})
export class RideMapComponent implements OnInit {
  constructor(
    private route: ActivatedRoute,
    private locationService: LocationService,
    private rideService: RideService,
    private snackbar: SnackBarService,
    private router: Router,
    public modal: MatDialog
  ) {}
  ngOnInit(): void {
    this.initializeSocket();
    this.initializeMapOptions();
    this.route.params.subscribe((params) => {
      this.rideId = Number(params['id']);
      this.getGeoJsonRoute(this.rideId);
      this.getDriverLocation(this.rideId);
    });
  }

  rideId: number = 0;
  stompClient: any;
  driverMarker!: any;
  driverId: number = -1;
  map!: Map;
  routeGeoJson: any[] = [];
  options!: MapOptions;

  routeLayer: FeatureGroup = new FeatureGroup();
  markerLayer: FeatureGroup = new FeatureGroup();

  initializeSocket() {
    let ws = new SockJS('http://localhost:8080/socket');
    this.stompClient = Stomp.over(ws);
    this.stompClient.debug = null;
    let that = this;
    this.stompClient.connect({}, function () {
      that.openGlobalSocket();
    });
  }

  initializeMap(map: Map) {
    this.map = map;
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

  getGeoJsonRoute(rideId: number) {
    this.rideService
      .getGeoJsonRouteById(rideId)
      .subscribe((routeGeoJsonData: string[]) => {
        routeGeoJsonData.forEach((routeStr: string) => {
          this.routeGeoJson.push(JSON.parse(routeStr));
        });
        console.log(this.routeGeoJson);
        this.createRoutes();
      });
  }

  createRoutes() {
    this.routeLayer = new FeatureGroup();
    this.routeGeoJson.forEach((route: any) => {
      this.createRoute(route);
    });
    this.routeLayer.addTo(this.map);
    this.map.fitBounds(this.routeLayer.getBounds());
  }
  createRoute(route: any) {
    let geoJson = geoJSON(route, { style: this.routeStyle() });
    geoJson.addTo(this.routeLayer);
    this.map.fitBounds(geoJson.getBounds());
  }

  routeStyle() {
    return {
      color: 'black',
      weight: 6,
    };
  }

  getDriverLocation(rideId: number) {
    this.locationService
      .getDriverLocationByRideId(rideId)
      .subscribe((locationDto: any) => {
        this.driverId = locationDto.driverId;
        this.driverMarker = marker(
          [locationDto.longitude, locationDto.latitude],
          {
            icon: icon({
              iconUrl: 'assets/taxi.svg',
              iconSize: [20, 20],
              iconAnchor: [10, 10],
            }),
          }
        );
        this.driverMarker.addTo(this.markerLayer);
        this.markerLayer.addTo(this.map);
        this.map.fitBounds(this.markerLayer.getBounds());
      });
  }
  openGlobalSocket() {
    this.stompClient.subscribe(
      '/location-updates/update-driver-location',
      (message: { body: string }) => {
        let locationDto = JSON.parse(message.body);
        if (locationDto.driverId === this.driverId) {
          console.log(locationDto);
          this.driverMarker.setLatLng([
            locationDto.longitude,
            locationDto.latitude,
          ]);

          this.driverMarker.update();
          this.map.fitBounds(this.markerLayer.getBounds());
        }
      }
    );
    this.stompClient.subscribe('/ride/arrived', (message: { body: string }) => {
      let rideId = JSON.parse(message.body);
      if (rideId === this.rideId) {
        this.snackbar.openSuccessSnackBar('Driver has arrived.');
      }
    });
    this.stompClient.subscribe(
      '/ride/rejected',
      (message: { body: string }) => {
        let dto = JSON.parse(message.body);
        if (this.rideId === dto.rideId) {
          this.snackbar.openFailureSnackBar('Ride was rejected.');
          let route = '/client';
          this.router.navigate([route]);
          console.log(route);
        }
      }
    );
    this.stompClient.subscribe('/ride/aborted', (message: { body: string }) => {
      let dto = JSON.parse(message.body);
      if (this.rideId === dto.rideId) {
        this.snackbar.openFailureSnackBar('Ride was aborted.');
        let route = '/client';
        this.router.navigate([route]);
        console.log(route);
      }
    });
    this.stompClient.subscribe(
      '/ride/finished',
      (message: { body: string }) => {
        console.log(message.body);
        let dto = JSON.parse(message.body);
        if (this.rideId === dto.rideId) {
          this.snackbar.openSuccessSnackBar('Ride finished.');
          this.modal.open(ReviewRideModalComponent, {
            data: dto.rideId,
          });
        }
      }
    );
  }
}
