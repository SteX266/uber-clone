import { Component, OnInit } from '@angular/core';
import { latLng, LayerGroup, tileLayer, Map, icon, marker } from 'leaflet';
import { MapPoint } from '../../models/map-point.model';

@Component({
  selector: 'app-map-small',
  templateUrl: './map-small.component.html',
  styleUrls: ['./map-small.component.scss'],
})
export class MapSmallComponent implements OnInit {
  ngOnInit(): void {
    this.initializeStart();
    this.initializeMapOptions();
  }

  map!: Map;
  geoJsonLayer: any;
  start!: MapPoint;
  destionation!: MapPoint;
  options = {
    layers: [
      tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        maxZoom: 18,
        attribution: '...',
      }),
    ],
    zoom: 14,
    center: latLng(45.253434, 19.831323),
  };

  private initializeMapOptions() {
    this.options = {
      zoom: 14,
      layers: [
        tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
          maxZoom: 18,
          attribution: 'OSM',
        }),
      ],
      center: latLng(45.253434, 19.831323),
    };
  }

  initializeMap(map: Map) {
    this.map = map;
  }

  private initializeStart() {
    if (navigator.geolocation)
      navigator.geolocation.getCurrentPosition((position) => {
        if (position) {
          this.start = new MapPoint(
            'Current location',
            position.coords.latitude,
            position.coords.longitude
          );
          this.createMarker(this.start);
        }
      });
  }

  private createMarker(point: MapPoint) {
    let coordinates = latLng([point.latitude, point.longitude]);
    this.geoJsonLayer = marker(coordinates).addTo(this.map);
    this.map.setView(coordinates, this.map.getZoom());
  }
}
