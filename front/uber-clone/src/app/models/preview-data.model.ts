import { MapPoint } from './map-point.model';

export class PreviewData {
  stops: Array<MapPoint>;
  distance: number;
  time: number;
  price: number;
  hasBaby: boolean;
  hasPet: boolean;
  customers: Array<string>;
  vehicleType: string;
  routeGeoJson: any;

  constructor(
    stops: Array<MapPoint>,
    distanceInKm: number,
    estimatedTimeInMin: number,
    priceInTokens: number,
    hasBaby: boolean,
    hasPet: boolean,
    people: Array<string>,
    vehicleType: string,
    routeGeoJson: any
  ) {
    this.stops = stops;
    this.distance = distanceInKm;
    this.time = estimatedTimeInMin;
    this.price = priceInTokens;
    this.hasBaby = hasBaby;
    this.hasPet = hasPet;
    this.customers = people;
    this.vehicleType = vehicleType;
    this.routeGeoJson = routeGeoJson;
  }
}
