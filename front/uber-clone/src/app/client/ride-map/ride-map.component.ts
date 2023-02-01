import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import {
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
import * as Stomp from 'stompjs';
@Component({
  selector: 'app-ride-map',
  templateUrl: './ride-map.component.html',
  styleUrls: ['./ride-map.component.scss'],
})
export class RideMapComponent implements OnInit {
  constructor(
    private route: ActivatedRoute,
    private locationService: LocationService,
    private rideService: RideService
  ) {}
  ngOnInit(): void {
    this.initializeMapOptions();
    this.route.params.subscribe((params) => {
      this.rideId = Number(params['id']);
      this.getGeoJsonRoute(this.rideId);
      this.getDriverLocation(this.rideId);
    });
    this.initializeSocket();
  }

  rideId: number = 0;
  stompClient: any;
  driverMarker!: any;
  driverId: number = -1;
  map!: Map;
  routeGeoJson: any[] = [];
  options!: MapOptions;

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
      .subscribe((routeGeoJsonData: any) => {
        this.routeGeoJson = JSON.parse(routeGeoJsonData);
        this.createRoute(this.routeGeoJson);
        console.log(this.routeGeoJson);
      });
  }

  createRoute(route: any) {
    let geoJson = geoJSON(route, { style: this.routeStyle() });
    geoJson.addTo(this.map);
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
        this.driverMarker.addTo(this.map);
      });
  }
  openGlobalSocket() {
    this.stompClient.subscribe(
      '/location-updates/update-driver-location',
      (message: { body: string }) => {
        let locationDto = JSON.parse(message.body);
        if (locationDto.driverId === this.driverId) {
          this.driverMarker.setLatLng([
            locationDto.latitude,
            locationDto.longitude,
          ]);
          this.driverMarker.update();
        }
      }
    );
  }
}
