export class FavoriteRouteDTO {
  name: string;
  stops: Array<string>;

  routeGeoJson: Array<string>;

  customer: string;

  distance: number;

  estimatedTime: number;

  estimatedCost: number;
  startCoordinates: number[];
  endCoordinates: number[];

  constructor(
    stops: Array<string>,
    routeGeoJson: Array<string>,
    customer: string,
    distance: number,
    estimatedTime: number,
    estimatedCost: number,
    startCoordinates: number[],
    endCoordinates: number[]
  ) {
    this.name = stops[0] + stops[stops.length - 1];
    this.stops = stops;
    this.routeGeoJson = routeGeoJson;
    this.customer = customer;
    this.distance = distance;
    this.estimatedTime = estimatedTime;

    this.estimatedCost = estimatedCost;
    this.startCoordinates = startCoordinates;
    this.endCoordinates = endCoordinates;
  }
}
