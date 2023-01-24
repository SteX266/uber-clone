import { Component, OnInit } from '@angular/core';
import {
  latLng,
  LayerGroup,
  tileLayer,
  layerGroup,
  Map,
  icon,
  marker,
  Marker,
  MapOptions,
  TileLayer,
  Icon,
  Polyline,
  polyline,
  control,
  Control,
  LatLng,
  geoJSON,
  LeafletMouseEvent,
} from 'leaflet';
import { MapSearchService } from 'src/app/services/map-search.service';
import { MapPoint } from '../../models/map-point.model';
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
  }

  map!: Map;
  startLayer: any;
  start!: MapPoint;
  directionsLayer!: LayerGroup;
  end!: MapPoint;
  options!: MapOptions;

  routes!: any;
  selectedRoute!: any;
  geoJsonData!: any;

  private initializeDirectionsLayer() {
    this.directionsLayer = layerGroup();
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
