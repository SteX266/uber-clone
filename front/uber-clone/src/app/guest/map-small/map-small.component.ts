import { Component, OnInit } from '@angular/core';
import {
  latLng,
  LayerGroup,
  tileLayer,
  layerGroup,
  Map,
  icon,
  marker,
  MapOptions,
  Icon,
  geoJSON,
} from 'leaflet';
import { MapSearchService } from 'src/app/services/map-search/map-search.service';
import { MapPoint } from '../../models/map-point.model';

import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';
@Component({
  selector: 'app-map-small',
  templateUrl: './map-small.component.html',
  styleUrls: ['./map-small.component.scss'],
})
export class MapSmallComponent implements OnInit {
  constructor(private mapService: MapSearchService) {}
  ngOnInit(): void {
    this.initializeDirectionsLayer();
    this.initializeMapOptions();
    this.initializeWebSocketConnection();
  }

  map!: Map;
  startLayer: any;
  start!: MapPoint;
  directionsLayer!: LayerGroup;
  driversLayer: LayerGroup = new LayerGroup();

  end!: MapPoint;
  options!: MapOptions;

  routes!: any;
  selectedRoute!: any;
  geoJsonData!: any;

  private stompClient: any;

  drivers: any = {};

  private initializeDirectionsLayer() {
    this.directionsLayer = layerGroup();
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

  openGlobalSocket() {
    this.stompClient.subscribe(
      '/location-updates/update-driver-location',
      (message: { body: string }) => {
        console.log(message.body);
        let locationDto = JSON.parse(message.body);
        let existingDriver = this.drivers[locationDto.driverId];
        existingDriver.setLatLng([locationDto.longitude, locationDto.latitude]);
        existingDriver.update();
      }
    );
    this.stompClient.subscribe(
      '/location-updates/new-driver',
      (message: { body: string }) => {
        console.log(message.body);
        let locationDto = JSON.parse(message.body);
        let driverMarker = marker(
          [locationDto.longitude, locationDto.latitude],
          {
            icon: icon({
              iconUrl: 'assets/car.png',
              iconSize: [35, 45],
              iconAnchor: [18, 45],
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
        console.log(message.body);
        let driverId = JSON.parse(message.body);
        delete this.drivers[driverId];
      }
    );
  }

  private initializeMapOptions() {
    this.options = {
      zoom: 14,
      layers: [
        tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
          maxZoom: 18,
          attribution: 'OSM',
        }),
        this.directionsLayer,
      ],
      center: latLng(45.253434, 19.831323),
    };
  }

  initializeMap(map: Map) {
    this.map = map;
  }

  drawMarkers() {
    this.clearMap();
    if (!this.start || !this.end) return;
    const startCoords = latLng([this.start.latitude, this.start.longitude]);
    const destCoords = latLng([this.end.latitude, this.end.longitude]);

    var startPoint = marker(startCoords, this.defaultIcon());
    var destPoint = marker(destCoords, this.defaultIcon());
    var geoJson = geoJSON();
    var selectedRoute = geoJSON();
    this.routes.forEach((route: any) => {
      if (
        route.properties.summary.distance ===
        this.selectedRoute.properties.summary.distance
      ) {
        selectedRoute = geoJSON(route, { style: this.selectedRouteStyle() });
        geoJson.addData(route);
      } else geoJson.addData(route);
    });

    this.directionsLayer = layerGroup([
      startPoint,
      destPoint,
      geoJson,
      selectedRoute,
    ]).addTo(this.map);
    this.map.fitBounds(geoJson.getBounds());
  }

  selectedRouteStyle() {
    return {
      color: 'red',
      weight: 5,
    };
  }

  selectRoute(index: number) {
    this.selectedRoute = this.routes[index];
    this.drawMarkers();
  }

  defaultRouteStyle() {
    return {
      color: 'blue',
      opacity: 0.8,
    };
  }

  defaultIcon() {
    return {
      icon: icon({
        ...Icon.Default.prototype.options,
        iconUrl: 'assets/marker-icon.png',
        iconRetinaUrl: 'assets/marker-icon-2x.png',
        shadowUrl: 'assets/marker-shadow.png',
      }),
    };
  }

  refreshDirections(points: Array<MapPoint>) {
    this.start = points[0];
    this.end = points[1];
    this.mapService
      .directionsWithAlternatives(this.start, this.end)
      .subscribe((geoJsonData: any) => {
        console.log(geoJsonData.features[0]);
        this.routes = geoJsonData.features;
        this.selectedRoute = this.routes[0];
        this.geoJsonData = geoJsonData;
        this.drawMarkers();
      });
  }

  clearMap() {
    if (this.map.hasLayer(this.directionsLayer))
      this.map.removeLayer(this.directionsLayer);
  }
}
